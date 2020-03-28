package com.xsbk.article.config;

import java.io.File;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.xsbk.article.common.model.FdfsProperties;

/**
 * 
 * @author chrilwe
 *
 */
@org.springframework.context.annotation.Configuration
public class Configuration {
	
/*	@Bean
	public StorageClient1 storageClient1() throws Exception {
		initFdfs();
		TrackerClient tc = new TrackerClient();
		TrackerServer trackerServer = tc.getConnection();
		StorageServer storageServer = tc.getStoreStorage(trackerServer);
		StorageClient1 client = new StorageClient1(trackerServer, storageServer);
		return client;
	}*/
	
	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
	
	@Bean
	@ConfigurationProperties(prefix="fdfs")
	public FdfsProperties fdfsProperties() {
		return new FdfsProperties();
	}
	
	@Autowired
	private FdfsProperties fdfsProperties;
	
	protected void initFdfs() throws Exception {
		ClientGlobal.init(fdfsProperties.getTrackerServers());
		ClientGlobal.setG_charset(fdfsProperties.getCharset());
		ClientGlobal.setG_connect_timeout(fdfsProperties.getConnectTime());
	}
}
