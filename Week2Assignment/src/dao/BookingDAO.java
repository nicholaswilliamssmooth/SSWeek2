package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.Booking;
import domain.BookingUser;


public class BookingDAO extends BaseDAO<Booking> {
	
	public BookingDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	public void updateBooking(Booking booking) throws ClassNotFoundException, SQLException {
		save("UPDATE booking SET is_active = ?, confirmation_code = ? WHERE id = ?",
				new Object[] {booking.getIsActive(), booking.getConfirmationCode(), booking.getId()});
	}

	public void deleteBooking(Booking booking) throws ClassNotFoundException, SQLException {
		save("DELETE FROM booking WHERE id = ?", 
				new Object[] {booking.getId()});
	}
	
	public void addBooking(Booking booking) throws ClassNotFoundException, SQLException {
		save("INSERT INTO booking VALUES (?, ?, ?)", 
				new Object[] {booking.getId(), booking.getIsActive(), booking.getConfirmationCode()});
	}
	
	public List<List<Booking>> readAllMatchingID(List<BookingUser> booking) throws ClassNotFoundException, SQLException {
		List<List<Booking>> allMatching = new ArrayList<>();
		for (BookingUser b : booking) {
			if (read("SELECT * FROM booking WHERE id = ?  AND is_active = 1", new Object[] {b.getBookingID()}).size() != 0) {
				allMatching.add(read("SELECT * FROM booking WHERE id = ? AND is_active = 1", new Object[] {b.getBookingID()}));
			}
		}
		return allMatching;
	}
	
	public List<Booking> readAllBookings() throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM booking", null);
	}
	
	public List<Booking> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<Booking> bookings = new ArrayList<>();
		while (rs.next()) {
			Booking b = new Booking();
			b.setId(rs.getInt("id"));
			b.setIsActive(rs.getInt("is_active"));
			b.setConfirmationCode(rs.getString("confirmation_code"));
			
			bookings.add(b);
		}
		return bookings;
		
	}	
}
