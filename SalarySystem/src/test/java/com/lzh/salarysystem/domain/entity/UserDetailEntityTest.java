package com.lzh.salarysystem.domain.entity;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.Whitebox;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lzh.salarysystem.domain.entity.UserDetailEntity.UserDetailEntityContext;
import com.lzh.salarysystem.dto.form.UserDetailForm;
import com.lzh.salarysystem.exception.BusinessException;
import com.lzh.salarysystem.exception.ErrorCodeConstants;
import com.lzh.salarysystem.infrastructure.entityfactory.UserDetailEntityFactory;
import com.lzh.salarysystem.infrastructure.persistent.po.UserPersonalInfo;
import com.lzh.salarysystem.infrastructure.persistent.po.UserPersonalInfo.Gender;
import com.lzh.salarysystem.infrastructure.persistent.po.entity.UserDetailPO;
import com.lzh.salarysystem.repository.UserDetailEntityRepository;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserDetailEntityTest {
	
	private UserDetailEntity SUT;
	
	@Mock
	private UserDetailEntityRepository userDetailEntityRepository;
	
	@InjectMocks
	private UserDetailEntityContext context;
	
	private UserDetailEntityFactory entityFactory = new UserDetailEntityFactory();
	
	@Captor
	private ArgumentCaptor<UserDetailEntity> entityCaptor;
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	@Before
	public void setUp() {
		Whitebox.setInternalState(entityFactory, "userDetailEntityContext", context);
	}
	
	@Test
	public void can_add_a_new_userdetail_to_repository() {
		UserDetailForm form = generateNormalForm();
		SUT = entityFactory.buildEntity(form);
		
		SUT.save();
		
		verify(userDetailEntityRepository,times(1)).save(entityCaptor.capture(), any());
		assertEquals(SUT, entityCaptor.getValue());
	}

	private UserDetailForm generateNormalForm() {
		UserDetailForm form = new UserDetailForm();
		form.setAuthorities(new ArrayList<>());
		form.setPassword("test_password");
		form.setUsername("test_username");
		UserPersonalInfo info = new UserPersonalInfo();
		info.setAge(10);
		info.setGender(Gender.Male);
		info.setName("test_name");
		info.setUserPictureUrl("/test.jpg");
		return form;
	}
	
	@Test
	public void should_throw_USERNAME_ISNULL_when_the_given_form_not_has_usermane() throws Exception {
		BusinessException exception = new BusinessException(ErrorCodeConstants.USERNAME_ISNULL);
		expectedException.expect(is(equalTo(exception)));
		UserDetailForm form = generateNormalForm();
		form.setUsername(null);
		SUT = entityFactory.buildEntity(form);
		
		SUT.save();
	}
	
	@Test
	public void should_throw_USERNAME_IS_DUPLICATED_when_the_given_username_exist_in_DB() throws Exception {
		BusinessException exception = new BusinessException(ErrorCodeConstants.USERNAME_IS_DUPLICATED);
		expectedException.expect(is(equalTo(exception)));
		UserDetailForm form = generateNormalForm();
		String username = form.getUsername();
		when(userDetailEntityRepository.isUsernameDuplicated(username)).thenReturn(true);
		SUT = entityFactory.buildEntity(form);
		
		SUT.save();
	}
	
	@Test
	public void should_throw_PASSWORD_ISNULL_when_the_given_password_is_null() throws Exception {
		BusinessException exception = new BusinessException(ErrorCodeConstants.PASSWORD_ISNULL);
		expectedException.expect(is(equalTo(exception)));
		UserDetailForm form = generateNormalForm();
		form.setPassword(null);
		SUT = entityFactory.buildEntity(form);
		
		SUT.save();
	}
	
	@Test
	public void can_merge_all_properties_from_form_object() {
		UserDetailPO userDetailPO = new UserDetailPO();
		String usernameInPO = "username_in_PO",passwordInPO = "password_in_PO";
		userDetailPO.setUsername(usernameInPO);
		userDetailPO.setPassword(passwordInPO);
		UserPersonalInfo infoInPO = new UserPersonalInfo();
		userDetailPO.setPersionalInfo(infoInPO);
		String nameInInfoInPO = "name_inPO", userPicUrlInInfoInPO = "url_inPO";
		infoInPO.setName(nameInInfoInPO);
		infoInPO.setUserPictureUrl(userPicUrlInInfoInPO);
		SUT = entityFactory.buildEntity(userDetailPO);
		UserDetailForm form = generateNormalForm();
		form.setPassword(null);
		UserPersonalInfo infoInForm = new UserPersonalInfo();
		form.setPersionalInfo(infoInForm);
		String nameInInfoInForm = "nameInform";
		infoInForm.setName(nameInInfoInForm);
		
		SUT.merge(form);
		
		assertEquals(form.getUsername(), userDetailPO.getUsername());
		assertEquals(passwordInPO, userDetailPO.getPassword());
		assertEquals(nameInInfoInForm, userDetailPO.getPersionalInfo().getName());
		assertEquals(userPicUrlInInfoInPO, userDetailPO.getPersionalInfo().getUserPictureUrl());
	}
	
}
