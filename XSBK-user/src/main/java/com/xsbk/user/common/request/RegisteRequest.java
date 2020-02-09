package com.xsbk.user.common.request;

import com.xsbk.core.request.Request;

import lombok.Data;
/**
 * 
 * @author Chrilwe
 *
 */
@Data
public class RegisteRequest extends Request {
	//账号
	private String account;
	//密码
	private String password;
	//昵称
	private String nickName;
	//手机号
	private String phoneNo;
	//邮箱号
	private String email;
}
