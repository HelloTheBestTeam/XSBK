package com.xsbk.core.model.chat;

import lombok.Data;
import lombok.ToString;

/**
 * 用户和群聊关联
 * @author chrilwe
 *
 */
@Data
@ToString
public class UserChatGroup {
	private int id;
	private int chatGroupId;
	private int userId;
}
