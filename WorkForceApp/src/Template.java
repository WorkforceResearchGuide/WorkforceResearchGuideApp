
public final class Template {
	
	private static String country;
	private static String metric;
	private static String timeperiod;
	
	private Template(){
		//default values
		country="USA";
		metric="0";
		timeperiod="1";
	}
	
	public static void setCountry(String countryy){
		country=countryy;
	}
	
	public static String getCountry(){
		return country;
	}
	
	public static void setMetric(String metricc){
		metric=metricc;
	}
	
	public static String getMetric(){
		return metric;
	}
	
	public static void setTimePeriod(String timeperiodd){
		timeperiod=timeperiodd;
	}
	
	public static String getTimePeriod(){
		return timeperiod;
	}	
}
