package workforceresearch;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
				.append("values('" + entity.getStatement() + "', ")
				.append("'" + entity.getNote() + "', ")
				.append("'" + entity.getRegion() + "', ")
				.append("'" + entity.getMetric() + "', ")
				.append("'" + entity.getTimeperiod() + "', ")
				.append("'" + entity.isBelief() + "', ")
				.append("'" + entity.getPerson() + "', ")
				.append("'" + entity.getStrength() + "')");
		result = statement.executeUpdate(insertEntityQuery.toString());
		int entityid = statement.getGeneratedKeys().getInt(1);

		// insert entity-file relations
		StringBuilder insertFileRelations = new StringBuilder();
		insertFileRelations
				.append("insert into file_relations(entity_id, file_path) values");

		Iterator<String> filepaths = entity.getFilePaths().iterator();
		while (filepaths.hasNext()) {
			insertFileRelations.append("('" + entityid + "', '"
					+ filepaths.next() + "')");
		}

		if (entity.getFilePaths().size() > 0) {
			statement.executeUpdate(insertFileRelations.toString());
		}

		// insert entity-entity relations
		StringBuilder insertEntityRelations = new StringBuilder();
		insertEntityRelations
				.append("insert into entity_relations(entity_id, related_entity_id) values");

		for (Map.Entry<Integer, String> entry : entity.getRelatedEntities()
				.entrySet()) {
			insertEntityRelations.append("('" + entityid + "', '"
					+ (int) entry.getKey() + "')");
		}

		if (entity.getRelatedEntities().size() > 0) {
			statement.executeUpdate(insertEntityRelations.toString());
		}

		return result;
	}

	public List<Entity> searchEntity(String searchQuery, String region,
			String metric, String timeperiod) {
		List<Entity> qualifiedEntities = new ArrayList<Entity>();
		String query = createSearchQuery(searchQuery, region, metric,
				timeperiod);

		if (query != null) {
			try {
				// create connection
				Class.forName("org.sqlite.JDBC");
				Connection connection = DriverManager
						.getConnection("jdbc:sqlite:db/workforceresearchguide.db");

				PreparedStatement ps = connection.prepareStatement(query);

				ResultSet rs = ps.executeQuery();
				Entity e;
				while (rs.next()) {
					// TODO: I think we don't need all attributes here, not
					// sure.
					// Let me know what you guys think..?
					// In that case, I can select only 2 columns from db.
					e = new Entity();
					e.setId(rs.getInt("entity_id"));
					e.setStatement(rs.getString("statement"));
					qualifiedEntities.add(e);
				}
				rs.close();
				ps.close();
				connection.close();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return qualifiedEntities;
	}

	private String createSearchQuery(String searchQuery, String region,
			String metric, String timeperiod) {
		String query;
		boolean searchQueryFlag = false;
		boolean regionFlag = false;
		boolean metricFlag = false;
		boolean timeperiodFlag = false;

		if (searchQuery != null && searchQuery.length() > 0)
			searchQueryFlag = true;
		if (region != null && region.length() > 0)
			regionFlag = true;
		if (metric != null && metric.length() > 0)
			metricFlag = true;
		if (timeperiod != null && timeperiod.length() > 0)
			timeperiodFlag = true;

		if (searchQueryFlag && !regionFlag && !metricFlag && !timeperiodFlag) {
			// 1. statement/person
			query = "select * from entities where statement like '%"
					+ searchQuery + "%' or person like '%" + searchQuery + "%'";
		} else if (!searchQueryFlag && regionFlag && !metricFlag
				&& !timeperiodFlag) {
			// 2. region
			query = "select * from entities where region = '" + region + "'";
		} else if (!searchQueryFlag && !regionFlag && metricFlag
				&& !timeperiodFlag) {
			// 3. metric
			query = "select * from entities where metric = '" + metric + "'";
		} else if (!searchQueryFlag && !regionFlag && !metricFlag
				&& timeperiodFlag) {
			// 4. timeperiod
			query = "select * from entities where time_period = '" + timeperiod
					+ "'";
		} else if (!searchQueryFlag && regionFlag && metricFlag
				&& !timeperiodFlag) {
			// 5. region and metric
			query = "select * from entities where region = '" + region
					+ "' and metric = '" + metric + "'";
		} else if (!searchQueryFlag && regionFlag && !metricFlag
				&& timeperiodFlag) {
			// 6. region and timeperiod
			query = "select * from entities where region = '" + region
					+ "' and time_period = '" + timeperiod + "'";
		} else if (!searchQueryFlag && !regionFlag && metricFlag
				&& timeperiodFlag) {
			// 7. metric and timeperiod
			query = "select * from entities where metric = '" + metric
					+ "' and time_period = '" + timeperiod + "'";
		} else if (!searchQueryFlag && regionFlag && metricFlag
				&& timeperiodFlag) {
			// 8. region and metric and timeperiod
			query = "select * from entities where region = '" + region
					+ "' and metric = '" + metric + "' and time_period = '"
					+ timeperiod + "'";
		} else if (searchQueryFlag && regionFlag && !metricFlag
				&& !timeperiodFlag) {
			// 9. statement/person and region
			query = "select * from entities where region = '" + region
					+ "' and (statement like '%" + searchQuery
					+ "%' or person like '%" + searchQuery + "%')";
		} else if (searchQueryFlag && !regionFlag && metricFlag
				&& !timeperiodFlag) {
			// 10. statement/person & metric
			query = "select * from entities where metric = '" + metric
					+ "' and (statement like '%" + searchQuery
					+ "%' or person like '%" + searchQuery + "%')";
		} else if (searchQueryFlag && !regionFlag && !metricFlag
				&& timeperiodFlag) {
			// 11. statement/person & timeperiod
			query = "select * from entities where time_period = '" + timeperiod
					+ "' and (statement like '%" + searchQuery
					+ "%' or person like '%" + searchQuery + "%')";
		} else if (searchQueryFlag && regionFlag && metricFlag
				&& !timeperiodFlag) {
			// 12. statement/person & region & metric
			query = "select * from entities where region = '" + region
					+ "' and metric = '" + metric + "' and (statement like '%"
					+ searchQuery + "%' or person like '%" + searchQuery
					+ "%')";
		} else if (searchQueryFlag && regionFlag && !metricFlag
				&& timeperiodFlag) {
			// 13. statement/person & region & timeperiod
			query = "select * from entities where region = '" + region
					+ "' and time_period = '" + timeperiod
					+ "' and (statement like '%" + searchQuery
					+ "%' or person like '%" + searchQuery + "%')";
		} else if (searchQueryFlag && !regionFlag && metricFlag
				&& timeperiodFlag) {
			// 14. statement/person & metric & timeperiod
			query = "select * from entities where metric = '" + metric
					+ "' and time_period = '" + timeperiod
					+ "' and (statement like '%" + searchQuery
					+ "%' or person like '%" + searchQuery + "%')";
		} else if (searchQueryFlag && regionFlag && metricFlag
				&& timeperiodFlag) {
			// 15. statement/person & region & metric & timeperiod
			query = "select * from entities where region = '" + region
					+ "' and metric = '" + metric + "' and time_period = '"
					+ timeperiod + "' and (statement like '%" + searchQuery
					+ "%' or person like '%" + searchQuery + "%')";
		} else {
			// 16. if no criteria mentioned, return empty list
			query = null;
		}
		return query;
	}

	public Entity searchEntity(int entityId) {
		Entity entity = null;
		List<String> filePathsList = new ArrayList<String>();
		HashMap<Integer, String> relatedEntitiesMap = new HashMap<Integer, String>();
		try {
			entity = new Entity();

			// create connection
			Class.forName("org.sqlite.JDBC");
			Connection connection = DriverManager
					.getConnection("jdbc:sqlite:db/workforceresearchguide.db");

			PreparedStatement ps = connection
					.prepareStatement("select * from entities where entity_id = ?");
			ps.setInt(1, entityId);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				entity.setId(rs.getInt("entity_id"));
				entity.setStatement(rs.getString("statement"));

				Region r = new Region();
				r.setValue(rs.getString("region"));
				entity.setRegion(r);

				Metric m = new Metric();
				m.setValue(rs.getString("metric"));
				entity.setMetric(m);

				Timeperiod tp = new Timeperiod();
				tp.setValue(rs.getString("time_period"));
				entity.setTimeperiod(tp);

				Strength s = new Strength();
				s.setValue(rs.getString("strength"));
				entity.setStrength(s);

				entity.setBelief(rs.getBoolean("is_belief"));
				entity.setPerson(rs.getString("person"));
				entity.setPerson(rs.getString("person"));
				entity.setNote(rs.getString("note"));
			}

			// retrieve entity-entity relations
			ps = connection
					.prepareStatement("select er.related_entity_id, e.statement from entity_relations as er, entities as e where er.entity_id = ? and er.related_entity_id = e.entity_id");
			ps.setInt(1, entityId);

			rs = ps.executeQuery();
			while (rs.next()) {
				relatedEntitiesMap.put(rs.getInt(1), rs.getString(2));
			}
			entity.setRelatedEntities(relatedEntitiesMap);

			// retrieve entity-file relations
			ps = connection
					.prepareStatement("select * from file_relations where entity_id = ?");
			ps.setInt(1, entityId);

			rs = ps.executeQuery();
			while (rs.next()) {
				filePathsList.add(rs.getString("file_path"));
			}
			entity.setFilePaths(filePathsList);

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

	public Entity updateEntity(Entity entityUpdated) {
		Entity entityCurrent = searchEntity(entityUpdated.getId());

		try {
			List<String> updates = new ArrayList<String>();

			// different statement
			if (!((entityCurrent.getStatement() != null
					&& entityUpdated.getStatement() != null && entityCurrent
					.getStatement().equals(entityUpdated.getStatement())) || (entityUpdated
					.getStatement() == null && entityUpdated.getStatement() == null))) {
				updates.add("update entities set statement = '"
						+ entityUpdated.getStatement() + "' where entity_id = "
						+ entityUpdated.getId());
			}

			// different region
			if (!((entityCurrent.getRegion() != null
					&& entityUpdated.getRegion() != null && entityCurrent
					.getRegion().equals(entityUpdated.getRegion())) || (entityUpdated
					.getRegion() == null && entityUpdated.getRegion() == null))) {
				updates.add("update entities set region = '"
						+ entityUpdated.getRegion() + "' where entity_id = "
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
			if (!((entityCurrent.getTimeperiod() != null
					&& entityUpdated.getTimeperiod() != null && entityCurrent
					.getTimeperiod().equals(entityUpdated.getTimeperiod())) || (entityUpdated
					.getTimeperiod() == null && entityUpdated.getTimeperiod() == null))) {
				updates.add("update entities set time_period = '"
						+ entityUpdated.getTimeperiod()
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
			if (entityCurrent.isBelief() != entityUpdated.isBelief()) {
				updates.add("update entities set is_belief = '"
						+ (entityUpdated.isBelief() ? 1 : 0)
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
			HashMap<Integer, String> updatedEntityRelations = entityUpdated
					.getRelatedEntities();

			HashMap<Integer, String> currentEntityRelations = entityCurrent
					.getRelatedEntities();

			for (Integer entityId : updatedEntityRelations.keySet()) {
				if (!(currentEntityRelations.containsKey(entityId))) {
					// add this new relation
					updates.add("insert into entity_relations(entity_id, related_entity_id) values("
							+ entityUpdated.getId() + ", " + entityId + ")");
				}
			}

			for (Integer entityId : currentEntityRelations.keySet()) {
				if (!(updatedEntityRelations.containsKey(entityId))) {
					// remove this existing relation
					updates.add("delete from entity_relations where related_entity_id = "
							+ entityId
							+ " and entity_id = "
							+ entityUpdated.getId());
				}
			}

			// different entity-file relations
			List<String> updatedFileRelations = entityUpdated.getFilePaths();
			List<String> currentFileRelations = entityCurrent.getFilePaths();

			String[] updatedFileRelationsArr = updatedFileRelations
					.toArray(new String[0]);
			String[] currentFileRelationsArr = currentFileRelations
					.toArray(new String[0]);
			Arrays.sort(updatedFileRelationsArr);
			Arrays.sort(currentFileRelationsArr);

			for (int i = 0, j = 0; i < updatedFileRelationsArr.length
					&& j < currentFileRelationsArr.length;) {
				if (updatedFileRelationsArr[i] != null
						&& updatedFileRelationsArr[i]
								.compareTo(currentFileRelationsArr[j]) == 0) {
					i++;
					j++;
				} else if (updatedFileRelationsArr[i] != null
						&& updatedFileRelationsArr[i]
								.compareTo(currentFileRelationsArr[j]) < 0) {
					updates.add("insert into file_relations(entity_id, file_path) values("
							+ entityUpdated.getId()
							+ ",'"
							+ updatedFileRelationsArr[i] + "')");
					i++;
				} else if (updatedFileRelationsArr[i] != null
						&& updatedFileRelationsArr[i]
								.compareTo(currentFileRelationsArr[j]) > 0) {
					updates.add("delete from file_relations where entity_id = "
							+ entityUpdated.getId() + " and file_path = '"
							+ currentFileRelationsArr[j] + "'");
					j++;
				}
			}

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
        
        public List<Entity> retrieveAllEntities()
        {
            return new ArrayList<Entity>();        }
}
