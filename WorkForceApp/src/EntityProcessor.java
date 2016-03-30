import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;



public class EntityProcessor {
	//Only one DBhandler for one EntityProcessor (class diagram)
	DBHandler dbhand=new DBHandler();
	private List<Entity> entityList=new ArrayList<Entity>();
	
	//Add one entity, not associated to file
	public boolean addEntity(String name, String country, String metric, String timeperiod,String[] file_paths, int[] related_entities, boolean isBelief, String person, String strength, String note){
		Entity entity=new Entity(name,country,metric,timeperiod,null,related_entities,isBelief,person,strength,note);
		dbhand.addEntity(entity);
		return true;
	}
	
	//Add entities through a xlsx file, not associated to file
	public boolean addEntityBatch(String filepath){
		try {
			//find the xlsx input file in the filepath
			File infile=new File(filepath);
			if(!infile.exists()) return false;
			
			//find the csv output file in the filepath
			File outfile=new File("..\\csv\\xlsx2csv.csv");
			if(!outfile.exists()) outfile.createNewFile();
			//convert xslx to csv file
			XlsxToCSV.xlsx(infile,outfile);
			
			//read csv file for manipulating string
			FileReader fr=new FileReader(outfile);
			BufferedReader br=new BufferedReader(fr);
			String line;
			while((line=br.readLine())!=null){
				//Each row in CSV(name,country,metric,timeperiod,null file paths,related_entities("2","5"),isBelief,person,strengh,note)
				String[] arry=line.split(",");
				String[] arry_str=arry[5].split(",");
				int[] arry_int=new int[arry_str.length];
				for(int i=0 ;i<arry_str.length;i++){
					arry_int[i]=Integer.parseInt(arry_str[i]);
				}
				Entity entity=new Entity(arry[0],arry[1],arry[2],arry[3],null,arry_int,Boolean.parseBoolean(arry[6]),arry[7],arry[8],arry[9]);
				entityList.add(entity);
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
			for(int i=0;i<files.length;i++){
				String name=(String)Array.get(files[i].getName().split("."), 0);
				String[] file_paths=new String[1];
				Array.set(file_paths,0,files[i].toString());
				Entity entity=new Entity(name,file_paths);
				entityList.add(entity);
			}			
			dbhand.addEntityBatch(entityList);
		return true;
	}
	
	public Entity searchEntity(int entityid){
		return null;
	}
	
	public List<Entity> searchEntity(String searchquery){
		return null;
	}
}
