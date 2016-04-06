
public class Strength {
	int strengthId;
	String value;
	boolean isDisabled;
	
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
		
}
