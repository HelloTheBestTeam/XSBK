package com.xsbk.im.redis;

import java.util.Map;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * redis客户端
 * @author chrilwe
 *
 */
public class RedisTemplate {
	
	private Jedis jedis;
	
	private static class Singleton {
		private static RedisTemplate redisTemplate = null;
		static {
			redisTemplate = new RedisTemplate();
		}
		private static RedisTemplate getInstance() {
			return redisTemplate;
		}
	}
	
	/**
	 * 初始化连接
	 */
	private RedisTemplate() {
		this.jedis = getResource();
	}
	
	protected Jedis getResource() {
		JedisPoolConfig jedisConfig = new JedisPoolConfig();
		jedisConfig.setMaxTotal(50);
		jedisConfig.setMaxIdle(5);
		JedisPool jedisPool = new JedisPool(jedisConfig, "192.168.43.163", 6379);
		
		return jedisPool.getResource();
	}
	
	public static RedisTemplate getInstance() {
		return Singleton.getInstance();
	}
	
	public static void init() {
		getInstance();
	}
	
	public void set(String key, String value) {
		jedis.set(key, value);
	}
	
	public String get(String key) {
		
		return jedis.get(key);
	}
	
	public void hset(String key1, String key2, String value) {
		jedis.hset(key1, key2, value);
	}
	
	public void expire(String key, int time) {
		jedis.expire(key, time);
	}
	
	public String hget(String key1, String key2) {
		
		return jedis.hget(key1, key2);
	}
	
	public Map<String, String> hgetAll(String key1) {
		
		return jedis.hgetAll(key1);
	}
}
