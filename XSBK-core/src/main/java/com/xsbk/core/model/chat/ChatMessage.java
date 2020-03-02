package com.xsbk.core.model.chat;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

/**
 * 聊天消息模型
 * @author chrilwe
 *
 */
@Data
@ToString
public class ChatMessage {
	private String id;
	//消息发送方
	private int senderId;
	//消息接收方
	private int receiverId;
	//消息内容
	private String message;
	//身份令牌
	private String accessToken;
	//发送消息时间
	private Date sendTime;
	//群发/私聊 
	private String type;
	//发送到聊天群id
	private int groupId;
	//消息状态：已读 2、已发 1发送中 0
	private int status;
	//服务端是否应答
	private boolean response;
}
