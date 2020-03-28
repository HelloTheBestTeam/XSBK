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
	private String password1;
	private String password2;
	//昵称
	private String nickName;
	//手机号
	private String phoneNo;
	//邮箱号
	private String email;
	//验证码
	private String code;
}
