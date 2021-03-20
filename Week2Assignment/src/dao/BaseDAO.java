package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class BaseDAO<T> {
	
	private static final String driver = "com.mysql.cj.jdbc.Driver";
	private static final String url = "jdbc:mysql://localhost:3306/utopia";
	private static final String username = "root";
	private static final String password = "john15";
	
	public Connection getConnection() throws ClassNotFoundException, SQLException {
		// Read from our one database
		Class.forName(driver);
		// Create Connection
		Connection conn = DriverManager.getConnection(url, username, password);
		return conn;
	}
	
	public void save(String sql, Object[] vals) throws SQLException, ClassNotFoundException {
		PreparedStatement pstmt = getConnection().prepareStatement(sql);
		int count = 1;
		for (Object o : vals) {
			pstmt.setObject(count, o);
			count ++;
		}
		pstmt.executeUpdate();
	}
	
	public List<T> read(String sql, Object[] vals) throws SQLException, ClassNotFoundException {
		PreparedStatement pstmt = getConnection().prepareStatement(sql);
		int count = 1;
		if (vals != null) {
			for (Object o : vals) {
				pstmt.setObject(count, o);
				count ++;
			}
		}
		ResultSet rs = pstmt.executeQuery();
		return extractData(rs);
	}
	
	abstract public List<T> extractData(ResultSet rs) throws ClassNotFoundException, SQLException;
		
}
