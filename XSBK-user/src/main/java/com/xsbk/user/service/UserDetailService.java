package com.xsbk.user.service;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.jni.Lock;
import org.redisson.RedissonMultiLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xsbk.core.model.user.User;
import com.xsbk.core.model.user.UserFensi;
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
	//@Autowired
	//private RedissonClient redissonClient;
	

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
			
			//插入数据
			User user = new User();
			user.setAccountNo(request.getAccount());
			user.setCreateTime(new Date());
			user.setPhoneNo(request.getPhoneNo());
			user.setPassword(request.getPassword1());
			user.setNickName(nickName);
			user.setStatus("正常");
			userMapper.insertUser(user);
		}
	}

	private boolean validateRegisteParams(RegisteRequest request) {
		//校验code
		
		// 账号密码 手机号不能为空
		String account = request.getAccount();
		String password1 = request.getPassword1();
		String password2 = request.getPassword2();
		String phoneNo = request.getPhoneNo();
		if (StringUtils.isAnyEmpty(account, password1, password2, phoneNo)) {
			throw new RuntimeException(Msg.PARAMS_NULL_ERROR);
		}
		
		//校验密码是否一致
		if(!password1.equals(password2)) {
			throw new RuntimeException(Msg.PASSWORD_DIFF);
		}

		// 校验账号密码和手机号是否符合规定
		char[] ac = account.toCharArray();
		char[] pd = password1.toCharArray();
		if(ac.length != 11) {
			throw new RuntimeException(Msg.ACCOUNT_TYPE_ERR);
		}
		if(pd.length < 6 || pd.length > 11) {
			throw new RuntimeException(Msg.PASSWORD_TYPE_ERR);
		}
		
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
	
	public User getUserById(int id) {
		return userMapper.selectUserById(id);
	}
	
	public User getUserByAuthUid(String authUid) {
		return userMapper.selectUserByAuthUid(authUid);
	}
	
	@Transactional
	public void addUserFensi(int userId, int fensiId) {
		//生成用户粉丝表
		UserFensi userFensi = new UserFensi();
		userFensi.setAddTime(new Date());
		userFensi.setFensiId(fensiId);
		userFensi.setUserId(userId);
		userMapper.insertUserFensi(userFensi);
		
		//将关注数量加一
		User user = userMapper.selectUserById(userId);
		user.setNoticeNum(user.getNoticeNum() + 1);
		userMapper.updateUser(user);
		//将粉丝数量加一 
		User u = userMapper.selectUserById(fensiId);
		u.setFensiNum(u.getFensiNum() + 1);
		userMapper.updateUser(u);
	}
	
	@Transactional
	public void deleteUserFensi(int userId, int fensiId) {
		userMapper.deleteUserFensiByFensiIdAndUid(fensiId, userId);
	}
	
	public UserFensi getUserFensiByFensiIdAndUserId(int userId, int fensiId) {
		return userMapper.selectUserFensiByFensiIdAndUserId(fensiId, userId);
	}
}
