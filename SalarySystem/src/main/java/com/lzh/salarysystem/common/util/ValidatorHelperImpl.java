package com.lzh.salarysystem.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.lzh.salarysystem.service.Validator;

@Component
public class ValidatorHelperImpl implements ValidatorHelper,ApplicationContextAware{
	
	private ApplicationContext context;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}

	@Override
	public <T> Validator<T> getValidator(Class<? extends Validator<T>> clazz) {
		return context.getBean(clazz);
	}

}
