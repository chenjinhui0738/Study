package com.cjh.rabbitmq.config;

/*@Configuration
public class DelayQueueRabbitConfig {
    //Dead-Letter-Exchange，简称DLX。
    public static final String DLX_QUEUE = "queue.dlx";//死信队列
    public static final String DLX_EXCHANGE = "exchange.dlx";//死信交换机
    public static final String DLX_ROUTING_KEY = "routingkey.dlx";//死信队列与死信交换机绑定的routing-key

    public static final String ORDER_QUEUE = "queue.order";//订单的延时队列
    public static final String ORDER_EXCHANGE = "exchange.order";//订单交换机
    public static final String ORDER_ROUTING_KEY = "routingkey.order";//延时队列与订单交换机绑定的routing-key

    *//**
 * 定义死信队列
 * <p>
 * 定义死信交换机
 * <p>
 * 死信队列和死信交换机绑定
 * 设置路由键：routingkey.dlx
 * <p>
 * 订单延时队列
 * 设置队列里的死信转发到的DLX名称
 * 设置死信在转发时携带的 routing-key 名称
 * <p>
 * 订单交换机
 * <p>
 * 把订单队列和订单交换机绑定在一起
 **//*
    @Bean
    public Queue dlxQueue(){
        //durable 是否持久化消息
        return new Queue(DLX_QUEUE,true);
    }

    *//**
 * 定义死信交换机
 **//*
    @Bean
    public DirectExchange dlxExchange(){
        return new DirectExchange(DLX_EXCHANGE, true, false);
    }


    *//**
 * 死信队列和死信交换机绑定
 * 设置路由键：routingkey.dlx
 **//*
    @Bean
    Binding bindingDLX(){
        return BindingBuilder.bind(dlxQueue()).to(dlxExchange()).with(DLX_ROUTING_KEY);
    }


    *//**
 * 订单延时队列
 * 设置队列里的死信转发到的DLX名称
 * 设置死信在转发时携带的 routing-key 名称
 **//*
    @Bean
    public Queue orderQueue() {
        Map<String, Object> params = new HashMap<>();
        params.put("x-dead-letter-exchange", DLX_EXCHANGE);
        params.put("x-dead-letter-routing-key", DLX_ROUTING_KEY);
        //exclusive独占与否
        return new Queue(ORDER_QUEUE, true, false, false, params);
    }

    *//**
 * 订单交换机
 **//*
    @Bean
    public DirectExchange orderExchange() {
        return new DirectExchange(ORDER_EXCHANGE, true, false);
    }

    *//**
 * 把订单队列和订单交换机绑定在一起
 **//*
    @Bean
    public Binding orderBinding() {
        return BindingBuilder.bind(orderQueue()).to(orderExchange()).with(ORDER_ROUTING_KEY);
    }
}*/
