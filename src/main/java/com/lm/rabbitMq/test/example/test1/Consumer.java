package com.lm.rabbitMq.test.example.test1;

import java.io.IOException;  
import com.rabbitmq.client.AMQP.BasicProperties;  
import com.rabbitmq.client.Channel;  
import com.rabbitmq.client.Connection;  
import com.rabbitmq.client.ConnectionFactory;  
import com.rabbitmq.client.DefaultConsumer;  
import com.rabbitmq.client.Envelope;  
  
public class Consumer {  
    public static void main(String[] args) {  
        // 实例化工厂  
        ConnectionFactory factory = new ConnectionFactory();  
        // 设置相关参数,地址，端口，账号，密码  
        factory.setHost("localhost");  
        factory.setPort(5672);  
        factory.setUsername("guest");  
        factory.setPassword("guest");  
        try {  
            // 获取connection  
            Connection conn = factory.newConnection();  
            // 获取channel  
            Channel channel = conn.createChannel();  
            // 创建队列 1-队列名称 2-队列是否持久化 3-是否是排他队列 4-使用完之后是否删除此队列 5-其他属性  
            channel.queueDeclare("hello", false, false, false, null);  
          //消费消息. 1-消费队列  2-是否自动发送消息回执    3-回调函数  
            channel.basicConsume("hello", true, new DefaultConsumer(channel){  
                  
                @Override  
                public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)  
                        throws IOException {  
                    String message = new String(body, "UTF-8");  
                    System.out.println("接收到的消息为: " + message);  
                }  
                  
            });  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
}  