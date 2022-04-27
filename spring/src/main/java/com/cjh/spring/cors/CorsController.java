package com.cjh.spring.cors;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * 3. 使用注解 (局部跨域)
 */
@RestController
//@CrossOrigin(origins = "*")
public class CorsController {
    /**
     * 在方法上使用注解 @CrossOrigin
     * @return
     */
    //@CrossOrigin(origins = "*")
    //@CrossOrigin(value = "http://localhost:8081") //指定具体ip允许跨域
    @RequestMapping("/test")
    @ResponseBody
    public String test(String name) {
        return name;
    }

    /**
     * 4. 手动设置响应头(局部跨域)
     * @param response
     * @return
     */
    @RequestMapping("/index")
    public String index(HttpServletResponse response) {
        response.addHeader("Access-Allow-Control-Origin","*");
        return "index";
    }
}
