package com.xsbk.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xsbk.core.model.user.ext.UserExt;
import com.xsbk.user.common.request.RegisteRequest;
import com.xsbk.user.mapper.UserMapper;

/**
 * 
 * @author Chrilwe
 *
 */
@Service
public class UserDetailService {
	
	@Autowired
	private UserMapper userMapper;
	
	public UserExt getUserExtByAccount(String account) {
		
		return userMapper.selectUserExtByAcount(account);
	}
	
	@Transactional
	public void registeUser(RegisteRequest request) {
		
		
	}
}
