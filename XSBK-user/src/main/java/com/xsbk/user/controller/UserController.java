package com.xsbk.user.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xsbk.core.base.BaseController;
import com.xsbk.core.model.user.User;
import com.xsbk.core.model.user.ext.UserExt;
import com.xsbk.core.response.Result;
import com.xsbk.user.common.code.Code;
import com.xsbk.user.common.msg.Msg;
import com.xsbk.user.common.request.RegisteRequest;
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
	
	@Value("${validateType}")
	private String validateType;

	/**
	 * 根据account查询
	 * 
	 * @param account
	 * @return
	 */
	@GetMapping("/getUserDetailByAccount")
	public UserExt getUserDetailByAccount(@RequestParam("account") String account) {
		// 校验参数非空
		if (StringUtils.isEmpty(account)) {
			throw new RuntimeException(Msg.PARAMS_ERROR);
		}

		// 查询
		UserExt userExt = userDetailService.getUserExtByAccount(account);
		return userExt;
	}

	/**
	 * 注册
	 * 
	 * @param registeRequest
	 * @return
	 */
	@PostMapping("/registe")
	public RegisterResult registeUser(@RequestBody RegisteRequest registeRequest) {
		RegisterResult result = null;
		if (registeRequest == null) {
			result = new RegisterResult(Code.PARAMS_ERROR, Msg.PARAMS_ERROR, false);
		}

		try {
			userDetailService.registeUser(registeRequest);
		} catch (Exception e) {
			e.printStackTrace();
			return new RegisterResult(Code.FAIL,e.getMessage(),false);
		}
		result = new RegisterResult(Code.SUCCESS, Msg.SUCCESS, true);
		return result;
	}

	/**
	 * 检验是否存在
	 * 
	 * @param account
	 * @return
	 */
	@GetMapping("/validate/{type}")
	public Result validateIsExists(@RequestParam("data") String data, @PathVariable("type") String type) {
		if (StringUtils.isEmpty(data)) {
			return new Result(Code.FAIL, Msg.PARAMS_ERROR, false);
		}
		
		boolean flag = false;
		String[] split = validateType.split(",");
		for (String string : split) {
			if(string.equals(type)) {
				flag = true;
				break;
			}
		}
		if(!flag) {
			return new Result(Code.FAIL,Msg.PARAMS_ERROR,false);
		}
		
		User user = userDetailService.getUserByParam(type, data);
		if(user != null) {
			if(type.equals("account")) {
				return new Result(Code.FAIL,Msg.ACCOUNT_EXISTS,false);
			} else if(type.equals("nickName")) {
				return new Result(Code.FAIL,Msg.NICK_NAME_EXISTS,false); 
			} else if(type.equals("phone")) {
				return new Result(Code.FAIL,Msg.PHONENO_EXISTS,false);
			}
		}
		
		return new Result(Code.SUCCESS,Msg.SUCCESS,true);
	}
	
	@GetMapping("/getUserById")
	public User getUserById(@RequestParam("id")int id) {
		return userDetailService.getUserById(id);
	}
	
	//根据第三方登录id查询
	@GetMapping("/getUserByAuthUid")
	public User getUserByAuthUid(@RequestParam("authUid")String authUid) {
		if(StringUtils.isEmpty(authUid)) {
			return null;
		}
		return userDetailService.getUserByAuthUid(authUid);
	}
	
	//根据用户id批量查询
	@GetMapping("/getUserBatchByIds")
	public List<User> selectUserBatchByIds(List<Integer> ids) {
		List<User> res = new ArrayList<User>();
		for (Integer id : ids) {
			User user = userDetailService.getUserById(id);
			res.add(user);
		}
		return res;
	}
}
