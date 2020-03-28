package com.xsbk.search.service;

import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xsbk.core.model.article.Article;
import com.xsbk.core.model.article.ArticlePub;
import com.xsbk.core.model.user.User;
import com.xsbk.search.common.msg.Msg;

/**
 * 
 * @author chrilwe
 *
 */
@Service
public class ArticleService {

	@Autowired
	DocService docService;

	private static final GetRequest GetRequest = null;

	// 添加文章
	public void addOrUpdateArticle(Article article) {
		IndexRequest indexRequest = new IndexRequest("xsbk","article",article.getId()+"");
		Map<String, Object> dataMap = new HashMap<>();
		dataMap.put("id", article.getId());
		dataMap.put("title", article.getTitle());
		dataMap.put("imagesOrVideo", article.getImagesOrVideo());
		dataMap.put("urls", article.getUrls());
		dataMap.put("sendTime", article.getSendTime());
		dataMap.put("smilNum", article.getSmileNum());
		dataMap.put("cryNum", article.getCryNum());
		dataMap.put("forwordNum", article.getForwordNum());
		dataMap.put("readNum", article.getReadNum());
		dataMap.put("commentNum", article.getCommentNum());
		dataMap.put("content", article.getContent());
		dataMap.put("userId", article.getUserId());
		dataMap.put("status", article.getStatus());
		docService.addOrUpdateDoc(indexRequest , dataMap);
	}

	//创建索引
	public boolean createIndex() throws Exception {
		Map<String, String> propertiesMap = new HashMap<String,String>();
		propertiesMap.put("id", "text");
		propertiesMap.put("title", "text");
		propertiesMap.put("imagesOrVideo", "text");
		propertiesMap.put("urls", "text");
		propertiesMap.put("sendTime", "date");
		propertiesMap.put("smilNum", "integer");
		propertiesMap.put("cryNum", "integer");
		propertiesMap.put("forwordNum", "integer");
		propertiesMap.put("readNum", "integer");
		propertiesMap.put("commentNum", "integer");
		propertiesMap.put("content", "text");
		propertiesMap.put("userId", "integer");
		propertiesMap.put("status", "integer");
		boolean create = docService.createIndex("xsbk", "article", propertiesMap);
		return create;
	}
	
	//根据id搜索
	public ArticlePub searchById(String id) {
		String[] includes = {"id", "title", "imagesOrVideo", "urls", 
				"sendTime", "smilNum", "cryNum", "forwordNum", "readNum",
				"commentNum", "content", "userId", "status"};
		String[] excludes = {};
		Map res = docService.getDocument(id, "article", "xsbk", includes , excludes);
		int articleId = Integer.parseInt((String)res.get("id"));
		String title = (String)res.get("title");
		String imagesOrVideo = (String)res.get("imagesOrVideo");
		String urls = (String)res.get("urls");
		String st = (String)res.get("sendTime");
		String content = (String)res.get("content");
		int userId = Integer.parseInt((String)res.get("userId"));
		int status = Integer.parseInt((String)res.get("status"));
		
		ArticlePub ap = new ArticlePub();
		ap.setTitle(title);
		ap.setImagesOrVideo(imagesOrVideo);
		ap.setContent(content);
		ap.setUrls(urls);
		ap.setUserId(userId);
		ap.setId(articleId);
		ap.setStatus(status);
		return ap;
	}
}
