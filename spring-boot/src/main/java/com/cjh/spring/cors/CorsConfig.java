package com.cjh.spring.cors;

/**
 * 2. 重写 WebMvcConfigurer(全局跨域)
 */
/*@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //允许的请求路径
        registry.addMapping("/**")
                //是否发送Cookie
                .allowCredentials(true)
                //放行哪些原始域
                .allowedOrigins("*")
                //允许的请求类型
                .allowedMethods(new String[]{"GET", "POST", "PUT", "DELETE"})
                //允许的请求头参数
                .allowedHeaders("*")
                //暴露的请求头参数，也就是前端能够获得的参数信息
                .exposedHeaders("*");
    }
}*/
