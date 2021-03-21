package domain;

public class BookingPayment {
	private Integer bookingID;
	private String stripeID;
	private Integer refunded;
	
	public Integer getBookingID() {
		return bookingID;
	}
	public void setBookingID(Integer bookingID) {
		this.bookingID = bookingID;
	}
	public String getStripeID() {
		return stripeID;
	}
	public void setStripeID(String stripeID) {
		this.stripeID = stripeID;
	}
	public Integer getRefunded() {
		return refunded;
	}
	public void setRefunded(Integer refunded) {
		this.refunded = refunded;
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
		BookingPayment other = (BookingPayment) obj;
		if (bookingID == null) {
			if (other.bookingID != null)
				return false;
		} else if (!bookingID.equals(other.bookingID))
			return false;
		return true;
	}
	
	
}
