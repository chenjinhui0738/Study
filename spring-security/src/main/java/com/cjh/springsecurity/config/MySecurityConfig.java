package com.cjh.springsecurity.config;

import com.cjh.springsecurity.handler.MyAuthenticationAccessDeniedHandler;
import com.cjh.springsecurity.handler.MyAuthenticationFailureHandler;
import com.cjh.springsecurity.handler.MyAuthenticationSucessHandler;
import com.cjh.springsecurity.logout.MyLogOutSuccessHandler;
import com.cjh.springsecurity.session.MySessionExpiredStrategy;
import com.cjh.springsecurity.validateCode.imageCode.ImageCodeFilter;
import com.cjh.springsecurity.validateCode.smsCode.SmsAuthenticationConfig;
import com.cjh.springsecurity.validateCode.smsCode.SmsCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)//开启权限控制
public class MySecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyAuthenticationSucessHandler authenticationSucessHandler;
    @Autowired
    private MyAuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    private ImageCodeFilter imageCodeFilter;
    @Autowired
    private SmsCodeFilter smsCodeFilter;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private UserDetailServiceImpl userDetailServiceImpl;
    @Autowired
    private SmsAuthenticationConfig smsAuthenticationConfig;
    @Autowired
    private MySessionExpiredStrategy sessionExpiredStrategy;
    @Autowired
    private MyLogOutSuccessHandler logOutSuccessHandler;
    @Autowired
    private MyAuthenticationAccessDeniedHandler authenticationAccessDeniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling()
            .accessDeniedHandler(authenticationAccessDeniedHandler)//添加权限拒绝处理器
            .and()
            .addFilterBefore(imageCodeFilter, UsernamePasswordAuthenticationFilter.class) // 在密码校验过滤器前添加验证码校验过滤器
            .addFilterBefore(smsCodeFilter, UsernamePasswordAuthenticationFilter.class) // 添加短信验证码校验过滤器
            .formLogin()// 表单方式
            //http.httpBasic()// HTTP Basic方式
            //.loginPage("/login.html")//设置自定义登录页
            .loginPage("/authentication/require")/// 登录跳转 URL
            .loginProcessingUrl("/login")//处理表单登录 URL
            .successHandler(authenticationSucessHandler)// 处理登录成功
            .failureHandler(authenticationFailureHandler) // 处理登录失败
            .and()
            .rememberMe()//记住我功能
            .tokenRepository(persistentTokenRepository()) // 配置 token 持久化仓库
            .tokenValiditySeconds(3600) // remember 过期时间，单为秒
            .userDetailsService(userDetailServiceImpl) // 处理自动登录逻辑
            .and()
            .authorizeRequests()// 授权配置
            .antMatchers("/code/sms","/code/image","/authentication/require","/login.html","/session/invalid","/logout/success").permitAll()//登录跳转 URL 无需认证
            .anyRequest()// 所有请求
            .authenticated()// 都需要认证
            .and()
            .sessionManagement() // 添加 Session管理器
            .invalidSessionUrl("/session/invalid") // Session失效后跳转到这个链接
            .maximumSessions(1)//控制一个账号同一时刻最多能登录多少个
            .expiredSessionStrategy(sessionExpiredStrategy)//session过期策略处理，Session并发控制只对Spring Security默认的登录方式——账号密码登录有效，而像短信验证码登录，社交账号登录并不生效
            .maxSessionsPreventsLogin(true)//达到最大数后，禁止此账号在其他地方登录
            .and()
            .and()
            .logout()
            .logoutUrl("/signout")
            //.logoutSuccessUrl("/signout/success")//成功登出后跳转的地址
            .logoutSuccessHandler(logOutSuccessHandler)
            .deleteCookies("JSESSIONID")
            .and().csrf().disable()//关闭CSRF攻击防御
            .apply(smsAuthenticationConfig);// 将短信验证码认证配置加到 Spring Security 中

    }

    /**
     * 设置密码加密方式
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 注入token持久化对象
     * @return
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        jdbcTokenRepository.setCreateTableOnStartup(false);//这里手动复制出来创建表
        return jdbcTokenRepository;
    }
}
