package com.cjh.onlinePreview.controller;

import com.cjh.onlinePreview.service.OnlinePreviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;


/**
 * 1.OpenOffice
 * 首先需安装openoffice
 * windows在C:\Program Files (x86)\OpenOffice 4\program  目录下执行命令启动openoffice服务:soffice -headless -accept=“socket,host=127.0.0.1,port=8100;urp;” -nofirststartwizard
 * 使用postman请求数据，将返回的数据保存为pdf格式
 * 缺点：必须自行引入jodconverter-2.2.2.jar版本，否则不支持docx文件,doc、docx文件预览都与实际效果有差异
 *
 *2.LibreOffice
 * 首先需安装LibreOffice
 *
 * 缺点：doc文件预览与实际效果有差异,需要使用docx格式
 */
@Controller
public class OnlinePreviewController {
    @Autowired
    private OnlinePreviewService onlinePreviewService;

    @PostMapping("/onlinePreview")
    public void onlinePreview(@RequestParam("url") String url, HttpServletResponse response) throws Exception {
        onlinePreviewService.onlinePreview(url, response);
    }
}
