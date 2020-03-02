package com.xsbk.auth.controller;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.yaml.snakeyaml.util.UriEncoder;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayClient;
import com.xsbk.auth.common.code.Code;
import com.xsbk.auth.common.model.LoginStatus;
import com.xsbk.auth.common.msg.Msg;
import com.xsbk.auth.common.response.ApplyAlipayLoginUrlResult;
import com.xsbk.auth.config.AlipayProperties;
import com.xsbk.auth.service.AlipayLoginService;

/**
 * 
 * @author chrilwe
 *
 */
@RestController
@RequestMapping("/alipay")
public class AlipayLoginController {

	// 支付宝第三方登录二维码拼接引导地址
	@Value("${alipay.authUrl}")
	private String alipayAuthUrl;

	@Autowired
	private AlipayClient alipayClient;

	@Autowired
	private AlipayProperties alipayProperties; 
	
	@Autowired
	private AlipayLoginService alipayLoginService;

	/**
	 * 支付宝登录 生成登录二维码和标识当前客户端的唯一state
	 * 
	 * @return
	 */
	@GetMapping("/login/authUrl")
	public ApplyAlipayLoginUrlResult alipaylogin() {
		// 拼接登录地址
		// 对redirect_uri进行编码
		String uriEncode = UriEncoder.encode(alipayProperties.getRedirect_uri());

		// 生成客户端唯一标识
		String state = UUID.randomUUID().toString();

		// 将登录状态改为登录中
		/*
		 * LoginStatus ls = new LoginStatus(); ls.setState(state);
		 * ls.setStatus(Status.LOGIN_WAIT);
		 * stringRedisTemplate.boundValueOps(state).set(JSON.toJSONString(ls));
		 */

		String authUrl = alipayProperties.getAuthUrl() + "?app_id=" + alipayProperties.getAppid() + "&scope=auth_user"
				+ "&redirect_uri=" + uriEncode + "&state=" + state;

		return new ApplyAlipayLoginUrlResult(Code.SUCCESS, Msg.SUCCESS, true, authUrl, state);
	}
	
	/**
	 * 支付宝登录状态
	 * @return
	 */
	@GetMapping("/alipaylogin/status")
	public LoginStatus alipayLoginStatus(@RequestParam("state")String state) {
		
		return alipayLoginService.alipayLoginStatus(state);
	}
}
