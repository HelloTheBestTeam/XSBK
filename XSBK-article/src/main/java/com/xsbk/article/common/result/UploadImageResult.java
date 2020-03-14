package com.xsbk.article.common.result;

import com.xsbk.core.response.Result;

import lombok.Data;
import lombok.ToString;

/**
 * 
 * @author chrilwe
 *
 */
@Data
@ToString
public class UploadImageResult extends Result {
	
	private String imageUrls;

	public UploadImageResult(int code, String msg, boolean isSuccess,String imageUrls) {
		super(code, msg, isSuccess);
		this.imageUrls = imageUrls;
	}

}
