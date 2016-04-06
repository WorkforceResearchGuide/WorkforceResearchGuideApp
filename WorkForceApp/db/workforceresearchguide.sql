-- --------------------------------------------------------

-- Steps(Windows laptop):

-- 1. Install sqlite3 and perform .read thisFile.sql
-- 2. open cmd, cd to project's db directory
-- 3. run "sqlite3 workforceresearchguide.db". This will start this database. If it exisits it will open that, otherwise it will create a new database with this name.
-- 4. run ".read workforceresearchguide.sql". This will run the sql statements from the file. it will create the required schema.
-- 5. To quite, run ".quit".

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

DROP TABLE IF EXISTS `metrics`;
CREATE TABLE IF NOT EXISTS `metrics` (
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

-- --------------------------------------------------------

--
-- Table structure for table `strengths`
--

DROP TABLE IF EXISTS `strengths`;
CREATE TABLE IF NOT EXISTS `strengths` (
`strength_id` integer PRIMARY KEY AUTOINCREMENT,
  `value` varchar(100),
  `is_disabled` boolean
);