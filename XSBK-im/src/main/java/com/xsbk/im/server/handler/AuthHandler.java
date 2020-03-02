package com.xsbk.im.server.handler;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.xsbk.core.model.chat.ChatMessage;
import com.xsbk.im.common.base.Status;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * 用户认证
 * @author chrilwe
 *
 */
public class AuthHandler extends MessageToMessageDecoder<TextWebSocketFrame> {
	
	/**
	 * 认证以及将消息解析为ChatMessage
	 */
	@Override
	protected void decode(ChannelHandlerContext ctx, TextWebSocketFrame msg, List<Object> out) throws Exception {
		String text = msg.text();
		System.out.println(text);
		//解析
		ChatMessage chatMessage = JSON.parseObject(text, ChatMessage.class);
		chatMessage.setStatus(Status.MESSAGE_SENDED);
		chatMessage.setSendTime(new Date());
		//TODO 认证
		out.add(chatMessage);
	}
	
}
