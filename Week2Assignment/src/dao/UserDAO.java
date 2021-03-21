package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.Airport;
import domain.Route;
import domain.User;
import domain.UserRole;

public class UserDAO extends BaseDAO<User> {
	public UserDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	public void updateUser(User user) throws ClassNotFoundException, SQLException {
		save("UPDATE user SET role_id= ?, given_name = ?, family_name = ?, username = ?, email = ?, password = ?, phone = ? WHERE id = ?",
				new Object[] {user.getRoleID().getId(), user.getGivenName(), user.getFamilyName(), user.getUsername(), user.getPassword(), user.getPhone(), user.getId()});
	}

	public void deleteUser(User user) throws ClassNotFoundException, SQLException {
		save("DELETE FROM user WHERE id = ?", 
				new Object[] {user.getId()});
	}
	
	public void addUser(User user) throws ClassNotFoundException, SQLException {
		save("INSERT INTO user VALUES (?, ?, ?, ?, ?, ?, ?, ?)", 
				new Object[] {user.getId(), user.getRoleID().getId(), user.getGivenName(), user.getFamilyName(), user.getUsername(), user.getPassword(), user.getPhone()});
	}
	
	public List<User> readUserByLogin(String user, String pass) throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM user WHERE username = ? AND password = ?", new Object[] { user, pass });
	}
	
	public List<User> readAllUsers() throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM user", null);
	}
	
	public List<User> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<User> users = new ArrayList<>();
		while (rs.next()) {
			User u = new User();
			u.setId(rs.getInt("id"));
			
			UserRole r = new UserRole();
			r.setId(rs.getInt("role_id"));
			u.setRoleID(r);
			
			u.setGivenName(rs.getString("given_name"));
			u.setFamilyName(rs.getString("family_name"));
			u.setUsername(rs.getString("username"));
			u.setEmail(rs.getString("email"));
			u.setPassword(rs.getString("password"));
			u.setPhone(rs.getString("phone"));
			
			users.add(u);
		}
		return users;
		
	}	
}
