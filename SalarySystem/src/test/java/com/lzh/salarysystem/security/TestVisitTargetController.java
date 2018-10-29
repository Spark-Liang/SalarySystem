package com.lzh.salarysystem.security;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.Test;

import com.lzh.salarysystem.ittest.environment.WebAppTestEnv;

public class TestVisitTargetController extends WebAppTestEnv{
	
	@Test
	public void testVisit() throws Exception{
		String testUrl = "test/test_security_request";
		String content = getMockMvc()
			.perform(get(testUrl))
			.andReturn().getResponse().getContentAsString()
			;
		System.err.println(content);
	}
}
