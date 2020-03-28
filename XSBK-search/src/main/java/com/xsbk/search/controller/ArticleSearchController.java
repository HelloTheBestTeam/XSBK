package com.xsbk.search.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xsbk.core.model.article.Article;
import com.xsbk.core.model.article.ArticlePub;
import com.xsbk.core.model.user.ext.UserExt;
import com.xsbk.core.response.Result;
import com.xsbk.search.common.code.Code;
import com.xsbk.search.common.msg.Msg;
import com.xsbk.search.service.ArticleService;

/**
 * 文章搜索
 * @author chrilwe
 *
 */
@RestController
@RequestMapping("/search/article")
public class ArticleSearchController {
	
	@Autowired
	private ArticleService articleService;
	
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
	
	/**
	 * 添加文章 
	 * @param article
	 * @return
	 */
	@PostMapping("/addArticle")
	public Result addArticle(@RequestBody Article article) {
		if(article == null) {
			return new Result(Code.FAIL,Msg.FAIL,false);
		}
		articleService.addOrUpdateArticle(article);
		return new Result(Code.SUCCESS,Msg.SUCCESS,true);
	}
	
	
	/**
	 * 根据文章id 查询
	 * @param id
	 * @return
	 */
	@GetMapping("/searchById")
	public ArticlePub searchById(@RequestParam("id")String id) {
		return articleService.searchById(id);
	}
}
