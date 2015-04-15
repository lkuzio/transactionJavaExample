package com.acme.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

public class JdbcIsolationTransaction {

	DataSource ds;

	public JdbcIsolationTransaction() {
		ds = new JDBCDataSource().getDataSource();
	}

	public void transactionReadUncommitted() throws SQLException {
		// In PostgreSQL READ UNCOMMITTED is treated as READ COMMITTED. !!!!
		System.out.println("********** transactionReadUncommitted:    WARN: In PostgreSQL READ UNCOMMITTED is treated as READ COMMITTED. !!!!");
		Connection conn = null;
		Connection conn2 = null;
		PreparedStatement pstm = null;

		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
			conn2 = ds.getConnection();
			conn2.setAutoCommit(false);
			conn2.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);

			
			String sql = "INSERT INTO users(name,password,group_id) VALUES(?,?,?)";
			String selectQuery = "SELECT * FROM users";
			
			System.out.println("\nSelected before insert: ");
			selectAllRows(conn, selectQuery);
			pstm = conn2.prepareStatement(sql);
			pstm.setString(1, "NameNoGroup");
			pstm.setString(2, "Pa$$w0rd");
			pstm.setLong(3, 0);
			pstm.execute();

			System.out.println("\nSelected after insert: ");
			selectAllRows(conn, selectQuery);
			conn2.commit();
			System.out.println("\nSelected after commit(): ");
			selectAllRows(conn, selectQuery);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pstm.close();
			conn.close();
			conn2.close();
		}
		System.out.println(" \n ********** end transactionReadUncommitted \n");
	}

	private void selectAllRows(Connection conn, String selectQuery)
			throws SQLException {
		PreparedStatement selectPstm;
		selectPstm = conn.prepareStatement(selectQuery);
		selectPstm.execute();
		ResultSet rs = selectPstm.getResultSet();
		while (rs.next()) {
			System.out.print(" |" + rs.getInt("id") + " "
					+ rs.getString("name"));
		}
		selectPstm.close();
	}

	public void transactionReadcommitted() throws SQLException {
		System.out.println("*********** transactionReadcommitted:");
		Connection conn = null;
		Connection conn2 = null;
		PreparedStatement pstm = null;
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			conn2 = ds.getConnection();
			conn2.setAutoCommit(false);
			conn2.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

			String selectQuery = "SELECT * FROM users";
			String sql = "INSERT INTO users(name,password,group_id) VALUES(?,?,?)";

			System.out.println("\nSelected before insert: ");
			selectAllRows(conn, selectQuery);
			pstm = conn2.prepareStatement(sql);
			pstm.setString(1, "NameNoGroup");
			pstm.setString(2, "Pa$$w0rd");
			pstm.setLong(3, 0);
			pstm.execute();

			System.out.println("\nSelected before commit: ");
			selectAllRows(conn, selectQuery);
			conn2.commit();
			System.out.println("\nSelected after commmit: ");
			selectAllRows(conn, selectQuery);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pstm.close();
			conn.close();
			conn2.close();
		}
		System.out.println("\n ************ end transactionReadcommitted\n");
	}

	public void transactionReadRepetable() throws SQLException {
		System.out
				.println("********** transactionReadRepetable: ***************** ");
		Connection conn = null;
		Connection conn2 = null;
		Connection conn3 = null;
		PreparedStatement pstm = null;
		PreparedStatement selectPstm2 = null;
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
			conn2 = ds.getConnection();
			conn2.setAutoCommit(false);
			conn2.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);

			String selectQuery = "SELECT * FROM users";
			String sql = "INSERT INTO users(name,password,group_id) VALUES(?,?,?)";

			System.out.println("\nSelected before insert: ");
			selectAllRows(conn, selectQuery);
			pstm = conn2.prepareStatement(sql);
			pstm.setString(1, "NameNoGroup");
			pstm.setString(2, "Pa$$w0rd");
			pstm.setLong(3, 0);
			pstm.execute();

			System.out.println("\nSelected before commit: (the same transaction) ");
			selectAllRows(conn2, selectQuery);
			conn2.commit();

			System.out.println("\nSelected after commit: ");
			selectAllRows(conn, selectQuery);

			conn3 = ds.getConnection();
			conn3.setAutoCommit(false);
			conn3.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
			selectPstm2 = conn.prepareStatement(selectQuery);
			selectPstm2.execute();
			ResultSet rs2 = selectPstm2.getResultSet();
			System.out.println("\nSelected in 3th transaction after commit(): ");
			while (rs2.next()) {
				System.out.print(" |" + rs2.getInt("id") + " "
						+ rs2.getString("name"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pstm.close();
			conn.close();
			conn2.close();
		}
		System.out
				.println("\n *************** end transactionReadRepetable:\n");
	}

	public void transactionReadSerializable() throws SQLException {
		System.out.println(" ************** transactionReadSerializable:");
		Connection conn = null;
		Connection conn2 = null;
		Connection conn3 = null;
		PreparedStatement pstm = null;
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			conn2 = ds.getConnection();
			conn2.setAutoCommit(false);
			conn2.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

			String selectQuery = "SELECT * FROM users";
			String sql = "INSERT INTO users(name,password,group_id) VALUES(?,?,?)";

			System.out.println("\nSelected before insert: ");
			selectAllRows(conn, selectQuery);
			pstm = conn2.prepareStatement(sql);
			pstm.setString(1, "NameNoGroup");
			pstm.setString(2, "Pa$$w0rd");
			pstm.setLong(3, 0);
			pstm.execute();

			System.out.println("\nSelected after insert: ");
			selectAllRows(conn, selectQuery);
			System.out.println("\nSelected after insert (other transaction): ");
			selectAllRows(conn2, selectQuery);
			conn2.commit();


			conn3 = ds.getConnection();
			conn3.setAutoCommit(false);
			conn3.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
			System.out.println("\nSelected after commit: ");
			selectAllRows(conn3, selectQuery);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pstm.close();
			conn.close();
			conn2.close();
		}
		System.out.println("\n ************ end transactionReadSerializable:");
	}

}
