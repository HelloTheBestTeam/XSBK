package com.xsbk.article.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

public class TestUpload {
	public static void main(String[] args) throws Exception {
		TestUpload tp = new TestUpload();
		String fdfsConfFilePath = tp.getClass().getClassLoader().getResource("").getPath() + "Fdfs.conf";
		System.out.println(fdfsConfFilePath);
		File file = new File(fdfsConfFilePath);
		boolean exists = file.exists();
		ClientGlobal.initByTrackers("192.168.43.163:22122");
		TrackerClient tc = new TrackerClient();
		TrackerServer trackerServer = tc.getConnection();
		StorageServer storageServer = tc.getStoreStorage(trackerServer);
		StorageClient1 client = new StorageClient1(trackerServer, storageServer);
		File image = new File("D:/demo-1-bg.jpg");
		byte[] bytes = image.toString().getBytes();
		String[] upload_file = client.upload_file(bytes, "jpg", null);
		for (String string : upload_file) {
			System.out.println(string);
		}
	}
}
