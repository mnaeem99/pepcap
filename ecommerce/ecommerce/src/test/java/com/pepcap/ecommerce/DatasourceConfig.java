package com.pepcap.ecommerce;

import java.sql.SQLException;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import static com.pepcap.ecommerce.DatabaseContainerConfig.*;
import liquibase.integration.spring.SpringLiquibase;

@Configuration
@Profile("test")
public class DatasourceConfig {
	
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName("org.postgresql.Driver");
        ds.setUrl(databaseContainer.getJdbcUrl()+"&stringtype=unspecified&currentSchema=e_commerce");
		ds.setUsername(databaseContainer.getUsername());
		ds.setPassword(databaseContainer.getPassword());
		ds.setSchema("e_commerce");
		return ds;
	}

	@Bean
	public SpringLiquibase springLiquibase(DataSource dataSource) throws SQLException {

		tryToCreateSchema(dataSource);
		SpringLiquibase liquibase = new SpringLiquibase();
		liquibase.setDropFirst(true);
		liquibase.setDataSource(dataSource);
		liquibase.setDefaultSchema("e_commerce");
		liquibase.setChangeLog("classpath:/db/changelog/test/db.changelog-testmaster.xml");
		return liquibase;
	}

	private void tryToCreateSchema(DataSource dataSource) throws SQLException {
		String CREATE_SCHEMA_QUERY = "CREATE SCHEMA IF NOT EXISTS e_commerce";
		dataSource.getConnection().createStatement().execute(CREATE_SCHEMA_QUERY);
	}

//	@Bean
//	public ServletWebServerFactory servletWebServerFactory() {
//		return new TomcatServletWebServerFactory();
//	}

}

