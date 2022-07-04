package com.cjh.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class XDelayedMessageConfig {

    public static final String DIRECT_QUEUE = "queue.direct";//队列
    public static final String DELAYED_EXCHANGE = "exchange.delayed";//延迟交换机
    public static final String ROUTING_KEY = "routingkey.bind";//绑定的routing-key

    /**
     * 定义队列
     **/
    @Bean
    public Queue directQueue() {
        return new Queue(DIRECT_QUEUE, true);
    }

    /**
     * 定义延迟交换机
     * args:根据该参数进行灵活路由，设置为“direct”，意味着该插件具有与直连交换机具有相同的路由行为，
     * 如果想要不同的路由行为，可以更换现有的交换类型如：“topic”
     * 交换机类型为 x-delayed-message
     **/
    @Bean
    public CustomExchange delayedExchange() {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("x-delayed-type", "direct");
        return new CustomExchange(DELAYED_EXCHANGE, "x-delayed-message", true, false, args);
    }

    /**
     * 队列和延迟交换机绑定
     **/
    @Bean
    public Binding orderBinding() {
        return BindingBuilder.bind(directQueue()).to(delayedExchange()).with(ROUTING_KEY).noargs();
    }

}
