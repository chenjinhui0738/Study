package com.cjh.easyexcel.dao;

import com.cjh.easyexcel.vo.SysSystemVO;

import java.util.List;

public interface SysSystemMapper {
    Integer selectCountSysSystemVOList(SysSystemVO sysSystemVO);

    List<SysSystemVO> selectSysSystemVOList(SysSystemVO sysSystemVO);
}
