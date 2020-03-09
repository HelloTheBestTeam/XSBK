package com.xsbk.balancer.zk;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.KeeperState;

/**
 * zk客户端
 * @author chrilwe
 *
 */
public class ZkClient {
	
	private ZooKeeper zk = null;
	private CountDownLatch cdl = new CountDownLatch(1);
	
	//初始化连接
	public ZkClient(String zkAddress) throws Exception {
		if(StringUtils.isEmpty(zkAddress)) {
			throw new RuntimeException("zookeeper服务器地址为空");
		}
		zk = new ZooKeeper(zkAddress, 1000, new Watcher(){

			@Override
			public void process(WatchedEvent event) {
				if(event.getState() == KeeperState.SyncConnected) {
					cdl.countDown();
				}
			}
			
		});
		cdl.await();
		System.out.println("zookeeper已连接");
	}
	
	//创建节点
	public void createNode(String path, String value) {
		
	}
	
	//删除节点
	public void deleteNode(String path) {
		
	}
	
	//查询节点
	public String findNode(String path) {
		
		return null;
	}
}
