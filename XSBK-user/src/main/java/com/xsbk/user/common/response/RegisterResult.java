package com.xsbk.user.common.response;

import com.xsbk.core.response.Result;

import lombok.Data;
import lombok.ToString;

/**
 * 
 * @author Chrilwe
 *
 */
@ToString
@Data
public class RegisterResult extends Result {
	
	public RegisterResult(int code, String msg, boolean isSuccess) {
		super(code, msg, isSuccess);
	}

}
