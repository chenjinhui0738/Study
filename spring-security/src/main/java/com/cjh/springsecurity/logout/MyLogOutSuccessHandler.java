package com.cjh.springsecurity.logout;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登出处理器
 * 1.使当前的Sesion失效；
 * 2.清除与当前用户关联的RememberMe记录；
 * 3.清空当前的SecurityContext；
 * 4.重定向到登录页。
 */
@Component
public class MyLogOutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.setContentType("application/json;charset=utf-8");
        //String currentSessionId = httpServletRequest.getRequestedSessionId();
        //sessionRegistry.getSessionInformation(currentSessionId).expireNow();
        httpServletResponse.getWriter().write("退出成功，请重新登录");
    }
}
