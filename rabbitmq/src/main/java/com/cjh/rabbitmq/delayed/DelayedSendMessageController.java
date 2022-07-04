package com.cjh.rabbitmq.delayed;

import com.cjh.rabbitmq.config.XDelayedMessageConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/delayed")
public class DelayedSendMessageController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/sendManyMessage")
    public String sendManyMessage() {

        send("延迟消息睡10秒", 10000);
        send("延迟消息睡2秒", 2000);
        send("延迟消息睡5秒", 5000);
        return "ok";
    }

    private void send(String msg, Integer delayTime) {
        //将消息携带路由键值
        rabbitTemplate.convertAndSend(
                XDelayedMessageConfig.DELAYED_EXCHANGE,
                XDelayedMessageConfig.ROUTING_KEY,
                msg,
                message -> {
                    message.getMessageProperties().setDelay(delayTime);
                    return message;
                });
    }
}
