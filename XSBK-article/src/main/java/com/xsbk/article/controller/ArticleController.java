package com.xsbk.article.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.xsbk.article.common.result.UploadImageResult;
import com.xsbk.article.service.ArticleService;
import com.xsbk.core.model.article.Article;
import com.xsbk.core.model.article.ArticlePub;
import com.xsbk.core.response.Result;

/**
 * 文章内容
 * @author chrilwe
 *
 */
@RestController
@RequestMapping("/article")
public class ArticleController {
	
	@Autowired
	private ArticleService articleService;
	
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
	
	/**
	 * 查询文章列表
	 * @return
	 */
	@GetMapping("/list")
	public List<ArticlePub> articleList(@RequestParam("page")int page,
			@RequestParam("size")int size,@RequestParam("keyword")String keyword) {
		
		return articleService.queryListOrderByDate(page, size, keyword);
	}
}
