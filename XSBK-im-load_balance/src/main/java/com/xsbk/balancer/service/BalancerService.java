package com.xsbk.balancer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xsbk.balancer.zk.ZkClient;

/**
 * 
 * @author chrilwe
 *
 */
@Service
public class BalancerService {
	
	@Autowired
	private ZkClient zkClient;
	
	public String getServer() {
		
		return null;
	}
	
	public String getServerListFromZk(String path) {
		return zkClient.findNode(path);
	}
	
}
