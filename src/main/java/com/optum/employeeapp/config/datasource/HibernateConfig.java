package com.optum.employeeapp.config.datasource;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableJpaRepositories(basePackages={"com.optum.employeeapp.dao"})
@EnableTransactionManagement
public class HibernateConfig {

	@Autowired 
	Environment env;
	
	private static final String HIBERNATE_SQL_PROPERTY = "hibernate.show_sql";
	private static final String HIBERNATE_DIALECT_PROPERTY = "hibernate.dialect";
	private static final String HIBERNATE_HBM2DDl_PROPERTY = "hibernate.hbm2ddl.auto";
	private static final String HIBERNATE_STATS_PROPERTY = "hibernate.generate_statistics";
	
	@Bean(name = "entityManagerFactory")
	public EntityManagerFactory entityManagerFactory( @Qualifier("dataSource")DataSource dataSource, @Qualifier("jpaVendorAdapter")JpaVendorAdapter jpaVendorAdapter) {
		LocalContainerEntityManagerFactoryBean enitityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		enitityManagerFactory.setDataSource(dataSource);
		enitityManagerFactory.setPackagesToScan("com.optum.employeeapp.models");
		enitityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter);
		enitityManagerFactory.setJpaProperties(getHibernateProperties());
		enitityManagerFactory.afterPropertiesSet();
		return enitityManagerFactory.getObject();
	}

	@Bean(name="jpaVendorAdapter")
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		hibernateJpaVendorAdapter.setShowSql(Boolean.parseBoolean(env.getProperty(HIBERNATE_SQL_PROPERTY)));
		hibernateJpaVendorAdapter.setGenerateDdl(true);
		hibernateJpaVendorAdapter.setDatabasePlatform(env.getProperty(HIBERNATE_DIALECT_PROPERTY));

		return hibernateJpaVendorAdapter;
	}

	
	public Properties getHibernateProperties() {
		Properties properties = new Properties();
		properties.put(HIBERNATE_DIALECT_PROPERTY, env.getProperty(HIBERNATE_DIALECT_PROPERTY));
		properties.put(HIBERNATE_SQL_PROPERTY, env.getProperty(HIBERNATE_SQL_PROPERTY));
		properties.put(HIBERNATE_HBM2DDl_PROPERTY, env.getProperty(HIBERNATE_HBM2DDl_PROPERTY));
		properties.put(HIBERNATE_STATS_PROPERTY, env.getProperty(HIBERNATE_STATS_PROPERTY));
		return properties;
	}
	
	@Bean(name="transactionManager")
    JpaTransactionManager transactionManager(@Qualifier("entityManagerFactory")EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }


}
