package workforceresearch;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class EntityProcessor {

	// CSV file header
	private static final String[] FILE_HEADER_MAPPING = { "Statement",
			"Geography", "Metric", "Timeperiod", "isBelief", "Person",
			"Strength", "Note" };

	DBHandler dbhand = new DBHandler();
	TemplateProcessor tp = new TemplateProcessor();

	public boolean addEntity(String name, String region, String metric,
			String timeperiod, List<String> file_paths,
			HashMap<Integer, String> related_entities, boolean isBelief,
			String person, String strength, String note) {

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
		if (file_paths == null) {
			file_paths = new ArrayList<String>();
		}
		if (related_entities == null) {
			related_entities = new HashMap<Integer, String>();
		}

		Entity entity = new Entity(name, reg, metr, tPeriod, file_paths,
				related_entities, isBelief, person, s, note);
		dbhand.addEntity(entity);
		return true;
	}

	@SuppressWarnings("finally")
	public boolean addEntityBatch(String filepath) {
		boolean result = true;
		FileReader fileReader = null;
		CSVParser csvFileParser = null;

		// Create the CSVFormat object with the header mapping
		CSVFormat csvFileFormat = CSVFormat.DEFAULT
				.withHeader(FILE_HEADER_MAPPING);

		try {

			// Create a new list of student to be filled by CSV file data
			List<Entity> entityList = new ArrayList<Entity>();

			// initialize FileReader object
			fileReader = new FileReader(filepath);

			// initialize CSVParser object
			csvFileParser = new CSVParser(fileReader, csvFileFormat);

			// Get a list of CSV file records
			List<CSVRecord> csvRecords = csvFileParser.getRecords();

			// list of related filePaths
			List<String> relatedFiles = new ArrayList<String>();
			relatedFiles.add(filepath);

			// Read the CSV file records starting from the second record to skip
			// the header
			for (int i = 1; i < csvRecords.size(); i++) {
				CSVRecord record = csvRecords.get(i);
				// Create a new student object and fill his data
				Entity entity = new Entity();
				entity.setStatement(record.get(FILE_HEADER_MAPPING[0]));

				Region region = new Region();
				region.setValue(record.get(FILE_HEADER_MAPPING[1]));
				entity.setRegion(region);

				Metric metric = new Metric();
				metric.setValue(record.get(FILE_HEADER_MAPPING[2]));
				entity.setMetric(metric);

				Timeperiod timeperiod = new Timeperiod();
				timeperiod.setValue(record.get(FILE_HEADER_MAPPING[3]));
				entity.setTimeperiod(timeperiod);

				entity.setBelief(Boolean.parseBoolean(record
						.get(FILE_HEADER_MAPPING[4])));
				entity.setPerson(record.get(FILE_HEADER_MAPPING[5]));

				Strength strength = new Strength();
				strength.setValue(record.get(FILE_HEADER_MAPPING[6]));
				entity.setStrength(strength);

				entity.setNote(record.get(FILE_HEADER_MAPPING[7]));

				entity.setFilePaths(relatedFiles);
				entity.setRelatedEntities(new HashMap<Integer, String>());

				entityList.add(entity);

				// adding possible new templates from csv to allowed templates
				tp.addMetric(metric.getValue());
				tp.addRegion(region.getValue());
				tp.addTimeperiod(timeperiod.getValue());
				tp.addStrength(strength.getValue());
			}

			result = dbhand.addEntityBatch(entityList);

			fileReader.close();
			csvFileParser.close();
		} catch (Exception e) {
			System.out.println(e.toString());
			result = false;
		} finally {
			return result;
		}
	}

	// Add entities, each of which associates to one file in the folder path
	public boolean addEntityFolderScan(String folderpath) {
		File directory = new File(folderpath);
		if (!directory.exists()) {
			return false;
		}

		File files[] = directory.listFiles();
		if (files.length == 0) {
			return false;
		}

		List<Entity> entityList = new ArrayList<Entity>();
		for (File f : files) {
			String name = f.getName();
			List<String> filePaths = new ArrayList<String>();
			filePaths.add(f.getAbsolutePath());
			HashMap<Integer, String> entityRelationsMap = new HashMap<Integer, String>();
			String folderScan = "Added by folder scan";
			Region region = new Region();
			region.setValue(folderScan);
			Metric metric = new Metric();
			metric.setValue(folderScan);
			Timeperiod tp = new Timeperiod();
			tp.setValue(folderScan);
			Strength strength = new Strength();
			strength.setValue(folderScan);

			Entity entity = new Entity(name, region, metric, tp, filePaths,
					entityRelationsMap, false, null, strength, null);
			entityList.add(entity);
		}
		return dbhand.addEntityFolderScan(entityList);
	}

	public List<Entity> searchEntity(String searchQuery, String region,
			String metric, String timeperiod) {

		return dbhand.searchEntity(searchQuery, region, metric, timeperiod);
	}

	public Entity searchEntity(int entityid) {

		return dbhand.searchEntity(entityid);
	}

	public boolean deleteEntity(int entityid) {

		return dbhand.deleteEntity(entityid);
	}

	public Entity updateEntity(int entityid, String name, String region,
			String metric, String timeperiod, List<String> file_paths,
			HashMap<Integer, String> related_entities, boolean isBelief,
			String person, String strength, String note) {

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

		Entity entity = new Entity(entityid, name, reg, metr, tPeriod,
				file_paths, related_entities, isBelief, person, s, note);
		return dbhand.updateEntity(entity);
	}

	public List<Entity> retrieveAllEntities() {

		return dbhand.retrieveAllEntities();
	}

}
