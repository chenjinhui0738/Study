package com.cjh.easyexcel.service;

import com.cjh.easyexcel.vo.ResultVO;
import com.cjh.easyexcel.vo.SysSystemVO;

import javax.servlet.http.HttpServletResponse;

public interface ExportExcelService {
    ResultVO<Void> exportLessThan20w(SysSystemVO sysSystemVO, HttpServletResponse response) throws Exception;
    ResultVO<Void> exportLessThan100w(SysSystemVO sysSystemVO, HttpServletResponse response) throws Exception;
    ResultVO<Void> exportMoreThan100w(SysSystemVO sysSystemVO, HttpServletResponse response) throws Exception;
}
