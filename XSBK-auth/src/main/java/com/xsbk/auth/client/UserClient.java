package com.xsbk.auth.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.xsbk.core.model.user.User;
import com.xsbk.core.model.user.ext.UserExt;
import com.xsbk.core.service.ServiceNameList;

/**
 * 用户数据接口
 * @author chrilwe
 *
 */
@FeignClient(ServiceNameList.XSBK_USER)
@RequestMapping("/user")
public interface UserClient {
	
	@GetMapping("/getUserDetailByAccount")
	public UserExt getUserDetailByAccount(@RequestParam("account") String account);
	
	@GetMapping("/getUserById")
	public User getUserById(@RequestParam("id")int id);
	
	@GetMapping("/getUserByAuthUid")
	public User getUserByAuthUid(@RequestParam("authUid")String authUid);
}
