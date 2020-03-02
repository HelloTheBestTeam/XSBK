package com.xsbk.core.model.article;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

/**
 * 帖子
 * @author chrilwe
 *
 */
@Data
@ToString
public class Article {
	private int id;
	//文章标题
	private String title;
	//文章图片/视频 image video
	private String imagesOrVideo;
	//文章图片或者视频地址
	private String urls;
	//发送时间
	private Date sendTime;
	//笑脸数量/点赞数
	private int smileNum;
	//踩一下数量
	private int cryNum;
	//转发数量
	private int forwordNum;
	//阅读数量
	private int readNum;
	//评论人数
	private int commentNum;
	//文章内容
	private String content;
	private int userId;
}
