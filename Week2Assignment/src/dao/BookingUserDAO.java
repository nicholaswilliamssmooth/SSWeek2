package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.BookingUser;
import domain.User;

public class BookingUserDAO extends BaseDAO<BookingUser> {
	
	public void updateBookingUser(BookingUser bookingUser) throws ClassNotFoundException, SQLException {
		save("UPDATE booking_user SET user_id = ? WHERE id = ?",
				new Object[] {bookingUser.getUserID().getId(), bookingUser.getBookingID()});
	}

	public void deleteBookingUser(BookingUser bookingUser) throws ClassNotFoundException, SQLException {
		save("DELETE FROM booking_user WHERE id = ?", 
				new Object[] {bookingUser.getBookingID()});
	}
	
	public void addBookingUser(BookingUser bookingUser) throws ClassNotFoundException, SQLException {
		save("INSERT INTO booking_user VALUES (?, ?)", 
				new Object[] {bookingUser.getBookingID(), bookingUser.getUserID().getId()});
	}
	
	public List<BookingUser> readAllBookingUser() throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM booking_user", null);
	}
	
	public List<BookingUser> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<BookingUser> bookingUsers = new ArrayList<>();
		while (rs.next()) {
			BookingUser b = new BookingUser();
			b.setBookingID(rs.getInt("booking_id"));
			
			User u = new User();
			u.setId(rs.getInt("user_id"));
			b.setUserID(u);;
		
			
			bookingUsers.add(b);
		}
		return bookingUsers;
		
	}	
	
	
}
