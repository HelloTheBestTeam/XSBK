package com.xsbk.core.model.article;

import lombok.Data;
import lombok.ToString;

/**
 * 文章分类关联
 * @author chrilwe
 *
 */
@Data
@ToString
public class ArticleCat {
	private int id;
	private int articleId;
	private int catId;
}
