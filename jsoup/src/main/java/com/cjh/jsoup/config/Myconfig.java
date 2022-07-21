package com.cjh.jsoup.config;

import com.cjh.jsoup.filter.XssFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
@Configuration
public class Myconfig {
    @Bean
    public FilterRegistrationBean xssFilterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new XssFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.setEnabled(true);
        filterRegistrationBean.addUrlPatterns("/*");
        Map<String, String> initParameters = new HashMap<String, String>();
        initParameters.put("excludes", "/favicon.ico,/img/*,/js/*,/css/*");//excludes用于配置不需要参数过滤的请求url
        initParameters.put("isIncludeRichText", "true");//默认为true，主要用于设置富文本（项目内约束以content为名或以WithHtml结尾）内容是否需要过滤，该选项可根据公司具体情况调整，建议约束富文本编辑框支持的标签并开启改约束，减少安全隐患
        filterRegistrationBean.setInitParameters(initParameters);
        return filterRegistrationBean;
    }
}
