package com.cjh.spring.controller;

import com.cjh.spring.bean.CustomizeProperties;
import com.cjh.spring.bean.MyProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @Autowired
    private MyProperties myProperties;//注入自定义属性bean
    @Autowired
    private CustomizeProperties customizeProperties;//注入自定义属性文件bean

    @RequestMapping("/")
    public String index() {
        //获取自定义属性
        System.out.println(myProperties.getName() + "-" + myProperties.getTitle());
        System.out.println(customizeProperties.getName() + "-" + customizeProperties.getAge());
        return "index";
    }
}
