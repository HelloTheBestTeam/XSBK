package com.xsbk.search.service;

import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.action.index.IndexRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xsbk.core.model.user.User;
import com.xsbk.core.model.user.ext.UserExt;

/**
 * 用户信息搜索
 * @author chrilwe
 *
 */
@Service
public class UserService {
	
	@Autowired
	DocService docService;

	//添加用户
	public void addOrUpdateUser(User user) {
		IndexRequest indexRequest = new IndexRequest("xsbk","user",user.getId()+"");
		Map<String, Object> propertiesMap = new HashMap<String, Object>();
		propertiesMap.put("id", user.getId());
		propertiesMap.put("accountNo", user.getAccountNo());
		propertiesMap.put("nickName", user.getNickName());
		propertiesMap.put("status", user.getStatus());
		propertiesMap.put("gender", user.getGender());
		propertiesMap.put("grade", user.getGrade());
		propertiesMap.put("createTime", user.getCreateTime());
		propertiesMap.put("updateTime", user.getUpdateTime());
		propertiesMap.put("email", user.getEmail());
		propertiesMap.put("phoneNo", user.getPhoneNo());
		propertiesMap.put("userPic", user.getUserPic());
		docService.addOrUpdateDoc(indexRequest, propertiesMap);
	}
	
	//创建索引
	public boolean createIndex() throws Exception {
		Map<String, String> propertiesMap = new HashMap<String, String>();
		propertiesMap.put("id", "integer");
		propertiesMap.put("accountNo", "text");
		propertiesMap.put("nickName", "text");
		propertiesMap.put("status", "text");
		propertiesMap.put("gender", "text");
		propertiesMap.put("grade", "integer");
		propertiesMap.put("createTime", "date");
		propertiesMap.put("updateTime", "date");
		propertiesMap.put("email", "text");
		propertiesMap.put("phoneNo", "text");
		propertiesMap.put("userPic", "text");
		return docService.createIndex("xsbk", "user", propertiesMap);
	}
	
	//根据id查询
	public User searchById(int id) {
		String[] includes = {"id","accountNo","nickName","status","gender",
				"grade","createTime","updateTime","email","phoneNo","userPic"};
		String[] excludes = {};
		Map<String, Object> document = docService.getDocument(id+"", "user", "xsbk", includes, excludes);
		int userId = Integer.parseInt((String)document.get("id"));
		String accountNo = (String)document.get("accountNo");
		String nickName = (String)document.get("nickName");
		String status = (String)document.get("status");
		String gender = (String)document.get("gender");
		int grade = Integer.parseInt((String)document.get("grade"));
		String email = (String)document.get("email");
		String phoneNo = (String)document.get("phoneNo");
		String userPic = (String)document.get("userPic");
		
		User u = new User();
		u.setAccountNo(accountNo);
		u.setId(userId);
		u.setEmail(email);
		u.setGender(gender);
		u.setGrade(grade);
		u.setNickName(nickName);
		u.setEmail(email);
		u.setPhoneNo(phoneNo);
		u.setUserPic(userPic);
		u.setStatus(status);
		return u;
	}
}
