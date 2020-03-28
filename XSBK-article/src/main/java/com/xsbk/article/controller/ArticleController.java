package com.xsbk.article.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.xsbk.article.common.Msg.Msg;
import com.xsbk.article.common.code.Code;
import com.xsbk.article.common.request.PublishArticleRequest;
import com.xsbk.article.common.result.UploadImageResult;
import com.xsbk.article.service.ArticleService;
import com.xsbk.core.base.BaseController;
import com.xsbk.core.model.article.Article;
import com.xsbk.core.model.article.ArticlePub;
import com.xsbk.core.model.user.ext.UserExt;
import com.xsbk.core.response.Result;
import com.xsbk.core.util.Oauth2Util;

/**
 * 文章内容
 * @author chrilwe
 *
 */
@RestController
@RequestMapping("/article")
public class ArticleController extends BaseController {
	
	@Autowired
	private ArticleService articleService;
	
	/**
	 * 文章发布
	 */
	@PostMapping("/push")
	public Result pushArticle(@RequestBody PublishArticleRequest pubArticleRequest) {
		if(pubArticleRequest == null) {
			return new Result(Code.FAIL,Msg.FAIL,false);
		}
		
		UserExt userExt = Oauth2Util.getUserExtInRequestHead(request);
		
		int catId = pubArticleRequest.getCatId();
		String content = pubArticleRequest.getContent();
		String imagesOrVideo = pubArticleRequest.getImagesOrVideo();
		String title = pubArticleRequest.getTitle();
		String urls = pubArticleRequest.getUrls();
		
		Article article = new Article();
		article.setContent(content);
		article.setTitle(title);
		article.setImagesOrVideo(imagesOrVideo);
		article.setUrls(urls);
		article.setUserId(userExt.getId());
		article.setSendTime(new Date());
		article.setStatus(1);
		articleService.addArticle(article, catId);
		return new Result(Code.SUCCESS,Msg.SUCCESS,true);
	}
	
	
	/**
	 * 文章删除
	 * @return
	 */
	@GetMapping("/delete")
	public Result deleteArticle(@RequestParam("articleId")int articleId) {
		UserExt userExt = Oauth2Util.getUserExtInRequestHead(request);
		int userId = userExt.getId();
		return null;
	}
	
	/**
	 * 文章列表
	 * @param page
	 * @param size
	 * @param keyword
	 * @param flagId 如果下拉刷新，表明当前的id为最新一条数据id,如果上拉刷新，表明当前id为最后一条数据id
	 * @param option 加载操作： 上拉获取最新数据up  下拉获取最新数据down   初始化加载获取最新数据 initLoad
	 * @return
	 */
	@GetMapping("/list")
	public List<ArticlePub> articleList(
			@RequestParam("size")int size,@RequestParam("keyword")String keyword,
			@RequestParam("flagId")int flagId,
			@RequestParam("option")String option) {
		UserExt userExt = Oauth2Util.getUserExtInRequestHead(request);
		return articleService.queryList(option,flagId,size,keyword,userExt.getId());
	}
}
