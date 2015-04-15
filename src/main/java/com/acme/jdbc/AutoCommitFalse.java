package com.acme.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Savepoint;

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
	

	public void saveTreeUserCommitAfterSecond() throws SQLException
	{
		Connection conn = null;
		PreparedStatement pstm = null;
		PreparedStatement pstm2 = null;
		PreparedStatement pstm3 = null;
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			String sql="INSERT INTO users(name,password,group_id) VALUES(?,?,?)";
			pstm= conn.prepareStatement(sql);
			pstm.setString(1, "NameNoGroup");
			pstm.setString(2, "Pa$$w0rd");
			pstm.setLong(3, 0);
			pstm.execute();		
	
			pstm2= conn.prepareStatement(sql);
			pstm2.setString(1, "NameNoGroup");
			pstm2.setString(2, "Pa$$w0rd");
			pstm2.setLong(3, 0);
			pstm2.execute();		
			conn.commit();
			pstm3= conn.prepareStatement(sql);
			pstm3.setString(1, "NameNoGroup");
			pstm3.setString(2, "Pa$$w0rd");
			pstm3.setLong(3, 0);
			pstm3.execute();		

		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			pstm.close();
			pstm2.close();
			pstm3.close();
			conn.close();
		}
	}
	

	public void saveTreeUserCommitAfterSecondRollbackAfterLast() throws SQLException
	{
		Connection conn = null;
		PreparedStatement pstm = null;
		PreparedStatement pstm2 = null;
		PreparedStatement pstm3 = null;
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			String sql="INSERT INTO users(name,password,group_id) VALUES(?,?,?)";
			pstm= conn.prepareStatement(sql);
			pstm.setString(1, "NameNoGroup");
			pstm.setString(2, "Pa$$w0rd");
			pstm.setLong(3, 0);
			pstm.execute();		
	
			pstm2= conn.prepareStatement(sql);
			pstm2.setString(1, "NameNoGroup");
			pstm2.setString(2, "Pa$$w0rd");
			pstm2.setLong(3, 0);
			pstm2.execute();		
			conn.commit();
			pstm3= conn.prepareStatement(sql);
			pstm3.setString(1, "NameNoGroup");
			pstm3.setString(2, "Pa$$w0rd");
			pstm3.setLong(3, 0);
			pstm3.execute();		
			conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			pstm.close();
			pstm2.close();
			pstm3.close();
			conn.close();
		}
	}
	
	public void saveUserException() throws SQLException
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
			conn.commit();
//			throw new Exception();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			pstm.close();
			conn.close();
		}
	}
	
	public void saveTreeUserWithSavepoints() throws SQLException
	{
		Connection conn = null;
		PreparedStatement pstm = null;
		PreparedStatement pstm2 = null;

		Savepoint s1=null;
		try {
			
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			String sql="INSERT INTO users(name,password,group_id) VALUES(?,?,?)";
			pstm= conn.prepareStatement(sql);
			pstm.setString(1, "aaa111");
			pstm.setString(2, "Pa$$w0rd");
			pstm.setLong(3, 0);
			pstm.execute();		
			
			s1 = conn.setSavepoint();
			pstm2= conn.prepareStatement(sql);
			pstm2.setString(1, "aaa222");
			pstm2.setString(2, "Pa$$w0rd");
			pstm2.setLong(3, 0);
			pstm2.execute();		
			conn.commit();
			//conn.rollback(s1); //org.postgresql.util.PSQLException: BŁĄD: ROLLBACK TO SAVEPOINT może być użyty tylko w blokach transakcji
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			pstm.close();
			pstm2.close();

			conn.close();
		}
	}
}
