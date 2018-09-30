package com.lzh.salarysystem;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.lzh.salarysystem.annotation.profile.ProfileValue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SalarySystemApplication.class)
@ActiveProfiles(ProfileValue.DEV)
public class SalarySystemApplicationTests {

	@Test
	public void contextLoads() {
	}
	
	
}
