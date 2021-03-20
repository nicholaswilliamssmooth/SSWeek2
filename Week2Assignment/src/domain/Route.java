package domain;

public class Route {
	private Integer id;
	private Airport originID;
	private Airport destinationID;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Airport getOriginID() {
		return originID;
	}
	public void setOriginID(Airport originID) {
		this.originID = originID;
	}
	public Airport getDestinationID() {
		return destinationID;
	}
	public void setDestinationID(Airport destinationID) {
		this.destinationID = destinationID;
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
		Route other = (Route) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
