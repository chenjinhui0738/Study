package com.cjh.thymeleaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/thymeleaf")
public class ThymeleafController {
    @RequestMapping("/test")
    public String test(Model model){
        model.addAttribute("msg","Thymeleaf模板测试");
        return "test";
    }
}
