package com.lzh.salarysystem.common.util;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Arrays;

import org.junit.Test;

import com.lzh.salarysystem.dto.form.UserDetailForm;
import com.lzh.salarysystem.exception.ErrorCode;

public class BeanUtilsTest {
	
	@Test
	public void testGetPropertiesDescriptor() throws Exception{
		PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(UserDetailForm.class).getPropertyDescriptors();
		Arrays.asList(propertyDescriptors).forEach(System.out::println);
		System.out.println();
		propertyDescriptors = Introspector.getBeanInfo(ErrorCode.class).getPropertyDescriptors();
		Arrays.asList(propertyDescriptors).forEach(System.out::println);
	}
}
