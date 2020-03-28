package com.xsbk.article.client;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.xsbk.core.model.user.User;
import com.xsbk.core.model.user.UserFensi;
import com.xsbk.core.service.ServiceNameList;

/**
 * 用户相关信息接口
 * @author chrilwe
 *
 */
@FeignClient(ServiceNameList.XSBK_USER)
public interface UserInfoClient {
	
	@GetMapping("/user/getUserById")
	public User getUserById(@RequestParam("id")int id);
	
	@GetMapping("/fensi/findFensiById")
	public UserFensi findUserFensiById(
			@RequestParam("fensiId")int fensiId);
	
	@GetMapping("/user/getUserBatchByIds")
	public List<User> selectUserBatchByIds(List<Integer> ids);
	
	@GetMapping("/fensi/findFensiBatchById")
	public Map<Integer,UserFensi> findUserFensiById(List<Integer> fensiIds);
}
