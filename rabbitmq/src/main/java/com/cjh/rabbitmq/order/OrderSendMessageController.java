package com.cjh.rabbitmq.order;

/*@Controller
@RequestMapping("/order")
public class OrderSendMessageController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/sendMessage")
    public String sendMessage(){
        //延迟时间10s
        String delayTime = "10000";
        //发送消息
        rabbitTemplate.convertAndSend(DelayQueueRabbitConfig.ORDER_EXCHANGE, DelayQueueRabbitConfig.ORDER_ROUTING_KEY,
                "发送消息！",message->{
                    message.getMessageProperties().setExpiration(delayTime);
                    return message;
                });
        return "ok";
    }
    //发送多条消息
    @GetMapping("/sendManyMessage")
    public String sendManyMessage(){
        send("延迟消息睡10秒",10000+"");
        send("延迟消息睡2秒",2000+"");
        send("延迟消息睡5秒",5000+"");
        return "ok";
    }

    private void send(String msg, String delayTime){
        rabbitTemplate.convertAndSend(DelayQueueRabbitConfig.ORDER_EXCHANGE,
                DelayQueueRabbitConfig.ORDER_ROUTING_KEY,
                msg,message->{
                    message.getMessageProperties().setExpiration(delayTime);
                    return message;
                });
    }
}*/
