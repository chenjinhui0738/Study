package com.cjh.spring.controller;


import com.cjh.spring.utils.IpUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class IpController {
    @RequestMapping("/getIp")
    public String getIp(HttpServletRequest request){
        String cityIpString = IpUtils.getCityInfo(request.getRemoteAddr());
        return cityIpString;
    }
}
