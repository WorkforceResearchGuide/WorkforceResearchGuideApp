-- Install sqlite3 and perform .read thisFile.sql
-- It will create the schema on your machine.
-- Create the database under /db folder for now, we will figure out where to kep it later.

-- --------------------------------------------------------

--
-- Table structure for table `entities`
--

DROP TABLE IF EXISTS `entities`;
CREATE TABLE IF NOT EXISTS `entities` (
`entity_id` integer PRIMARY KEY AUTOINCREMENT,
  `statement` varchar(250),
  `note` text,
  `region` varchar(100),
  `metric` varchar(100),
  `time_period` varchar(100),
  `is_belief` boolean,
  `person` varchar(100),
  `strength` varchar(100)
);

-- --------------------------------------------------------

--
-- Table structure for table `entity_relations`
--

DROP TABLE IF EXISTS `entity_relations`;
CREATE TABLE IF NOT EXISTS `entity_relations` (
`entity_relation_id` integer PRIMARY KEY AUTOINCREMENT,
  `entity_id` int(11),
  `related_entity_id` int(11)
);

-- --------------------------------------------------------

--
-- Table structure for table `file_relations`
--

DROP TABLE IF EXISTS `file_relations`;
CREATE TABLE IF NOT EXISTS `file_relations` (
`file_relation_id` integer PRIMARY KEY AUTOINCREMENT,
  `entity_id` int(11),
  `file_path` varchar(250)
);

-- --------------------------------------------------------

--
-- Table structure for table `metric`
--

DROP TABLE IF EXISTS `metric`;
CREATE TABLE IF NOT EXISTS `metric` (
`metric_id` integer PRIMARY KEY AUTOINCREMENT,
  `value` varchar(100),
  `is_disabled` boolean
);

-- --------------------------------------------------------

--
-- Table structure for table `regions`
--

DROP TABLE IF EXISTS `regions`;
CREATE TABLE IF NOT EXISTS `regions` (
`region_id` integer PRIMARY KEY AUTOINCREMENT,
  `value` varchar(100),
  `is_disabled` boolean
);

-- --------------------------------------------------------

--
-- Table structure for table `time_periods`
--

DROP TABLE IF EXISTS `time_periods`;
CREATE TABLE IF NOT EXISTS `time_periods` (
`time_period_id` integer PRIMARY KEY AUTOINCREMENT,
  `value` varchar(100),
  `is_disabled` boolean
);