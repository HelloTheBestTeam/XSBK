package com.xsbk.article.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.xsbk.core.model.user.ext.UserExt;
import com.xsbk.core.service.ServiceNameList;

/**
 * 
 * @author chrilwe
 *
 */
@FeignClient(ServiceNameList.XSBK_AUTH)
public interface AuthServiceClient {
	@GetMapping("/auth/getUserByAccessToken")
	public UserExt getUserDetailByAccessToken(@RequestParam("accessToken")String accessToken);
}
