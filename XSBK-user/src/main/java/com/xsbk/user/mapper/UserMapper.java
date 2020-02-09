package com.xsbk.user.mapper;

import org.apache.ibatis.annotations.Param;

import com.xsbk.core.model.user.User;
import com.xsbk.core.model.user.ext.UserExt;

public interface UserMapper {
	
	public UserExt selectUserExtByAcount(String account);
	
	public void insertUser(User user);
	
	public User selectUserById(int id);
	
	public User updateUser(User user);
	
	public User deleteUserById(int id);
	
	public User selectUserByOneParam(@Param("param")String param, @Param("data")String data);
	
}
