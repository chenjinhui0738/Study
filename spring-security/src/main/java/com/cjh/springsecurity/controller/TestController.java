package com.cjh.springsecurity.controller;

import com.cjh.springsecurity.validateCode.imageCode.ImageCode;
import com.cjh.springsecurity.validateCode.imageCode.ImageCodeUtil;
import com.cjh.springsecurity.validateCode.smsCode.SmsCode;
import com.cjh.springsecurity.validateCode.smsCode.SmsCodeUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class TestController {
    //图形验证码key
    public final static String SESSION_KEY_SMS_CODE = "SESSION_KEY_SMS_CODE";
    //短信验证码key
    public final static String SESSION_KEY_IMAGE_CODE = "SESSION_KEY_IMAGE_CODE";
    //请求缓存
    private RequestCache requestCache = new HttpSessionRequestCache();
    //重定向策略
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    //session策略
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
    @GetMapping("hello")
    public String hello() {
        return "hello spring security";
    }

    /**
     * 只有拥有admin权限的人才能访问
     * @return
     */
    @GetMapping("/auth/admin")
    @PreAuthorize("hasAuthority('admin')")
    public String authenticationTest() {
        return "您拥有admin权限，可以查看";
    }
    /**
     *登录跳转的url
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @GetMapping("/authentication/require")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest != null) {
            String targetUrl = savedRequest.getRedirectUrl();
            if (StringUtils.endsWithIgnoreCase(targetUrl, ".html"))
                redirectStrategy.sendRedirect(request, response, "/login.html");
        }
        return "访问的资源需要身份认证！";
    }

    /**
     * 登录成功跳转的url
     * 输出权限信息-方式1
     * @return
     */
    /*@GetMapping("index")
    public Object index(){
        return SecurityContextHolder.getContext().getAuthentication();
    }*/

    /**
     * 登录成功跳转的url
     * 输出权限信息-方式2
     * @return
     */
    @GetMapping("index")
    public Object index(Authentication authentication) {
        return authentication;
    }

    /**
     * 获取图形验证码
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping("/code/image")
    public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ImageCode imageCode = ImageCodeUtil.createImageCode();
        //解决BufferedImage无法序列化的问题
        ImageCode codeInRedis = new ImageCode(null,imageCode.getCode(),imageCode.getExpireTime());
        sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY_IMAGE_CODE, codeInRedis);
        ImageIO.write(imageCode.getImage(), "jpeg", response.getOutputStream());
    }

    /**
     * 获取短信验证码
     * @param request
     * @param response
     * @param mobile
     * @throws IOException
     */
    @GetMapping("/code/sms")
    public void createSmsCode(HttpServletRequest request, HttpServletResponse response, String mobile) throws IOException {
        SmsCode smsCode = SmsCodeUtil.createSMSCode();
        sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY_SMS_CODE + mobile, smsCode);
        // 输出验证码到控制台代替短信发送服务
        System.out.println("您的登录验证码为：" + smsCode.getCode() + "，有效时间为60秒");
    }

    /**
     * session失效
     * @return
     */
    @GetMapping("session/invalid")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String sessionInvalid(){
        return "session已失效，请重新认证";
    }

    /**
     * 成功退出跳转地址
     * @return
     */
    @GetMapping("/signout/success")
    public String signout() {
        return "退出成功，请重新登录";
    }
}
