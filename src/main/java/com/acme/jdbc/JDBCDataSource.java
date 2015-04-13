package com.acme.jdbc;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

public class JDBCDataSource {

	public DataSource getDataSource() {

		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName("org.postgresql.Driver");
		ds.setUrl("jdbc:postgresql://localhost:5432/transexample");
		ds.setUsername("postgres");
		ds.setPassword("password");

		return ds;
	}
}
