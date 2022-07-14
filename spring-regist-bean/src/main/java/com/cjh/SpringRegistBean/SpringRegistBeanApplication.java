package com.cjh.SpringRegistBean;

import com.cjh.SpringRegistBean.demo.config.WebConfig;
import com.cjh.SpringRegistBean.demo.domain.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

@SpringBootApplication
public class SpringRegistBeanApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringRegistBeanApplication.class, args);
        // 返回 IOC 容器，使用注解配置，传入配置类
        ApplicationContext context = new AnnotationConfigApplicationContext(WebConfig.class);
        User user = context.getBean(User.class);
        //打印bean
        System.out.println(user);
        // 查看 User 这个类在 Spring 容器中叫啥玩意
        String[] beanNames = context.getBeanNamesForType(User.class);
        Arrays.stream(beanNames).forEach(System.out::println);
    }

}
