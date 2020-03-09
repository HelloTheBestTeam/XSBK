package com.xsbk.balancer.common.algorithm.impl;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;

import com.xsbk.balancer.common.algorithm.Balancer;

/**
 * 一致性哈希算法
 * 
 * @author chrilwe
 *
 */
public class ConsistenceBalancer {
	
	private TreeMap<Integer, String> treeMap = new TreeMap<Integer,String>();

	private int hash(String key) {
		final int p = 16777619;
		int hash = (int) 2166136261L;
		for (int i = 0; i < key.length(); i++)
			hash = (hash ^ key.charAt(i)) * p;
		hash += hash << 13;
		hash ^= hash >> 7;
		hash += hash << 3;
		hash ^= hash >> 17;
		hash += hash << 5;

		// 如果算出来的值为负数则取其绝对值
		if (hash < 0)
			hash = Math.abs(hash);
		return hash;
	}
	
	//初始化一个hash环
	public ConsistenceBalancer(List<String> serverList) {
		if(serverList == null && serverList.size() <= 0) {
			throw new RuntimeException("请配置服务器地址");
		}
		for (String serverAddress : serverList) {
			int hash = this.hash(serverAddress);
			treeMap.put(hash, serverAddress);
		}
	}

	public String getServer(String clientIP) {
		if(StringUtils.isEmpty(clientIP)) {
			throw new RuntimeException("客户端ip地址不能为空");
		}
		int hash = this.hash(clientIP);
		SortedMap<Integer, String> tailMap = treeMap.tailMap(hash);
		if(tailMap == null) {
			return treeMap.get(treeMap.firstKey());
		}
		return tailMap.get(tailMap.firstKey());
	}
	
}
