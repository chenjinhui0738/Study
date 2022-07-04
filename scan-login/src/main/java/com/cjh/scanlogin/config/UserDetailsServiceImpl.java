package com.cjh.scanlogin.config;


import com.cjh.scanlogin.factory.AuthUserFactory;
import com.cjh.scanlogin.model.SysUser;
import com.cjh.scanlogin.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 用户通过用户名密码登录时的实现接口，用户获取用户信息
 *
 * @author Guoqing
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    /**
     * 系统服务
     */
    @Autowired
    private SystemService systemService;

    @Override
    public UserDetails loadUserByUsername(String loginName) {
        SysUser user = systemService.getUserByLoginName(loginName);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", loginName));
        } else {
            return AuthUserFactory.create(user);
        }
    }

}
