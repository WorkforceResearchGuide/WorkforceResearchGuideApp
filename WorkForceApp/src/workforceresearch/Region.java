package workforceresearch;


public class Region {
	private int regionId;
	private String value;
	private boolean isDisabled;
	
	public Region() {
	
	}
	
	public Region(int id, String value, boolean isDisabled) {
		this.regionId = id;
		this.value = value;
		this.isDisabled = isDisabled;
	}
	
	public int getRegionId() {
		return regionId;
	}
	
	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public boolean isDisabled() {
		return isDisabled;
	}
	
	public void setDisabled(boolean isDisabled) {
		this.isDisabled = isDisabled;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (isDisabled ? 1231 : 1237);
		result = prime * result + regionId;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Region other = (Region) obj;
		if (isDisabled != other.isDisabled)
			return false;
		if (regionId != other.regionId)
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	
}
