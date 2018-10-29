package com.lzh.salarysystem.configuration.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "security.login")
public class LoginControllerProperties {
	
	private String loginPageUrl;
	
	private String loginProcessingUrl;
	
	
}
