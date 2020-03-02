package com.xsbk.core.model.chat;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

/**
 * 聊天群
 * @author chrilwe
 *
 */
@Data
@ToString
public class ChatGroup {
	private int id;
	//群聊昵称
	private String name;
	//群聊账号
	private String groupAccount;
	//群聊人数限制
	private int maxPeople;
	//当前群聊人数
	private int peoples;
	//创建时间
	private Date createTime;
}
