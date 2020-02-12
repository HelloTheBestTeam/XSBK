package com.xsbk.auth.controller;

import java.util.UUID;

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

import com.alibaba.druid.util.StringUtils;
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

/**
 * 认证服务
 * @author chrilwe
 *
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
	
	//支付宝第三方登录二维码拼接引导地址
	@Value("${alipay.authUrl}")
	private String alipayAuthUrl;
	
	@Autowired
	private AlipayClient alipayClient;
	
	@Autowired
	private AlipayProperties alipayProperties;
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
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
	 * 微信登录
	 * @return
	 */
	@PostMapping("/wechatlogin")
	public LoginResult wechatlogin() {
		
		return null;
	}
	
	
	/**
	 * 支付宝登录
	 * 生成登录二维码和标识当前客户端的唯一state
	 * @return
	 */
	@GetMapping("/alipaylogin/authUrl")
	public ApplyAlipayLoginUrlResult alipaylogin() {
		//拼接登录地址
		//对redirect_uri进行编码
		String uriEncode = UriEncoder.encode(alipayProperties.getRedirect_uri());
		
		//生成客户端唯一标识
		String state = UUID.randomUUID().toString();
		
		//将登录状态改为登录中
		LoginStatus ls = new LoginStatus();
		ls.setState(state);
		ls.setStatus(Status.LOGIN_WAIT);
		stringRedisTemplate.boundValueOps(state).set(JSON.toJSONString(ls));
		
		String authUrl = alipayProperties.getAuthUrl() + "?app_id=" + alipayProperties.getAppid() + "&scope=auth_user" + "&redirect_uri=" + uriEncode + "&state=" + state;
		
		return new ApplyAlipayLoginUrlResult(Code.SUCCESS, Msg.SUCCESS, true, authUrl, state);
	}
	
	
	/**
	 * 支付宝登录状态
	 * @return
	 */
	@GetMapping("/alipaylogin/status")
	public LoginStatus alipayLoginStatus(@RequestParam("state")String state) {
		if(StringUtils.isEmpty(state)) {
			return null;
		}
		String string = stringRedisTemplate.boundValueOps(state).get();
		if(StringUtils.isEmpty(string)) {
			return null;
		}
		LoginStatus ls = JSON.parseObject(string,LoginStatus.class);
		return ls;
	}
	
}
