
public final class Template {
	
	private static String geography;
	private static String metric;
	private static String timeperiod;
	
	private Template(){
		//default values
		geography="USA";
		metric="0";
		timeperiod="1";
	}
	
	public static void setgeography(String geographyy){
		geography=geographyy;
	}
	
	public static String getgeography(){
		return geography;
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
