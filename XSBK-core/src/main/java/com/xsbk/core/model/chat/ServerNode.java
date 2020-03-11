package com.xsbk.core.model.chat;

import lombok.Data;
import lombok.ToString;

/**
 * 服务器节点信息
 * @author chrilwe
 *
 */
@Data
@ToString
public class ServerNode {
	private String serverId;
	private String serverIp;
	private String applicationName;
}
