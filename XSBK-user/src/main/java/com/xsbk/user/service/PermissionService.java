package com.xsbk.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xsbk.core.model.user.Permission;
import com.xsbk.user.mapper.PermissionMapper;
/**
 * 
 * @author chrilwe
 *
 */
@Service
public class PermissionService {
	
	@Autowired
	private PermissionMapper permissionMapper;
	
	@Transactional
	public void addPermission(Permission permission) {
		//检查权限目录是否已经被创建
		String name = permission.getName();
	}
	
	
	//将汉字转换为拼音
	public String hanziToPingying(String name) {
		
		return "";
	}
}
