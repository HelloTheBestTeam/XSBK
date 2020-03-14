package com.xsbk.article.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xsbk.article.common.Msg.Msg;
import com.xsbk.article.common.code.Code;
import com.xsbk.article.service.ArticleCategoryService;
import com.xsbk.core.model.article.ArticleCategory;
import com.xsbk.core.response.Result;

/**
 * 文章分类
 * @author chrilwe
 *
 */
@RestController
@RequestMapping("/article/category")
public class ArticleCategoryController {
	
	@Autowired
	private ArticleCategoryService articleCategoryService;
	
	/**
	 * 添加文章内容分类
	 * @return
	 */
	@PostMapping("/add")
	public Result addArticleCategory(@RequestBody ArticleCategory articleCategory) {
		if(articleCategory == null) {
			return new Result(Code.FAIL,Msg.FAIL,false);
		}
		
		articleCategoryService.addArticleCategory(articleCategory);
		return new Result(Code.SUCCESS,Msg.SUCCESS,true);
	}
	
	@GetMapping("/list")
	public List<ArticleCategory> queryAll() {
		
		return articleCategoryService.queryAll();
	}
}
