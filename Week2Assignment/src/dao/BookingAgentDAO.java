package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import domain.BookingAgent;
import domain.User;

public class BookingAgentDAO extends BaseDAO<BookingAgent> {
	
	public BookingAgentDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	public void updateBookingAgent(BookingAgent bookingAgent) throws ClassNotFoundException, SQLException {
		save("UPDATE booking_agent SET agent_id = ? WHERE id = ?",
				new Object[] {bookingAgent.getAgentID().getId(), bookingAgent.getBookingID()});
	}

	public void deleteBookingAgent(BookingAgent bookingAgent) throws ClassNotFoundException, SQLException {
		save("DELETE FROM booking_agent WHERE id = ?", 
				new Object[] {bookingAgent.getBookingID()});
	}
	
	public void addBookingAgent(BookingAgent bookingAgent) throws ClassNotFoundException, SQLException {
		save("INSERT INTO booking_agent VALUES (?, ?)", 
				new Object[] {bookingAgent.getBookingID(), bookingAgent.getAgentID().getId()});
	}
	
	public List<BookingAgent> readAllBookingAgents() throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM booking_agent", null);
	}
	
	public List<BookingAgent> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<BookingAgent> bookingAgents = new ArrayList<>();
		while (rs.next()) {
			BookingAgent b = new BookingAgent();
			b.setBookingID(rs.getInt("booking_id"));
			
			User u = new User();
			u.setId(rs.getInt("user_id"));
			b.setAgentID(u);;
		
			
			bookingAgents.add(b);
		}
		return bookingAgents;
		
	}	
	
}
