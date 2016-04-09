import java.util.ArrayList;
import java.util.List;

public class TemplateProcessor {

	private DBHandler db = new DBHandler();
	private Region r = new Region();
	private Metric m = new Metric();
	private Timeperiod tp = new Timeperiod();
	private Strength s = new Strength();
	
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
	
	public boolean disableRegion(int regionId){
		
		return db.disableRegion(regionId);
	}
	
	public boolean disableMetric(int metricId){
		
		return db.disableMetric(metricId);
	}
	
	public boolean disableTimeperiod(int timeperiodId){
		
		return db.disableTimeperiod(timeperiodId);
	}
	
	public boolean disableStrength(int strengthId){
		
		return db.disableStrength(strengthId);
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
	
	public List<Region> retrieveEnabledRegions(){
		
		List<Region> regionList = new ArrayList<Region>(retrieveAllRegions());
		List<Region> enabledRegionList = new ArrayList<Region>();
		for(Region r : regionList){
			if(!r.isDisabled()){
				enabledRegionList.add(r);
			}
		}
		return enabledRegionList;
	}

	public List<Metric> retrieveEnabledMetrics(){
		
		List<Metric> metricList = new ArrayList<Metric>(retrieveAllMetrics());
		List<Metric> enabledMetricList = new ArrayList<Metric>();
		for(Metric m : metricList){
			if(!m.isDisabled){
				enabledMetricList.add(m);
			}
		}
		return enabledMetricList;
	}

	public List<Timeperiod> retrieveEnabledTimeperiods(){
		
		List<Timeperiod> timperiodList = new ArrayList<Timeperiod>(retrieveAllTimeperiods());
		List<Timeperiod> enabledTimeperiodList = new ArrayList<Timeperiod>();
		for(Timeperiod t : timperiodList){
			if(!t.isDisabled){
				enabledTimeperiodList.add(t);
			}
		}
		return enabledTimeperiodList;
	}

	public List<Strength> retrieveEnabledStrengths(){
		
		List<Strength> strengthList = new ArrayList<Strength>(retrieveAllStrengths());
		List<Strength> enabledStrengthList = new ArrayList<Strength>();
		for(Strength s : strengthList){
			if(!s.isDisabled){
				enabledStrengthList.add(s);
			}
		}
		return enabledStrengthList;
	}

	
}
