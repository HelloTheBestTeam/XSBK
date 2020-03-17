package com.xsbk.balancer.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.xsbk.balancer.common.algorithm.impl.RoundRobinWeightBalance;
import com.xsbk.balancer.common.model.ServerDetail;
import com.xsbk.balancer.common.model.ServerProperties;
import com.xsbk.balancer.common.model.WeightModel;


/**
 * 
 * @author chrilwe
 *
 */
@Service
public class BalancerService {
	
	@Autowired
	private ZooKeeper zookeeper;
	public static final String PATH_PARENT = "/REGISTER/";
	@Autowired
	private Map<String, Integer> serverConfigMap;
	
	public String getServer() throws Exception {
		List<ServerDetail> serverDetailList = getServerDetailFromZk();
		List<WeightModel> list = new ArrayList<WeightModel>();
		for (ServerDetail serverDetail : serverDetailList) {
			WeightModel weightModel = new WeightModel();
			weightModel.setServerAddress(serverDetail.getServerIp() + "/ws");
			int weight = 1;
			for(Iterator iter = serverConfigMap.entrySet().iterator(); iter.hasNext();) {
				String next = (String) iter.next();
				if(next.equals(serverDetail.getServerIp())) {
					weight = serverConfigMap.get(next);
					break;
				}
			}
			weightModel.setStaticWeight(weight);
			list.add(weightModel);
		}
		RoundRobinWeightBalance r = new RoundRobinWeightBalance(list);
		
		return r.getServer("");
	}
	
	protected List<ServerDetail> getServerDetailFromZk() throws Exception {
		List<String> children = zookeeper.getChildren(PATH_PARENT, false);
		List<ServerDetail> list = new ArrayList<ServerDetail>();
		for (String string : children) {
			byte[] data = zookeeper.getData(PATH_PARENT + string, false, new Stat());
			String s = new String(data);
			ServerDetail sd = JSON.parseObject(s,ServerDetail.class);
			list.add(sd);
		}
		return list;
	}
}
