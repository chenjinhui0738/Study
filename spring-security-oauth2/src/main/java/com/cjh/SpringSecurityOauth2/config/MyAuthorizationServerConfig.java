package com.cjh.SpringSecurityOauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 认证服务配置
 */
@Configuration
@EnableAuthorizationServer//开启认证服务
public class    MyAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Resource
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailServiceImpl userDetailService;
    @Autowired
    private TokenStore jwtTokenStore;
    /*@Autowired
    private TokenStore redisTokenStore;*/
    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;
    @Autowired
    private TokenEnhancer tokenEnhancer;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    /*@Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.authenticationManager(authenticationManager)
                 .tokenStore(redisTokenStore);//设置token存储到redis,这里必选将JWTokenConfig中的TokenStore注入去掉，spring是单例只允许注入一个
    }*/
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        //添加token增强器
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> enhancers = new ArrayList<>();
        enhancers.add(tokenEnhancer);
        enhancers.add(jwtAccessTokenConverter);
        enhancerChain.setTokenEnhancers(enhancers);
        //设置token
        endpoints.authenticationManager(authenticationManager)
                 .tokenStore(jwtTokenStore)//默认的token存储方式，存储在内存中
                 .accessTokenConverter(jwtAccessTokenConverter)//使用自定义生成token,在JWTokenConfig配置
                 .tokenEnhancer(enhancerChain)//添加token增强器
                 .userDetailsService(userDetailService);
    }

    /**
     * 1.定义两个client_id，及客户端可以通过不同的client_id来获取不同的令牌；
     * 2.client_id为test1的令牌有效时间为3600秒，client_id为test2的令牌有效时间为7200秒；
     * 3.client_id为test1的refresh_token（下面会介绍到）有效时间为864000秒，即10天，也就是说在这10天内都可以通过refresh_token来换取新的令牌；
     * 4.在获取client_id为test1的令牌的时候，scope只能指定为all，a，b或c中的某个值，否则将获取失败；
     * 5.只能通过密码模式(password)来获取client_id为test1的令牌，而test2则无限制。
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
               .withClient("test1")
               .secret(new BCryptPasswordEncoder().encode("test1111"))
               .accessTokenValiditySeconds(3600)//token有效期
               .refreshTokenValiditySeconds(864000)//token刷新的有效期
               .authorizedGrantTypes("password", "refresh_token")//授权模式，即设置获取令牌的方式，
               .scopes("all", "a", "b", "c")
               .authorizedGrantTypes("password")
               .and()
               .withClient("test2")
               .secret(new BCryptPasswordEncoder().encode("test2222"))
               .accessTokenValiditySeconds(7200);
    }
}
