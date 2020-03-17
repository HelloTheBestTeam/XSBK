package com.xsbk.im.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.UUID;

import com.alibaba.fastjson.JSON;
import com.xsbk.im.common.channel.WsServerChannelInitializer;
import com.xsbk.im.common.model.Config;
import com.xsbk.im.common.model.ServerDetail;
import com.xsbk.im.redis.RedisTemplate;
import com.xsbk.im.zk.ZookeeperClient;

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
	
	public static Config config;
	public static final String PATH_PARENT = "/REGISTER/";
	
	public static void main(String[] args) throws Exception {
		ServerMain main = new ServerMain();
		//解析配置
		config = main.paraseConfigProperties();
		//想注册中心注册服务器信息
		main.registeServer();
		//启动服务器
		main.startWsServer(config.getPort());
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
			ChannelFuture channelFuture = sb.bind(port).sync();
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
	
	//解析配置文件
	protected Config paraseConfigProperties() throws Exception {
		String configFilePath = this.getClass().getClassLoader().getResource("").getPath() + "config.properties";
		File file = new File(configFilePath);
		FileInputStream input = new FileInputStream(file);
		
		Properties properties = new Properties();
		properties.load(input);
		
		Config config = new Config();
		config.setPort(Integer.parseInt(properties.getProperty("port")));
		config.setRedisServerAddress(properties.getProperty("redisServerAddress"));
		config.setZkServerAddress(properties.getProperty("zkServerAddress"));
		config.setRedisUsername(properties.getProperty("redisUsername"));
		config.setRedisPassword(properties.getProperty("redisPassword"));
		
		return config;
	}
	
	protected void registeServer() {
		while(true) {
			Config config = ServerMain.config;
			try {
				String ip = config.getIp();
				String serverName = config.getServerName();
				String serverId = UUID.randomUUID().toString();
				ServerDetail serverDetail = new ServerDetail();
				serverDetail.setServerId(serverId);
				serverDetail.setServerIp(ip);
				serverDetail.setServerName(serverName);
				ZookeeperClient.getInstance().createDataNode(PATH_PARENT + serverName, JSON.toJSONString(serverDetail));
				break;
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
	}
}
