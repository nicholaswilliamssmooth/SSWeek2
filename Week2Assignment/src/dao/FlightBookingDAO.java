package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.Booking;
import domain.Flight;
import domain.FlightBooking;

public class FlightBookingDAO extends BaseDAO<FlightBooking> {

	// Since there's no ID on this one, there is not update function

	public void deleteFlightBooking(FlightBooking flightBooking) throws ClassNotFoundException, SQLException {
		save("DELETE FROM flight_booking WHERE booking = ?", 
				new Object[] {flightBooking.getBookingID().getId()});
	}
	
	public void addFlightBooking(FlightBooking flightBooking) throws ClassNotFoundException, SQLException {
		save("INSERT INTO flight_booking VALUES (?, ?)", 
				new Object[] {flightBooking.getBookingID().getId(), flightBooking.getFlightID().getId()});
	}
	
	public List<FlightBooking> readAllFlightBookings() throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM flight_booking", null);
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
