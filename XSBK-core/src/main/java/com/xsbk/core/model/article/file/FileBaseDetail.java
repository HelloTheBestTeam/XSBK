package com.xsbk.core.model.article.file;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

/**
 * 文件基本信息
 * @author chrilwe
 *
 */
@Data
@ToString
public class FileBaseDetail {
	private int id;
	private String name;
	private String extName;
	private long size;
	private Date createTime;
}
