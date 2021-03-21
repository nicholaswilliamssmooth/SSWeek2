package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.AirplaneType;

public class AirplaneTypeDAO extends BaseDAO<AirplaneType> {
	public AirplaneTypeDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	public void updateAirplaneType(AirplaneType airplaneType) throws ClassNotFoundException, SQLException {
		save("UPDATE airplane_type SET max_capacity = ?, first_class = ?, business_class = ?  WHERE id = ?",
				new Object[] {airplaneType.getMaxCapacity(), airplaneType.getFirstClass(), airplaneType.getBusinessClass(), airplaneType.getId()});
	}

	public void deleteAirplaneType(AirplaneType airplaneType) throws ClassNotFoundException, SQLException {
		save("DELETE FROM airplane_type WHERE id = ?", 
				new Object[] {airplaneType.getId()});
	}
	
	public void addAirplaneType(AirplaneType airplaneType) throws ClassNotFoundException, SQLException {
		save("INSERT INTO airplane_type VALUES (?, ?, ?, ?)", 
				new Object[] {airplaneType.getId(), airplaneType.getMaxCapacity(), airplaneType.getFirstClass(), airplaneType.getBusinessClass()});
	}
	
	public List<AirplaneType> readAllAirplaneType() throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM airplane_type", null);
	}
	
	public List<AirplaneType> readAirplaneTypeByID(AirplaneType airplane) throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM airplane_type WHERE id = ?", new Object[] {airplane.getId()});
	}

	
	public List<AirplaneType> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<AirplaneType> planeTypes = new ArrayList<>();
		while (rs.next()) {
			AirplaneType a = new AirplaneType();
			a.setId(rs.getInt("id"));
			a.setMaxCapacity(rs.getInt("max_capacity"));
			a.setFirstClass(rs.getInt("first_class"));
			a.setBusinessClass(rs.getInt("business_class"));

			planeTypes.add(a);
		}
		return planeTypes;
		
	}
}
