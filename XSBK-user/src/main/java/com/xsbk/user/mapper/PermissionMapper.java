package com.xsbk.user.mapper;

import java.util.List;

import com.xsbk.core.model.user.Permission;

/**
 * 权限
 * @author chrilwe
 *
 */
public interface PermissionMapper {
	
	public void insertPermission(Permission permission);
	
	public void deletePermissionById(int id);
	
	public void updatePermission(Permission permission);
	
	public List<Permission> selectPermissionListByUid(int uid);
	
	//public List<Permission> selectAllPermissionLeaf();
	
	public Permission selectPermissionByName(String name);
	
}
