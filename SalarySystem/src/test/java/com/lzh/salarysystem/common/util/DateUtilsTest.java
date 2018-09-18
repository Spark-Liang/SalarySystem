package com.lzh.salarysystem.common.util;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;

public class DateUtilsTest {
	
	@Test
	public void should_return_the_right_value() {
		Date a = new Date(1)
			,b = new Date(2)
			,rightResult = new Date(3);
		
		Date result = DateUtils.add(a,b);
		
		assertEquals(rightResult, result);
	}
	
	@Test
	public void should_return_same_value_when_first_param_is_null() {
		Date a = null
			,b = new Date(2);
		
		Date result = DateUtils.add(a,b);
		
		assertEquals(b, result);
	}
	
	@Test
	public void should_return_same_value_when_second_param_is_null() {
		Date a = new Date(1)
			,b = null;
		
		Date result = DateUtils.add(a,b);
		
		assertEquals(a, result);
	}
	
	@Test
	public void should_return_0_value_when_all_param_is_null() {
		Date a = null
			,b = null;
		
		Date result = DateUtils.add(a,b);
		
		assertEquals(new Date(0), result);
	}
	
	@Test
	public void should_return_the_right_value_when_sub() {
		Date a = new Date(1)
			,b = new Date(2)
			,rightResult = new Date(-1);
		
		Date result = DateUtils.sub(a,b);
		
		assertEquals(rightResult, result);
	}
	
	@Test
	public void should_return_same_value_when_the_minuend_is_null() {
		Date a = null
			,b = new Date(2);
		
		Date result = DateUtils.sub(a,b);
		
		assertEquals(new Date(-2), result);
	}
	
	@Test
	public void should_return_same_value_when_subtrahend_is_null() {
		Date a = new Date(1)
			,b = null;
		
		Date result = DateUtils.sub(a,b);
		
		assertEquals(a, result);
	}
	
	@Test
	public void should_return_0_value_when_do_sub_with_all_param_is_null() {
		Date a = null
			,b = null;
		
		Date result = DateUtils.sub(a,b);
		
		assertEquals(new Date(0), result);
	}
}
