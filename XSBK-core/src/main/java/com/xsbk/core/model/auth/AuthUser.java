package com.xsbk.core.model.auth;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

/**
 * 
 * @author chrilwe
 *
 */
@Data
@ToString
public class AuthUser {
	private int id;
	private int userId;
	//第三方登录的userId
	private String auth_uid;
	private Date createTime;
}
