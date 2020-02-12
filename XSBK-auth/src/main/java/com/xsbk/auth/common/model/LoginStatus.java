package com.xsbk.auth.common.model;

import lombok.Data;

/**
 * 登录状态
 * @author chrilwe
 *
 */
@Data
public class LoginStatus {
	//客户端唯一标识
	private String state;
	//登录状态
	private String status;
}
