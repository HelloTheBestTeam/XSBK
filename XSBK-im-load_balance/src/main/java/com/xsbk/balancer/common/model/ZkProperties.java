package com.xsbk.balancer.common.model;

import lombok.Data;

/**
 * zookeeper配置
 * @author chrilwe
 *
 */
@Data
public class ZkProperties {
	private String serverAddress;
	private int sessionTime;
}
