package com.xsbk.article.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xsbk.core.model.article.Article;
import com.xsbk.core.model.article.ArticleCat;

/**
 * 
 * @author chrilwe
 *
 */
public interface ArticleMapper {
	public Article insertArticle(Article article);

	public void deleteArticleByArticleIdAndUserId(@Param("articleId") int articleId,
			@Param("userId") int userId,@Param("status")int status);

	public Article selectArticleById(int articleId);
	
	public List<Article> selectArticleListOrderByDate(@Param("start")int start,@Param("size")int size);

	//初始化加载、下拉刷星
	public List<Article> selectTheLastArticleListDescByLimit(@Param("size")int size,@Param("status")int status);
	
	//上拉刷新
	public List<Article> selectArticleListDescSmallerThanIndex(@Param("size")int size,
			@Param("status")int status,@Param("index")int index);
	
	public void insertArticleCat(ArticleCat articleCat);
}
