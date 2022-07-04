package com.cjh.scanlogin.controller;

import cn.hutool.core.codec.Base64;
import com.alibaba.fastjson.JSONObject;
import com.cjh.scanlogin.enums.QrCodeEnmu;
import com.cjh.scanlogin.enums.ResponseCode;
import com.cjh.scanlogin.model.LoginDTO;
import com.cjh.scanlogin.model.ResponseResult;
import com.cjh.scanlogin.service.JwtTokenService;
import com.cjh.scanlogin.service.SystemService;
import com.cjh.scanlogin.util.RandomUtils;
import com.cjh.scanlogin.util.VerifyCodeUtils;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@CrossOrigin//处理跨域请求的注解
@RequestMapping("/login")
public class LoginController {
    private Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenService jwtTokenService;
    @Autowired
    private SystemService systemService;

    /**
     * 获取token
     *
     * @param loginDTO
     * @return
     */
    @RequestMapping("/token")
    public ResponseResult getToken(@RequestBody LoginDTO loginDTO) {
        String userName = loginDTO.getUserName();
        String password = loginDTO.getPassword();
        String verifyCode = loginDTO.getVerifyCode();
        String verifyCodeKey = loginDTO.getVerifyCodeKey();
        //验证码是否还存在
        if (!redisTemplate.hasKey(verifyCodeKey)) {
            return new ResponseResult(ResponseCode.IVALID_VERIFY_CODE);
        }
        //验证码是否正确
        String code = (String) redisTemplate.opsForValue().get(verifyCodeKey);
        if (code.equals(verifyCode)) {
            //验证成功删除验证码
            redisTemplate.delete(verifyCodeKey);
        } else {
            return new ResponseResult(ResponseCode.VERIFY_CODE_ERROR);
        }

        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            UserDetails userDetails = (UserDetails) authenticate.getPrincipal();
            String token = jwtTokenService.generateToken(userDetails, RandomUtils.getRandomString(6));
            Map<String, Object> resultMap = Maps.newHashMap();
            resultMap.put("user", userDetails);
            resultMap.put("token", token);
            return new ResponseResult(ResponseCode.SUCCESS, resultMap);
        } catch (UsernameNotFoundException e) {
            logger.info("用户认证失败：" + "userName wasn't in the system");
            return new ResponseResult(ResponseCode.USERNAME_ERROR);
        } catch (LockedException lae) {
            logger.info("用户认证失败：" + "account for that username is locked, can't login");
            return new ResponseResult(ResponseCode.ACCOUNT_LOCKED);
        } catch (AuthenticationException ace) {
            logger.info("用户认证失败：" + ace);
            ace.printStackTrace();
            return new ResponseResult(ResponseCode.USER_INFO_ERROR);
        }
    }

    /**
     * 获取扫一扫的二维码内容
     * 1、二维码内容的有效时间是5分钟，生成的code是唯一码，存在redis缓存中，value里面带有该code的登陆状态
     *
     * @param jsonObject
     * @return
     */
    @PostMapping(value = "/getQrcodeContent")
    public ResponseResult getQrcodeContent(@RequestBody JSONObject jsonObject) {
        String oldQrcodeMark = jsonObject.containsKey("qrcodeMark") ? jsonObject.getString("qrcodeMark") : "";
        //如果页面有旧的二维码，同时请求新的二维码内容，则直接删除旧内容
        if (!"".equals(oldQrcodeMark)) {
            if (redisTemplate.hasKey(oldQrcodeMark)) {
                redisTemplate.delete(oldQrcodeMark);
            }
        }
        //生成二维码唯一标识并存储扫描状态
        String qrcodeMark = "qrcode_mark_" + Base64.encode(UUID.randomUUID().toString());
        redisTemplate.opsForValue().set(qrcodeMark, QrCodeEnmu.notscan.toString());
        redisTemplate.expire(qrcodeMark, 120, TimeUnit.SECONDS);
        return new ResponseResult(ResponseCode.SUCCESS, qrcodeMark);
    }

    /**
     * 检测是否有登录动作
     *
     * @param jsonObject
     * @param httpRequest
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/qrcodeCheckLogin")
    public ResponseResult qrcodeCheckLogin(@RequestBody JSONObject jsonObject, HttpServletRequest httpRequest) throws Exception {
        String qrcodeMark = jsonObject.containsKey("qrcodeMark") ? jsonObject.getString("qrcodeMark") : "";
        //验证码无效
        if ("".equals(qrcodeMark)) {
            return new ResponseResult(ResponseCode.IVALID_QRCODE);
        }
        //二维码失效
        if (!redisTemplate.hasKey(qrcodeMark)) {
            return new ResponseResult(ResponseCode.QRCODE_EXPIRED);
        } else {
            //二维码未超时
            String status = redisTemplate.opsForValue().get(qrcodeMark).toString();
            if (QrCodeEnmu.notscan.toString().equals(status)) {
                //未扫码
                return new ResponseResult(ResponseCode.NOT_SCAN);
            } else if (QrCodeEnmu.scaned.toString().equals(status)) {
                //已扫码
                return new ResponseResult(ResponseCode.SCAN_SUCCESS);
            } else if (QrCodeEnmu.cancel.toString().equals(status)) {
                //已取消登录
                return new ResponseResult(ResponseCode.CANCEL_SUCCESS);
            } else if (QrCodeEnmu.login.toString().equals(status)) {
                //确认登录
                return new ResponseResult(ResponseCode.CONFIRM_SUCCESS);
            }
        }

        return new ResponseResult();
    }

    /**
     * 生成验证码图片
     *
     * @param jsonObject
     * @return
     */
    @PostMapping(value = "/getVerifyCode")
    public ResponseResult getVerifyCode(@RequestBody JSONObject jsonObject) {
        Map<Object, Object> resultMap = Maps.newHashMap();
        try {
            String code = VerifyCodeUtils.getRandKey(4);
            String key = VerifyCodeUtils.VERIFY_CODE + RandomUtils.uuid();
            String img = VerifyCodeUtils.verifyCode(90, 25, code);
            resultMap.put("img", img);
            resultMap.put("verifyCodeKey", key);
            //将验证码存入redis，1分钟有效
            redisTemplate.opsForValue().set(key, code);
            redisTemplate.expire(key, 60, TimeUnit.SECONDS);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseResult(ResponseCode.SUCCESS, resultMap);
    }

    /**
     * 扫码动作
     *
     * @param jsonObject
     * @return
     */
    @PostMapping("/scan")
    public ResponseResult scan(@RequestBody JSONObject jsonObject, HttpServletRequest httpRequest) {
        //APP扫描到的内容信息
        String qrcodeMark = jsonObject.containsKey("qrcodeMark") ? jsonObject.getString("qrcodeMark") : "";
        String token = httpRequest.getHeader("Authorization");

        if (Objects.nonNull(qrcodeMark)) {//扫码登录
            //获取uuid，校验redis中是否存在该标识
            if (!redisTemplate.hasKey(qrcodeMark)) {
                //如果不存在该KEY，表示二维码已经失效
                return new ResponseResult(ResponseCode.QRCODE_EXPIRED);
            } else {
                //更新二维码状态值
                redisTemplate.opsForValue().set(qrcodeMark, QrCodeEnmu.scaned.toString());
                redisTemplate.expire(qrcodeMark, 5 * 60, TimeUnit.SECONDS);
                //绑定token和用户信息
                redisTemplate.opsForValue().set(token, qrcodeMark);
                redisTemplate.expire(token, 5 * 60, TimeUnit.SECONDS);
                return new ResponseResult(ResponseCode.SUCCESS);
            }
        } else {
            return new ResponseResult(ResponseCode.IVALID_QRCODE);
        }
    }

    /**
     * 扫码登录：确认/取消登录
     *  @param jsonObject
     *  @return 
     */
    @PostMapping("/scanLogin")
    public ResponseResult scanLogin(@RequestBody JSONObject jsonObject, HttpServletRequest httpRequest) {
        String token = httpRequest.getHeader("Authorization");
        String userName = jsonObject.containsKey("userName") ? jsonObject.getString("userName") : "";
        String qrcodeMark = jsonObject.containsKey("qrcodeMark") ? jsonObject.getString("qrcodeMark") : "";
        //type:login-确认登录，cancel-取消登录
        String type = jsonObject.containsKey("type") ? jsonObject.getString("type") : "";
        //token和登录类型缺一不可
        if ("".equals(token) || "".equals(type)) {
            return new ResponseResult(ResponseCode.IVALID_ERROR);
        }

        //根据token获取用户信息
        String username = jwtTokenService.getUsernameFromToken(token);
        //根据token获取绑定的uuid，并校验是否已失效
        if (!redisTemplate.hasKey(token)) {
            return new ResponseResult(ResponseCode.GET_MESSAGE_FAILED);
        } else {
            if (QrCodeEnmu.login.toString().equals(type)) {
                //确认登入
                //修改二维码状态
                redisTemplate.opsForValue().set(qrcodeMark, QrCodeEnmu.login.toString());
                redisTemplate.expire("user_auth_token_" + userName, 60 * 60 * 24L, TimeUnit.SECONDS);
                redisTemplate.expire("user_auth_info_" + userName, 60 * 60 * 24L, TimeUnit.SECONDS);
                return ResponseResult.success();
            } else if (QrCodeEnmu.cancel.toString().equals(type)) {
                //取消登录
                //修改二维码状态
                redisTemplate.opsForValue().set(qrcodeMark, QrCodeEnmu.cancel.toString());
                return ResponseResult.success();
            }
        }
        return new ResponseResult();
    }
}
