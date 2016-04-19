package workforceresearch;


public class Timeperiod {
	private int timeperiodId;
	private String value;
	private boolean isDisabled;
	
	public Timeperiod() {
	
	}
	
	public Timeperiod(int id, String value, boolean isDisabled) {
		this.timeperiodId = id;
		this.value = value;
		this.isDisabled = isDisabled;
	}
	
	public int getTimeperiodId() {
		return timeperiodId;
	}

	public void setTimeperiodId(int timeperiodId) {
		this.timeperiodId = timeperiodId;
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
		result = prime * result + timeperiodId;
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
		Timeperiod other = (Timeperiod) obj;
		if (isDisabled != other.isDisabled)
			return false;
		if (timeperiodId != other.timeperiodId)
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}	

}
