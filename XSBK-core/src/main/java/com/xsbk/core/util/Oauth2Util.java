package com.xsbk.core.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.xsbk.core.model.user.ext.UserExt;

/**
 * 
 * @author chrilwe
 *
 */
public class Oauth2Util {

	// 从请求头中获取用户信息
	public static UserExt getUserExtInRequestHead(HttpServletRequest request) {
		if (request == null) {
			throw new RuntimeException("请求为空");
		}
		String authorization = request.getHeader("Authorization");
		// 判断是否为认证头部格式
		if (StringUtils.isEmpty(authorization) || authorization.indexOf("Bearer ") < 0) {
			throw new RuntimeException("未认证");
		}
		// 解析令牌
		String jwt = authorization.substring(7);
		return null;
	}
}
