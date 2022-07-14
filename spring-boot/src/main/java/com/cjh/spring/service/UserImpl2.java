package com.cjh.spring.service;

import com.cjh.spring.bean.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary//设置注入优先级
public class UserImpl2 implements UserInterface {
    @Override
    public User getUser() {
        return new User(2, "李四");
    }
}
