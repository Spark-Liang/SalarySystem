package com.lzh.salarysystem.common.util;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.pool2.KeyedPooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

public class FTPClientFactory implements KeyedPooledObjectFactory<String, FTPClient>{

	private final String host;
	
	private Integer port;
	
	private final String username;
	
	private final String password;
	
	public FTPClientFactory(String host, String username, String password) {
		super();
		this.host = host;
		this.username = username;
		this.password = password;
	}

	@Override
	public PooledObject<FTPClient> makeObject(String key) throws Exception {
		FTPClient newFTP = new FTPClient();
		if(port != null) {
			newFTP.connect(host, port);
		}else {
			newFTP.connect(host);
		}
		newFTP.login(username, password);
		if(!newFTP.changeWorkingDirectory(key)) {
			newFTP.makeDirectory(key);
			newFTP.changeWorkingDirectory(key);
		}
		return new DefaultPooledObject<FTPClient>(newFTP);
	}

	@Override
	public void destroyObject(String key, PooledObject<FTPClient> p) throws Exception {
		FTPClient ftpClient = p.getObject();
		if(ftpClient != null) {
			ftpClient.disconnect();
		}
	}

	@Override
	public boolean validateObject(String key, PooledObject<FTPClient> p) {
		FTPClient ftpClient = p.getObject();
		
		if(ftpClient == null || !ftpClient.isAvailable()) {
			return false;
		}
		
		if(port != null) {
			if (ftpClient.getRemotePort() != port) {
				return false;
			}
		}else {
			if(ftpClient.getDefaultPort() != ftpClient.getRemotePort()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void activateObject(String key, PooledObject<FTPClient> p) throws Exception {
		
	}

	@Override
	public void passivateObject(String key, PooledObject<FTPClient> p) throws Exception {
		
	}
	
	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	
}
