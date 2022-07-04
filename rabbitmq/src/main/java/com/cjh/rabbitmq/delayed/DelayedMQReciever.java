package com.cjh.rabbitmq.delayed;

import com.cjh.rabbitmq.config.XDelayedMessageConfig;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = XDelayedMessageConfig.DIRECT_QUEUE)//监听队列名称
public class DelayedMQReciever {


    @RabbitHandler
    public void process(String message) {
        System.out.println("DelayedMQReciever接收到的消息是：" + message);
    }
}