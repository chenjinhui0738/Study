package com.cjh.SpringSecurityOauth2.config;

import com.cjh.SpringSecurityOauth2.handler.MyAuthenticationFailureHandler;
import com.cjh.SpringSecurityOauth2.handler.MyAuthenticationSucessHandler;
import com.cjh.SpringSecurityOauth2.validateCode.smsCode.SmsAuthenticationConfig;
import com.cjh.SpringSecurityOauth2.validateCode.smsCode.SmsCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 资源服务配置
 */
@Configuration
@EnableResourceServer//开启资源服务
public class MyResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Autowired
    private MyAuthenticationSucessHandler authenticationSucessHandler;
    @Autowired
    private MyAuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    private SmsCodeFilter smsCodeFilter;
    @Autowired
    private SmsAuthenticationConfig smsAuthenticationConfig;
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(smsCodeFilter, UsernamePasswordAuthenticationFilter.class) // 添加短信验证码校验过滤器
            .formLogin() // 表单登录
            .loginProcessingUrl("/login") // 处理表单登录 URL
            .successHandler(authenticationSucessHandler) // 处理登录成功
            .failureHandler(authenticationFailureHandler) // 处理登录失败
            .and()
            .authorizeRequests() // 授权配置
            .antMatchers("/code/sms").permitAll()
            .anyRequest()  // 所有请求
            .authenticated() // 都需要认证
            .and()
            .csrf().disable()
            .apply(smsAuthenticationConfig);//短信认证配置
    }
}
