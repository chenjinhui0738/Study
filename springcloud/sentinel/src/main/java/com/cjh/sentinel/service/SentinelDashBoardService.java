package com.cjh.sentinel.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

@Service
public class SentinelDashBoardService {
    //将该方法标识为一个sentinel资源，名称为hello，blockHandler为降级时调用的方法，fallback为错误时调用的方法，两个函数的函数明与参数要与原函数一致，exceptionsToIgnore为不对异常做忽略处理
    @SentinelResource(value="hello",blockHandler = "blockHandler",fallback = "fallback"/*,exceptionsToIgnore = NullPointerException.class*/)
    public String hello(Integer type) {
        if(Objects.equals(type,1)){
            throw new IllegalArgumentException("非法参数");
        }else if(Objects.equals(type,2)){
            throw new NullPointerException("空指针");
        }
        return "hello";
    }

    /**
     * 降级方法
     * @param ex
     * @return
     */
    public String blockHandler(Integer type, BlockException ex) {
        return "请求次数过多";
    }
    /**
     * 发生错误时跳转的方法
     * @return
     */
    public String fallback(Integer type,Throwable throwable) {
        if(Objects.equals(type,1)){
            return "非法参数";
        }else if(Objects.equals(type,2)){
            return "空指针";
        }
        return null;
    }
}
