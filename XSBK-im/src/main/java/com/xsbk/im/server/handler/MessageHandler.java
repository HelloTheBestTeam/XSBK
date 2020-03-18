package com.xsbk.im.server.handler;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

import com.alibaba.fastjson.JSON;
import com.xsbk.core.model.chat.ChatMessage;
import com.xsbk.im.common.base.ChatType;
import com.xsbk.im.common.model.ClientConectDetail;
import com.xsbk.im.common.model.ServerDetail;
import com.xsbk.im.common.thread.CustomerExecutors;
import com.xsbk.im.redis.RedisTemplate;
import com.xsbk.im.server.ServerMain;
import com.xsbk.im.zk.ZookeeperClient;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * 消息转发
 * @author chrilwe
 *
 */
public class MessageHandler extends SimpleChannelInboundHandler<ChatMessage>{

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ChatMessage msg) throws Exception {
		System.out.println(msg.getMessage());
		//将消息发送给指定客户端
		String type = msg.getType();
		//点对点聊天
		if(type.equals(ChatType.POINT)) {
			accessPointToPointMsg(ctx, msg);
		} else if(type.equals(ChatType.TOPIC)) {
			accessOneToMoreMsg(msg);
		}
	}
	
	protected void accessPointToPointMsg(ChannelHandlerContext ctx, ChatMessage msg) {
		ClientConectDetail clientConnectDetail = getClientConnectDetail(msg.getReceiverId());
		ServerDetail serverDetail = getServerDetail(clientConnectDetail.getServerId());
		//判断目标连接是否在本机
		String serverName = serverDetail.getServerName();
		if(serverName.equals(ServerMain.config.getServerName())) {
			//在本机查询目标对象的连接通道
			String channelId = clientConnectDetail.getChannelId();
			ChannelId cId = JSON.parseObject(channelId, ChannelId.class);
			Channel channel = AuthHandler.channelGroup.find(cId);
			channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(msg)));
		} else {
			//将消息推送到各个服务器
			pushMessageToOtherServer(msg);
		}
	}
	
	protected void accessOneToMoreMsg(ChatMessage msg) {
		
	}
	
	protected ServerDetail getServerDetail(String serverId) {
		byte[] dataNode = ZookeeperClient.getInstance().getDataNode(ServerMain.PATH_PARENT + serverId);
		ServerDetail serverDetail = JSON.parseObject(new String(dataNode), ServerDetail.class);
		return serverDetail;
	}
	
	protected ClientConectDetail getClientConnectDetail(int receiverId) {
		//获取接受方连接到哪个服务器信息
		String s = RedisTemplate.getInstance().hget(AuthHandler.LOGIN, receiverId + "");
		ClientConectDetail clientConectDetail = JSON.parseObject(s, ClientConectDetail.class);
		return clientConectDetail;
	}
	
	protected void pushMessageToOtherServer(ChatMessage chatMessage) {
		
	}
}
