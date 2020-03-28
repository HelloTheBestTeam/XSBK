package com.xsbk.article.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 
 * @author chrilwe
 *
 */
import org.springframework.transaction.annotation.Transactional;

import com.xsbk.article.client.AuthServiceClient;
import com.xsbk.article.client.UserInfoClient;
import com.xsbk.article.mapper.ArticleCategoryMapper;
import com.xsbk.article.mapper.ArticleMapper;
import com.xsbk.core.model.article.Article;
import com.xsbk.core.model.article.ArticleCat;
import com.xsbk.core.model.article.ArticleCategory;
import com.xsbk.core.model.article.ArticlePub;
import com.xsbk.core.model.user.User;
import com.xsbk.core.model.user.UserFensi;
@Service
public class ArticleService {
	
	@Autowired
	private ArticleMapper articleMapper;
	
	@Autowired
	private UserInfoClient userInfoClient;
	
	@Autowired
	private ArticleCategoryMapper articleCategoryMapper;
	
	public List<ArticlePub> queryList(String option,int index,int size, String keyword,int userId) {
		List<Article> list = null;
		if(option.equals("up")) {
			list = articleMapper.selectArticleListDescSmallerThanIndex(size, 1, index);
		} else {
			list = articleMapper.selectTheLastArticleListDescByLimit(size, 1);
		}
		if(list != null) {
			List<ArticlePub> resList = new ArrayList<>();
			List<Integer> fensiIds = new ArrayList<>();
			for(Article article: list) {
				UserFensi userFensi = userInfoClient.findUserFensiById(article.getUserId());
				User user = userInfoClient.getUserById(article.getUserId());
				ArticlePub articlePub = new ArticlePub();
				if(userFensi == null) {
					articlePub.setGanzhu(false);
				} else {
					articlePub.setGanzhu(true);
				}
				articlePub.setAccount(user.getAccountNo());
				articlePub.setNickName(user.getNickName());
				articlePub.setCommentNum(article.getCommentNum());
				articlePub.setContent(article.getContent());
				articlePub.setCryNum(article.getCryNum());
				articlePub.setForwordNum(article.getForwordNum());
				articlePub.setId(article.getId());
				articlePub.setImagesOrVideo(article.getImagesOrVideo());
				articlePub.setReadNum(article.getReadNum());
				articlePub.setSendTime(article.getSendTime());
				articlePub.setSmileNum(article.getSmileNum());
				articlePub.setStatus(article.getStatus());
				articlePub.setTitle(article.getTitle());
				articlePub.setUrls(article.getUrls());
				articlePub.setUserId(article.getUserId());
				resList.add(articlePub);
				fensiIds.add(article.getUserId());
			}
			return resList;
		}
		return null;
	}
	
	@Transactional
	public void addArticle(Article article, int catId) {
		Article a = articleMapper.insertArticle(article);
		ArticleCat articleCat = new ArticleCat();
		articleCat.setArticleId(a.getId());
		articleCat.setCatId(catId);
		articleMapper.insertArticleCat(articleCat);
	}
}
