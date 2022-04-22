package com.cjh.easyexcel.controller;

import com.cjh.easyexcel.service.ExportExcelService;
import com.cjh.easyexcel.vo.SysSystemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/easyexcel")
public class ExportExcelController {
    @Autowired
    private ExportExcelService exportExcelService;


    @RequestMapping(value = "/exportLessThan20w")
    public void exportLessThan20w(HttpServletResponse response) throws Exception {
        SysSystemVO sysSystemVO = new SysSystemVO();
        sysSystemVO.setOffset(0);
        sysSystemVO.setLimit(200000);
        exportExcelService.exportLessThan20w(sysSystemVO,response);
    }
    @RequestMapping(value = "/exportLessThan100w")
    public void exportLessThan100w(HttpServletResponse response) throws Exception {
        SysSystemVO sysSystemVO = new SysSystemVO();
        sysSystemVO.setOffset(0);
        sysSystemVO.setLimit(1000000);
        exportExcelService.exportLessThan100w(sysSystemVO,response);
    }
    @RequestMapping(value = "/exportMoreThan100w")
    public void exportMoreThan100w(HttpServletResponse response) throws Exception {
        SysSystemVO sysSystemVO = new SysSystemVO();
        sysSystemVO.setOffset(0);
        sysSystemVO.setLimit(5000000);
        exportExcelService.exportMoreThan100w(sysSystemVO,response);
    }

}
