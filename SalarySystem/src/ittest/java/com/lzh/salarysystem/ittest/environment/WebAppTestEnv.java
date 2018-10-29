package com.lzh.salarysystem.ittest.environment;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.lzh.salarysystem.test.envirnoment.DBUnitTestEnv;

@WebAppContextEnvironmentPlugin
public class WebAppTestEnv extends DBUnitTestEnv{
	
	@Autowired
	private WebApplicationContext webContext;
	
	private MockMvc mockMvc;

	@Before
	public void setUpMvc() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
	}
	
	@Test
	public void loadWebContext() {
		
	}
	
	protected WebApplicationContext getWebContext() {
		return webContext;
	}

	protected MockMvc getMockMvc() {
		return mockMvc;
	}

}
