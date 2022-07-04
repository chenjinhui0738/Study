package com.cjh.spring.service;

import com.cjh.spring.bean.Person;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "interface.user", havingValue = "test1")
public class PersonImpl implements PersonInterface {
    @Override
    public Person getPerson() {
        return new Person(1, "张三");
    }
}
