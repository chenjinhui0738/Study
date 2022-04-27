package com.cjh.spring;

import com.cjh.spring.bean.User;
import com.cjh.spring.service.UserInterface;
import com.cjh.spring.service.UserService;
import com.cjh.spring.utils.SpringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

//没有用到数据库连接，但是springboot会自动帮我们注入数据源，但是我们又没有配置数据源和驱动就会报错：启动注解中加：exclude={DataSourceAutoConfiguration.class}
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
//为了让@Async注解能够生效，还需要在Spring Boot的主程序中配置@EnableAsync
@EnableAsync
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
