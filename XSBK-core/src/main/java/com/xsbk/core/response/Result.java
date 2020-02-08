package com.xsbk.core.response;

import lombok.Data;
import lombok.ToString;

/**
 * 
 * @author chrilwe
 *
 */
@Data
@ToString
public class Result {
	private int code;//状态码
	private String msg;//返回信息
	private boolean isSuccess;//请求是否成功
	
	public Result(int code, String msg, boolean isSuccess) {
		this.code = code;
		this.msg = msg;
		this.isSuccess = isSuccess;
	}
}
