package com.xsbk.article.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xsbk.article.mapper.ArticleCategoryMapper;
import com.xsbk.core.model.article.ArticleCategory;

/**
 * 
 * @author chrilwe
 *
 */
@Service
public class ArticleCategoryService {
	
	@Autowired
	private ArticleCategoryMapper articleCategoryMapper;
	
	@Transactional
	public void addArticleCategory(ArticleCategory articleCategory) {
		
		articleCategoryMapper.addArticleCategory(articleCategory);
	}
	
	public List<ArticleCategory> queryAll() {
		
		return articleCategoryMapper.allList();
	}
}
