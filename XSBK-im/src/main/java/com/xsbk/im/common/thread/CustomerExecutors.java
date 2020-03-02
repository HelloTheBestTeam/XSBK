package com.xsbk.im.common.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 自定义线程池
 * @author chrilwe
 *
 */
public class CustomerExecutors {
	
	private static Map<String, ThreadPoolExecutor> singletonMap = new HashMap<String, ThreadPoolExecutor>();
	
	private CustomerExecutors(){
		
	}
	
	public ThreadPoolExecutor getExecutors(int coreSize,int maxSize,long time,String name, BlockingQueue<Runnable> queue) {
		//首先判断当前的线程池是否已经创建,如果创建了，直接从map取
		ThreadPoolExecutor executor = singletonMap.get(name);
		if(executor == null) {
			executor = new ThreadPoolExecutor(coreSize, maxSize, time, TimeUnit.SECONDS, queue);
			singletonMap.put(name, executor);
		}
		return executor;
	}
	
	private static class Singleton {
		private static CustomerExecutors ce = new CustomerExecutors();
		private static CustomerExecutors getInstance() {
			return ce;
		}
	}
	
	public static CustomerExecutors getInstance() {
		return Singleton.getInstance();
	}
}
