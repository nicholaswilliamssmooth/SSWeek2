package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
	private static final String driver = "com.mysql.cj.jdbc.Driver";
	private static final String url = "jdbc:mysql://localhost:3306/utopia";
	private static final String username = "root";
	private static final String password = "john15";
	
	public Connection getConnection() throws ClassNotFoundException, SQLException {
		// Read from our one database
		Class.forName(driver);
		// Create Connection
		Connection conn = DriverManager.getConnection(url, username, password);
		conn.setAutoCommit(Boolean.FALSE);
		return conn;
	}
}
