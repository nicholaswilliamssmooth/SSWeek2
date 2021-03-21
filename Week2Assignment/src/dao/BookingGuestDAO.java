package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.BookingGuest;


public class BookingGuestDAO extends BaseDAO<BookingGuest> {
	
	public BookingGuestDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	public void updateBookingGuest(BookingGuest bookingGuest) throws ClassNotFoundException, SQLException {
		save("UPDATE booking_guest SET contact_email = ?, contact_phone = ? WHERE id = ?",
				new Object[] {bookingGuest.getContactEmail(), bookingGuest.getPhone(), bookingGuest.getBookingID()});
	}

	public void deleteBookingGuest(BookingGuest bookingGuest) throws ClassNotFoundException, SQLException {
		save("DELETE FROM booking_guest WHERE id = ?", 
				new Object[] {bookingGuest.getBookingID()});
	}
	
	public void addBookingGuest(BookingGuest bookingGuest) throws ClassNotFoundException, SQLException {
		save("INSERT INTO booking_guest VALUES (?, ?, ?)", 
				new Object[] {bookingGuest.getBookingID(), bookingGuest.getContactEmail(), bookingGuest.getPhone()});
	}
	
	public List<BookingGuest> readAllBookingGuests() throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM booking_guest", null);
	}
	
	public List<BookingGuest> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<BookingGuest> bookingGuests = new ArrayList<>();
		while (rs.next()) {
			BookingGuest b = new BookingGuest();
			b.setBookingID(rs.getInt("booking_id"));
			b.setContactEmail(rs.getString("contact_email"));
			b.setPhone(rs.getString("contact_phone"));
			
			bookingGuests.add(b);
		}
		return bookingGuests;
		
	}	
	
}
