package com.xsbk.auth.common.response;

import com.xsbk.core.model.user.ext.UserExt;
import com.xsbk.core.response.Result;

import lombok.Data;
import lombok.ToString;

/**
 * 
 * @author chrilwe
 *
 */
@ToString
@Data
public class LoginResult extends Result{
	
	private String accessToken;
	
	public LoginResult(int code, String msg, boolean isSuccess) {
		super(code, msg, isSuccess);
	}
	 
}
