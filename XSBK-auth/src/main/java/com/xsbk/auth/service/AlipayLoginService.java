package com.xsbk.auth.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.xsbk.auth.common.model.LoginStatus;

/**
 * 
 * @author chriwel
 *
 */
@Service
public class AlipayLoginService {
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	
	public LoginStatus alipayLoginStatus(@RequestParam("state")String state) {
		if(StringUtils.isEmpty(state)) {
			return null;
		}
		String string = stringRedisTemplate.boundValueOps(state).get();
		if(StringUtils.isEmpty(string)) {
			return null;
		}
		LoginStatus ls = JSON.parseObject(string,LoginStatus.class);
		return ls;
	}
}
