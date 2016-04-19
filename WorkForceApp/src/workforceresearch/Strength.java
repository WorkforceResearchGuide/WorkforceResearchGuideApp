package workforceresearch;


public class Strength {
	private int strengthId;
	private String value;
	private boolean isDisabled;
	
	public Strength() {
	
	}
	
	public Strength(int id, String value, boolean isDisabled) {
		this.strengthId = id;
		this.value = value;
		this.isDisabled = isDisabled;
	}
	
	public int getStrengthId() {
		return strengthId;
	}

	public void setStrengthId(int strengthId) {
		this.strengthId = strengthId;
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
		result = prime * result + strengthId;
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
		Strength other = (Strength) obj;
		if (isDisabled != other.isDisabled)
			return false;
		if (strengthId != other.strengthId)
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
		
}
