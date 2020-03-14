package com.xsbk.article.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.xsbk.article.common.Msg.Msg;
import com.xsbk.article.common.code.Code;
import com.xsbk.article.common.result.UploadImageResult;
import com.xsbk.article.service.FileService;

/**
 * 文件管理
 * @author chrilwe
 *
 */
@RestController
@RequestMapping("/file")
public class FileController {
	
	@Autowired
	private FileService fileService;
	
	/**
	 * 图片上传
	 */
	@PostMapping("/uploadImage")
	public UploadImageResult uploadImage(MultipartFile image) {
		if(image == null) {
			return new UploadImageResult(Code.FAIL, Msg.FAIL, false, "");
		}
		try {
			String imageUrls = fileService.uploadImageToLocal(image);
			return new UploadImageResult(Code.SUCCESS, Msg.SUCCESS, true, imageUrls);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new UploadImageResult(Code.FAIL, Msg.FAIL, false, "");
	}
}
