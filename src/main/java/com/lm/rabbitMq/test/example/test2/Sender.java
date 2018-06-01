package com.lm.rabbitMq.test.example.test2;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Sender {
  private final static String QUEUE_NAME = "myQueue1";
  
  public static void main(String[] args) {
	  send();
}
  
  public static void send(){
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
		  // 1 队列名 2 是否持久化 3 是否排他队列 4 使用后是否删除队列 5 其他信息
		  channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		  String msg = "hello rabbit mq!";
		  //1 路由名称 2 队列名称 3 其他信息 4 发送的内容
		  channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
		  System.out.println("生产方发送的消息： " + msg);
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
