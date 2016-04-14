package workforceresearch;

import java.util.HashMap;
import java.util.List;

public class AppHandler {

	private EntityProcessor eprocessor = new EntityProcessor();
	private TemplateProcessor tprocessor = new TemplateProcessor();
	
	public boolean addEntity(String name, String country, String metric, String timeperiod,List<String> filePaths, HashMap<Integer, String> relatedEntities, boolean isBelief, String person, String strength, String note) {
		
		return eprocessor.addEntity(name, country, metric, timeperiod, filePaths, relatedEntities, isBelief, person, strength, note);
	}
	
	public boolean addEntityBatch(String filePath) {
		
		return eprocessor.addEntityBatch(filePath);
	}
	
	public boolean addEntityFolderScan(String folderPath) {
		
		return eprocessor.addEntityFolderScan(folderPath);
	}
	
	public List<Entity> searchEntity(String searchQuery, String region, String metric, String timeperiod) {
		
		return eprocessor.searchEntity(searchQuery, region, metric, timeperiod);
	}

	public Entity searchEntity(int entityId) {
		
		return eprocessor.searchEntity(entityId);
	}

	public boolean deleteEntity(int entityId) {
		
		return eprocessor.deleteEntity(entityId);
	}

	public Entity updateEntity(int entityId, String name, String country, String metric, String timeperiod,List<String> filePaths, HashMap<Integer, String> relatedEntities, boolean isBelief, String person, String strength, String note) {
		
		return eprocessor.updateEntity(entityId, name, country, metric, timeperiod, filePaths, relatedEntities, isBelief, person, strength, note);
	}
	
	public List<Entity> retrieveAllEntities(){
		
		return eprocessor.retrieveAllEntities();
	}
	
	public boolean addRegion(String region){
		
		return tprocessor.addRegion(region);
	}
	
	public boolean disableRegion(String regionValue){
		
		return tprocessor.disableRegion(regionValue);
	}
	
	public boolean addMetric(String metric){
		
		return tprocessor.addMetric(metric);
	}
	
	public boolean disableMetric(String metricValue){
		
		return tprocessor.disableMetric(metricValue);
	}
	
	public boolean addTimeperiod(String timeperiod){
		
		return tprocessor.addTimeperiod(timeperiod);
	}
	
	public boolean disableTimeperiod(String timeperiodValue){
		
		return tprocessor.disableTimeperiod(timeperiodValue);
	}
	
	public boolean addStrength(String strength){
		
		return tprocessor.addStrength(strength);
	}
	
	public boolean disableStrength(String strengthValue){
		
		return tprocessor.disableStrength(strengthValue);
	}
	
	public List<Region> retrieveAllRegions(){
		
		return tprocessor.retrieveAllRegions();
	}
	
	public List<Metric> retrieveAllMetrics(){
		
		return tprocessor.retrieveAllMetrics();
	}
	
	public List<Timeperiod> retrieveAllTimeperiods(){
		
		return tprocessor.retrieveAllTimeperiods();
	}
	
	public List<Strength> retrieveAllStrengths(){
		
		return tprocessor.retrieveAllStrengths();
	}
	
	public List<Region> retrieveEnabledRegions(){
		
		return tprocessor.retrieveEnabledRegions();
	}
	
	public List<Metric> retrieveEnabledMetrics(){
		
		return tprocessor.retrieveEnabledMetrics();
	}
	
	public List<Timeperiod> retrieveEnabledTimeperiods(){
		
		return tprocessor.retrieveEnabledTimeperiods();
	}
	
	public List<Strength> retrieveEnabledStrengths(){
		
		return tprocessor.retrieveEnabledStrengths();
	}
}
