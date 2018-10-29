package com.lzh.salarysystem.infrastructure.entityfactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lzh.salarysystem.domain.entity.UserDetailEntity;
import com.lzh.salarysystem.domain.entity.UserDetailEntity.UserDetailEntityContext;
import com.lzh.salarysystem.dto.form.UserDetailForm;
import com.lzh.salarysystem.infrastructure.persistent.po.entity.UserDetailPO;

@Component
public class UserDetailEntityFactory {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UserDetailEntityContext userDetailEntityContext;
	
	public UserDetailEntity buildEntity(UserDetailForm form) {
		UserDetailPO po = new UserDetailPO();
		try {
			BeanUtils.copyProperties(form, po);
		} catch (BeansException e) {
			logger.info("fail to build UserDetailPO from UserDetailForm,reason is :{}",e);
		}
		return buildEntity(po);
	}
	
	public UserDetailEntity buildEntity(UserDetailPO po) {
		return new UserDetailEntity(userDetailEntityContext, po);
	}
}
