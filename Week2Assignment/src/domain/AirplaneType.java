package domain;

public class AirplaneType {
	
	private Integer maxCapacity;
	private Integer firstClass;
	private Integer businessClass;
	
	public Integer getFirstClass() {
		return firstClass;
	}
	public void setFirstClass(Integer firstClass) {
		this.firstClass = firstClass;
	}
	public Integer getBusinessClass() {
		return businessClass;
	}
	public void setBusinessClass(Integer businessClass) {
		this.businessClass = businessClass;
	}
	private Integer id;
	
	public Integer getMaxCapacity() {
		return maxCapacity;
	}
	public void setMaxCapacity(int maxCapacity) {
		this.maxCapacity = maxCapacity;
	}
	public Integer getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		AirplaneType other = (AirplaneType) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
