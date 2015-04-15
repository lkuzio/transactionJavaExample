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
		System.out.println("********** transactionReadUncommitted:");
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

			String selectQuery = "SELECT * FROM users";
			String sql = "INSERT INTO users(name,password,group_id) VALUES(?,?,?)";

			selectPstm = conn.prepareStatement(selectQuery);
			selectPstm.execute();
			ResultSet rs = selectPstm.getResultSet();
			System.out.println("Selected before insert: ");
			while (rs.next()) {
				System.out.print(" |" + rs.getInt("id") + " "
						+ rs.getString("name"));
			}
			pstm = conn2.prepareStatement(sql);
			pstm.setString(1, "NameNoGroup");
			pstm.setString(2, "Pa$$w0rd");
			pstm.setLong(3, 0);
			pstm.execute();

			System.out.println("Selected after insert: ");
			selectPstm = conn.prepareStatement(selectQuery);
			selectPstm.execute();
			rs = selectPstm.getResultSet();
			while (rs.next()) {
				System.out.print(" |" + rs.getInt("id") + " "
						+ rs.getString("name"));
			}
			conn2.commit();
			System.out.println("Selected after commit(): ");
			selectPstm = conn.prepareStatement(selectQuery);
			selectPstm.execute();
			rs = selectPstm.getResultSet();
			while (rs.next()) {
				System.out.print(" |" + rs.getInt("id") + " "
						+ rs.getString("name"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pstm.close();
			selectPstm.close();
			conn.close();
			conn2.close();
		}
		System.out.println(" \n ********** end transactionReadUncommitted \n");
	}

	public void transactionReadcommitted() throws SQLException {
		System.out.println("*********** transactionReadcommitted:");
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

			String selectQuery = "SELECT * FROM users";
			String sql = "INSERT INTO users(name,password,group_id) VALUES(?,?,?)";

			selectPstm = conn.prepareStatement(selectQuery);
			selectPstm.execute();
			ResultSet rs = selectPstm.getResultSet();
			System.out.println("Selected before insert: ");
			while (rs.next()) {
				System.out.print(" |" + rs.getInt("id") + " "
						+ rs.getString("name"));
			}
			pstm = conn2.prepareStatement(sql);
			pstm.setString(1, "NameNoGroup");
			pstm.setString(2, "Pa$$w0rd");
			pstm.setLong(3, 0);
			pstm.execute();

			selectPstm = conn.prepareStatement(selectQuery);
			selectPstm.execute();
			rs = selectPstm.getResultSet();
			System.out.println("Selected after insert: ");
			while (rs.next()) {
				System.out.print(" |" + rs.getInt("id") + " "
						+ rs.getString("name"));
			}
			conn2.commit();
			selectPstm = conn.prepareStatement(selectQuery);
			selectPstm.execute();
			rs = selectPstm.getResultSet();
			System.out.println("Selected after commit: ");
			while (rs.next()) {
				System.out.print(" |" + rs.getInt("id") + " "
						+ rs.getString("name"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pstm.close();
			selectPstm.close();
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
		PreparedStatement selectPstm = null;
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

			selectPstm = conn.prepareStatement(selectQuery);
			selectPstm.execute();
			ResultSet rs = selectPstm.getResultSet();
			System.out.println("Selected before insert: ");
			while (rs.next()) {
				System.out.print(" |" + rs.getInt("id") + " "
						+ rs.getString("name"));
			}
			pstm = conn2.prepareStatement(sql);
			pstm.setString(1, "NameNoGroup");
			pstm.setString(2, "Pa$$w0rd");
			pstm.setLong(3, 0);
			pstm.execute();

			selectPstm = conn.prepareStatement(selectQuery);
			selectPstm.execute();
			rs = selectPstm.getResultSet();
			System.out.println("Selected after insert: ");
			while (rs.next()) {
				System.out.print(" |" + rs.getInt("id") + " "
						+ rs.getString("name"));
			}
			selectPstm = conn2.prepareStatement(selectQuery);
			selectPstm.execute();
			rs = selectPstm.getResultSet();
			System.out
					.println("Selected after insert (the same transaction): ");
			while (rs.next()) {
				System.out.print(" |" + rs.getInt("id") + " "
						+ rs.getString("name"));
			}
			conn2.commit();

			selectPstm = conn.prepareStatement(selectQuery);
			selectPstm.execute();
			rs = selectPstm.getResultSet();
			System.out.println("\nSelected after commit: ");
			while (rs.next()) {
				System.out.print(" |" + rs.getInt("id") + " "
						+ rs.getString("name"));
			}

			conn3 = ds.getConnection();
			conn3.setAutoCommit(false);
			conn3.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
			selectPstm2 = conn.prepareStatement(selectQuery);
			selectPstm2.execute();
			ResultSet rs2 = selectPstm2.getResultSet();
			System.out.println("Selected in 3th transaction after commit(): ");
			while (rs2.next()) {
				System.out.print(" |" + rs2.getInt("id") + " "
						+ rs2.getString("name"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pstm.close();
			selectPstm.close();
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
		PreparedStatement selectPstm = null;
		PreparedStatement selectPstm2 = null;
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			conn2 = ds.getConnection();
			conn2.setAutoCommit(false);
			conn2.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

			String selectQuery = "SELECT * FROM users";
			String sql = "INSERT INTO users(name,password,group_id) VALUES(?,?,?)";

			selectPstm = conn.prepareStatement(selectQuery);
			selectPstm.execute();
			ResultSet rs = selectPstm.getResultSet();
			System.out.print("Selected before insert: ");
			while (rs.next()) {
				System.out.print(" |" + rs.getInt("id") + " "
						+ rs.getString("name"));
			}
			System.out.println("");
			pstm = conn2.prepareStatement(sql);
			pstm.setString(1, "NameNoGroup");
			pstm.setString(2, "Pa$$w0rd");
			pstm.setLong(3, 0);
			pstm.execute();

			selectPstm = conn.prepareStatement(selectQuery);
			selectPstm.execute();
			rs = selectPstm.getResultSet();
			System.out.println("Selected after insert: ");
			while (rs.next()) {
				System.out.print(" |" + rs.getInt("id") + " "
						+ rs.getString("name"));
			}
			selectPstm = conn2.prepareStatement(selectQuery);
			selectPstm.execute();
			rs = selectPstm.getResultSet();
			System.out
					.println("Selected after insert (the same transaction): ");
			while (rs.next()) {
				System.out.print(" |" + rs.getInt("id") + " "
						+ rs.getString("name"));
			}
			conn.commit();

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
			selectPstm.close();
			conn.close();
			conn2.close();
		}
		System.out.println("\n ************ end transactionReadSerializable:");
	}

}
