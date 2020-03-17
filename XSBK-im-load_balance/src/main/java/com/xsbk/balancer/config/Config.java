package com.xsbk.balancer.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.xsbk.balancer.common.algorithm.impl.RoundRobinWeightBalance;
import com.xsbk.balancer.common.model.ServerProperties;
import com.xsbk.balancer.common.model.WeightModel;
import com.xsbk.balancer.common.model.ZkProperties;

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
	public ZooKeeper zk() throws Exception {
		final CountDownLatch cdl = new CountDownLatch(1);
		ZooKeeper zk = new ZooKeeper(zkProperties.getServerAddress(), zkProperties.getSessionTime(), new Watcher() {
			public void process(WatchedEvent event) {
				if(event.getState() == KeeperState.SyncConnected) {
					cdl.countDown();
				}
			}
		});
		cdl.await();
		System.out.println("===============已连接zkServer=========");
		return zk;
	}
	
	@Bean
	@ConfigurationProperties(prefix="server")
	public ServerProperties serverProperties() {
		return new ServerProperties();
	}
	
	
	//解析服务器权重
	@Bean(name="serverConfigMap")
	public Map<String, Integer> serverConfigMap() {
		Map<String,Integer> map = new HashMap<String, Integer>();
		String serverAddressAndWeight = serverProperties.getServerAddressAndWeight();
		String[] split = serverAddressAndWeight.split(",");
		for (String string : split) {
			String[] split2 = string.split("_");
			int weight = 0;
			if(split2.length == 1) {
				weight = 1;
			} else {
				weight = Integer.parseInt(split2[1]);
			}
			String address = split2[0];
			
			map.put(address, weight);
		}
		return map;
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
