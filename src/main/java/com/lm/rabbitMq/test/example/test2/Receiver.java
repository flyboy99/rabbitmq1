package com.lm.rabbitMq.test.example.test2;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;  
import com.rabbitmq.client.AMQP.BasicProperties;

public class Receiver {
	 private final static String QUEUE_NAME = "myQueue1";
	  
	  public static void main(String[] args) {
		  receive();
	}
	  public static void receive(){
		  ConnectionFactory factory = null;
		  Connection connection = null;
		  Channel channel = null;
		  try{
			  factory = new ConnectionFactory();
			  factory.setHost("localhost");
			  factory.setPort(5672);
			  factory.setUsername("guest");
			  factory.setPassword("guest");
			  connection = factory.newConnection();
			  channel = connection.createChannel();
			  channel.queueDeclare(QUEUE_NAME, false, false, false, null);
			  Consumer consumer = new DefaultConsumer(channel){
				 @Override
				 public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties,  
	                        byte[] body) throws IOException {  
					 System.out.println("consumer ...");
					 String msg = new String(body,"UTF-8");
					 System.out.println("收到的消息: "  + msg);
				 }
			  };
			  channel.basicConsume(QUEUE_NAME, true,consumer);
		  }catch(Exception e){
			  e.printStackTrace();
		  }finally{
			  try{
				  channel.close();
				  connection.close();
			  }catch(Exception e){
				  e.printStackTrace();
			  }
		  }
	  }
}
