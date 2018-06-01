package com.lm.rabbitMq.test.example.test1;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
public class Producer {
	public static void main(String[] args) throws IOException, TimeoutException {	
		//实例化工厂
		ConnectionFactory factory = new ConnectionFactory();
		//设置相关参数,地址，端口，账号，密码
		factory.setHost("localhost");
		factory.setPort(5672);
		factory.setUsername("guest");
		factory.setPassword("guest");
		//获取connection
		Connection conn = factory.newConnection();
		//获取channel
		Channel channel = conn.createChannel();
		//创建队列		1-队列名称	2-队列是否持久化	3-是否是排他队列	4-使用完之后是否删除此队列	5-其他属性
		channel.queueDeclare("hello", false, false, false, null);
		//创建路由		1-路由名称	2-路由类型
		channel.exchangeDeclare("myexchange", "topic");
		//绑定路由队列	1-队列名称	2-路由名称	3-routing key		
		channel.queueBind("hello", "myexchange", "shensha");
		//发送消息		1-路由名称	2-routing key	3-其他信息	4-消息字节数组
		channel.basicPublish("myexchange", "shensha", null, "HelloWorld".getBytes());
		//关闭资源
		channel.close();
		conn.close();
	}
}
