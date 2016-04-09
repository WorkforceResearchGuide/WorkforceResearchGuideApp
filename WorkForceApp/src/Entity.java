import java.util.List;

public class Entity {
	private int id;


	private String statement;
	private List<String> filePaths;
	private List<Integer> relatedEntities;
	private boolean isBelief;
	private String person;
	private Strength strength;
	private Region region;
	private Metric metric;
	private Timeperiod timeperiod;
	private String note;

	public Entity() {

	}

	public Entity(int entityid, String name, Region region, Metric metric, Timeperiod timeperiod, List<String> filePaths,
			List<Integer> relatedEntities, boolean isBelief, String person, Strength strength, String note) {

		this.id = entityid;
		this.statement = name;
		this.region = region;
		this.metric = metric;
		this.timeperiod = timeperiod;
		this.filePaths = filePaths;
		this.relatedEntities = relatedEntities;
		this.isBelief = isBelief;
		this.person = person;
		this.note = note;
		this.strength = strength;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}

	public List<String> getFilePaths() {
		return filePaths;
	}

	public void setFile_paths(List<String> filePaths) {
		this.filePaths = filePaths;
	}

	public List<Integer> getRelatedEntities() {
		return relatedEntities;
	}

	public void setRelatedEntities(List<Integer> relatedEntities) {
		this.relatedEntities = relatedEntities;
	}

	public boolean isBelief() {
		return isBelief;
	}

	public void setBelief(boolean isBelief) {
		this.isBelief = isBelief;
	}

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public Strength getStrength() {
		return strength;
	}

	public void setStrength(Strength strength) {
		this.strength = strength;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public Metric getMetric() {
		return metric;
	}

	public void setMetric(Metric metric) {
		this.metric = metric;
	}

	public Timeperiod getTimeperiod() {
		return timeperiod;
	}

	public void setTimeperiod(Timeperiod timeperiod) {
		this.timeperiod = timeperiod;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}