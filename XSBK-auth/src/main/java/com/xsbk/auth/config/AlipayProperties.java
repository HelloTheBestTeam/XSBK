package com.xsbk.auth.config;

import lombok.Data;
import lombok.ToString;

/**
 * 支付宝配置参数
 * @author chrilwe
 *
 */
@Data
@ToString
public class AlipayProperties {
	private String authUrl;
	private String url;
	private String appid;
	private String pid;
	private String alipay_public_key;
	private String app_public_key;
	private String app_private_key;
	private String format;
	private String charset;
	private String sign_type;
	private String redirect_uri;
}

