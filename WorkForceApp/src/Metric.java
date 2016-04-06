
public class Metric {
	int metricId;
	String value;
	boolean isDisabled;

	public Metric() {
	
	}
	
	public Metric(int id, String value, boolean isDisabled) {
		this.metricId = id;
		this.value = value;
		this.isDisabled = isDisabled;
	}
	
	public int getMetricId() {
		return metricId;
	}
	
	public void setMetricId(int metricId) {
		this.metricId = metricId;
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
