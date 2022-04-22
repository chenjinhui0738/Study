package com.cjh.openoffice.controller;

import com.cjh.openoffice.service.OpenOfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

/**首先需安装openoffice
 * 在C:\Program Files (x86)\OpenOffice 4\program  目录下执行命令
 * soffice -headless -accept=“socket,host=127.0.0.1,port=8100;urp;” -nofirststartwizard
 * 启动openoffice服务
 * 使用postman请求数据，将返回的数据保存为pdf格式
 */
@Controller
@RequestMapping(value = "/openoffice")
public class OpenOfficeController {
    @Autowired
    private OpenOfficeService openOfficeService;
    @PostMapping("/onlinePreview")
    public void onlinePreview(@RequestParam("url") String url, HttpServletResponse response) throws Exception{
        openOfficeService.onlinePreview(url,response);
    }
}
