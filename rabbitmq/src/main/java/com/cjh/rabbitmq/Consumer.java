package com.cjh.rabbitmq;

public class Consumer {
    /*public Consumer() {
    }

    public static void main(String[] args) throws Exception {

        //1 创建一个ConnectionFactory, 并进行配置
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        //2 通过连接工厂创建连接
        Connection connection = connectionFactory.newConnection();

        //3 通过connection创建一个Channel
        Channel channel = connection.createChannel();

        //4 声明（创建）一个队列
        String queueName = "test001";
//        参数：队列名称、持久化与否、独占与否、无消息队列是否自动删除、消息参数
//        queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments)
        channel.queueDeclare(queueName, true, false, false, null);

        //5 创建消费者
        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);

        //6 设置Channel
//         参数：队列名称、自动签收、消费者回调
//        basicConsume(String queue, boolean autoAck, Consumer callback)
        channel.basicConsume(queueName, true, queueingConsumer);

        try {
            while(true){
                //7 获取消息（Delivery：传送）
                QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
                String msg = new String(delivery.getBody());
                System.err.println("消费端: " + msg);
                //Envelope envelope = delivery.getEnvelope();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ShutdownSignalException e) {
            e.printStackTrace();
        } catch (ConsumerCancelledException e) {
            e.printStackTrace();
        }

    }*/
}
