package com.xsbk.core.model.article;

import lombok.Data;
import lombok.ToString;

/**
 * 文章发布信息
 * @author chrilwe
 *
 */
@Data
@ToString
public class ArticlePub extends Article {
	private String account;
	private String userPic;
}
