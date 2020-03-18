package com.xsbk.im.server.handler;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.xsbk.core.model.chat.ChatMessage;
import com.xsbk.core.model.user.ext.UserExt;
import com.xsbk.im.common.base.Status;
import com.xsbk.im.common.model.ClientConectDetail;
import com.xsbk.im.common.model.ServerDetail;
import com.xsbk.im.common.thread.CustomerExecutors;
import com.xsbk.im.redis.RedisTemplate;
import com.xsbk.im.server.ServerMain;
import com.xsbk.im.zk.ZookeeperClient;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * 用户认证
 * @author chrilwe
 *
 */
public class AuthHandler extends MessageToMessageDecoder<TextWebSocketFrame> {
	
	public static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	public static Map<String, String> channelIdAndClientIdMap = new HashMap<String, String>();
	
	//客户端断开与服务器连接
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		//将redis中的登录连接信息移除，将channelGroup的channel移除
		System.out.println("客户端与服务端断开连接");
		boolean contains = channelGroup.contains(ctx.channel());
		if(contains) {
			channelGroup.remove(ctx.channel());
			String accessToken = channelIdAndClientIdMap.get(JSON.toJSONString(ctx.channel().id()));
			RedisTemplate.getInstance().delete(accessToken);
		}
		
	}

	public static final String LOGIN = "LOGIN";
	private static CustomerExecutors executors = CustomerExecutors.getInstance();
	
	/**
	 * 认证以及将消息解析为ChatMessage
	 */
	@Override
	protected void decode(ChannelHandlerContext ctx, TextWebSocketFrame msg, List<Object> out) throws Exception {
		String text = msg.text();
		System.out.println(text);
		//解析
		ChatMessage chatMessage = null;
		try {
			chatMessage = JSON.parseObject(text, ChatMessage.class);
			chatMessage.setStatus(Status.MESSAGE_SENDED);
			chatMessage.setSendTime(new Date());
		} catch (Exception e) {
			e.printStackTrace();
			TextWebSocketFrame res = new TextWebSocketFrame("错误的消息");
			ctx.writeAndFlush(res);
			//关闭连接
			ctx.close();
			return;
		}
		String accessToken = chatMessage.getAccessToken();
		if(StringUtils.isEmpty(accessToken)) {
			TextWebSocketFrame res = new TextWebSocketFrame("未认证");
			ctx.writeAndFlush(res);
			//关闭连接
			ctx.channel().close();
			return;
		}
		refreshToken(accessToken);
		try {
			auth(chatMessage, ctx.channel().id().toString(),ctx);
		} catch (Exception e) {
			e.printStackTrace();
			TextWebSocketFrame res = new TextWebSocketFrame("未认证");
			ctx.writeAndFlush(res);
			return;
		}
		out.add(chatMessage);
	}
	
	protected void refreshToken(String accessToken) {
		RedisTemplate.getInstance().expire(accessToken, ServerMain.config.getTokenExpire());
	}
	
	protected void auth(ChatMessage chatMessage, String channelId, ChannelHandlerContext ctx) {
		// 判断是否已经连接了聊天室
		String jwt = RedisTemplate.getInstance().hget(LOGIN, chatMessage.getAccessToken());
		if(StringUtils.isEmpty(jwt)) {
			// 认证accessToken
			String v = RedisTemplate.getInstance().get(chatMessage.getAccessToken());
			if(StringUtils.isEmpty(v)) {
				throw new RuntimeException("令牌过期");
			}
			
			//获取当前服务器信息
			byte[] dataNode = ZookeeperClient.getInstance().getDataNode(ServerMain.PATH_PARENT + ServerMain.config.getServerName());
			String val = new String(dataNode);
			ServerDetail serverDetail = JSON.parseObject(val, ServerDetail.class);
			
			//获取用户信息
			try {
				UserExt userExt = getUserInfoByAccessToken(chatMessage.getAccessToken());
				//将客户端连接信息保存
				ClientConectDetail clientDetail = new ClientConectDetail();
				clientDetail.setChannelId(channelId);
				clientDetail.setClientId(userExt.getId()+"");
				clientDetail.setConnectTime(new Date());
				clientDetail.setServerId(serverDetail.getServerId());
				saveClientDetailSync(clientDetail);
				channelGroup.add(ctx.channel());
				channelIdAndClientIdMap.put(JSON.toJSONString(ctx.channel().id()), userExt.getId()+"");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("系统异常");
			}
			
		}
	}
	
	protected void saveClientDetailSync(ClientConectDetail clientDetail) {
		ThreadPoolExecutor pool = executors.getExecutors(10, 15, 1000, "saveClientDetailSync", new ArrayBlockingQueue<>(20));
		pool.execute(new Runnable() {
			public void run() {
				RedisTemplate.getInstance().hset(LOGIN, clientDetail.getClientId(), JSON.toJSONString(clientDetail));
			}
		});
	}
	
	protected UserExt getUserInfoByAccessToken(String accessToken) throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<UserExt> res = restTemplate.getForEntity(new URI(ServerMain.config.getUserInfoUrl() + "?accessToken=" + accessToken), UserExt.class);
		UserExt body = res.getBody();
		return body;
	}
	
}
