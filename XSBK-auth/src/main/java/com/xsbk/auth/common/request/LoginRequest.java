package com.xsbk.auth.common.request;

import lombok.Data;

/**
 * 
 * @author chrilwe
 *
 */
@Data
public class LoginRequest {
	private String username;
	private String password;
	private String phone;
	private String code;
}
