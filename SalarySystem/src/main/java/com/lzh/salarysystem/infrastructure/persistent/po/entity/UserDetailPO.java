package com.lzh.salarysystem.infrastructure.persistent.po.entity;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;

import com.lzh.salarysystem.infrastructure.persistent.converter.AuthoritiesConverter;
import com.lzh.salarysystem.infrastructure.persistent.po.UserPersonalInfo;
import com.lzh.salarysystem.infrastructure.persistent.po.valueobject.VersionPO;

@Entity
public class UserDetailPO extends VersionPO{
	
	
	/*---------------------------------- Field Start --------------------------------------*/
	private Long id;
	
	private String username;
	
	private String password;
	
	private Collection<? extends GrantedAuthority> authorities = new ArrayList<>();

	private UserPersonalInfo persionalInfo;
	/*----------------------------------  Field End  --------------------------------------*/
	
	/*---------------------------------- logic Start --------------------------------------*/
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authorities == null) ? 0 : authorities.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((persionalInfo == null) ? 0 : persionalInfo.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof UserDetailPO))
			return false;
		UserDetailPO other = (UserDetailPO) obj;
		if (authorities == null) {
			if (other.authorities != null)
				return false;
		} else if (!authorities.equals(other.authorities))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (persionalInfo == null) {
			if (other.persionalInfo != null)
				return false;
		} else if (!persionalInfo.equals(other.persionalInfo))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserDetailPO [");
		if (id != null)
			builder.append("id=").append(id).append(", ");
		if (authorities != null)
			builder.append("authorities=").append(authorities).append(", ");
		if (password != null)
			builder.append("password=").append(password).append(", ");
		if (persionalInfo != null)
			builder.append("persionalInfo=").append(persionalInfo).append(", ");
		if (username != null)
			builder.append("username=").append(username);
		builder.append("]");
		return builder.toString();
	}
	/*----------------------------------- logic End ---------------------------------------*/

	/*--------------------------- getter and setter Start ---------------------------------*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(length = 32 ,nullable = false ,unique = true)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(length = 512, nullable = false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Convert(converter = AuthoritiesConverter.class)
	@Column(length = 1024,nullable = false)
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}
	
	@Embedded
	public UserPersonalInfo getPersionalInfo() {
		return persionalInfo;
	}

	public void setPersionalInfo(UserPersonalInfo persionalInfo) {
		this.persionalInfo = persionalInfo;
	}
	/*---------------------------  getter and setter End  ---------------------------------*/
}
