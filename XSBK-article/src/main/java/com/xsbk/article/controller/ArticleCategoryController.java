package com.xsbk.article.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xsbk.core.response.Result;

/**
 * 文章分类
 * @author chrilwe
 *
 */
@RestController
@RequestMapping("/article/category")
public class ArticleCategoryController {
	
	/**
	 * 添加文章内容分类
	 * @return
	 */
	@PostMapping("/add")
	public Result addArticleCategory() {
		
		return null;
	}
}
