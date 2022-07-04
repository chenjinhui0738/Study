package com.cjh.scanlogin.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cjh.scanlogin.mapper.SysUserMapper;
import com.cjh.scanlogin.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemService {
    @Autowired
    private SysUserMapper sysUserMapper;

    public SysUser getUserByLoginName(String loginName) {
        LambdaQueryWrapper<SysUser> query = new LambdaQueryWrapper<SysUser>();
        query.eq(SysUser::getLoginName, loginName);
        List<SysUser> userList = sysUserMapper.selectList(query);

        if (userList.size() == 0) {
            return null;
        }

        return userList.get(0);
    }
}
