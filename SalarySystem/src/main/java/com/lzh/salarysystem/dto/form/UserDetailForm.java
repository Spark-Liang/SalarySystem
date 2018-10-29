package com.lzh.salarysystem.dto.form;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.lzh.salarysystem.infrastructure.persistent.po.UserPersonalInfo;

public class UserDetailForm {
	
	private Long id;
	
	private String username;
	
	private String password;
	
	private Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

	private UserPersonalInfo persionalInfo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Collection<SimpleGrantedAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Collection<SimpleGrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public UserPersonalInfo getPersionalInfo() {
		return persionalInfo;
	}

	public void setPersionalInfo(UserPersonalInfo persionalInfo) {
		this.persionalInfo = persionalInfo;
	}
	
	
}
