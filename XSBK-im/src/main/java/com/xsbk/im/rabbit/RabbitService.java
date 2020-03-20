package com.xsbk.im.rabbit;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;
import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.BuiltinExchangeType;
import com.xsbk.core.model.chat.ChatMessage;
import com.xsbk.im.common.model.Config;
import com.xsbk.im.server.ServerMain;
import com.xsbk.im.server.handler.AuthHandler;

import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * 消息服务
 * @author chrilwe
 *
 */
public class RabbitService {
	
	protected Channel channel;
	
	private static class Singleton {
		private static RabbitService rs = null;
		static {
			rs = new RabbitService();
		}
		private static RabbitService getInstance() {
			return rs;
		}
	}
	
	private RabbitService() {
		try {
			createChannel();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void pushMessage(String msg) {
		try {
			channel.basicPublish("exchange", ServerMain.config.getServerName(), null, msg.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void receiveMessage() throws IOException {
		channel.basicConsume(ServerMain.config.getServerName(), new DefaultConsumer(channel) {
			
			//监听到消息，将消息发送给目标对象
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				String msg = new String(body);
				System.out.println(msg);
				ChatMessage chatMessage = JSON.parseObject(msg, ChatMessage.class);
				//获取目标对象与服务器的连接实例
				int receiverId = chatMessage.getReceiverId();
				String channelId = AuthHandler.channelIdAndClientIdMap.get(receiverId+"");
				io.netty.channel.Channel c = JSON.parseObject(channelId, io.netty.channel.Channel.class);
				//将消息发送给客户端
				c.writeAndFlush(new TextWebSocketFrame(msg));
			}
			
		});
	}
	
	public static RabbitService getInstance() {
		return Singleton.getInstance();
	}
	
	protected void createChannel() throws IOException, TimeoutException {
		Config config = ServerMain.config;
		ConnectionFactory factory = new ConnectionFactory();
		factory.setUsername(config.getRabbit_username());
		factory.setPassword(config.getRabbit_password());
		factory.setVirtualHost(config.getVirtualHost());
		factory.setHost(config.getRabbit_host());
		factory.setPort(Integer.parseInt(config.getRabbit_port()));
		Connection c = factory.newConnection();
		Channel channel = c.createChannel();
		channel.exchangeDeclare("exchange", BuiltinExchangeType.TOPIC);
		channel.queueDeclare(config.getServerName(), true, false, false, null);
		channel.queueBind(config.getServerName(), "im", config.getServerName());
		this.channel = channel;
	}
}
