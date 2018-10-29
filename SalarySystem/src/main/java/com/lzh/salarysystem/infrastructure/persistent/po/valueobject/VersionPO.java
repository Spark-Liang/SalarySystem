package com.lzh.salarysystem.infrastructure.persistent.po.valueobject;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public class VersionPO {
	
	/*---------------------------------- Field Start --------------------------------------*/
	
	private Long version;

	/*----------------------------------  Field End  --------------------------------------*/
	
	/*---------------------------------- logic Start --------------------------------------*/
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("VersionPO [");
		if (version != null) {
			builder.append("version=");
			builder.append(version);
		}
		builder.append("]");
		return builder.toString();
	}
	
	/*----------------------------------- logic End ---------------------------------------*/
	
	/*--------------------------- getter and setter Start ---------------------------------*/
	
	
	@Version
	@Column(name = "version")
	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	/*---------------------------  getter and setter End  ---------------------------------*/
}
