package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.AirplaneType;

public class AirplaneTypeDAO extends BaseDAO<AirplaneType> {
	public void updateAirplane(AirplaneType airplaneType) throws ClassNotFoundException, SQLException {
		save("UPDATE airplane SET type_id = ? WHERE id = ?",
				new Object[] {airplaneType.getMaxCapacity(), airplaneType.getId()});
	}

	public void deleteRoute(AirplaneType airplaneType) throws ClassNotFoundException, SQLException {
		save("DELETE FROM airplane WHERE id = ?", 
				new Object[] {airplaneType.getId()});
	}
	
	public void addRoute(AirplaneType airplaneType) throws ClassNotFoundException, SQLException {
		save("INSERT INTO airplane VALUES (?, ?)", 
				new Object[] {airplaneType.getId(), airplaneType.getMaxCapacity()});
	}
	
	public List<AirplaneType> readAllRoutes() throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM airplane", null);
	}
	
	public List<AirplaneType> readRouteByID(AirplaneType airplane) throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM airplane WHERE id = ?", new Object[] {airplane.getId()});
	}

	
	public List<AirplaneType> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<AirplaneType> planeTypes = new ArrayList<>();
		while (rs.next()) {
			AirplaneType a = new AirplaneType();
			a.setId(rs.getInt("id"));
			a.setMaxCapacity(rs.getInt("max_capacity"));
			planeTypes.add(a);
		}
		return planeTypes;
		
	}
}
