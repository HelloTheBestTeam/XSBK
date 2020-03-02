package com.xsbk.gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.xsbk.gateway.model.Properties;

@Configuration
public class Config {
	
	@Bean
	@ConfigurationProperties(prefix="gateway")
	public Properties properties() {
		return new Properties();
	}
}
