package com.cjh.spring.service;

import com.cjh.spring.bean.User;
import org.springframework.stereotype.Component;

@Component("userImpl1")
public class UserImpl1 implements UserInterface {
    @Override
    public User getUser() {
        return new User(1,"张三");
    }
}
