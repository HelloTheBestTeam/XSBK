package com.xsbk.article.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.xsbk.article.mapper.ImageDetailMapper;
import com.xsbk.core.model.article.file.ImageDetail;
import com.xsbk.core.service.ServiceNameList;

/**
 * 
 * @author chrilwe
 *
 */
@Service
public class FileService {

	@Autowired
	private LoadBalancerClient loadBalancerClient;
	
	@Autowired
	private ImageDetailMapper imageDetailMapper;

	/*
	 * @Autowired private StorageClient1 storageClient1;
	 * 
	 * public String uploadImage(MultipartFile image) throws Exception {
	 * if(image == null) { throw new RuntimeException("上传文件为空"); } String
	 * fileName = image.getOriginalFilename(); String extName =
	 * paraseImageExtName(fileName); String[] res =
	 * storageClient1.upload_file(image.getBytes(), extName, null); return null;
	 * }
	 */

	protected String paraseImageExtName(String originFileName) {
		String extName = originFileName.substring(originFileName.lastIndexOf(".") + 1);
		return extName;
	}

	protected String paraseImageFileName(MultipartFile image) {
		if (image == null) {
			throw new RuntimeException("上传文件为空");
		}
		String originalFilename = image.getOriginalFilename();
		return originalFilename.substring(0, originalFilename.lastIndexOf("."));
	}

	// 上传文件到本地
	@Transactional
	public String uploadImageToLocal(MultipartFile image) throws Exception {
		String originFileName = image.getOriginalFilename();
		long size = image.getSize();
		String extName = paraseImageExtName(originFileName);

		// 生成图片名称
		String imageName = UUID.randomUUID().toString();

		// 将图片保存到image文件下
		// 图片存放目录
		String imageFloder = this.getClass().getClassLoader().getResource("").getPath() + "static/image/" + imageName
				+ "." + extName;
		File file = new File(imageFloder);
		FileOutputStream output = new FileOutputStream(file);
		IOUtils.copy(image.getInputStream(), output);

		// 返回访问图片的uri
		URI serviceUri = getServiceUri();
		String uri = serviceUri + "/image/" + imageName + "." + extName;

		// 保存图片信息
		// 生成图片信息
		ImageDetail imageDetail = new ImageDetail();
		imageDetail.setCreateTime(new Date());
		imageDetail.setExtName(extName);
		imageDetail.setName(image.getName());
		imageDetail.setImageName(imageName);
		imageDetail.setSize(size);
		imageDetail.setImageUrl(uri);
		imageDetail.setOwnerId(1);
		imageDetailMapper.insertImageDetail(imageDetail);

		return uri;
	}

	protected URI getServiceUri() {
		ServiceInstance choose = loadBalancerClient.choose(ServiceNameList.XSBK_ARTICLE);
		URI uri = choose.getUri();
		return uri;
	}
}
