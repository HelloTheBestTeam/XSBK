package com.xsbk.gateway.filter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.xsbk.gateway.service.AccessTokenService;

/**
 * 
 * @author chrilwe
 *
 */
@Component
public class PreFilter extends ZuulFilter {
	
	@Autowired
	private AccessTokenService accessTokenService;

	@Override
	public Object run() throws ZuulException {
		RequestContext currentContext = RequestContext.getCurrentContext();
		accessTokenService.dealAccessToken(currentContext);
		return null;
	}

	@Override
	public boolean shouldFilter() {

		return true;
	}

	@Override
	public int filterOrder() {
		
		return 10;
	}

	@Override
	public String filterType() {
		
		return "pre";
	}

}
