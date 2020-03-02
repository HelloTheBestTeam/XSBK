package com.xsbk.core.model.article;

import lombok.Data;
import lombok.ToString;

/**
 * 文章内容分类
 * @author chrilwe
 *
 */
@Data
@ToString
public class ArticleCategory {
	private int id;
	private int pid;
	private String name;
	private int grade;
	private String code;
}
