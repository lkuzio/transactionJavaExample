package com.acme.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

public class AutoCommitTrue {

DataSource ds;
	
	public AutoCommitTrue()
	{
		ds = new JDBCDataSource().getDataSource();
	}
	
	public void saveUserWithoutGroup() throws SQLException
	{
		Connection conn = null;
		PreparedStatement pstm = null;
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(true);
			String sql="INSERT INTO users(name,password,group_id) VALUES(?,?,?)";
			pstm= conn.prepareStatement(sql);
			pstm.setString(1, "NameNoGroup");
			pstm.setString(2, "Pa$$w0rd");
			pstm.setLong(3, 0);
			pstm.execute();		
			conn.commit();		//org.postgresql.util.PSQLException: Cannot commit when autoCommit is enabled.
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			pstm.close();
			conn.close();
		}
	}
	
	@SuppressWarnings("resource")
	public void saveTreeUserCommitAfterSecond() throws SQLException
	{
		Connection conn = null;
		PreparedStatement pstm = null;
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(true);
			String sql="INSERT INTO users(name,password,group_id) VALUES(?,?,?)";
			pstm= conn.prepareStatement(sql);
			pstm.setString(1, "NameNoGroup");
			pstm.setString(2, "Pa$$w0rd");
			pstm.setLong(3, 0);
			pstm.execute();		
	
			pstm= conn.prepareStatement(sql);
			pstm.setString(1, "NameNoGroup");
			pstm.setString(2, "Pa$$w0rd");
			pstm.setLong(3, 0);
			pstm.execute();		
			conn.commit();		//org.postgresql.util.PSQLException: Cannot commit when autoCommit is enabled.
			pstm= conn.prepareStatement(sql);
			pstm.setString(1, "NameNoGroup");
			pstm.setString(2, "Pa$$w0rd");
			pstm.setLong(3, 0);
			pstm.execute();		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			pstm.close();
			conn.close();
		}
	}
	
}
