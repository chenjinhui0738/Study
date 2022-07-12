package com.cjh.spring.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 这两个对象中，WebApplicationObjectSupport 继承了 ApplicationObjectSupport，所以并无实质的区别。
 */
@Component
public class SpringUtil extends /*WebApplicationObjectSupport*/ ApplicationObjectSupport {
    private static ApplicationContext applicationContext = null;

    public static <T> T getBean(String beanName) {
        return (T) applicationContext.getBean(beanName);
    }

    public static <T> T getBean(Class<T> clazz) {
        return (T) applicationContext.getBean(clazz);
    }

    @PostConstruct
    public void init() {
        applicationContext = super.getApplicationContext();
    }
}
