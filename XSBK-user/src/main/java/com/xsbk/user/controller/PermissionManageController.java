package com.xsbk.user.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xsbk.core.model.user.Permission;
import com.xsbk.core.response.Result;

/**
 * 权限管理
 * @author chrilwe
 *
 */
@RestController
@RequestMapping("/permission")
public class PermissionManageController {
	
	/**
	 * 添加权限
	 * @param permission
	 * @return
	 */
	@PostMapping("/add")
	public Result addPermission(@RequestBody Permission permission) {
		
		return null;
	}
}
