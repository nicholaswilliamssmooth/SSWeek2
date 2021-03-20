package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.Airplane;
import domain.Airport;
import domain.Flight;
import domain.Route;

public class FlightDAO extends BaseDAO<Flight> {
	public void updateFlight(Flight flight) throws ClassNotFoundException, SQLException {
		save("UPDATE flight SET route_id = ?, airplane_id = ?, departure_time = ?, reserved_seats = ?, seat_price = ?  WHERE id = ?",
				new Object[] {flight.getRouteID(), flight.getAirplaneID(), flight.getDepartureTime(), flight.getReservedSeats(), flight.getSeatPrice(), flight.getId()});
	}

	public void deleteFlight(Flight flight) throws ClassNotFoundException, SQLException {
		save("DELETE FROM flight WHERE id = ?", 
				new Object[] {flight.getId()});
	}
	
	public void addFlight(Flight flight) throws ClassNotFoundException, SQLException {
		save("INSERT INTO flight VALUES (?, ?, ?, ?, ?, ?)", 
				new Object[] {flight.getId(), flight.getRouteID(), flight.getAirplaneID(), flight.getDepartureTime(), flight.getReservedSeats(), flight.getSeatPrice()});
	}
	
	public List<Flight> readAllFlights() throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM flight", null);
	}
	
	public List<Flight> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<Flight> flights = new ArrayList<>();
		while (rs.next()) {
			Flight f = new Flight();
			f.setId(rs.getInt("id"));
			
			Route r = new Route();
			r.setId(rs.getInt("route_id"));
			f.setRouteID(r);
			
			Airplane a = new Airplane();
			a.setId(rs.getInt("airplane_id"));
			f.setAirplaneID(a);
			
			
			
			
			

			flights.add(f);
		}
		return flights;
		
	}	

}
