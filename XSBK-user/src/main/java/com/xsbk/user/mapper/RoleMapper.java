package com.xsbk.user.mapper;

import java.util.List;

import com.xsbk.core.model.user.Role;

/**
 * 角色
 * @author chrilwe
 *
 */
public interface RoleMapper {
	
	public void insertRole(Role role);
	
	public void deleteRole(int id);
	
	public void updateRole(Role role);
	
	public List<Role> selectRoleListByUid(int uid);
	
	public Role selectRoleById(int id);
}
