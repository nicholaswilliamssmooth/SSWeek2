package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.Booking;
import domain.Flight;
import domain.FlightBooking;

public class FlightBookingDAO extends BaseDAO<FlightBooking> {

	// Since there's no ID on this one, there is not update function

	public FlightBookingDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	public void deleteFlightBooking(FlightBooking flightBooking) throws ClassNotFoundException, SQLException {
		save("DELETE FROM flight_bookings WHERE booking = ?", 
				new Object[] {flightBooking.getBookingID().getId()});
	}
	
	public void addFlightBooking(FlightBooking flightBooking) throws ClassNotFoundException, SQLException {
		save("INSERT INTO flight_bookings VALUES (?, ?)", 
				new Object[] {flightBooking.getFlightID().getId(), flightBooking.getBookingID().getId()});
	}
	
	public List<FlightBooking> readAllFlightBookings() throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM flight_bookings", null);
	}
	
	public List<FlightBooking> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<FlightBooking> flightBookings = new ArrayList<>();
		while (rs.next()) {
			FlightBooking f = new FlightBooking();
			
			Flight fl = new Flight();
			fl.setId(rs.getInt("flight_id"));
			f.setFlightID(fl);
			
			Booking b = new Booking();
			b.setId(rs.getInt("booking_id"));
			f.setBookingID(b);
			
			flightBookings.add(f);
		}
		return flightBookings;
		
	}	
	
}
