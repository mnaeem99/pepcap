package com.pepcap.inventorymanagement;

import java.sql.SQLException;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import static com.pepcap.inventorymanagement.DatabaseContainerConfig.*;
import liquibase.integration.spring.SpringLiquibase;

@Configuration
@Profile("test")
public class DatasourceConfig {
	
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName("org.postgresql.Driver");
        ds.setUrl(databaseContainer.getJdbcUrl()+"&stringtype=unspecified&currentSchema=inventory_management");
		ds.setUsername(databaseContainer.getUsername());
		ds.setPassword(databaseContainer.getPassword());
		ds.setSchema("inventory_management");
		return ds;
	}

	@Bean
	public SpringLiquibase springLiquibase(DataSource dataSource) throws SQLException {

		tryToCreateSchema(dataSource);
		SpringLiquibase liquibase = new SpringLiquibase();
		liquibase.setDropFirst(true);
		liquibase.setDataSource(dataSource);
		liquibase.setDefaultSchema("inventory_management");
		liquibase.setChangeLog("classpath:/db/changelog/test/db.changelog-testmaster.xml");
		return liquibase;
	}

	private void tryToCreateSchema(DataSource dataSource) throws SQLException {
		String CREATE_SCHEMA_QUERY = "CREATE SCHEMA IF NOT EXISTS inventory_management";
		dataSource.getConnection().createStatement().execute(CREATE_SCHEMA_QUERY);
	}

//	@Bean
//	public ServletWebServerFactory servletWebServerFactory() {
//		return new TomcatServletWebServerFactory();
//	}

}

