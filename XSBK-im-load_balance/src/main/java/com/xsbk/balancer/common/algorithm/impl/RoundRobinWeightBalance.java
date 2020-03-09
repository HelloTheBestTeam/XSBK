package com.xsbk.balancer.common.algorithm.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.xsbk.balancer.common.algorithm.Balancer;
import com.xsbk.balancer.common.model.WeightModel;

/**
 * 权重轮询算法
 * @author chrilwe
 *
 */
public class RoundRobinWeightBalance implements Balancer{
	
	private List<WeightModel> serverList = new ArrayList<WeightModel>();
	private int variableWeightSum;
	
	public String getServer(String key) {
		//上一次动态权重加上静态权重
		for (WeightModel weightModel : serverList) {
			int staticWeight = weightModel.getStaticWeight();
			int variableWeight = weightModel.getVariableWeight();
			variableWeight = variableWeight + staticWeight;
			weightModel.setVariableWeight(variableWeight);
		}
		
		//获取最大动态权重值
		int max = -1000;
		int index = 0;
		int maxIndex = 0;
		for (WeightModel weightModel : serverList) {
			int variableWeight = weightModel.getVariableWeight();
			if(max < variableWeight) {
				max = variableWeight;
				maxIndex = index;
			}
			index++;
		}
		//将最大值动态权重减去总权重
		WeightModel maxWM = serverList.get(maxIndex);
		maxWM.setVariableWeight(maxWM.getVariableWeight() - variableWeightSum);
		return maxWM.getServerAddress();
	}
	
	//初始化服务器列表
	public RoundRobinWeightBalance(List<WeightModel> serverList) {
		if(serverList == null && serverList.size() <= 0) {
			throw new RuntimeException("请配置服务器地址");
		}
		int sum = 0;
		for (WeightModel weightModel : serverList) {
			String serverAddress = weightModel.getServerAddress();
			if(StringUtils.isEmpty(serverAddress)) {
				throw new RuntimeException("请配置服务器地址");
			}
			sum += weightModel.getStaticWeight();
		}
		this.serverList = serverList;
		this.variableWeightSum = sum;
	}
	
	public static void main(String[] args) {
		List<WeightModel> serverList = new ArrayList<WeightModel>();
		WeightModel e = new WeightModel();
		e.setServerAddress("A");
		e.setStaticWeight(5);
		serverList.add(e);
		WeightModel e1 = new WeightModel();
		e1.setServerAddress("B");
		e1.setStaticWeight(1);
		serverList.add(e1);
		WeightModel e2 = new WeightModel();
		e2.setServerAddress("C");
		e2.setStaticWeight(1);
		serverList.add(e2);
		RoundRobinWeightBalance b = new RoundRobinWeightBalance(serverList);
		for(int i=0; i<7;i++) {
			String server = b.getServer("");
			System.out.println(server);
		}
	}
}
