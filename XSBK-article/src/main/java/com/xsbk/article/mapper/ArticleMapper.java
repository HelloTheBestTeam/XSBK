package com.xsbk.article.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xsbk.core.model.article.Article;

/**
 * 
 * @author chrilwe
 *
 */
public interface ArticleMapper {
	public void insertArticle(Article article);

	public void deleteArticleByArticleIdAndUserId(@Param("articleId") int articleId, @Param("userId") int userId);

	public Article selectArticleById(int articleId);
	
	public List<Article> selectArticleListOrderByDate(@Param("start")int start,@Param("size")int size);
}
