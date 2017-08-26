package com.thesopfactory.thesopmaker.configs;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
Author- Nikhil
Date - Jul 16, 2017
*/
@Configuration
public class ApplicationConfig {

	@Bean ( destroyMethod = "close" )
	public DataSource dataSource() throws SQLException {
		
		HikariConfig hikariConfig = new HikariConfig("/hikari.properties");
		HikariDataSource dataSource = new HikariDataSource(hikariConfig);
		
		return dataSource;
	}
	
}
