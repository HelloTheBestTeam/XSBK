package com.xsbk.auth.config;

import lombok.Data;
import lombok.ToString;

/**
 * 
 * @author chrilwe
 *
 */
@Data
@ToString
public class AuthProperties {
	private String clientId;
	private String clientSecret;
	private int expire;
}
