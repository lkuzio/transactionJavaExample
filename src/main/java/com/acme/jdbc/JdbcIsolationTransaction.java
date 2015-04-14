package com.acme.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

public class JdbcIsolationTransaction {

	DataSource ds;
	
	public JdbcIsolationTransaction()
	{
		ds = new JDBCDataSource().getDataSource();
	}
	
	public void transactionReadUncommitted() throws SQLException
	{
		System.out.println("transactionReadUncommitted:");
		Connection conn = null;
		Connection conn2 = null;
		PreparedStatement pstm = null;
		PreparedStatement selectPstm = null;
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
			conn2 = ds.getConnection();
			conn2.setAutoCommit(false);
			conn2.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
			
			String selectQuery="SELECT * FROM users";
			String sql="INSERT INTO users(name,password,group_id) VALUES(?,?,?)";
			
			selectPstm=conn.prepareStatement(selectQuery);
			selectPstm.execute();
			ResultSet rs= selectPstm.getResultSet();
			System.out.println("Selected before insert: ");
			while(rs.next())
			{
				System.out.print(" |"+rs.getInt("id")+" "+rs.getString("name"));
			}
			System.out.println("");
			pstm= conn.prepareStatement(sql);
			pstm.setString(1, "NameNoGroup");
			pstm.setString(2, "Pa$$w0rd");
			pstm.setLong(3, 0);
			pstm.execute();		
			
			System.out.println("Selected after insert: ");
			selectPstm=conn.prepareStatement(selectQuery);
			selectPstm.execute();
			rs= selectPstm.getResultSet();
			while(rs.next())
			{
				System.out.print(" |"+rs.getInt("id")+" "+rs.getString("name"));
			}
			conn.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			pstm.close();
			conn.close();
		}
		System.out.println(" \n end transactionReadUncommitted \n");
	}
	
	
	public void transactionReadcommitted() throws SQLException
	{
		System.out.println("transactionReadcommitted:");
		Connection conn = null;
		Connection conn2 = null;
		PreparedStatement pstm = null;
		PreparedStatement selectPstm = null;
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			conn2 = ds.getConnection();
			conn2.setAutoCommit(false);
			conn2.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			
			String selectQuery="SELECT * FROM users";
			String sql="INSERT INTO users(name,password,group_id) VALUES(?,?,?)";
			
			selectPstm=conn.prepareStatement(selectQuery);
			selectPstm.execute();
			ResultSet rs= selectPstm.getResultSet();
			System.out.println("Selected before insert: ");
			while(rs.next())
			{
				System.out.print(" |"+rs.getInt("id")+" "+rs.getString("name"));
			}
			System.out.println("");
			pstm= conn.prepareStatement(sql);
			pstm.setString(1, "NameNoGroup");
			pstm.setString(2, "Pa$$w0rd");
			pstm.setLong(3, 0);
			pstm.execute();		
			
			selectPstm=conn.prepareStatement(selectQuery);
			selectPstm.execute();
			rs= selectPstm.getResultSet();
			System.out.println("Selected after insert: ");
			while(rs.next())
			{
				System.out.print(" |"+rs.getInt("id")+" "+rs.getString("name"));
			}
			conn.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			pstm.close();
			conn.close();
		}
		System.out.println("\nend transactionReadcommitted\n");
	}
	
	public void transactionReadRepetable() throws SQLException
	{
		System.out.println("transactionReadRepetable:");
		Connection conn = null;
		Connection conn2 = null;
		PreparedStatement pstm = null;
		PreparedStatement selectPstm = null;
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
			conn2 = ds.getConnection();
			conn2.setAutoCommit(false);
			conn2.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
			
			String selectQuery="SELECT * FROM users";
			String sql="INSERT INTO users(name,password,group_id) VALUES(?,?,?)";
			
			selectPstm=conn.prepareStatement(selectQuery);
			selectPstm.execute();
			ResultSet rs= selectPstm.getResultSet();
			System.out.println("Selected before insert: ");
			while(rs.next())
			{
				System.out.print(" |"+rs.getInt("id")+" "+rs.getString("name"));
			}
			System.out.println("");
			pstm= conn.prepareStatement(sql);
			pstm.setString(1, "NameNoGroup");
			pstm.setString(2, "Pa$$w0rd");
			pstm.setLong(3, 0);
			pstm.execute();		
			
			selectPstm=conn.prepareStatement(selectQuery);
			selectPstm.execute();
			rs= selectPstm.getResultSet();
			System.out.println("Selected after insert: ");
			while(rs.next())
			{
				System.out.print(" |"+rs.getInt("id")+" "+rs.getString("name"));
			}
			conn.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			pstm.close();
			conn.close();
		}
		System.out.println("\nend transactionReadRepetable:\n");
	}
	
	public void transactionReadSerializable() throws SQLException
	{
		System.out.println("transactionReadSerializable:");
		Connection conn = null;
		Connection conn2 = null;
		PreparedStatement pstm = null;
		PreparedStatement selectPstm = null;
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			conn2 = ds.getConnection();
			conn2.setAutoCommit(false);
			conn2.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			
			String selectQuery="SELECT * FROM users";
			String sql="INSERT INTO users(name,password,group_id) VALUES(?,?,?)";
			
			selectPstm=conn.prepareStatement(selectQuery);
			selectPstm.execute();
			ResultSet rs= selectPstm.getResultSet();
			System.out.print("Selected before insert: ");
			while(rs.next())
			{
				System.out.print(" |"+rs.getInt("id")+" "+rs.getString("name"));
			}
			System.out.println("");
			pstm= conn.prepareStatement(sql);
			pstm.setString(1, "NameNoGroup");
			pstm.setString(2, "Pa$$w0rd");
			pstm.setLong(3, 0);
			pstm.execute();		
			
			selectPstm=conn.prepareStatement(selectQuery);
			selectPstm.execute();
			rs= selectPstm.getResultSet();
			System.out.print("Selected after insert: ");
			while(rs.next())
			{
				System.out.print(" |"+rs.getInt("id")+" "+rs.getString("name"));
			}
			conn.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			pstm.close();
			conn.close();
		}
		System.out.println("end transactionReadSerializable:");
	}
	
	
}
