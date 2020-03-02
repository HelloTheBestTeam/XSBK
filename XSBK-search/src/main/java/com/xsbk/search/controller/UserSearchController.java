package com.xsbk.search.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xsbk.core.model.user.ext.UserExt;

/**
 * 搜索用户
 * @author chrilwe
 *
 */
@RestController
@RequestMapping("/search/user")
public class UserSearchController {

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
}
