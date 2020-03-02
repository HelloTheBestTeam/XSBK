package com.xsbk.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xsbk.core.base.BaseController;
import com.xsbk.core.model.user.UserFensi;
import com.xsbk.core.response.Result;
import com.xsbk.user.common.code.Code;
import com.xsbk.user.common.msg.Msg;
import com.xsbk.user.service.UserDetailService;

/**
 * 用户粉丝
 * @author chrilwe
 *
 */
@RestController
@RequestMapping("/fensi")
public class FensiController extends BaseController {
	
	@Autowired
	private UserDetailService userDetailService;
	
	/**
	 * 关注
	 * @param fensiId
	 * @return
	 */
	@GetMapping("/notice")
	public Result noticeUser(@RequestParam("fensiId")int fensiId) {
		int userId = 0;
		//关注的对象是否已经被关注
		UserFensi userFensi = userDetailService.getUserFensiByFensiIdAndUserId(userId, fensiId);
		if(userFensi != null) {
			return new Result(Code.FAIL,Msg.IS_NOTICE,false);
		}
		
		//关联,关注数量修改，粉丝数量修改
		userDetailService.addUserFensi(userId, fensiId);
		return new Result(Code.SUCCESS,Msg.SUCCESS,true);
	}
	
	/**
	 * 取关
	 * @param fensiId
	 * @return
	 */
	@GetMapping("/delete")
	public Result deleteNoticeUser(@RequestParam("fensiId")int fensiId) {
		int userId = 0;
		userDetailService.deleteUserFensi(userId, fensiId);
		return new Result(Code.SUCCESS,Msg.SUCCESS,true);
	}
}
