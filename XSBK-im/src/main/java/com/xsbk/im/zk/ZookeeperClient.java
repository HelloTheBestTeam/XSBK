package com.xsbk.im.zk;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;

/**
 * zk客户端
 * @author chrilwe
 *
 */
public class ZookeeperClient {
	
	private ZooKeeper zookeeper;
	private CountDownLatch cdl = new CountDownLatch(1);
	
	private static class Singleton {
		private static ZookeeperClient client = null;
		static {
			client = new ZookeeperClient();
		}
		private static ZookeeperClient getInstance() {
			return client;
		}
	}
	
	public static ZookeeperClient getInstance() {
		return Singleton.getInstance();
	}
	
	private ZookeeperClient() {
		try {
			zookeeper = new ZooKeeper("",1000,new Watcher() {
				public void process(WatchedEvent event) {
					if(event.getState() == KeeperState.SyncConnected) {
						cdl.countDown();
					}
				}
			});
			
			System.out.println("已连接到zkServer");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//创建一个节点
	public void createDataNode(String path, String data) {
		try {
			String create = zookeeper.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}
	
	
	//获取节点数据
	public byte[] getDataNode(String path) {
		try {
			byte[] data = zookeeper.getData(path, false, new Stat());
			return data;
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	//删除节点数据
	public void deleteDataNode(String path) {
		try {
			Stat stat = zookeeper.exists(path, false);
			int version = stat.getVersion();
			zookeeper.delete(path, version);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//判断节点是否存在
	public boolean exists(String path) {
		try {
			Stat stat = zookeeper.exists(path, false);
			if(stat != null) {
				return true;
			}
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
