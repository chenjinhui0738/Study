package com.cjh.SsoApplicationOne.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Order(101)
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)//开启权限校验
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {
}

