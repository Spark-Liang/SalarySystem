package com.lzh.salarysystem.test.envirnoment;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.lzh.salarysystem.SalarySystemApplication;
import com.lzh.salarysystem.annotation.profile.ProfileValue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SalarySystemApplication.class)
@ActiveProfiles(ProfileValue.DEV)
public class SalarySystemApplicationTests implements ApplicationContextAware{

	@Test
	public void contextLoads() {
	}
	
	private ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}
	
	protected ApplicationContext getContext() {
		return this.context;
	}
	
}
