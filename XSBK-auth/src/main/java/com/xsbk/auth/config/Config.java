package com.xsbk.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;

/**
 * 
 * @author chrilwe
 *
 */
@Configuration
public class Config {

	@Autowired
	private AlipayProperties alipayProperties;

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@ConfigurationProperties(prefix = "alipay")
	@Bean
	public AlipayProperties alipayProperties() {
		return new AlipayProperties();
	}
	
	@ConfigurationProperties(prefix = "encrypt.key-store")
	@Bean
	public AuthProperties authProperties() {
		return new AuthProperties();
	}
	

	// 初始化支付宝客户端
	@Bean
	public AlipayClient alipayClient() {
		AlipayClient alipayClient = new DefaultAlipayClient(alipayProperties.getUrl(), alipayProperties.getAppid(),
				alipayProperties.getApp_private_key(), alipayProperties.getFormat(), alipayProperties.getCharset(),
				alipayProperties.getAlipay_public_key(), alipayProperties.getSign_type());
		return alipayClient;
	}
}
