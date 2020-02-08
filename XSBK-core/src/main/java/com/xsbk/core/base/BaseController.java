package com.xsbk.core.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * 
 * @author chrilwe
 *
 */
public class BaseController {
	
	public HttpServletRequest request;
	public HttpServletResponse response;
	
	@ModelAttribute
	public void getReqAndRes(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}
}
