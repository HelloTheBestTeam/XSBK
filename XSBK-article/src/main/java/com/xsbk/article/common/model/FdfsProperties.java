package com.xsbk.article.common.model;

import lombok.Data;

/**
 * fdfs参数配置
 * @author chrilwe
 *
 */
@Data
public class FdfsProperties {
	private String charset;
	private int connectTime;
	private String trackerServers;
}
