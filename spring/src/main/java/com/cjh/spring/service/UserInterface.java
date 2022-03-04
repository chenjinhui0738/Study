package com.cjh.spring.service;

import com.cjh.spring.bean.User;
import org.springframework.stereotype.Component;

/**
 * 当application.properties配置了interface.user的值为test1时可以注入
 */
@Component
public interface UserInterface {
    User getUser();
}
