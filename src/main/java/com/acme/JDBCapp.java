package com.acme;

import java.sql.SQLException;
import com.acme.jdbc.AutoCommitFalse;
import com.acme.jdbc.AutoCommitTrue;

public class JDBCapp {

	public static void main(String[] args) {

		AutoCommitFalse aCF = new AutoCommitFalse();
		AutoCommitTrue aCT = new AutoCommitTrue();

		try {
			aCF.saveWithoutCommit();
			aCF.saveWithRollback();
			aCF.saveTreeUserCommitAfterSecond();
			aCF.saveTreeUserCommitAfterSecondRollbackAfterLast();
			aCF.saveUserException();
			aCF.saveTreeUserWithSavepoints();
			
			aCT.saveTreeUserCommitAfterSecond();
			aCT.saveUserWithoutGroup();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	private static void deleteUser(Connection conn, User user)
			throws SQLException {
		String updateQuery = "DELETE FROM users WHERE id=? ";
		PreparedStatement ustm = null;
		Savepoint save1 = null;
		try {
			save1 = conn.setSavepoint();
			ustm = conn.prepareStatement(updateQuery);
			ustm.setLong(1, user.getId());
			ustm.execute();
		} catch (SQLException e) {
			conn.rollback(save1);
			e.printStackTrace();
		} finally {
			ustm.close();
		}
	}

	private static void updateUser(Connection conn, User user)
			throws SQLException {
		String updateQuery = "UPDATE users SET name=?, password=?, group_id=? WHERE id=? ";
		PreparedStatement ustm = null;
		Savepoint save1 = null;
		try {
			save1 = conn.setSavepoint();
			ustm = conn.prepareStatement(updateQuery);
			ustm.setString(1, user.getName());
			ustm.setString(2, user.getPassword());
			ustm.setLong(3, user.getGroup().getId());
			ustm.setLong(4, user.getId());
			ustm.execute();
		} catch (SQLException e) {
			conn.rollback(save1);
			e.printStackTrace();
		} finally {
			ustm.close();
		}
	}

	private static void insertUser(Connection conn, User user)
			throws SQLException {
		Savepoint save1 = null;
		PreparedStatement prstm = null;
		try {
			save1 = conn.setSavepoint();
			String insertQuery = "INSERT INTO users(name,password,group_id) VALUES(?,?,?) ";
			prstm = conn.prepareStatement(insertQuery);
			prstm.setString(1, user.getName());
			prstm.setString(2, user.getPassword());
			prstm.setLong(3, user.getGroup().getId());
			prstm.execute();

			Statement stm = conn.createStatement();
			stm.execute("select currval('users_seq')");
			ResultSet rs = stm.getResultSet();
			rs.next();
			user.setId(rs.getLong(1));
		} catch (SQLException e) {
			conn.rollback(save1);
			e.printStackTrace();
		} finally {
			prstm.close();
		}
	}

	private static Set<User> getUsersList(Connection conn) {
		Statement stmt = null;
		Set<User> users = new HashSet<User>();
		String sql = "Select * from users";
		try {
			stmt = conn.createStatement();

			if (stmt.execute(sql)) {
				ResultSet rs = stmt.getResultSet();

				while (rs.next()) {
					User tmpUser = new User();
					tmpUser.setId(rs.getLong("id"));
					tmpUser.setName(rs.getString("name"));
					tmpUser.setPassword(rs.getString("password"));
					users.add(tmpUser);
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return users;
	}
*/
}
