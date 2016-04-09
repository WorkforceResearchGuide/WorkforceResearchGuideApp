import java.util.List;

public class TemplateProcessor {

	DBHandler db = new DBHandler();
	Region r = new Region();
	Metric m = new Metric();
	Timeperiod tp = new Timeperiod();
	Strength s = new Strength();
	
	public boolean addRegion(String region){
		
		r.setValue(region);
		r.setDisabled(false);
		return db.addRegion(region);
	}
	
	public boolean addMetric(String metric){
		
		m.setValue(metric);
		m.setDisabled(false);
		return db.addMetric(metric);
	}
	
	public boolean addTimeperiod(String timeperiod){
		
		tp.setValue(timeperiod);
		tp.setDisabled(false);
		return db.addTimeperiod(timeperiod);
	}
	
	public boolean addStrength(String strength){
		
		s.setValue(strength);
		s.setDisabled(false);
		return db.addStrength(strength);
	}
	
	public boolean disableRegion(String region){
		
		return db.disableRegion(region);
	}
	
	public boolean disableMetric(String metric){
		
		return db.disableMetric(metric);
	}
	
	public boolean disableTimeperiod(String timeperiod){
		
		return db.disableTimeperiod(timeperiod);
	}
	
	public boolean disableStrength(String strength){
		
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
