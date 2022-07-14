package com.cjh.spring;

import com.cjh.spring.bean.CustomizeProperties;
import com.cjh.spring.bean.MyProperties;
import com.cjh.spring.bean.User;
import com.cjh.spring.service.UserInterface;
import com.cjh.spring.service.UserService;
import com.cjh.spring.utils.SpringUtil;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Arrays;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})//没有用到数据库连接，但是springboot会自动帮我们注入数据源，但是我们又没有配置数据源和驱动就会报错：启动注解中加：exclude={DataSourceAutoConfiguration.class}
@EnableAsync//为了让@Async注解能够生效，还需要在Spring Boot的主程序中配置@EnableAsync
@EnableConfigurationProperties({MyProperties.class, CustomizeProperties.class})//开启自定义属性
public class Application {

    public static void main(String[] args) {
        //SpringApplication app = new SpringApplication(Application.class);
        //app.setBannerMode(Banner.Mode.OFF);//关闭启动动画
        //app.setAddCommandLineProperties(false);//在运行Spring Boot jar文件时，可以使用命令java -jar xxx.jar --server.port=8081来改变端口的值。这条命令等价于我们手动到application.properties中修改,这里可以禁止通过命令行设置属性值
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
