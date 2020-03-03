package com.xsbk.gateway.service;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.netflix.zuul.context.RequestContext;

/**
 * 响应处理
 * @author chrilwe
 *
 */
@Service
public class ResponseService {
	
	public void res(RequestContext currentContext) {
		HttpServletResponse response = currentContext.getResponse();
		int status = response.getStatus();
		if(status == 400 || status == 401) {
			currentContext.setResponseStatusCode(status);
			try {
				currentContext.getResponse().getWriter().write("认证失败");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
