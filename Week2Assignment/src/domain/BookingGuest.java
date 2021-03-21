package domain;

public class BookingGuest {
	private Integer bookingID;
	private String contactEmail;
	private String phone;
	public Integer getBookingID() {
		return bookingID;
	}
	public void setBookingID(Integer bookingID) {
		this.bookingID = bookingID;
	}
	public String getContactEmail() {
		return contactEmail;
	}
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
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
		BookingGuest other = (BookingGuest) obj;
		if (bookingID == null) {
			if (other.bookingID != null)
				return false;
		} else if (!bookingID.equals(other.bookingID))
			return false;
		return true;
	}
	
	
	
	
	
}
