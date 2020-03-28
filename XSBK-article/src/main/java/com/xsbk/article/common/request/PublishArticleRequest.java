package com.xsbk.article.common.request;

import com.xsbk.core.request.Request;

import lombok.Data;

/**
 * 
 * @author chrilwe
 *
 */
@Data
public class PublishArticleRequest extends Request {
	private String title;
	// 文章图片/视频 image video
	private String imagesOrVideo;
	// 文章图片或者视频地址
	private String urls;
	// 文章内容
	private String content;
	//分类
	private int catId;
}
