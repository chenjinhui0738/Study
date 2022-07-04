package com.cjh.spring.service;

import com.cjh.spring.bean.Person;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * 当application.properties配置了interface.user的值为test1时可以注入
 */
@Component
@ConditionalOnProperty(name = "interface.user", havingValue = "test1")
public interface PersonInterface {
    Person getPerson();
}
