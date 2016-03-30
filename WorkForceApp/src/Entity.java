
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
	
	//For addEntity and addBatchEntity functions in EntityProcessor Class
	public Entity(String name, String country, String metric, String timeperiod,String[] file_paths, int[] related_entities, boolean isBelief, String person, String strength, String note){
		//id will be given when storing the Entity to database.
		this.name=name;
		Template.setCountry(country);
		Template.setMetric(metric);	
		Template.setTimePeriod(timeperiod);
		setFilePaths(file_paths);
		setRelatedEntities(related_entities);
		this.isBelief=isBelief;
		this.person=person;
		setStrength(strength);
		this.note=note;
	}
	
	//For addEntityFolderScan function in EntityProcessor Class
	public Entity(String name, String[] file_paths){
		this.name=name;
		setFilePaths(file_paths);
	}
	
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
	
	public void setCountry(String country){
		Template.setCountry(country);
	}
	
	public String getCountry(){
		return Template.getCountry();
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
		
		//add one entity and the entity relates to files
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
		int count=0;
		for(int re:related_entities){
			this.related_entities[count]=re;
			count++;
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
		switch(strength){
			case "undecided":
				this.strength=Strength.UNDECIDED;
				break;
			case "belief":
				this.strength=Strength.BELIEF;
				break;
			case "unbelief":
				this.strength=Strength.UNBELIEF;
				break;
			default:
				System.err.println("Error strength input.");
				System.err.println("Please enter 'undecided', 'belief', or 'unbelief'");
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
}
