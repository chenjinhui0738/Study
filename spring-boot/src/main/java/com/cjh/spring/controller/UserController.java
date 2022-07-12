package com.cjh.spring.controller;

import com.cjh.spring.bean.User;
import com.cjh.spring.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取应用上下文ApplicationContext两种方式
 */
@RestController
@RequestMapping("/user")
@Qualifier
public class UserController {
    @GetMapping("/getUser1")
    public User getUser1(HttpServletRequest request) {
        //直接通过方法中的HttpServletRequest对象
        ApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
        UserService userService = (UserService) applicationContext.getBean(UserService.class);

        return userService.getUser2();
    }

    @GetMapping("/getUser2")
    public Object getUser2() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();//手动获取request对象
        ApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
        UserService userService = (UserService) applicationContext.getBean(UserService.class);
        return userService.getUser2();
    }
}
