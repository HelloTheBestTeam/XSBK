package com.xsbk.user.common.response;

import com.xsbk.core.model.user.ext.UserExt;
import com.xsbk.core.response.Result;

import lombok.Data;
import lombok.ToString;

/**
 * 登录返回
 * @author Chrilwe
 *
 */
@ToString
@Data
public class LoginResult extends Result {
	
	private UserExt userExt;
	
	public LoginResult(int code, String msg, boolean isSuccess, UserExt userExt) {
		super(code, msg, isSuccess);
		this.userExt = userExt;
	}

}
