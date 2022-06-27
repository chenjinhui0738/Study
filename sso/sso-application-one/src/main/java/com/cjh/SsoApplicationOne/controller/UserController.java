package com.cjh.SsoApplicationOne.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserController {
    /**
     *获取用户信息
     * @param principal
     * @return
     */
    @GetMapping("user")
    public Principal user(Principal principal) {
        return principal;
    }

    /**
     * 添加权限
     * @return
     */
    @GetMapping("auth/test1")
    @PreAuthorize("hasAuthority('user:add')")
    public String authTest1(){
        return "您拥有'user:add'权限";
    }

    /**
     * 更新权限
     * @return
     */
    @GetMapping("auth/test2")
    @PreAuthorize("hasAuthority('user:update')")
    public String authTest2(){
        return "您拥有'user:update'权限";
    }
}