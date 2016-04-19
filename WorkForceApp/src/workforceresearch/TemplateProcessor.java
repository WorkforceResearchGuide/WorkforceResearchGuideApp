package workforceresearch;

import java.util.ArrayList;
import java.util.List;

public class TemplateProcessor {

	private DBHandler db = new DBHandler();

	public boolean addRegion(String region) {
		boolean result;
		result = db.addRegion(region);
		result = result || db.enableRegion(region);
		return result;
	}

	public boolean addMetric(String metric) {
		boolean result;
		result = db.addMetric(metric);
		result = result || db.enableMetric(metric);
		return result;
	}

	public boolean addTimeperiod(String timeperiod) {
		boolean result;
		result = db.addTimeperiod(timeperiod);
		result = result || db.enableTimeperiod(timeperiod);
		return result;
	}

	public boolean addStrength(String strength) {
		boolean result;
		result = db.addStrength(strength);
		result = result || db.enableStrength(strength);
		return result;
	}

	public boolean disableRegion(String regionValue) {
		return db.disableRegion(regionValue);
	}

	public boolean disableMetric(String metricValue) {
		return db.disableMetric(metricValue);
	}

	public boolean disableTimeperiod(String timeperiodValue) {
		return db.disableTimeperiod(timeperiodValue);
	}

	public boolean disableStrength(String strengthValue) {
		return db.disableStrength(strengthValue);
	}

	public List<Region> retrieveAllRegions() {
		return db.retrieveAllRegions();
	}

	public List<Metric> retrieveAllMetrics() {
		return db.retrieveAllMetrics();
	}

	public List<Timeperiod> retrieveAllTimeperiods() {
		return db.retrieveAllTimeperiods();
	}

	public List<Strength> retrieveAllStrengths() {
		return db.retrieveAllStrengths();
	}

	public List<Region> retrieveEnabledRegions() {
		List<Region> regionList = retrieveAllRegions();
		List<Region> enabledRegionList = new ArrayList<Region>();
		for (Region r : regionList) {
			if (!r.isDisabled()) {
				enabledRegionList.add(r);
			}
		}
		return enabledRegionList;
	}

	public List<Metric> retrieveEnabledMetrics() {
		List<Metric> metricList = retrieveAllMetrics();
		List<Metric> enabledMetricList = new ArrayList<Metric>();
		for (Metric m : metricList) {
			if (!m.isDisabled()) {
				enabledMetricList.add(m);
			}
		}
		return enabledMetricList;
	}

	public List<Timeperiod> retrieveEnabledTimeperiods() {
		List<Timeperiod> timperiodList = retrieveAllTimeperiods();
		List<Timeperiod> enabledTimeperiodList = new ArrayList<Timeperiod>();
		for (Timeperiod t : timperiodList) {
			if (!t.isDisabled()) {
				enabledTimeperiodList.add(t);
			}
		}
		return enabledTimeperiodList;
	}

	public List<Strength> retrieveEnabledStrengths() {
		List<Strength> strengthList = retrieveAllStrengths();
		List<Strength> enabledStrengthList = new ArrayList<Strength>();
		for (Strength s : strengthList) {
			if (!s.isDisabled()) {
				enabledStrengthList.add(s);
			}
		}
		return enabledStrengthList;
	}
}