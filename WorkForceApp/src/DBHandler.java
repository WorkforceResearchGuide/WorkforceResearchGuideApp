import java.util.List;


public class DBHandler {
	public  boolean addEntity(Entity entity){
		return true;
	}
	
	public  boolean addEntityBatch(List<Entity> entityList){
		return true;
	}
	
	public  boolean addEntityFolderScan(List<Entity> entityList){
		return true;
	}
	
	public List<Entity> searchEntity(String country,String metric,String timeperiod){
		return null;
	}
	
	public Entity searchEntity(int entityid){
		return null;
	}
	
	public boolean deleteEntity(int entityid){
		return true;		
	}
	
	public boolean updateEntity(Entity entity){
		return true;		
	}
	
	public int getLastEntityIdInDB(){
		return 0;
	}
}
