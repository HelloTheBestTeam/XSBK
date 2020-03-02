package com.xsbk.gateway.filter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

/**
 * 
 * @author chrilwe
 *
 */
@Component
public class PreFilter extends ZuulFilter {

	@Override
	public Object run() throws ZuulException {
		RequestContext currentContext = RequestContext.getCurrentContext();
		HttpServletRequest request = currentContext.getRequest();
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
