package com.xsbk.balancer.common.model;

import lombok.Data;

/**
 * 服务端启动信息
 * @author chrilwe
 *
 */
@Data
public class ServerDetail {
	//服务器id
	private String serverId;
	//服务器ip地址
	private String serverIp;
	//服务器名称
	private String serverName;
}
