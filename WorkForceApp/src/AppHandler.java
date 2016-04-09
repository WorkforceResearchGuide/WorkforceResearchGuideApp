import java.util.List;

public class AppHandler {

	private EntityProcessor eprocessor = new EntityProcessor();
	private TemplateProcessor tprocessor = new TemplateProcessor();
	
	public boolean addEntity(String name, String country, String metric, String timeperiod,String[] file_paths, int[] related_entities, boolean isBelief, String person, String strength, String note) {
		
		return eprocessor.addEntity(name, country, metric, timeperiod, file_paths, related_entities, isBelief, person, strength, note);
	}
	
	public boolean addEntityBatch(String filePath) {
		
		return eprocessor.addEntityBatch(filePath);
	}
	
	public boolean addEntityFolderScan(String folderPath) {
		
		return eprocessor.addEntityFolderScan(folderPath);
	}
	
	public List<Entity> searchEntity(String searchQuery ) {
		
		return eprocessor.searchEntity(searchQuery);
	}

	public Entity searchEntity(int entityId) {
		
		return eprocessor.searchEntity(entityId);
	}

	public boolean deleteEntity(int entityId) {
		
		return eprocessor.deleteEntity(entityId);
	}

	public Entity updateEntity(int entityId, String name, String country, String metric, String timeperiod,String[] file_paths, int[] related_entities, boolean isBelief, String person, String strength, String note) {
		
		return eprocessor.updateEntity(entityId, name, country, metric, timeperiod, file_paths, related_entities, isBelief, person, strength, note);
	}
	
	public boolean addRegion(String region){
		
		return tprocessor.addRegion(region);
	}
	
	public boolean disableRegion(int regionId){
		
		return tprocessor.disableRegion(regionId);
	}
	
	public boolean addMetric(String metric){
		
		return tprocessor.addMetric(metric);
	}
	
	public boolean disableMetric(int metricId){
		
		return tprocessor.disableMetric(metricId);
	}
	
	public boolean addTimeperiod(String timeperiod){
		
		return tprocessor.addTimeperiod(timeperiod);
	}
	
	public boolean disableTimeperiod(int timeperiodId){
		
		return tprocessor.disableTimeperiod(timeperiodId);
	}
	
	public boolean addStrength(String strength){
		
		return tprocessor.addStrength(strength);
	}
	
	public boolean disableStrength(int strengthId){
		
		return tprocessor.disableStrength(strengthId);
	}
	
	public List<Region> retrieveAllRegions(){
		
		return tprocessor.retrieveAllRegions()
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
