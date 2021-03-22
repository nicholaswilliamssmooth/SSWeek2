package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import domain.Airplane;
import domain.Airport;
import domain.Flight;
import domain.Route;

public class FlightDAO extends BaseDAO<Flight> {
	public FlightDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	public void updateFlight(Flight flight) throws ClassNotFoundException, SQLException {
		save("UPDATE flight SET route_id = ?, airplane_id = ?, departure_time = ?, arrival_time = ?, reserved_seats = ?, reserved_first = ?, reserved_business = ?, seat_price = ?  WHERE id = ?",
				new Object[] {flight.getRouteID().getId(), flight.getAirplaneID().getId(), flight.getDepartureTime(), flight.getArrivalTime(), flight.getReservedSeats(), flight.getReservedFirstClass(), flight.getReservedBusiness(), flight.getSeatPrice(), flight.getId()});
	}

	public void deleteFlight(Flight flight) throws ClassNotFoundException, SQLException {
		save("DELETE FROM flight WHERE id = ?", 
				new Object[] {flight.getId()});
	}
	
	public void addFlight(Flight flight) throws ClassNotFoundException, SQLException {
		save("INSERT INTO flight VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", 
				new Object[] {flight.getId(), flight.getRouteID().getId(), flight.getAirplaneID().getId(), flight.getDepartureTime(), flight.getArrivalTime(), flight.getReservedSeats(), flight.getReservedFirstClass(), flight.getReservedBusiness(), flight.getSeatPrice()});
	}
	
	public List<Flight> readAllFlights() throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM flight", null);
	}
	
	public List<Flight> readFlightbyID(Flight flight) throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM flight where id = ?", new Object[] {flight.getId()});
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
			
			// split the date and time by the space and parse the localdatetime with it
			String[] departSplit = rs.getString("departure_time").split(" ");
			LocalDateTime dt = LocalDateTime.of(LocalDate.parse(departSplit[0]), LocalTime.parse(departSplit[1]));
			f.setDepartureTime(dt);
			
			String[] arriveSplit = rs.getString("arrival_time").split(" ");
			dt = LocalDateTime.of(LocalDate.parse(arriveSplit[0]), LocalTime.parse(arriveSplit[1]));
			f.setArrivalTime(dt);
			
			
			f.setReservedSeats(rs.getInt("reserved_seats"));
			f.setReservedFirstClass(rs.getInt("reserved_first"));
			f.setReservedBusiness(rs.getInt("reserved_business"));
			f.setSeatPrice(rs.getDouble("seat_price"));
			
			flights.add(f);
		}
		return flights;
		
	}	

}
