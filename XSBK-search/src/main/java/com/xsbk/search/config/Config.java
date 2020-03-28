package com.xsbk.search.config;

import java.util.List;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.xsbk.search.service.ArticleService;
import com.xsbk.search.service.UserService;
/**
 * 
 * @author chrilwe
 *
 */
@Configuration
public class Config {
	
	@Autowired
	ArticleService articleService;
	
	@Autowired
	UserService userService;
	
	//初始化es连接，并且创建相关索引
	@Bean
	public RestHighLevelClient esClient() {
		RestHighLevelClient client = new RestHighLevelClient(
				RestClient.builder(
						new HttpHost("localhost", 9200, "http")
						));
		
		//article表索引创建
		try {
			boolean createIndex = articleService.createIndex();
			if(createIndex) {
				System.out.println("/xsbk/article 索引创建成功");
			}
		} catch (Exception e) {
			//已经存在忽略
			e.printStackTrace();
		}
		
		//user表索引创建
		try {
			boolean createIndex = userService.createIndex();
			if(createIndex) {
				System.out.println("/xsbk/user 索引创建成功");
			}
		} catch (Exception e) {
			// 已经存在忽略
			e.printStackTrace();
		}
		
		return client;
	}
	
}
