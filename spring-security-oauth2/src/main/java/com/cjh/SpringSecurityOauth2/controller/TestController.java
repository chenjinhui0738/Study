package com.cjh.SpringSecurityOauth2.controller;

import com.cjh.SpringSecurityOauth2.service.RedisCodeService;
import com.cjh.SpringSecurityOauth2.validateCode.smsCode.SmsCode;
import com.cjh.SpringSecurityOauth2.validateCode.smsCode.SmsCodeUtil;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

@RestController
public class TestController {
    @Autowired
    private RedisCodeService redisCodeService;

    /*@GetMapping("index")
    public Object index(Authentication authentication){
        return authentication;
    }*/
    @GetMapping("/code/sms")
    public void createSmsCode(HttpServletRequest request, HttpServletResponse response, String mobile) throws Exception {
        SmsCode smsCode = SmsCodeUtil.createSMSCode();
        redisCodeService.save(smsCode, new ServletWebRequest(request), mobile);
        // 输出验证码到控制台代替短信发送服务
        System.out.println("手机号" + mobile + "的登录验证码为：" + smsCode.getCode() + "，有效时间为120秒");
    }
    @GetMapping("index")
    public Object index(@AuthenticationPrincipal Authentication authentication, HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        String token = StringUtils.substringAfter(header, "bearer ");

        return Jwts.parser().setSigningKey("test_key".getBytes(StandardCharsets.UTF_8)).parseClaimsJws(token).getBody();
    }

}
