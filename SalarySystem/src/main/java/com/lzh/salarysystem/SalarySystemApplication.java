package com.lzh.salarysystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAutoConfiguration(exclude={
		DataSourceAutoConfiguration.class
})
@EnableTransactionManagement
@EntityScan(basePackageClasses = {
	Jsr310JpaConverters.class
})
public class SalarySystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalarySystemApplication.class, args);
	}
}
