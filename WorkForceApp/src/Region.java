
public class Region {
	int regionId;
	String value;
	boolean isDisabled;
	
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
	
}
