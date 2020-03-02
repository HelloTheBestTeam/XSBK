package com.xsbk.user.config;

import lombok.Data;

/**
 * 分布式锁配置
 * @author chrilwe
 *
 */
@Data
public class RedissonProperties {
	private String mode;
	private String clusterAddress;
	private String singleAddress;
	private String username;
	private String password;
}
