import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.sql.*;

public class DBHandler {

	@SuppressWarnings("finally")
	public boolean addEntity(Entity entity) {
		int result = 0;
		try {
			// create connection
			Class.forName("org.sqlite.JDBC");
			Connection connection = DriverManager
					.getConnection("jdbc:sqlite:db/workforceresearchguide.db");
			// start of transaction
			connection.setAutoCommit(false);
			Statement statement = connection.createStatement();

			result = addEntityHelper(entity, statement);

			// end of transaction
			connection.commit();

			statement.close();
			connection.close();
		} catch (ClassNotFoundException e) {
			result = 0;
			e.printStackTrace();
		} catch (SQLException e) {
			result = 0;
			e.printStackTrace();
		} finally {
			// 1 update made to insert entity into entities table.
			if (result == 1) {
				return true;
			}
			return false;
		}
	}

	@SuppressWarnings("finally")
	public boolean addEntityBatch(List<Entity> entityList) {
		int result = 0;
		try {
			// create connection
			Class.forName("org.sqlite.JDBC");
			Connection connection = DriverManager
					.getConnection("jdbc:sqlite:db/workforceresearchguide.db");

			connection.setAutoCommit(false);
			Statement statement = connection.createStatement();

			// insert entities one by one as a transaction
			for (Entity entity : entityList) {
				result = addEntityHelper(entity, statement);
				// end of transaction
				connection.commit();
			}

			statement.close();
			connection.close();
		} catch (ClassNotFoundException e) {
			result = 0;
			e.printStackTrace();
		} catch (SQLException e) {
			result = 0;
			e.printStackTrace();
		} finally {
			// 1 update made to insert entity into entities table.
			if (result == 1) {
				return true;
			}
			return false;
		}
	}

	public boolean addEntityFolderScan(List<Entity> entityList) {
		return addEntityBatch(entityList);
	}

	private int addEntityHelper(Entity entity, Statement statement)
			throws SQLException {
		int result;

		// insert entity
		StringBuilder insertEntityQuery = new StringBuilder();
		insertEntityQuery
				.append("insert into entities(statement, note, region, metric, time_period, is_belief, person, strength)")
				.append("values('" + entity.getName() + "', ")
				.append("'" + entity.getNote() + "', ")
				.append("'" + entity.getgeography() + "', ")
				.append("'" + entity.getMetric() + "', ")
				.append("'" + entity.getTimePeriod() + "', ")
				.append("'" + entity.getIsBelief() + "', ")
				.append("'" + entity.getPerson() + "', ")
				.append("'" + entity.getStrength() + "')");
		result = statement.executeUpdate(insertEntityQuery.toString());
		int entityid = statement.getGeneratedKeys().getInt(1);

		// insert entity-file relations
		if (entity.getFilePaths().length > 0) {
			StringBuilder insertFileRelations = new StringBuilder();

			insertFileRelations
					.append("insert into file_relations(entity_id, file_path) values");
			for (String file_path : entity.getFilePaths()) {
				insertFileRelations.append("('" + entityid + "', '" + file_path
						+ "')");
			}
			statement.executeUpdate(insertFileRelations.toString());
		}

		// insert entity-entity relations
		if (entity.getRelatedEntities().length > 0) {
			StringBuilder insertEntityRelations = new StringBuilder();
			insertEntityRelations
					.append("insert into entity_relations(entity_id, related_entity_id) values");
			for (int relatedEntityId : entity.getRelatedEntities()) {
				insertEntityRelations.append("('" + entityid + "', '"
						+ relatedEntityId + "')");
			}
			statement.executeUpdate(insertEntityRelations.toString());
		}
		return result;
	}

	// TODO: Decide on Search feature -> how exactly it will work... inputs and
	// search field preferences
	// Then complete method.
	public List<Entity> searchEntity(String region, String metric,
			String timeperiod) {

		return null;
	}

	public Entity searchEntity(int entityId) {
		Entity entity = null;
		List<String> file_paths = new ArrayList<String>();
		List<String> related_entities = new ArrayList<String>();
		try {
			// create connection
			Class.forName("org.sqlite.JDBC");
			Connection connection = DriverManager
					.getConnection("jdbc:sqlite:db/workforceresearchguide.db");

			PreparedStatement ps = connection
					.prepareStatement("select * from entities where entity_id = ?");
			ps.setInt(1, entityId);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				entity = new Entity(rs.getInt("entity_id"),
						rs.getString("statement"), rs.getString("region"),
						rs.getString("metric"), rs.getString("time_period"),
						null, null, rs.getBoolean("is_belief"),
						rs.getString("person"), rs.getString("strength"),
						rs.getString("note"));
			}

			// retrieve entity-entity relations
			ps = connection
					.prepareStatement("select e.entity_id, e.statement from entity_relations as er, entities as e where er.entity_id = ? and er.related_entity_id = e.entity_id");
			ps.setInt(1, entityId);

			rs = ps.executeQuery();
			while (rs.next()) {
				related_entities.add(rs.getString(2));
			}
			// entity should have id and statement of related entities.. Convert
			// to list from array
			entity.setRelatedEntities(related_entities);

			// retrieve entity-file relations
			ps = connection
					.prepareStatement("select * from file_relations where entity_id = ?");
			ps.setInt(1, entityId);

			rs = ps.executeQuery();
			while (rs.next()) {
				file_paths.add(rs.getString("file_path"));
			}
			// entity shuld have list of string not array. This also should have
			// id..
			entity.setFilePaths(file_paths);

			ps.close();
			connection.close();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return entity;
	}

	@SuppressWarnings("finally")
	public boolean deleteEntity(int entityId) {
		boolean resultEntity = false;
		boolean resultFileRelation = false;
		boolean resultEntityRelation = false;
		try {
			// create connection
			Class.forName("org.sqlite.JDBC");
			Connection connection = DriverManager
					.getConnection("jdbc:sqlite:db/workforceresearchguide.db");
			// start of transaction
			connection.setAutoCommit(false);
			String deleteEntity = "delete from entities where entity_id = ?";
			String deleteEntityRelation = "delete from entity_relations where entity_id = ?";
			String deleteFileRelation = "delete from file_relations where entity_id = ?";
			PreparedStatement ps = connection.prepareStatement(deleteEntity);
			ps.setInt(1, entityId);
			resultEntity = ps.execute();

			ps = connection.prepareStatement(deleteEntityRelation);
			ps.setInt(1, entityId);
			resultEntityRelation = ps.execute();

			ps = connection.prepareStatement(deleteFileRelation);
			ps.setInt(1, entityId);
			resultFileRelation = ps.execute();

			// end of transaction
			if (resultEntity && resultEntityRelation && resultFileRelation)
				connection.commit();

			ps.close();
			connection.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			return (resultEntity && resultEntityRelation && resultFileRelation);
		}
	}

	// TODO: This method will return the same entity, or we can change it to return
	// boolean..
	public Entity updateEntity(Entity entityUpdated) {
		Entity entityCurrent = searchEntity(entityUpdated.getId());

		try {
			List<String> updates = new ArrayList<String>();
			// different statement
			if (!((entityCurrent.getName() != null
					&& entityUpdated.getName() != null && entityCurrent
					.getName().equals(entityUpdated.getName())) || (entityUpdated
					.getName() == null && entityUpdated.getName() == null))) {
				updates.add("update entities set statement = '"
						+ entityUpdated.getName() + "' where entity_id = "
						+ entityUpdated.getId());
			}

			// different region
			if (!((entityCurrent.getgeography() != null
					&& entityUpdated.getgeography() != null && entityCurrent
					.getgeography().equals(entityUpdated.getgeography())) || (entityUpdated
					.getgeography() == null && entityUpdated.getgeography() == null))) {
				updates.add("update entities set region = '"
						+ entityUpdated.getgeography() + "' where entity_id = "
						+ entityUpdated.getId());
			}

			// different metric
			if (!((entityCurrent.getMetric() != null
					&& entityUpdated.getMetric() != null && entityCurrent
					.getMetric().equals(entityUpdated.getMetric())) || (entityUpdated
					.getMetric() == null && entityUpdated.getMetric() == null))) {
				updates.add("update entities set metric = '"
						+ entityUpdated.getMetric() + "' where entity_id = "
						+ entityUpdated.getId());
			}

			// different timeperiod
			if (!((entityCurrent.getTimePeriod() != null
					&& entityUpdated.getTimePeriod() != null && entityCurrent
					.getTimePeriod().equals(entityUpdated.getTimePeriod())) || (entityUpdated
					.getTimePeriod() == null && entityUpdated.getTimePeriod() == null))) {
				updates.add("update entities set time_period = '"
						+ entityUpdated.getTimePeriod()
						+ "' where entity_id = " + entityUpdated.getId());
			}

			// different note
			if (!((entityCurrent.getNote() != null
					&& entityUpdated.getNote() != null && entityCurrent
					.getNote().equals(entityUpdated.getNote())) || (entityUpdated
					.getNote() == null && entityUpdated.getNote() == null))) {
				updates.add("update entities set note = '"
						+ entityUpdated.getNote() + "' where entity_id = "
						+ entityUpdated.getId());
			}

			// different isBelief
			if (entityCurrent.getIsBelief() != entityUpdated.getIsBelief()) {
				updates.add("update entities set is_belief = '"
						+ (entityUpdated.getIsBelief() ? 1 : 0)
						+ "' where entity_id = " + entityUpdated.getId());
			}

			// different persosn
			if (!((entityCurrent.getPerson() != null
					&& entityUpdated.getPerson() != null && entityCurrent
					.getPerson().equals(entityUpdated.getPerson())) || (entityUpdated
					.getPerson() == null && entityUpdated.getPerson() == null))) {
				updates.add("update entities set note = '"
						+ entityUpdated.getPerson() + "' where entity_id = "
						+ entityUpdated.getId());
			}

			// different strength
			if (!((entityCurrent.getStrength() != null
					&& entityUpdated.getStrength() != null && entityCurrent
					.getStrength().equals(entityUpdated.getStrength())) || (entityUpdated
					.getStrength() == null && entityUpdated.getStrength() == null))) {
				updates.add("update entities set note = '"
						+ entityUpdated.getStrength() + "' where entity_id = "
						+ entityUpdated.getId());
			}

			// different entity-entity relations
			int[] updatedEntityRelations = entityUpdated.getRelatedEntities();
			Arrays.sort(updatedEntityRelations);
			int[] currentEntityRelations = entityCurrent.getRelatedEntities();
			Arrays.sort(currentEntityRelations);
			if (updatedEntityRelations.equals(currentEntityRelations)) {
				// TODO: do update once entity is rectified
			}

			// different entity-file relations
			String[] updatedFileRelations = entityUpdated.getFilePaths();
			Arrays.sort(updatedFileRelations);
			String[] currentFileRelations = entityCurrent.getFilePaths();
			Arrays.sort(currentFileRelations);
			if (updatedFileRelations.equals(currentFileRelations)) {
				// TODO: do update once entity is rectified
			}

			//
			// create connection
			Class.forName("org.sqlite.JDBC");
			Connection connection = DriverManager
					.getConnection("jdbc:sqlite:db/workforceresearchguide.db");
			// start of transaction
			connection.setAutoCommit(false);
			PreparedStatement ps = null;
			for (String query : updates) {
				ps = connection.prepareStatement(query);
				ps.execute();
			}

			// end of transaction
			connection.commit();

			if (ps != null)
				ps.close();
			connection.close();
			return entityUpdated;

		} catch (SQLException e) {
			e.printStackTrace();
			return entityCurrent;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return entityCurrent;
		} catch (Exception e) {
			e.printStackTrace();
			return entityCurrent;
		}
	}

	// Add Template
	public boolean addRegion(String region) {
		int result = 0;
		try {
			// create connection
			Class.forName("org.sqlite.JDBC");
			Connection connection = DriverManager
					.getConnection("jdbc:sqlite:db/workforceresearchguide.db");
			Statement statement = connection.createStatement();

			result = statement
					.executeUpdate("insert into regions(value, is_disabled) values('"
							+ region + "', 0)");

			statement.close();
			connection.close();
			if (result == 1)
				return true;
			return false;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean addMetric(String metric) {
		int result = 0;
		try {
			// create connection
			Class.forName("org.sqlite.JDBC");
			Connection connection = DriverManager
					.getConnection("jdbc:sqlite:db/workforceresearchguide.db");
			Statement statement = connection.createStatement();

			result = statement
					.executeUpdate("insert into metric(value, is_disabled) values('"
							+ metric + "', 0)");

			statement.close();
			connection.close();
			if (result == 1)
				return true;
			return false;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean addTimeperiod(String timeperiod) {
		int result = 0;
		try {
			// create connection
			Class.forName("org.sqlite.JDBC");
			Connection connection = DriverManager
					.getConnection("jdbc:sqlite:db/workforceresearchguide.db");
			Statement statement = connection.createStatement();

			result = statement
					.executeUpdate("insert into time_periods(value, is_disabled) values('"
							+ timeperiod + "', 0)");

			statement.close();
			connection.close();
			if (result == 1)
				return true;
			return false;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean addStrength(String strength) {
		int result = 0;
		try {
			// create connection
			Class.forName("org.sqlite.JDBC");
			Connection connection = DriverManager
					.getConnection("jdbc:sqlite:db/workforceresearchguide.db");
			Statement statement = connection.createStatement();

			result = statement
					.executeUpdate("insert into strengths(value, is_disabled) values('"
							+ strength + "', 0)");

			statement.close();
			connection.close();
			if (result == 1)
				return true;
			return false;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// Disable template
	public boolean disableRegion(int regionId) {
		int result = 0;
		try {
			// create connection
			Class.forName("org.sqlite.JDBC");
			Connection connection = DriverManager
					.getConnection("jdbc:sqlite:db/workforceresearchguide.db");
			Statement statement = connection.createStatement();

			result = statement
					.executeUpdate("update regions set is_disabled = 1 where region_id = "
							+ regionId);

			statement.close();
			connection.close();
			if (result == 1)
				return true;
			return false;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean disableMetric(int metricId) {
		int result = 0;
		try {
			// create connection
			Class.forName("org.sqlite.JDBC");
			Connection connection = DriverManager
					.getConnection("jdbc:sqlite:db/workforceresearchguide.db");
			Statement statement = connection.createStatement();

			result = statement
					.executeUpdate("update metrics set is_disabled = 1 where metric_id = "
							+ metricId);

			statement.close();
			connection.close();
			if (result == 1)
				return true;
			return false;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean disableTimeperiod(int timeperiodId) {
		int result = 0;
		try {
			// create connection
			Class.forName("org.sqlite.JDBC");
			Connection connection = DriverManager
					.getConnection("jdbc:sqlite:db/workforceresearchguide.db");
			Statement statement = connection.createStatement();

			result = statement
					.executeUpdate("update time_periods set is_disabled = 1 where time_period_id = "
							+ timeperiodId);

			statement.close();
			connection.close();
			if (result == 1)
				return true;
			return false;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean disableStrength(int strengthId) {
		int result = 0;
		try {
			// create connection
			Class.forName("org.sqlite.JDBC");
			Connection connection = DriverManager
					.getConnection("jdbc:sqlite:db/workforceresearchguide.db");
			Statement statement = connection.createStatement();

			result = statement
					.executeUpdate("update strengths set is_disabled = 1 where strength_id = "
							+ strengthId);

			statement.close();
			connection.close();
			if (result == 1)
				return true;
			return false;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// Retrieve all for templates
	public List<Region> retrieveAllRegions() {
		List<Region> regions = new ArrayList<Region>();
		Region r = null;
		try {
			// create connection
			Class.forName("org.sqlite.JDBC");
			Connection connection = DriverManager
					.getConnection("jdbc:sqlite:db/workforceresearchguide.db");
			Statement statement = connection.createStatement();

			ResultSet rs = statement.executeQuery("select * from regions");

			while (rs.next()) {
				r = new Region(rs.getInt(1), rs.getString(2), rs.getBoolean(3));
				regions.add(r);
			}

			statement.close();
			connection.close();
			return regions;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Metric> retrieveAllMetrics() {
		List<Metric> metrics = new ArrayList<Metric>();
		Metric r = null;
		try {
			// create connection
			Class.forName("org.sqlite.JDBC");
			Connection connection = DriverManager
					.getConnection("jdbc:sqlite:db/workforceresearchguide.db");
			Statement statement = connection.createStatement();

			ResultSet rs = statement.executeQuery("select * from metrics");

			while (rs.next()) {
				r = new Metric(rs.getInt(1), rs.getString(2), rs.getBoolean(3));
				metrics.add(r);
			}

			statement.close();
			connection.close();
			return metrics;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Timeperiod> retrieveAllTimeperiods() {
		List<Timeperiod> timeperiods = new ArrayList<Timeperiod>();
		Timeperiod t = null;
		try {
			// create connection
			Class.forName("org.sqlite.JDBC");
			Connection connection = DriverManager
					.getConnection("jdbc:sqlite:db/workforceresearchguide.db");
			Statement statement = connection.createStatement();

			ResultSet rs = statement.executeQuery("select * from time_periods");

			while (rs.next()) {
				t = new Timeperiod(rs.getInt(1), rs.getString(2),
						rs.getBoolean(3));
				timeperiods.add(t);
			}

			statement.close();
			connection.close();
			return timeperiods;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Strength> retrieveAllStrengths() {
		List<Strength> strengths = new ArrayList<Strength>();
		Strength s = null;
		try {
			// create connection
			Class.forName("org.sqlite.JDBC");
			Connection connection = DriverManager
					.getConnection("jdbc:sqlite:db/workforceresearchguide.db");
			Statement statement = connection.createStatement();

			ResultSet rs = statement.executeQuery("select * from time_periods");

			while (rs.next()) {
				s = new Strength(rs.getInt(1), rs.getString(2),
						rs.getBoolean(3));
				strengths.add(s);
			}

			statement.close();
			connection.close();
			return strengths;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
