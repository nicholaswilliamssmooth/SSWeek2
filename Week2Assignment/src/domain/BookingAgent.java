package domain;

public class BookingAgent {
	private User agentID;
	private Integer bookingID;
	
	public User getAgentID() {
		return agentID;
	}
	public void setAgentID(User agentID) {
		this.agentID = agentID;
	}
	public Integer getBookingID() {
		return bookingID;
	}
	public void setBookingID(Integer bookingID) {
		this.bookingID = bookingID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bookingID == null) ? 0 : bookingID.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookingAgent other = (BookingAgent) obj;
		if (bookingID == null) {
			if (other.bookingID != null)
				return false;
		} else if (!bookingID.equals(other.bookingID))
			return false;
		return true;
	}
	
	
	
}
