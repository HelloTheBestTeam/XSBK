package com.xsbk.article.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xsbk.core.model.article.Article;
import com.xsbk.core.response.Result;

/**
 * 文章内容
 * @author chrilwe
 *
 */
@RestController
@RequestMapping("/article")
public class ArticleController {
	
	
	/**
	 * 文章发布
	 */
	@PostMapping("/push")
	public Result pushArticle(@RequestBody Article article,@RequestBody String articleCatId) {
		
		return null;
	}
	
	
	/**
	 * 文章删除
	 * @return
	 */
	@GetMapping("/delete")
	public Result deleteArticle(@RequestParam("articleId")int articleId) {
		
		return null;
	}
}
