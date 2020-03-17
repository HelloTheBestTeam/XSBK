package com.xsbk.im.common.model;

import lombok.Data;

/**
 * 配置文件
 * @author chrilwe
 *
 */
@Data
public class Config {
	//服务器真实的ip地址
	private String ip;
	//服务端口
	private int port;
	//服务器名称
	private String serverName;
	//zk服务地址
	private String zkServerAddress;
	//redis服务地址
	private String redisServerAddress;
	//redis username
	private String redisUsername;
	//redis password
	private String redisPassword;
	//令牌过期时间 秒
	private int tokenExpire;
}
