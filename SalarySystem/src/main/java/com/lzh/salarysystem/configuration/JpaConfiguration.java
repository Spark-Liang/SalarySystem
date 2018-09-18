package com.lzh.salarysystem.configuration;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
@PropertySource("classpath:/static/DB/database-config.properties")
@EnableConfigurationProperties(JpaProperties.class)
public class JpaConfiguration {
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private JpaProperties jpaProperties;
	
	@Bean
	public EntityManagerFactory entityManagerFactory() {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        //此处com.example.*.model是你的java bean所在的包名
        factory.setPackagesToScan("com.lzh.salarysystem.entity");
        factory.setDataSource(dataSource);
        vendorAdapter.setShowSql(jpaProperties.isShowSql());
        factory.setJpaPropertyMap(jpaProperties.getProperties());
        factory.afterPropertiesSet();
        return factory.getObject();
	}
}
