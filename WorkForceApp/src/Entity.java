
public class Entity {
	private int id;
	private String name;
	//file_paths is where entities associate to files.
	private String[] file_paths;
	private int[] related_entities;
	private boolean isBelief;
	private String person;
	private Strength strength;
	private String note;

	public Entity(int entityid,String name, String geography, String metric, String timeperiod,String[] file_paths, int[] related_entities, boolean isBelief, String person, String strength, String note){
		if((Integer)entityid==null){
			System.err.println("id can't be null!");
			return;
		}
		if(name==null || name.isEmpty()){
			System.err.println("name can't be null or empty!");
			return;
		}
		if(String.valueOf(isBelief).isEmpty()){
			System.err.println("isBelief can't be null or empty!");
			return;
		}
		if(strength==null || strength.isEmpty()){
			System.err.println("strength can't be null or empty!");
			return;
		} 
		
		this.id=entityid;
		this.name=name;
		Template.setgeography(geography);
		Template.setMetric(metric);	
		Template.setTimePeriod(timeperiod);
		if(file_paths==null){
			this.file_paths=file_paths;
		}else{
			this.file_paths=new String[file_paths.length];
			setFilePaths(file_paths);
		}
		if(related_entities==null){
			this.related_entities=related_entities;
		}else{
			this.related_entities=new int[related_entities.length];
			setRelatedEntities(related_entities);
		}
		this.isBelief=isBelief;
		this.person=person;
		setStrength(strength);	
		this.note=note;
	}
	
//	//For addEntityFolderScan function in EntityProcessor Class
//	public Entity(String name, String[] file_paths){
//		this.name=name;
//		setFilePaths(file_paths);
//	}
	

	public void setId(int id){
		this.id=id;
	}
	
	public int getId(){
		return this.id;
	}

	public void setName(String name){
		this.name=name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setgeography(String geography){
		Template.setgeography(geography);
	}
	
	public String getgeography(){
		return Template.getgeography();
	}
	public void setMetric(String metric){
		Template.setMetric(metric);
	}
	
	public String getMetric(){
		return Template.getMetric();
	}
	
	public void setTimePeriod(String timeperiod){
		Template.setTimePeriod(timeperiod);
	}
	
	public String getTimePeriod(){
		return Template.getTimePeriod();
	}
	
	public void setFilePaths(String[] file_paths){
		//add one entity
		if(file_paths==null) return;
		
		//entities relates to files in the folder path
		int count=0;
		for(String fp:file_paths){
			this.file_paths[count]=fp;
			count++;
		}		
	}

	public String[] getFilePaths(){
		return this.file_paths;
	}

	public void setRelatedEntities(int[] related_entities){
		if(related_entities==null) return;
		for(int i=0;i<related_entities.length;i++){
			this.related_entities[i]=related_entities[i];
		}		
	}

	public int[] getRelatedEntities(){
		return this.related_entities;
	}
	
	public void setIsBelief(boolean isBelief){
		this.isBelief=isBelief;
	}
	
	public boolean getIsBelief(){
		return this.isBelief;
	}
	
	public void setPerson(String person){
		this.person=person;
	}
	
	public String getPerson(){
		return this.person;
	}
	
	public void setStrength(String strength){
		if(strength.equalsIgnoreCase("undecided")){
				this.strength=Strength.UNDECIDED;
		}else if(strength.equalsIgnoreCase("belief")){
			this.strength=Strength.BELIEF;
		}else if(strength.equalsIgnoreCase("unbelief")){
			this.strength=Strength.UNBELIEF;
		}else{
			return;
		}
	}
	
	public Strength getStrength(){
		return this.strength;
	}
	
	public void setNote(String note){
		this.note=note;
	}
	
	public String getNote(){
		return this.note;
	}
	
	public void printEntityInfo(){
		System.out.println("Id: "+this.getId()+"\n Name: "+this.getName()
							+"\n Geography: "+this.getgeography()+"\n Metric: "+this.getMetric()+"\n TimePeriod: "+this.getTimePeriod()
							+"\n Unstructured file path: "+this.getFilePaths()[0]+"\n isBelief: "+this.getIsBelief()
							+"\n Person: "+this.getPerson()+"\n Strength: "+this.getStrength()
							+"\n Description: "+this.getNote());
		System.out.println("-----------------------------------------------");
	}
}
