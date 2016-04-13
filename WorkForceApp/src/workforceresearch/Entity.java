package workforceresearch;

import java.util.HashMap;
import java.util.List;

public class Entity {
	private int id;
	private String statement;
	// file_paths is where entities associate to files.
	private List<String> filePaths;
	private HashMap<Integer, String> relatedEntities;
	private boolean isBelief;
	private String person;
	private Strength strength;
	private Region region;
	private Metric metric;
	private Timeperiod timeperiod;
	private String note;

	public Entity() {

	}

	public Entity(String statement, Region region, Metric metric,
			Timeperiod timeperiod, List<String> filePaths,
			HashMap<Integer, String> relatedEntities, boolean isBelief,
			String person, Strength strength, String note) {
		
		this.statement = statement;
		this.region = region;
		this.metric = metric;
		this.timeperiod = timeperiod;
		this.filePaths = filePaths;
		this.relatedEntities = relatedEntities;
		this.isBelief = isBelief;
		this.person = person;
		this.strength = strength;
		this.note = note;
		
	}
	
	public Entity(int entityid, String statement, Region region, Metric metric,
			Timeperiod timeperiod, List<String> filePaths,
			HashMap<Integer, String> relatedEntities, boolean isBelief,
			String person, Strength strength, String note) {

		this.id = entityid;
		this.statement = statement;
		this.region = region;
		this.metric = metric;
		this.timeperiod = timeperiod;
		this.filePaths = filePaths;
		this.relatedEntities = relatedEntities;
		this.isBelief = isBelief;
		this.person = person;
		this.strength = strength;
		this.note = note;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the statement
	 */
	public String getStatement() {
		return statement;
	}

	/**
	 * @param statement
	 *            the statement to set
	 */
	public void setStatement(String statement) {
		this.statement = statement;
	}

	/**
	 * @return the filePaths
	 */
	public List<String> getFilePaths() {
		return filePaths;
	}

	/**
	 * @param filePaths
	 *            the filePaths to set
	 */
	public void setFilePaths(List<String> filePaths) {
		this.filePaths = filePaths;
	}

	/**
	 * @return the relatedEntities
	 */
	public HashMap<Integer, String> getRelatedEntities() {
		return relatedEntities;
	}

	/**
	 * @param relatedEntities
	 *            the relatedEntities to set
	 */
	public void setRelatedEntities(HashMap<Integer, String> relatedEntities) {
		this.relatedEntities = relatedEntities;
	}

	/**
	 * @return the isBelief
	 */
	public boolean isBelief() {
		return isBelief;
	}

	/**
	 * @param isBelief
	 *            the isBelief to set
	 */
	public void setBelief(boolean isBelief) {
		this.isBelief = isBelief;
	}

	/**
	 * @return the person
	 */
	public String getPerson() {
		return person;
	}

	/**
	 * @param person
	 *            the person to set
	 */
	public void setPerson(String person) {
		this.person = person;
	}

	/**
	 * @return the strength
	 */
	public Strength getStrength() {
		return strength;
	}

	/**
	 * @param strength
	 *            the strength to set
	 */
	public void setStrength(Strength strength) {
		this.strength = strength;
	}

	/**
	 * @return the region
	 */
	public Region getRegion() {
		return region;
	}

	/**
	 * @param region
	 *            the region to set
	 */
	public void setRegion(Region region) {
		this.region = region;
	}

	/**
	 * @return the metric
	 */
	public Metric getMetric() {
		return metric;
	}

	/**
	 * @param metric
	 *            the metric to set
	 */
	public void setMetric(Metric metric) {
		this.metric = metric;
	}

	/**
	 * @return the timeperiod
	 */
	public Timeperiod getTimeperiod() {
		return timeperiod;
	}

	/**
	 * @param timeperiod
	 *            the timeperiod to set
	 */
	public void setTimeperiod(Timeperiod timeperiod) {
		this.timeperiod = timeperiod;
	}

	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @param note
	 *            the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}
}