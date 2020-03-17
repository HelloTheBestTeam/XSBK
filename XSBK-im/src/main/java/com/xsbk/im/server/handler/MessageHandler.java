package com.xsbk.im.server.handler;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

import com.alibaba.fastjson.JSON;
import com.xsbk.core.model.chat.ChatMessage;
import com.xsbk.im.common.base.ChatType;
import com.xsbk.im.common.model.ClientConectDetail;
import com.xsbk.im.common.thread.CustomerExecutors;
import com.xsbk.im.zk.ZookeeperClient;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * 消息处理
 * @author chrilwe
 *
 */
public class MessageHandler extends SimpleChannelInboundHandler<ChatMessage>{
	
	private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	
	//客户端连接到服务端成功回调(保存连接)
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		channelGroup.add(ctx.channel());
		
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ChatMessage msg) throws Exception {
		System.out.println(msg.getMessage());
		serverResponseSync(ctx,msg);
		
		//处理消息
	}
	
	//接收到消息，服务端给客户端一个应答(异步)
	private void serverResponseSync(ChannelHandlerContext ctx, ChatMessage msg) {
		ThreadPoolExecutor executors = CustomerExecutors
										.getInstance()
										.getExecutors(10, 13, 2, "serverResponse", new ArrayBlockingQueue<>(50));
		executors.execute(new Runnable(){
			@Override
			public void run() {
				ChatMessage chat = new ChatMessage();
				chat.setResponse(true);
				chat.setId(msg.getId());
				TextWebSocketFrame ms = new TextWebSocketFrame(JSON.toJSONString(chat));
				ctx.writeAndFlush(ms);
			}	
		});
		
	}
}
