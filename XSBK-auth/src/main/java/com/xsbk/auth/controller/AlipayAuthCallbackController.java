package com.xsbk.auth.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.xsbk.auth.client.UserClient;
import com.xsbk.auth.common.model.LoginStatus;
import com.xsbk.auth.common.model.Status;
import com.xsbk.auth.common.msg.Msg;
import com.xsbk.core.model.user.User;

/**
 * 支付宝授权回调
 * @author chrilwe
 *
 */
@RestController
@RequestMapping("/alipay_auth/callback")
public class AlipayAuthCallbackController {
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@Autowired
	private AlipayClient alipayClient;
	
	@Autowired
	private UserClient userClient;
	
	@GetMapping("/auth")
	public String authCallback(@RequestParam("auth_code")String auth_code,@RequestParam("state")String state) {
		if(StringUtils.isAnyEmpty(auth_code,state)) {
			return Msg.FAIL;
		}
		
		//将登录状态改为扫码成功
		String v = stringRedisTemplate.boundValueOps(state).get();
		if(StringUtils.isEmpty(v)) {
			throw new RuntimeException(Msg.FAIL);
		}
		LoginStatus ls = JSON.parseObject(v, LoginStatus.class);
		if(ls.getStatus().equals(Status.LOGIN_WAIT)) {
			ls.setStatus(Status.SAOMA_SUCCESS);
		}
		stringRedisTemplate.boundValueOps(state).set(JSON.toJSONString(ls));
		
		//根据auth_code 获取token
		AlipaySystemOauthTokenResponse res = this.getAuthToken(auth_code);
		if(res.isSuccess()) {
			String accessToken = res.getAccessToken();
			String userId = res.getUserId();
			
			//查询之前是否已经用过第三方登录
			User user = userClient.getUserByAuthUid(userId);
			if(user != null) {
				//生成jwt令牌
				
				//修改登录状态为登录成功
			}
		}
		
		return Msg.SUCCESS;
	}
	
	private AlipaySystemOauthTokenResponse getAuthToken(String auth_code) {
		AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
		request.setCode(auth_code);
		request.setGrantType("authorization_code");
		AlipaySystemOauthTokenResponse res = null;
		try {
			res = alipayClient.execute(request);
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		return res;
	}
}
