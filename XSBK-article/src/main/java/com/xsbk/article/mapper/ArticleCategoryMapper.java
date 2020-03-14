package com.xsbk.article.mapper;

import java.util.List;

import com.xsbk.core.model.article.ArticleCategory;

/**
 * 
 * @author chrilwe
 *
 */
public interface ArticleCategoryMapper {
	public void addArticleCategory(ArticleCategory articleCategory);
	
	public List<ArticleCategory> allList();
	
}
