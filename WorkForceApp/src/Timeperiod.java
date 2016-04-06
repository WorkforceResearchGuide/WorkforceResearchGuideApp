
public class Timeperiod {
	int timeperiodId;
	String value;
	boolean isDisabled;
	
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

}
