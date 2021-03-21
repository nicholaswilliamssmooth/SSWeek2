package dao;

import domain.Airport;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class AirportDAO extends BaseDAO<Airport> {
	

	
	public AirportDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	public void updateAirport(Airport airport) throws ClassNotFoundException, SQLException {
		save("UPDATE airport SET city = ? WHETE iata_id = ?",
				new Object[] {airport.getCity(), airport.getAirportCode()});
	}

	public void deleteAirport(Airport airport) throws ClassNotFoundException, SQLException {
		save("DELETE FROM airport WHERE iata_id = ?", 
				new Object[] {airport.getAirportCode()});
	}
	
	public void addAirport(Airport airport) throws ClassNotFoundException, SQLException {
		save("INSERT INTO airport VALUES (?, ?)", 
				new Object[] {airport.getAirportCode(), airport.getCity()});
	}
	
	public List<Airport> readAllAirports() throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM airport", null);
	}
	
	public List<Airport> readAirportByCode(Airport airport) throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM airport WHERE iata_id = ?", new Object[] {airport.getAirportCode()});
	}

	public List<Airport> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<Airport> airports = new ArrayList<>();
		while (rs.next()) {
			Airport a = new Airport();
			a.setAirportCode(rs.getString("iata_id"));
			a.setCity(rs.getString("city"));
			airports.add(a);
		}
		return airports;
		
	}
}
