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

	public Entity updateEntity(String name, String country, String metric, String timeperiod,String[] file_paths, int[] related_entities, boolean isBelief, String person, String strength, String note) {
		
		return eprocessor.updateEntity(name, country, metric, timeperiod, file_paths, related_entities, isBelief, person, strength, note);
	}
	
	public boolean addTemplate(String fieldName, String value){
		
		return tprocessor.addTemplate(fieldName,value);
	}
	
	public boolean disableTemplate(String fieldName, String value){
		
		return tprocessor.disableTemplate(fieldName,value);
	}
	
	public ArrayList<Template> retrieveAllTemplates(){
		
		return tprocessor.retrieveAllTemplates();
	}
}
