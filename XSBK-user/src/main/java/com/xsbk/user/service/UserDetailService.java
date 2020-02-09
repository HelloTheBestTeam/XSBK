package com.xsbk.user.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xsbk.core.model.user.User;
import com.xsbk.core.model.user.ext.UserExt;
import com.xsbk.user.common.msg.Msg;
import com.xsbk.user.common.request.RegisteRequest;
import com.xsbk.user.mapper.UserMapper;

/**
 * 
 * @author Chrilwe
 *
 */
@Service
public class UserDetailService {

	@Autowired
	private UserMapper userMapper;
	

	public UserExt getUserExtByAccount(String account) {

		return userMapper.selectUserExtByAcount(account);
	}

	@Transactional
	public void registeUser(RegisteRequest request) {
		//校验
		boolean result = validateRegisteParams(request);
		
		if(result) {
			//检查昵称是否已填，没有则默认为当前账号为昵称
			String nickName = request.getNickName();
			if(StringUtils.isEmpty(nickName)) {
				nickName = request.getAccount();
			}
			
			//为用户赋予普通角色的权限
			
		}
	}

	private boolean validateRegisteParams(RegisteRequest request) {
		// 账号密码 手机号不能为空
		String account = request.getAccount();
		String password = request.getPassword();
		String phoneNo = request.getPhoneNo();
		if (StringUtils.isAnyEmpty(account, password, phoneNo)) {
			throw new RuntimeException(Msg.PARAMS_NULL_ERROR);
		}

		// 校验账号密码和手机号是否符合规定
		
		
		//校验账号 手机号 邮箱 和 昵称是否存在
		User u1 = this.getUserByParam("account_no", account);
		if(u1 != null) {
			throw new RuntimeException(Msg.ACCOUNT_EXISTS);
		}
		User u2 = this.getUserByParam("phone_no", phoneNo);
		if(u2 != null) {
			throw new RuntimeException(Msg.PHONENO_EXISTS);
		}
		String nickName = request.getNickName();
		String email = request.getEmail();
		if(!StringUtils.isEmpty(nickName)) {
			User u3 = this.getUserByParam("nick_name", nickName);
			if(u3 != null) {
				throw new RuntimeException(Msg.NICK_NAME_EXISTS);
			}
		}
		
		if(!StringUtils.isEmpty(email)) {
			User u4 = this.getUserByParam("email", email);
			if(u4 != null) {
				throw new RuntimeException(Msg.EMAIL_EXISTS);
			}
		}
		return true;
	}

	
	public User getUserByParam(String type, String data) {
		return userMapper.selectUserByOneParam(type, data);
	}
}
