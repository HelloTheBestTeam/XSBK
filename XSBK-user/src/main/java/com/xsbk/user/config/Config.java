package com.xsbk.user.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.session.RedisSessionProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.util.StringUtils;

/**
 * 配置
 * @author chrilwe
 *
 */
@Configuration
public class Config {
	
	@Autowired
	private RedissonProperties redissonProperties;
	
	
	@ConfigurationProperties(prefix="redis")
	@Bean
	public RedissonProperties redissonProperties() {
		return new RedissonProperties();
	}
	
	
	/*@Bean
	public RedissonClient redissionClient() {
		org.redisson.config.Config config = config();
		RedissonClient redissonClient = Redisson.create(config);
		return redissonClient;
	}*/
	
	
	/**
	 * 支持单节点和集群配置
	 * @return
	 */
	private org.redisson.config.Config config() {
		String mode = redissonProperties.getMode();
		String clusterAddress = redissonProperties.getClusterAddress();
		String password = redissonProperties.getPassword();
		String singleAddress = redissonProperties.getSingleAddress();
		String username = redissonProperties.getUsername();
		
		org.redisson.config.Config config = new org.redisson.config.Config();
		if(mode.equals("single")) {
			config.useSingleServer().setAddress(singleAddress);
		} else if(mode.equals("cluster")) {
			if(StringUtils.isEmpty(clusterAddress)) {
				throw new RuntimeException("集群地址不能为空");
			}
			String[] split = clusterAddress.split(",");
			ClusterServersConfig cfg = config.useClusterServers().setScanInterval(2000);
			for (String string : split) {
				cfg.addNodeAddress(string);
			}
		}
		return config;
	}
}
