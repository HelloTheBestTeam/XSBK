package com.xsbk.auth.common.response;

import com.xsbk.core.response.Result;

import lombok.Data;

/**
 * 支付宝第三方登录地址
 * @author chrilwe
 *
 */
@Data
public class ApplyAlipayLoginUrlResult extends Result {
	
	private String alipayAuthUrl;
	private String state;

	public ApplyAlipayLoginUrlResult(int code, String msg, boolean isSuccess, String url, String state) {
		super(code, msg, isSuccess);
		this.alipayAuthUrl = url;
		this.state = state;
	}

}
