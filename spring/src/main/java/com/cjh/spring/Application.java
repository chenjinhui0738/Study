package com.cjh.spring;

import com.cjh.spring.bean.User;
import com.cjh.spring.service.UserInterface;
import com.cjh.spring.service.UserService;
import com.cjh.spring.utils.SpringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        UserService userService = SpringUtil.getBean(UserService.class);
        userService.getUser(new UserInterface() {
            @Override
            public User getUser() {
                return null;
            }
        });
    }

}
