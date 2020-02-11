package com.xsbk.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xsbk.auth.common.response.LoginResult;

/**
 * 认证服务
 * @author chrilwe
 *
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
	
	
	/**
	 * 账号密码登录
	 * @return
	 */
	@PostMapping("/login")
	public LoginResult login() {
		
		return null;
	}
	
	/**
	 * 手机验证码登录
	 * @return
	 */
	@PostMapping("/phonelogin")
	public LoginResult phonelogin() {
		
		return null;
	}
	
	/**
	 * 微信登录
	 * @return
	 */
	@PostMapping("/wechatlogin")
	public LoginResult wechatlogin() {
		
		return null;
	}
}
