package workforceresearch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EntityProcessor {

	DBHandler dbhand=new DBHandler();
	
	public boolean addEntity(String name, String region, String metric, String timeperiod,List<String> file_paths, HashMap<Integer, String> related_entities, boolean isBelief, String person, String strength, String note){		

			Region reg = new Region();
			reg.setValue(region);
			reg.setDisabled(false);
			Metric metr = new Metric();
			metr.setValue(metric);
			metr.setDisabled(false);
			Timeperiod tPeriod = new Timeperiod();
			tPeriod.setValue(timeperiod);
			tPeriod.setDisabled(false);
			Strength s = new Strength();
			s.setValue(strength);
			s.setDisabled(false);
			
			Entity entity=new Entity(name,reg,metr,tPeriod,file_paths,related_entities,isBelief,person,s,note);
			dbhand.addEntity(entity);
			return true;		
	}
	
	public boolean addEntityBatch(String filepath){
		try {
			File infile=new File(filepath);
			if(!infile.exists()) {
				return false;
			}

			//read csv file for manipulating string
			FileReader fr=new FileReader(infile);
			BufferedReader br=new BufferedReader(fr);
			String line;
			List<Entity> entityList=new ArrayList<Entity>();
			br.readLine();
			while((line=br.readLine())!=null){
				//Each row in CSV(name,geography,metric,timeperiod,null file paths,related_entities("2","5"),isBelief,person,strengh,note)
				String[] arry=line.split(",");
				String[] arry_str=arry[5].replace("\"", "").split(" ");
				int[] arry_int=new int[arry_str.length];
				HashMap<Integer, String> relEntities = new HashMap<Integer, String>();
				for(int i=0 ;i<arry_str.length;i++){
					relEntities.put(Integer.parseInt(arry_str[i]), null);
				}
				if(arry[0]==null || arry[0].isEmpty()){
					return false;
				}
				if(arry[6].isEmpty()){
					return false;
				}
				if(arry[8]==null || arry[8].isEmpty()){
					return false;
				}
				
				boolean isBelief;
				if(arry[6].equalsIgnoreCase("fact")) isBelief=false;
				else isBelief=true;
				
				TemplateProcessor tp=new TemplateProcessor();
				Region reg = new Region();
				reg.setValue(arry[1]);
				reg.setDisabled(false);
				tp.addRegion(arry[1]);
				Metric metr = new Metric();
				metr.setValue(arry[2]);
				metr.setDisabled(false);
				tp.addMetric(arry[2]);
				Timeperiod tPeriod = new Timeperiod();
				tPeriod.setValue(arry[3]);
				tPeriod.setDisabled(false);
				tp.addTimeperiod(arry[3]);
				Strength s = new Strength();
				s.setValue(arry[8]);
				s.setDisabled(false);
				tp.addStrength(arry[8]);
				
				Entity entity=new Entity(arry[0],reg,metr,tPeriod,null,relEntities,isBelief,arry[7],s,arry[9]);
				entityList.add(entity);
			}
			dbhand.addEntityBatch(entityList);		
			br.close();
			fr.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	//Add entities, each of which associates to one file in the folder path
	public boolean addEntityFolderScan(String folderpath){
			File directory=new File(folderpath);
			if(!directory.exists()){
				return false;
			} 
			
			File files[]=directory.listFiles();
			if(files.length==0){
				return false;
			}
			
			List<Entity> entityList=new ArrayList<Entity>();
			for(int i=0;i<files.length;i++){
				String name=(String)Array.get(files[i].getName().split("\\."), 0);
				List<String> filePaths = new ArrayList<String>();
				filePaths.add(files[i].toString());
				
				//Set default isBelief =false
				Entity entity=new Entity(name, null, null, null,filePaths, null, false, null, null, null);
				entityList.add(entity);
			}
			dbhand.addEntityBatch(entityList);
		return true;
	}
	
	public List<Entity> searchEntity(String searchQuery, String region,String metric,String timeperiod){
		
		return dbhand.searchEntity(searchQuery,region,metric,timeperiod);
	}
	
	public Entity searchEntity(int entityid){	
		
		return dbhand.searchEntity(entityid);
	}
	
	public boolean deleteEntity(int entityid){
		
		return dbhand.deleteEntity(entityid);
	}

	public Entity updateEntity(int entityid,String name, String region, String metric, String timeperiod,List<String> file_paths, HashMap<Integer, String> related_entities, boolean isBelief, String person, String strength, String note){

		Region reg = new Region();
		reg.setValue(region);
		reg.setDisabled(false);
		Metric metr = new Metric();
		metr.setValue(metric);
		metr.setDisabled(false);
		Timeperiod tPeriod = new Timeperiod();
		tPeriod.setValue(timeperiod);
		tPeriod.setDisabled(false);
		Strength s = new Strength();
		s.setValue(strength);
		s.setDisabled(false);
		
		Entity entity=new Entity(entityid,name,reg,metr,tPeriod,file_paths,related_entities,isBelief,person,s,note);
		return dbhand.updateEntity(entity);
	}
	
	public List<Entity> retrieveAllEntities(){
		
		return dbhand.retrieveAllEntities();
	}
	
}
