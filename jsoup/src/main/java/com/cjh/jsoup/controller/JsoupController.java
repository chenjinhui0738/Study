package com.cjh.jsoup.controller;

import com.cjh.jsoup.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class JsoupController {
    /**
     * 提交表单测试xss脚本是否能注入
     * @param user
     * @return
     */
    @RequestMapping("/addUser")
    @ResponseBody
    public String addUser(User user) {
        System.out.println(user);
        return user.getRemark();
    }
}
