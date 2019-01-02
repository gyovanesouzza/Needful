package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.Log;

public class ConnectionFactory {
	static String drive = "com.mysql.jdbc.Driver";
	

	 private static final String url = "jdbc:mysql://localhost:3306/needful";
	private static final String user = "root";
 private static final String password = "root";

	public static Connection getConnection() throws SQLException, ClassNotFoundException {
				Class.forName(drive);
			return DriverManager.getConnection(url, user, password);
	}

	public static void closeConnection(Connection con) {

		try {
			if (con != null) {
				con.close();
			}
		} catch (SQLException ex) {
			Log.logErro(ex.getMessage(), ex.getCause(), ConnectionFactory.class);
		}
	}

	public static void closeConnection(Connection con, PreparedStatement stmt) {
		closeConnection(con);
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException ex) {
			Log.logErro(ex.getMessage(), ex.getCause(), ConnectionFactory.class);
		}
	}

	public static void closeConnection(Connection con, PreparedStatement stmt, ResultSet rs) {
		closeConnection(con, stmt);
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException ex) {
			Log.logErro(ex.getMessage(), ex.getCause(), ConnectionFactory.class);
		}
	}

}
