package com.xsbk.user.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xsbk.core.base.BaseController;
import com.xsbk.core.model.user.ext.UserExt;
import com.xsbk.user.common.code.Code;
import com.xsbk.user.common.msg.Msg;
import com.xsbk.user.common.request.RegisteRequest;
import com.xsbk.user.common.response.LoginResult;
import com.xsbk.user.common.response.RegisterResult;
import com.xsbk.user.service.UserDetailService;

/**
 * 
 * @author Chrilwe
 *
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
	
	@Autowired
	private UserDetailService userDetailService; 
	
	/**
	 * 根据account查询
	 * @param account
	 * @return
	 */
	@GetMapping("/getUserDetailByAccount")
	public LoginResult getUserDetailByAccount(@RequestParam("account")String account) {
		//校验参数非空
		LoginResult result = null;
		if(StringUtils.isEmpty(account)) {
			result = new LoginResult(Code.PARAMS_ERROR,Msg.PARAMS_ERROR,false,null);
		}
		
		//查询
		UserExt userExt = userDetailService.getUserExtByAccount(account);
		result = new LoginResult(Code.SUCCESS,Msg.SUCCESS,true,userExt);
		return result;
	}
	
	
	/**
	 * 注册
	 * @param registeRequest
	 * @return
	 */
	@PostMapping("/registe")
	public RegisterResult registeUser(@RequestBody RegisteRequest registeRequest) {
		RegisterResult result = null;
		if(registeRequest == null) {
			result = new RegisterResult(Code.PARAMS_ERROR,Msg.PARAMS_ERROR,false);
		}
		userDetailService.registeUser(registeRequest);
		result = new RegisterResult(Code.SUCCESS,Msg.SUCCESS,true);
		return result;
	}
}
