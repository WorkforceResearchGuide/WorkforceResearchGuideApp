import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.xml.sax.SAXException;



public class EntityProcessor {
	//Only one DBhandler for one EntityProcessor (class diagram)
	DBHandler dbhand=new DBHandler();
	
	//Add one entity, not associated to file
	public boolean addEntity(String name, String country, String metric, String timeperiod,String[] file_paths, int[] related_entities, boolean isBelief, String person, String strength, String note){		
			int currentId=dbhand.getLastEntityIdInDB()+1;
			if((Integer)currentId==null){
				System.err.println("Id can't be null. Please check DBHandler or database.");
				return false;
			}
			if(name==null || name.length()==0){
				System.err.println("Name can't be empty. Please enter text in name.");
				return false;
			}
			
			if(strength==null){
				System.err.println("Strength can't be null. Please enter 'undecided', 'belief', or 'unbelief'.");
				return false;
			}
			
			if(!strength.equalsIgnoreCase("undecided") || !strength.equalsIgnoreCase("belief") || !strength.equalsIgnoreCase("unbelief")){
				System.err.println("Wrong strength name. Please enter 'undecided', 'belief', or 'unbelief'.");
				return false;
			}
			Entity entity=new Entity(currentId,name,country,metric,timeperiod,null,related_entities,isBelief,person,strength,note);
			dbhand.addEntity(entity);
			return true;		
	}
	
	//Add entities through a xlsx file, not associated to file
	public boolean addEntityBatch(String filepath){
		try {
			//find the xlsx input file in the filepath
			File infile=new File(filepath);
			if(!infile.exists()) {
				System.err.println(filepath+" does't exist!");
				return false;
			}

//			//convert xslx to csv file	
//			//Can't use split("."), because dot means any character in in regular expression
//			String[] array=filepath.split("\\.");
//			File outfile=null;
//			if(array[array.length-1].equals("xlsx")){
//				outfile=new File("C:\\Users\\Shin-Yi\\git\\WorkforceResearchGuideApp\\WorkForceApp\\CSV\\xlsx2csv.csv");
//				if(!outfile.exists()) {
//					outfile.createNewFile();
//				}							
//				XlsxToCSV.xlsx(infile,outfile);
//			}else{
//				outfile=infile;
//			}
						
			//read csv file for manipulating string
			FileReader fr=new FileReader(infile);
			BufferedReader br=new BufferedReader(fr);
			String line;
			List<Entity> entityList=new ArrayList<Entity>();
			int cutrentId=dbhand.getLastEntityIdInDB()+1;
			while((line=br.readLine())!=null){
				//Each row in CSV(name,country,metric,timeperiod,null file paths,related_entities("2","5"),isBelief,person,strengh,note)
				String[] arry=line.split(",");
				String[] arry_str=arry[5].replace("\"", "").split(" ");
				int[] arry_int=new int[arry_str.length];
				for(int i=0 ;i<arry_str.length;i++){
					arry_int[i]=Integer.parseInt(arry_str[i]);
				}
				if((Integer)cutrentId==null){
					System.err.println("id can't be null!");
					return false;
				}
				if(arry[0]==null || arry[0].isEmpty()){
					System.err.println("name can't be null or empty!");
					return false;
				}
				if(arry[6].isEmpty()){
					System.err.println("isBelief can't be null or empty!");
					return false;
				}
				if(arry[8]==null || arry[8].isEmpty()){
					System.err.println("strength can't be null or empty!");
					return false;
				}
				Entity entity=new Entity(cutrentId,arry[0],arry[1],arry[2],arry[3],null,arry_int,Boolean.parseBoolean(arry[6]),arry[7],arry[8],arry[9]);
				entityList.add(entity);
				cutrentId++;
			}
			dbhand.addEntityBatch(entityList);		
			br.close();
			fr.close();
		} catch (FileNotFoundException e) {
			System.out.println("Unable to open file '" + filepath + "'");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Unable to read file '" + filepath + "'");
			e.printStackTrace();
		}
		return true;
	}
	
	//Add entities, each of which associates to one file in the folder path
	public boolean addEntityFolderScan(String folderpath){
			File directory=new File(folderpath);
			if(!directory.exists()) return false;
			
			File files[]=directory.listFiles();
			List<Entity> entityList=new ArrayList<Entity>();
			int cutrentId=dbhand.getLastEntityIdInDB()+1;
			for(int i=0;i<files.length;i++){
				String name=(String)Array.get(files[i].getName().split("."), 0);
				String[] file_paths=new String[1];
				Array.set(file_paths,0,files[i].toString());
				Entity entity=new Entity(cutrentId,name, null, null, null,file_paths, null, false, null, null, null);
				entityList.add(entity);
			}			
			dbhand.addEntityBatch(entityList);
		return true;
	}
	
	public List<Entity> searchEntity(String country,String metric,String timeperiod){		
		return dbhand.searchEntity(country,metric,timeperiod);
	}
	
	public Entity searchEntity(int entityid){	
		return dbhand.searchEntity(entityid);
	}
	
	public boolean deleteEntity(int entityid){
		Entity entity=searchEntity(entityid);
		if(entity==null) return false;
		return dbhand.deleteEntity(entityid);
	}

	public boolean updateEntity(int entityid,String name, String country, String metric, String timeperiod,String[] file_paths, int[] related_entities, boolean isBelief, String person, String strength, String note){
		Entity entity=new Entity(entityid,name,country,metric,timeperiod,file_paths,related_entities,isBelief,person,strength,note);
		return dbhand.updateEntity(entity);
	}	
}
