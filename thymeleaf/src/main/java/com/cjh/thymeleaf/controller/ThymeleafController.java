package com.cjh.thymeleaf.controller;

import com.cjh.thymeleaf.entity.Account;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/thymeleaf")
public class ThymeleafController {
    @RequestMapping("/account")
    public String index(Model m) {
        List<Account> list = new ArrayList<Account>();
        list.add(new Account("KangKang", "康康", "e10adc3949ba59abbe56e", "超级管理员", "17777777777"));
        list.add(new Account("Mike", "麦克", "e10adc3949ba59abbe56e", "管理员", "13444444444"));
        list.add(new Account("Jane","简","e10adc3949ba59abbe56e","运维人员","18666666666"));
        list.add(new Account("Maria", "玛利亚", "e10adc3949ba59abbe56e", "清算人员", "19999999999"));
        m.addAttribute("accountList",list);
        m.addAttribute("thText","测试文本");
        m.addAttribute("thUText","<h1>html文本</h1>");
        m.addAttribute("thValue","测试值");
        m.addAttribute("thIf","测试条件值");
        m.addAttribute("account",new Account("KangKang", "康康", "e10adc3949ba59abbe56e", "超级管理员", "17777777777"));
        return "account";
    }
}
