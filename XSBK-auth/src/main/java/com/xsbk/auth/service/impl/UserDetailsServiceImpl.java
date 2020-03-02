package com.xsbk.auth.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import com.xsbk.auth.config.UserJwt;

import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.util.encoders.Base64Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private ClientDetailsService clientDetailsService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 取出身份，如果身份为空说明没有认证
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		// 没有认证统一采用httpbasic认证，httpbasic中存储了client_id和client_secret，开始认证client_id和client_secret
		if (authentication == null) {
			ClientDetails clientDetails = clientDetailsService.loadClientByClientId(username);
			if (clientDetails != null) {
				// 密码
				String clientSecret = clientDetails.getClientSecret();
				return new User(username, clientSecret, AuthorityUtils.commaSeparatedStringToAuthorityList(""));
			}
		}
		if (StringUtils.isEmpty(username)) {
			return null;
		}
		// 调用服务查询用户
		/*UserExt userExt = (UserExt) userServiceClient.queryUserByAccountNoOrPhoneOrEmail(username);
		if (userExt == null) {
			return null;
		}*/
		com.xsbk.core.model.user.User u = new com.xsbk.core.model.user.User();
		u.setId(1);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encode = encoder.encode("123");
		u.setPassword(encode);
		u.setNickName("xiaobai");
		
		String permissionCodeString = "";

		UserJwt userDetails = new UserJwt(username, encode, AuthorityUtils.commaSeparatedStringToAuthorityList(permissionCodeString));
		userDetails.setNickName(u.getNickName());
		userDetails.setPic(u.getUserPic());
		return userDetails;
	}
}
