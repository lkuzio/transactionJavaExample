package com.acme.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

public class AutoCommitFalse {

	DataSource ds;
	
	public AutoCommitFalse()
	{
		ds = new JDBCDataSource().getDataSource();
	}
	
	
	public void saveWithoutCommit() throws SQLException
	{
		Connection conn = null;
		PreparedStatement pstm = null;
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			String sql="INSERT INTO users(name,password,group_id) VALUES(?,?,?)";
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
	
	public void saveWithRollback() throws SQLException
	{
		Connection conn = null;
		PreparedStatement pstm = null;
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			String sql="INSERT INTO users(name,password,group_id) VALUES(?,?,?)";
			pstm= conn.prepareStatement(sql);
			pstm.setString(1, "NameNoGroup");
			pstm.setString(2, "Pa$$w0rd");
			pstm.setLong(3, 0);
			pstm.execute();		
			conn.rollback();	
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
			conn.setAutoCommit(false);
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
			conn.commit();
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
	
	@SuppressWarnings("resource")
	public void saveTreeUserCommitAfterSecondRollbackAfterLast() throws SQLException
	{
		Connection conn = null;
		PreparedStatement pstm = null;
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
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
			conn.commit();
			pstm= conn.prepareStatement(sql);
			pstm.setString(1, "NameNoGroup");
			pstm.setString(2, "Pa$$w0rd");
			pstm.setLong(3, 0);
			pstm.execute();		
			conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			pstm.close();
			conn.close();
		}
	}
}
