package com.xsbk.auth.controller;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.yaml.snakeyaml.util.UriEncoder;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayClient;
import com.xsbk.auth.common.code.Code;
import com.xsbk.auth.common.model.LoginStatus;
import com.xsbk.auth.common.model.Status;
import com.xsbk.auth.common.msg.Msg;
import com.xsbk.auth.common.request.LoginRequest;
import com.xsbk.auth.common.response.ApplyAlipayLoginUrlResult;
import com.xsbk.auth.common.response.LoginResult;
import com.xsbk.auth.config.AlipayProperties;
import com.xsbk.auth.service.AuthService;
import com.xsbk.core.model.auth.AuthToken;
import com.xsbk.core.model.user.ext.UserExt;

/**
 * 认证服务
 * @author chrilwe
 *
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	/**
	 * 账号密码登录
	 * @return
	 */
	@PostMapping("/login")
	public LoginResult login(@RequestBody LoginRequest loginRequest) {
		if(loginRequest == null) {
			return new LoginResult(Code.FAIL,Msg.FAIL,false);
		}
		String password = loginRequest.getPassword();
		String username = loginRequest.getUsername();
		if(StringUtils.isAnyEmpty(username,password)) {
			return new LoginResult(Code.FAIL,Msg.USERNAME_OR_PASSWORD_EMPTY,false);
		}
		
		AuthToken authToken = null;
		try {
			authToken = authService.login(username, password);
		} catch (Exception e) {
			if(e.getMessage().equals(Msg.USERNAME_OR_PASSWORD_EMPTY)) {
				return new LoginResult(Code.FAIL,Msg.USERNAME_OR_PASSWORD_EMPTY,false);
			}
		}
		if(authToken != null) {
			LoginResult res = new LoginResult(Code.SUCCESS, Msg.SUCCESS, true);
			res.setAccessToken(authToken.getAccessToken());
			return res;
		}
		return new LoginResult(Code.FAIL,Msg.FAIL,false);
	}
	
	/**
	 * 手机验证码登录
	 * @return
	 */
	@PostMapping("/phonelogin")
	public LoginResult phonelogin(@RequestBody LoginRequest loginRequest) {
		if(loginRequest == null) {
			return new LoginResult(Code.FAIL,Msg.FAIL,false);
		}
		String phone = loginRequest.getPhone();
		String code = loginRequest.getCode();
		return null;
	}
	
	
	/**
	 * 发送手机验证码 
	 * @param phone
	 * @return
	 */
	@GetMapping("/sendCode")
	public String getPhoneCode(String phone) {
		
		return null;
	}
	
	/**
	 * 根据身份令牌获取用户信息
	 * @return
	 */
	@GetMapping("/getUserByAccessToken")
	public UserExt getUserDetailByAccessToken(@RequestParam("accessToken")String accessToken) {
		if(StringUtils.isEmpty(accessToken)) {
			return null;
		}
		return authService.getUserExtByAccessToken(accessToken);
	}
}
