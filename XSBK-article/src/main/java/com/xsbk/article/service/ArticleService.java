package com.xsbk.article.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 
 * @author chrilwe
 *
 */

import com.xsbk.article.mapper.ArticleMapper;
import com.xsbk.core.model.article.Article;
import com.xsbk.core.model.article.ArticlePub;
@Service
public class ArticleService {
	
	@Autowired
	private ArticleMapper articleMapper;
	
	public List<ArticlePub> queryListOrderByDate(int page, int size, String keyword) {
		int start = (page - 1) * size;
		List<Article> list = articleMapper.selectArticleListOrderByDate(start, size);
		List<ArticlePub> l = new ArrayList<ArticlePub>();
		for (Article article : list) {
			ArticlePub p = new ArticlePub();
			p.setNickName("小白");
			l.add(p);
		}
		return l;
	}
}
