import java.util.List;

public class TemplateProcessor {

	DBHandler db = new DBHandler();
	Region r;
	Metric m;
	Timeperiod tp;
	Strength s;
	
	public boolean addRegion(String region){
		
		r = new Region(region, false);
		return db.addRegion(region);
	}
	
	public boolean addMetric(String metric){
		
		m = new Metric(metric, false);
		return db.addMetric(metric);
	}
	
	public boolean addTimeperiod(String timeperiod){
		
		tp = new Timeperiod(timeperiod, false);
		return db.addTimeperiod(timeperiod);
	}
	
	public boolean addStrength(String strength){
		
		s = new Strength(strength, false);
		return db.addStrength(strength);
	}
	
	public boolean disableRegion(String region){
		
		r = new Region(region, true);
		return db.disableRegion(region);
	}
	
	public boolean disableMetric(String metric){
		
		m = new Metric(metric, true);
		return db.disableMetric(metric);
	}
	
	public boolean disableTimeperiod(String timeperiod){
		
		tp = new Timeperiod(timeperiod, true);
		return db.disableTimeperiod(timeperiod);
	}
	
	public boolean disableStrength(String strength){
		
		s = new Strength(strength, true);
		return db.disableStrength(strength);
	}
	
	public List<Region> retrieveAllRegions(){
		
		return db.retrieveAllRegions();
	}
	
	public List<Metric> retrieveAllMetrics(){
		
		return db.retrieveAllMetrics();
	}
	
	public List<Timeperiod> retrieveAllTimeperiods(){
		
		return db.retrieveAllTimeperiods();
	}
	
	public List<Strength> retrieveAllStrengths(){
		
		return db.retrieveAllStrengths();
	}
	
	
}
