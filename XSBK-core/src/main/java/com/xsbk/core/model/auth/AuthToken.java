package com.xsbk.core.model.auth;

import lombok.Data;
import lombok.ToString;

/**
 * 
 * @author chrilwe
 *
 */
@Data
@ToString
public class AuthToken {
	public String accessToken;
	public String jwt;
	public String refreshToken;
}
