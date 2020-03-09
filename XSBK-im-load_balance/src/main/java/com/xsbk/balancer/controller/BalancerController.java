package com.xsbk.balancer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author chriler
 *
 */
@RestController
@RequestMapping("/balance")
public class BalancerController {
	
	
	/**
	 * 获取真实netty服务器ip地址
	 * @return
	 */
	@GetMapping("/getServer")
	public String getServer() {
		
		return null;
	}
}
