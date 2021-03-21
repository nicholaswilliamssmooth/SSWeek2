package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.UserRole;

public class UserRoleDAO extends BaseDAO<UserRole> {

	public void updateUserRole(UserRole userRole) throws ClassNotFoundException, SQLException {
		save("UPDATE user_role SET name = ? WHERE id = ?",
				new Object[] {userRole.getName(), userRole.getId()});
	}

	public void deleteUserRole(UserRole userRole) throws ClassNotFoundException, SQLException {
		save("DELETE FROM user_role WHERE id = ?", 
				new Object[] {userRole.getId()});
	}
	
	public void addUserRole(UserRole userRole) throws ClassNotFoundException, SQLException {
		save("INSERT INTO user_role VALUES (?, ?)", 
				new Object[] {userRole.getId(), userRole.getName()});
	}
	
	public List<UserRole> readAllUserRole() throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM user_role", null);
	}
	
	public List<UserRole> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<UserRole> userRoles = new ArrayList<>();
		while (rs.next()) {
			UserRole u = new UserRole();
			u.setId(rs.getInt("id"));
			u.setName(rs.getString("name"));
			
			userRoles.add(u);
		}
		return userRoles;
		
	}	
	
}
