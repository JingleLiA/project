-- MySQL dump 10.13  Distrib 8.0.13, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: tree
-- ------------------------------------------------------
-- Server version	8.0.13

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
SET NAMES utf8mb4;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;

--
-- Table structure for table `tree_category`
--

DROP TABLE IF EXISTS `tree_category`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8mb4;
CREATE TABLE `tree_category`
(
  `id`            int(11) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(256) DEFAULT NULL COMMENT '类别名称',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='树木类别';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tree_category`
--

LOCK TABLES `tree_category` WRITE;
/*!40000 ALTER TABLE `tree_category`
  DISABLE KEYS */;
INSERT INTO `tree_category`
VALUES (1, '杨树'),
       (2, '柳树'),
       (3, '松树');
/*!40000 ALTER TABLE `tree_category`
  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tree_measurement`
--

DROP TABLE IF EXISTS `tree_measurement`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8mb4;
CREATE TABLE `tree_measurement`
(
  `id`                 int(11)  NOT NULL AUTO_INCREMENT,
  `time`               datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  `pic`                text,
  `diameter_calculate` double           DEFAULT NULL,
  `diameter_measure`   double           DEFAULT NULL,
  `diameter_correct`   double           DEFAULT NULL,
  `tree_code`          text     NOT NULL,
  `user_code`          char(12)         DEFAULT NULL,
  `times_no_dots`      int(11)          DEFAULT NULL,
  `times_no_treecode`  int(11)          DEFAULT NULL,
  `dots_calculate`     int(10) unsigned DEFAULT NULL,
  `dots_correct`       int(11)          DEFAULT NULL,
  `category_id`        int(11)          DEFAULT '1' COMMENT '数目类别',
  PRIMARY KEY (`id`),
  KEY `dots_id_calculate_fk` (`dots_calculate`),
  KEY `dots_id_correct_fk` (`dots_correct`)
) ENGINE = MyISAM
  AUTO_INCREMENT = 15
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tree_measurement`
--

LOCK TABLES `tree_measurement` WRITE;
/*!40000 ALTER TABLE `tree_measurement`
  DISABLE KEYS */;
INSERT INTO `tree_measurement`
VALUES (1, '2019-04-27 22:11:43', NULL, 11.1, 11.1, NULL, 'tree111', '1', 1, 1, 1, 1, 1),
       (2, '2019-04-27 22:11:43', NULL, 11.2, 11.2, NULL, 'tree1111', '1', 1, 1, 2, 2, 1),
       (3, '2019-04-27 22:11:43', '123', 100, NULL, NULL, 'tree111', '1', 1, 1, 5, NULL, 1),
       (4, '2019-04-27 22:11:43', '123', 100, NULL, NULL, 'tree111', '1', 1, 1, 6, NULL, 1),
       (5, '2019-04-27 22:11:43', '123', 100, NULL, NULL, 'test', '1', 1, 1, 7, NULL, 2),
       (6, '2019-04-27 22:11:43', '123', 100, NULL, NULL, 'test', '1', 1, 1, 8, NULL, 2),
       (7, '2019-04-27 22:11:43', '123', 100, NULL, NULL, 'test', '1', 1, 1, 9, NULL, 2),
       (8, '2019-04-27 22:11:43', '123', 100, NULL, NULL, 'test', '1', 1, 1, 10, NULL, 2),
       (9, '2019-04-27 22:11:43', '123', 100, NULL, NULL, 'test', '1', 1, 1, 11, NULL, 2),
       (10, '2019-04-27 22:11:43', '123', 100, NULL, NULL, 'test', '1', 1, 1, 12, NULL, 2),
       (11, '2019-04-27 22:11:43', '123', 100, NULL, NULL, 'test', '1', 1, 1, 13, NULL, 2),
       (12, '2019-04-27 22:11:43', '123', 100, NULL, NULL, 'test', '1', 1, 1, 14, NULL, 2),
       (13, '2019-04-27 22:11:43', '123', 100, NULL, NULL, 'test', '1', 1, 1, 15, NULL, 2),
       (14, '2019-04-27 22:11:43', '123', 100, NULL, NULL, 'test', '1', 1, 1, 16, NULL, 2);
/*!40000 ALTER TABLE `tree_measurement`
  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tree_measurement_dots`
--

DROP TABLE IF EXISTS `tree_measurement_dots`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8mb4;
CREATE TABLE `tree_measurement_dots`
(
  `id`     int(11) NOT NULL AUTO_INCREMENT,
  `upX`    int(11) DEFAULT NULL,
  `upY`    int(11) DEFAULT NULL,
  `downX`  int(11) DEFAULT NULL,
  `downY`  int(11) DEFAULT NULL,
  `leftX`  int(11) DEFAULT NULL,
  `leftY`  int(11) DEFAULT NULL,
  `rightX` int(11) DEFAULT NULL,
  `rightY` int(11) DEFAULT NULL,
  `angleL` double  DEFAULT '0',
  `angleR` double  DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE = MyISAM
  AUTO_INCREMENT = 17
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tree_measurement_dots`
--

LOCK TABLES `tree_measurement_dots` WRITE;
/*!40000 ALTER TABLE `tree_measurement_dots`
  DISABLE KEYS */;
INSERT INTO `tree_measurement_dots`
VALUES (1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0),
       (2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
       (3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
       (4, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
       (5, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
       (6, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
       (7, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
       (8, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
       (9, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
       (10, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
       (11, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
       (12, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
       (13, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
       (14, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
       (15, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
       (16, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
/*!40000 ALTER TABLE `tree_measurement_dots`
  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tree_user`
--

DROP TABLE IF EXISTS `tree_user`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8mb4;
CREATE TABLE `tree_user`
(
  `id`       int(11)      NOT NULL AUTO_INCREMENT,
  `username` varchar(128) NOT NULL,
  `password` varchar(128) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tree_user`
--

LOCK TABLES `tree_user` WRITE;
/*!40000 ALTER TABLE `tree_user`
  DISABLE KEYS */;
INSERT INTO `tree_user`
VALUES (1, 'root', 'root'),
       (2, 'admin', 'admin');
/*!40000 ALTER TABLE `tree_user`
  ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE = @OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;

-- Dump completed on 2019-04-27 22:50:17
