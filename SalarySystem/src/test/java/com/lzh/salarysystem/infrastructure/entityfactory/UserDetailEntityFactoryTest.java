package com.lzh.salarysystem.infrastructure.entityfactory;

import java.util.ArrayList;

import org.junit.Test;

import com.lzh.salarysystem.domain.entity.UserDetailEntity;
import com.lzh.salarysystem.dto.form.UserDetailForm;
import com.lzh.salarysystem.infrastructure.persistent.po.UserPersonalInfo;
import com.lzh.salarysystem.infrastructure.persistent.po.UserPersonalInfo.Gender;

public class UserDetailEntityFactoryTest {
	
	private UserDetailEntityFactory SUT = new UserDetailEntityFactory();
	
	@Test
	public void can_build_userDetailEntity_from_userDetailForm() {
		UserDetailForm form = new UserDetailForm();
		form.setAuthorities(new ArrayList<>());
		form.setPassword("test_password");
		form.setUsername("test_username");
		UserPersonalInfo info = new UserPersonalInfo();
		form.setPersionalInfo(info);
		info.setAge(10);
		info.setGender(Gender.Male);
		info.setName("test_name");
		info.setUserPictureUrl("/test.jpg");
		
		UserDetailEntity entity = SUT.buildEntity(form);
		
		System.err.println(entity);
	}
}
