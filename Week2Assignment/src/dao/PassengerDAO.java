package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import domain.Booking;
import domain.Passenger;

public class PassengerDAO extends BaseDAO<Passenger> {
	
	public PassengerDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	public void updatePassenger(Passenger passenger) throws ClassNotFoundException, SQLException {
		save("UPDATE passenger SET booking_id = ?, given_name = ?, family_name = ?, dob = ?, gender = ?, address = ? WHERE id = ?",
				new Object[] {passenger.getBookingID().getId(), passenger.getGivenName(), passenger.getFamilyName(), passenger.getDateOfBirth(), passenger.getGender(), passenger.getAddress(), passenger.getId()});
	}

	public void deletePassenger(Passenger passenger) throws ClassNotFoundException, SQLException {
		save("DELETE FROM passenger WHERE id = ?", 
				new Object[] {passenger.getId()});
	}
	
	public void addPassenger(Passenger passenger) throws ClassNotFoundException, SQLException {
		save("INSERT INTO passenger VALUES (?, ?, ?, ?, ?, ?, ?)", 
				new Object[] {passenger.getId(), passenger.getBookingID().getId(), passenger.getGivenName(), passenger.getFamilyName(), passenger.getDateOfBirth(), passenger.getGender(), passenger.getAddress()});
	}
	
	public List<Passenger> readAllPassengers() throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM passenger", null);
	}
	
	public List<Passenger> readPassengerFromBookingID(Passenger passenger) throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM passenger WHERE booking_id = ?", new Object[] {passenger.getBookingID().getId()});
	}
	
	public List<Passenger> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<Passenger> passengers = new ArrayList<>();
		while (rs.next()) {
			Passenger p = new Passenger();
			p.setId(rs.getInt("id"));
			
			Booking b = new Booking();
			b.setId(rs.getInt("booking_id"));
			p.setBookingID(b);
			
			p.setGivenName(rs.getString("given_name"));
			p.setFamilyName(rs.getString("family_name"));
			LocalDate dt = LocalDate.parse(rs.getString("dob"));
			p.setDateOfBirth(dt);
			p.setGender(rs.getString("gender"));
			p.setAddress(rs.getString("address"));
						
			passengers.add(p);
		}
		return passengers;
		
	}	
}
