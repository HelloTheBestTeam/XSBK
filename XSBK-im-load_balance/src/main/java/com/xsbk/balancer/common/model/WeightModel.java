package com.xsbk.balancer.common.model;

import lombok.Data;

/**
 * 
 * @author chrilwe
 *
 */
@Data
public class WeightModel {
	
	//服务器地址
	private String serverAddress;
	//静态权重比
	private int staticWeight;
	//动态权重比
	private int variableWeight;
}
