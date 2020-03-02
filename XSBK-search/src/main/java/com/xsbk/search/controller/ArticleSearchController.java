package com.xsbk.search.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xsbk.core.model.article.ArticlePub;
import com.xsbk.core.model.user.ext.UserExt;

/**
 * 文章搜索
 * @author chrilwe
 *
 */
@RestController
@RequestMapping("/search/article")
public class ArticleSearchController {
	
	/**
	 * 文章搜索
	 * @param keyword
	 * @return
	 */
	@GetMapping("/")
	public List<ArticlePub> searchArticle(@RequestParam("keyword")String keyword,
			@RequestParam(name="page", defaultValue="1")int page,
			@RequestParam(name="pageSize",defaultValue="5")int pageSize) {
		
		return null;
	}
	
	
}
