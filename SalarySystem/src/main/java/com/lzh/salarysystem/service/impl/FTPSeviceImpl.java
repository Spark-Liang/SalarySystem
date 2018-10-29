package com.lzh.salarysystem.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Consumer;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lzh.salarysystem.common.util.FTPClientFactory;
import com.lzh.salarysystem.service.FTPService;

@Service
public class FTPSeviceImpl implements FTPService{
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private static final String HOST = "47.106.230.145";
	private static final String USERNAME = "FTPUser";
	private static final String PASSWORD = "Loveuxiaojun0508";
	
	private GenericKeyedObjectPool<String, FTPClient> ftpClientPool = new GenericKeyedObjectPool<>(new FTPClientFactory(HOST, USERNAME, PASSWORD));
	
	@Override
	public void saveFile(String directPath, String filename, InputStream file){
		apply(directPath, ftpClient -> {
			try {
				ftpClient.storeFile(filename, file);
			} catch (IOException e) {
				logger.info("fail to upload file to remote server, reason is {}",e);
				new RuntimeException(e);
			}
		});
	}

	@Override
	public void getFile(String sourcePath, String filename, Consumer<InputStream> consumer){
		apply(sourcePath, ftpClient -> {
			try(InputStream remoteFile = ftpClient.retrieveFileStream(filename);){
				consumer.accept(remoteFile);
			}catch (IOException e) {
				logger.info("fail to retrieve the remote file, reasion is {}",e);
				throw new RuntimeException(e);
			}
		});
	}

	@Override
	public void apply(String directory, Consumer<FTPClient> consumer) {
		FTPClient ftpClient = null;
		try {
			 ftpClient = ftpClientPool.borrowObject(directory);
			 consumer.accept(ftpClient);
		} catch (Exception e) {
			logger.info("fail to retrieve the pool object, exception is {}",e);
			throw new RuntimeException(e);
		}finally {
			if(ftpClient != null) {
				ftpClientPool.returnObject(directory, ftpClient);
			}
		}
	}
	
}
