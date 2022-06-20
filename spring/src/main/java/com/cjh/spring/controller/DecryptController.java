package com.cjh.spring.controller;

import com.cjh.spring.AES.Decrypt;
import com.cjh.spring.AES.Encrypt;
import com.cjh.spring.bean.RespBean;
import com.cjh.spring.bean.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class DecryptController {
    @GetMapping("/user")
    @Encrypt
    public RespBean getUser() {
        User user = new User(1,"张三");
        return RespBean.ok("ok", user);
    }

    @PostMapping("/user")
    public RespBean addUser(@RequestBody @Decrypt User user) {
        User user1 = new User(1,"张三");
        System.out.println(Objects.toString(user1));
        return RespBean.ok("ok", user);
    }
}
