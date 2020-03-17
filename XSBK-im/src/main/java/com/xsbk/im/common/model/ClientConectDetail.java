package com.xsbk.im.common.model;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

/**
 * 客户端连接详细信息
 * @author chrilwe
 *
 */
@Data
@ToString
public class ClientConectDetail {
	//客户端id
	private String clientId;
	//连接到服务器的id
	private String serverId;
	//连接通道id
	private String channelId;
	//连接时间
	private Date connectTime;
}
