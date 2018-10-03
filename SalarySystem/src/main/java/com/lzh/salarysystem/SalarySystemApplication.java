package com.lzh.salarysystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@PropertySource(value = {
	"classpath:/application.properties",
	"classpath:/static/DB/database-config-${spring.profiles.active}.properties"
})
@EntityScan("com.lzh.salarysystem.domain")
@EnableAutoConfiguration(exclude={
		DataSourceAutoConfiguration.class
})
@EnableTransactionManagement
@ComponentScan(
	basePackages = {
			"com.lzh.salarysystem"
	},
	basePackageClasses = {
			Jsr310JpaConverters.class
		}
)
public class SalarySystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalarySystemApplication.class, args);
	}
}
