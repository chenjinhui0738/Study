package com.cjh.scanlogin.service;

import com.cjh.scanlogin.model.AuthUser;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@ConfigurationProperties("jwt")
public class JwtTokenService {
    public static final String TOKEN_TYPE_BEARER = "Bearer";

    private String header = "Authorization";

    private String secret = "defaultSecret";

    private Long expiration = 60 * 60 * 24L;

    private String authPath = "auth";

    private String md5Key = "randomKey";

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 生成token（通过用户名和签名时候用的随机数）
     *
     * @param userDetails
     * @param randomKey
     * @return
     */
    public String generateToken(UserDetails userDetails, String randomKey) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(md5Key, randomKey);
        String token = doGenerateToken(claims, userDetails);
        //将生成的token存入redis做唯一性校验
        redisTemplate.opsForValue().set("user_auth_token_" + userDetails.getUsername(), token);
        redisTemplate.expire("user_auth_token_" + userDetails.getUsername(), expiration, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set("user_auth_info_" + userDetails.getUsername(), new Gson().toJson(userDetails));
        redisTemplate.expire("user_auth_info_" + userDetails.getUsername(), expiration, TimeUnit.SECONDS);
        return token;
    }

    /**
     * 生成token
     *
     * @param claims
     * @param userDetails
     * @return
     */
    private String doGenerateToken(Map<String, Object> claims, UserDetails userDetails) {
        final Date createDate = new Date();
        final Date expirationDate = new Date(createDate.getTime() + expiration * 1000);

        return Jwts.builder()
                   .setClaims(claims)
                   .setSubject(userDetails.getUsername())//设置主题
                   .setIssuedAt(createDate)//签发时间
                   .setExpiration(expirationDate)//过期时间
                   .signWith(SignatureAlgorithm.HS512, secret)//签名的加密方式和密钥
                   .compact();
    }

    /**
     * 从token中获取用户名
     *
     * @param token
     * @return
     */
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token).getSubject();
    }

    /**
     * 获取jwt的payload部分
     *
     * @param token
     * @return
     */
    public Claims getClaimFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    /**
     * 获取用户信息
     *
     * @param token
     * @return
     */
    public UserDetails getUserDetails(String token) {
        String userName = getUsernameFromToken(token);
        String user = redisTemplate.opsForValue().get("user_auth_info_" + userName).toString();
        return new Gson().fromJson(user, AuthUser.class);
    }

    /**
     * 验证token是否过期，true为过期，false为没过期
     *
     * @param token
     * @return
     */
    public Boolean isTokenExpired(String token) {
        final Date expriation = getExpirationDateFromToken(token);
        String userName = getUsernameFromToken(token);
        String redisToken = redisTemplate.opsForValue().get("user_auth_token_" + userName).toString();
        return expriation.before(new Date()) && token.equals(redisToken);
    }

    /**
     * 从token获取失效时间
     *
     * @param token
     * @return
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token).getExpiration();
    }
}
