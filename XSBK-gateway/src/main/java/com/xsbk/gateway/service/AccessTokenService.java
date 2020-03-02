package com.xsbk.gateway.service;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.netflix.zuul.context.RequestContext;
import com.xsbk.core.model.auth.AuthToken;
import com.xsbk.gateway.model.Properties;

/**
 * 获取身份令牌，将jwt放到请求头
 * @author chrilwe
 *
 */
@Service
public class AccessTokenService {
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@Autowired
	private Properties properties;
	
	public void dealAccessToken(HttpServletRequest request) {
		String requestURI = request.getRequestURI();
		String accessToken = (String) request.getAttribute("accessToken");
		if(accessToken != null) {
			refreshRedisAccessToken(accessToken);
			String string = stringRedisTemplate.opsForValue().get(accessToken);
			AuthToken authToken = JSON.parseObject(string, AuthToken.class);
			String jwt = authToken.getJwt();
			//TODO 将令牌放到请求头
		}
	}
	
	protected void refreshRedisAccessToken(String accessToken) {
		String string = stringRedisTemplate.opsForValue().get(accessToken);
		if(!StringUtils.isEmpty(string)) {
			stringRedisTemplate.opsForValue().set(accessToken, string, properties.getTokenExpire(), TimeUnit.SECONDS);
		}
	}
}
