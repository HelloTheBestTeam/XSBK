package com.xsbk.balancer.config;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.xsbk.balancer.common.algorithm.impl.RoundRobinWeightBalance;
import com.xsbk.balancer.common.model.ServerProperties;
import com.xsbk.balancer.common.model.WeightModel;
import com.xsbk.balancer.common.model.ZkProperties;
import com.xsbk.balancer.zk.ZkClient;

/**
 * 
 * @author chrilwe
 *
 */
@Configuration
public class Config {
	
	@Autowired
	private ZkProperties zkProperties;
	
	@Autowired
	private ServerProperties serverProperties;
	
	
	@Bean
	public ZkClient zkClient() throws Exception {
		return new ZkClient(zkProperties.getServerAddress());
	}
	
	@Bean
	@ConfigurationProperties(prefix="server")
	public ServerProperties serverProperties() {
		return new ServerProperties();
	}
	
	//轮询
	@Bean
	public RoundRobinWeightBalance balance() {	
		List<WeightModel> serverList = new ArrayList<WeightModel>();
		String serverAddressAndWeight = serverProperties.getServerAddressAndWeight();
		String[] split = serverAddressAndWeight.split(",");
		for (String string : split) {
			String[] split2 = string.split(":");
			WeightModel wm = new WeightModel();
			wm.setServerAddress(split2[0]);
			wm.setVariableWeight(Integer.parseInt(split2[1]));
		}
		return new RoundRobinWeightBalance(serverList);
	}
	
	@Bean
	@ConfigurationProperties(prefix="zk")
	public ZkProperties zkProperties() {
		return new ZkProperties();
	}
}
