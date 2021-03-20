package domain;

import java.util.Date;

public class Flight {
	
	private Integer id;
	private Route routeID;
	private Airplane airplaneID;
	private Date departureTime;
	private Integer reservedSeats;
	private Integer seatPrice;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Route getRouteID() {
		return routeID;
	}
	public void setRouteID(Route routeID) {
		this.routeID = routeID;
	}
	public Airplane getAirplaneID() {
		return airplaneID;
	}
	public void setAirplaneID(Airplane airplaneID) {
		this.airplaneID = airplaneID;
	}
	public Date getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(Date departerTime) {
		this.departureTime = departerTime;
	}
	public Integer getReservedSeats() {
		return reservedSeats;
	}
	public void setReservedSeats(Integer reservedSeats) {
		this.reservedSeats = reservedSeats;
	}
	public Integer getSeatPrice() {
		return seatPrice;
	}
	public void setSeatPrice(Integer seatPrice) {
		this.seatPrice = seatPrice;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Flight other = (Flight) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}