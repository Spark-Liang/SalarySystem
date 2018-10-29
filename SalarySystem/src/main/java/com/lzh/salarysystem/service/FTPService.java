package com.lzh.salarysystem.service;

import java.io.InputStream;
import java.util.function.Consumer;

import org.apache.commons.net.ftp.FTPClient;

public interface FTPService {
	
	void saveFile(String directPath, String filename, InputStream file);
	
	void getFile(String sourcePath, String filename, Consumer<InputStream> consumer);
	
	void apply(String directory, Consumer<FTPClient> consumer);
}
