package com.cjh.springRetry.service.impl;

import com.cjh.springRetry.service.RetryService;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalTime;

@Service
public class RetryServiceImpl implements RetryService {
    /**
     * value：抛出指定异常才会重试
     * include：和value一样，默认为空，当exclude也为空时，默认所有异常
     * exclude：指定不处理的异常
     * maxAttempts：最大重试次数，默认3次
     * backoff：重试等待策略，默认使用@Backoff，@Backoff的value默认为1000L，我们设置为2000L；multiplier（指定延迟倍数）默认为0，表示固定暂停1秒后进行重试，如果把multiplier设置为1.5，则第一次重试为2秒，第二次为3秒，第三次为4.5秒。
     */
    @Override
    public void retry(String param) throws Exception {
        System.out.println("调用时间："+LocalTime.now());
        if (StringUtils.isEmpty(param)){
            System.out.println("远程执行，请求异常");
            throw new Exception();
        }
        System.out.println("重试调用成功");
    }

    /**
     * 重试失败之后执行的方法
     * @param e
     * @return
     */
    @Recover
    public void recover(Exception e, String param){
        System.out.println("回调方法执行！！！！");
        //记日志到数据库 或者调用其余的方法
    }
}
