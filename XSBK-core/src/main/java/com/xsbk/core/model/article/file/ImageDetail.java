package com.xsbk.core.model.article.file;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

/**
 * 图片详情
 * @author chrilwe
 *
 */
@Data
@ToString
public class ImageDetail extends FileBaseDetail {
	private String imageUrl;//图片访问路径
	private String imageName;//重新生成存放到服务器的图片名称
}
