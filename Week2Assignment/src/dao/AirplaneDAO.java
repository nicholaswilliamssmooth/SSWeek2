package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.Airplane;
import domain.AirplaneType;
import domain.Airport;
import domain.Route;

public class AirplaneDAO extends BaseDAO<Airplane> {
	public AirplaneDAO(Connection conn) {
		super(conn);
	}

	public void updateAirplane(Airplane airplane) throws ClassNotFoundException, SQLException {
		save("UPDATE airplane SET type_id = ? WHERE id = ?",
				new Object[] {airplane.getAirplaneModel(), airplane.getId()});
	}

	public void deleteAirplane(Airplane airplane) throws ClassNotFoundException, SQLException {
		save("DELETE FROM airplane WHERE id = ?", 
				new Object[] {airplane.getId()});
	}
	
	public void addAirplane(Airplane airplane) throws ClassNotFoundException, SQLException {
		save("INSERT INTO airplane VALUES (?, ?)", 
				new Object[] {airplane.getId(), airplane.getAirplaneModel()});
	}
	
	public List<Airplane> readAllAirplane() throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM airplane", null);
	}
	
	public List<Airplane> readAirplaneByID(Airplane airplane) throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM airplane WHERE id = ?", new Object[] {airplane.getId()});
	}

	
	public List<Airplane> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<Airplane> planes = new ArrayList<>();
		while (rs.next()) {
			Airplane a = new Airplane();
			a.setId(rs.getInt("id"));
			
			AirplaneType type = new AirplaneType();
			type.setId(rs.getInt("type_id"));
			a.setAirplaneModel(type);
			planes.add(a);
		}
		return planes;
		
	}
}
