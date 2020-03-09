package com.xsbk.auth.service;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.github.andrewoma.dexx.collection.Map;
import com.xsbk.auth.client.UserClient;
import com.xsbk.auth.common.util.ParseJwtToUser;
import com.xsbk.auth.config.AuthProperties;
import com.xsbk.core.model.auth.AuthToken;
import com.xsbk.core.model.user.ext.UserExt;
import com.xsbk.core.service.ServiceNameList;

/**
 * 
 * @author chrilwe
 *
 */
@Service
public class AuthService {

	private static final String OAUTH_URI = "/oauth/token";
	private static final String GRANT_TYPE = "grant_type";
	private static final String USER_NAME = "username";
	private static final String PASSWORD = "password";
	private static final String AUTHORIZATION = "Authorization";

	@Autowired
	private LoadBalancerClient loadBalancerClient;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private AuthProperties authProperties;
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	// 账号密码登录
	public AuthToken login(String username, String password) {
		String url = getApplayOauthTokenAddress();
		
		AuthToken authToken = null;
		try {
			authToken = applayToken(username, password, url);
		} catch (Exception e) {
			return null;
		}
		
		saveAuthToken(authToken);
		return authToken;
	}
	
	// 拼接申请令牌地址
	protected String getApplayOauthTokenAddress() {
		ServiceInstance serviceInstance = loadBalancerClient.choose(ServiceNameList.XSBK_AUTH);
		URI uri = serviceInstance.getUri();
		String applayOauthTokenAddress = uri + OAUTH_URI;
		return applayOauthTokenAddress;
	}
	
	//申请令牌
	protected AuthToken applayToken(String username, String password, String url) {
		LinkedMultiValueMap<String, String> header = new LinkedMultiValueMap<String, String>();
		LinkedMultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
		
		header.add(AUTHORIZATION, "Basic " + new String(Base64Utils.encode((authProperties.getClientId() + ":" + authProperties.getClientSecret()).getBytes())));
		body.add(USER_NAME, username);
		body.add(PASSWORD, password);
		
		AuthToken authToken = execute(header, body, url);
		
		return authToken;
	}
	
	//发送申请令牌请求
	protected AuthToken execute(LinkedMultiValueMap<String, String> header, LinkedMultiValueMap<String, String> body, String url) {
		setRestTemplateErrorHandler();
		HttpEntity httpEntity = new HttpEntity(header, body);
		ResponseEntity<Map> res = restTemplate.exchange(url, HttpMethod.POST, httpEntity, Map.class);
		Map b = res.getBody();
		String jwt = (String) b.get("access_token");
		String refreshToken = (String) b.get("refresh_token");
		String accessToken = (String)b.get("jti");
		AuthToken authToken = new AuthToken();
		authToken.setAccessToken(accessToken);
		authToken.setJwt(jwt);
		authToken.setRefreshToken(refreshToken);
		return authToken;
	}
	
	//400和401正常返回，不抛异常
	protected void setRestTemplateErrorHandler() {
		restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
			public void handleError(ClientHttpResponse response) throws IOException {
				HttpStatus statusCode = response.getStatusCode();
				int value = statusCode.value();
				if(value != 400 || value != 401) {
					super.handleError(response);
				}
			}
		});
	}
	
	//保存令牌信息到redis
	protected void saveAuthToken(AuthToken authToken) {
		stringRedisTemplate.opsForValue()
				.set(authToken.getAccessToken(), JSON.toJSONString(authToken), authProperties.getExpire(), TimeUnit.SECONDS);
	}
	
	public UserExt getUserExtByAccessToken(String accessToken) {
		String string = stringRedisTemplate.opsForValue().get(accessToken);
		if(StringUtils.isEmpty(string)) {
			return null;
		}
		AuthToken authToken = JSON.parseObject(string, AuthToken.class);
		String jwt = authToken.getJwt();
		
		// 将jwt令牌解析为authToken
		return ParseJwtToUser.parse(jwt);
	}
}
