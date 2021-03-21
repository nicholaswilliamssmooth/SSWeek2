package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.BookingPayment;


public class BookingPaymentDAO extends BaseDAO<BookingPayment> {

	public void updateBookingPayment(BookingPayment bookingPayment) throws ClassNotFoundException, SQLException {
		save("UPDATE booking_payment SET stripe_id = ?, refunded = ? WHERE id = ?",
				new Object[] {bookingPayment.getStripeID(), bookingPayment.getRefunded(), bookingPayment.getBookingID()});
	}

	public void deleteBookingPayment(BookingPayment bookingPayment) throws ClassNotFoundException, SQLException {
		save("DELETE FROM booking_payment WHERE id = ?", 
				new Object[] {bookingPayment.getBookingID()});
	}
	
	public void addBookingPayment(BookingPayment bookingPayment) throws ClassNotFoundException, SQLException {
		save("INSERT INTO booking_payment VALUES (?, ?, ?)", 
				new Object[] {bookingPayment.getBookingID(), bookingPayment.getStripeID(), bookingPayment.getRefunded()});
	}
	
	public List<BookingPayment> readAllBookingPayments() throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM booking_payment", null);
	}
	
	public List<BookingPayment> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<BookingPayment> bookingPayments = new ArrayList<>();
		while (rs.next()) {
			BookingPayment b = new BookingPayment();
			b.setBookingID(rs.getInt("booking_id"));
			b.setStripeID(rs.getString("stripe_id"));
			b.setRefunded(rs.getInt("refunded"));

			
			bookingPayments.add(b);
		}
		return bookingPayments;
		
	}	
	
}
