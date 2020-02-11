package com.xsbk.core.model.comment;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

/**
 * 评论(多级)
 * @author chrilwe
 *
 */
@Data
@ToString
public class Comment {
	private int id;
	private int articleId;
	//评论内容
	private String connent;
	//发布时间
	private Date createTime;
	//发布评论人
	private int userId;
	//父类id
	private int pid;
}
