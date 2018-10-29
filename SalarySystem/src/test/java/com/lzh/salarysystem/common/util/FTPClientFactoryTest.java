package com.lzh.salarysystem.common.util;

import static org.junit.Assert.assertTrue;

import java.io.StringBufferInputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.pool2.PooledObject;
import org.junit.Assert;
import org.junit.Test;

public class FTPClientFactoryTest {
	
	private static final String HOST = "47.106.230.145";
	private static final String USERNAME = "FTPUser";
	private static final String PASSWORD = "Loveuxiaojun0508";
	
	private FTPClientFactory SUT = new FTPClientFactory(HOST, USERNAME, PASSWORD);
	
	@Test
	public void should_return_avaliable_cliean_when_build_a_client_on_un_exists_path() throws Exception{
		String testPath = "/test_un_exists";
		FTPClient ftpClient = new FTPClient();
		ftpClient.connect(HOST);
		ftpClient.login(USERNAME, PASSWORD);
		try {
			ftpClient.dele(testPath);
		}catch (Exception e) {}
		
		PooledObject<FTPClient> result = SUT.makeObject(testPath);
		Assert.assertNotNull(result);
		FTPClient ftpClientResult = result.getObject();
		assertTrue(ftpClientResult.isAvailable());
		ftpClientResult.storeFile("test.txt", new StringBufferInputStream("test"));
	}
}
