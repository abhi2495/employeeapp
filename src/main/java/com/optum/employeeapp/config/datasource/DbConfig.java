package com.optum.employeeapp.config.datasource;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DbConfig {
	
	@Bean(name="dataSource")
	@ConfigurationProperties(prefix = "database.config")
    public DataSource lookupDataSource() {
            return DataSourceBuilder.create().build();
    }
}
