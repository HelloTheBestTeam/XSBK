package com.xsbk.balancer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xsbk.balancer.service.BalancerService;

/**
 * 
 * @author chriler
 *
 */
@RestController
@RequestMapping("/balance")
public class BalancerController {
	
	@Autowired
	private BalancerService balancerService;
	
	
	/**
	 * 获取真实netty服务器ip地址
	 * @return
	 */
	@GetMapping("/getServer")
	public String getServer() {
		String server = "";
		try {
			server = balancerService.getServer();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return server;
	}
}
