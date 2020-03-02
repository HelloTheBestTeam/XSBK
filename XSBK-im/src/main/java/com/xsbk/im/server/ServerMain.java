package com.xsbk.im.server;

import com.xsbk.im.common.channel.WsServerChannelInitializer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * socket服务端
 * 
 * @author chrilwe
 *
 */
public class ServerMain {
	public static void main(String[] args) {
		ServerMain main = new ServerMain();
		main.startWsServer(9004);
	}
	
	//启动wssocket服务
	private void startWsServer(int port) {
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workGroup = new NioEventLoopGroup();

		ServerBootstrap sb = new ServerBootstrap();
		sb.group(bossGroup, workGroup)
			.channel(NioServerSocketChannel.class)
			.childHandler(new WsServerChannelInitializer())
			.option(ChannelOption.SO_BACKLOG, 20)
			.childOption(ChannelOption.SO_KEEPALIVE, true);
		
		try {
			ChannelFuture channelFuture = sb.bind(9004).sync();
			channelFuture.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(bossGroup != null)
				bossGroup.shutdownGracefully();
			
			if(workGroup != null)
				workGroup.shutdownGracefully();
		}
	}
	
	//启动socket服务
	private static void startSocketServer(int port){
		
	}
}
