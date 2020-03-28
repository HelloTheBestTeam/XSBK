package com.xsbk.search.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xsbk.core.model.user.User;
import com.xsbk.core.model.user.ext.UserExt;
import com.xsbk.core.response.Result;
import com.xsbk.search.common.code.Code;
import com.xsbk.search.common.msg.Msg;
import com.xsbk.search.service.UserService;

/**
 * 搜索用户
 * @author chrilwe
 *
 */
@RestController
@RequestMapping("/search/user")
public class UserSearchController {
	
	@Autowired
	UserService userService;

	/**
	 * 用户搜索
	 * @param keyword
	 * @return
	 */
	@GetMapping("/")
	public List<UserExt> searchUserExt(@RequestParam("keyword")String keyword,
			@RequestParam(name="page", defaultValue="1")int page,
			@RequestParam(name="pageSize",defaultValue="5")int pageSize) {
		
		return null;
	}
	
	//修改、添加
	@PostMapping("/add")
	public Result addUser(@RequestBody User user) {
		if(user == null) {
			return new Result(Code.FAIL,Msg.DATA_NULL,false);
		}
		userService.addOrUpdateUser(user);
		return new Result(Code.SUCCESS,Msg.SUCCESS,true);
	}
	
	//根据id查询
	@GetMapping("searchById")
	public User searchById(@RequestParam("id")int id) {
		return userService.searchById(id);
	}
}
