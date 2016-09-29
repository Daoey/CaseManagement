CREATE DATABASE  IF NOT EXISTS `case_db` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `case_db`;
-- MySQL dump 10.13  Distrib 5.7.12, for linux-glibc2.5 (x86_64)
--
-- Host: localhost    Database: case_db
-- ------------------------------------------------------
-- Server version	5.7.15-0ubuntu0.16.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `issue_table`
--

DROP TABLE IF EXISTS `issue_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `issue_table` (
  `idissue_table` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(45) DEFAULT NULL,
  `idwork_item` int(11) NOT NULL,
  PRIMARY KEY (`idissue_table`),
  UNIQUE KEY `idissue_table_UNIQUE` (`idissue_table`),
  KEY `fk_issue_work_item_idx` (`idwork_item`),
  CONSTRAINT `fk_issue_work_item` FOREIGN KEY (`idwork_item`) REFERENCES `work_item_table` (`idwork_item_table`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `issue_table`
--

LOCK TABLES `issue_table` WRITE;
/*!40000 ALTER TABLE `issue_table` DISABLE KEYS */;
/*!40000 ALTER TABLE `issue_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `status_table`
--

DROP TABLE IF EXISTS `status_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `status_table` (
  `idstatus_table` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`idstatus_table`),
  UNIQUE KEY `idstatus_table_UNIQUE` (`idstatus_table`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `status_table`
--

LOCK TABLES `status_table` WRITE;
/*!40000 ALTER TABLE `status_table` DISABLE KEYS */;
/*!40000 ALTER TABLE `status_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `team_table`
--

DROP TABLE IF EXISTS `team_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `team_table` (
  `idteam_table` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `active` tinyint(4) NOT NULL,
  PRIMARY KEY (`idteam_table`),
  UNIQUE KEY `idteam_table_UNIQUE` (`idteam_table`),
  CONSTRAINT `fk_team_user` FOREIGN KEY (`idteam_table`) REFERENCES `user_table` (`idteam`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `team_table`
--

LOCK TABLES `team_table` WRITE;
/*!40000 ALTER TABLE `team_table` DISABLE KEYS */;
/*!40000 ALTER TABLE `team_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_table`
--

DROP TABLE IF EXISTS `user_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_table` (
  `iduser_table` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `username` varchar(45) NOT NULL,
  `active` tinyint(4) NOT NULL,
  `idteam` int(11) DEFAULT NULL,
  PRIMARY KEY (`iduser_table`),
  UNIQUE KEY `id_user_UNIQUE` (`iduser_table`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  KEY `fk_user_team_idx` (`idteam`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_table`
--

LOCK TABLES `user_table` WRITE;
/*!40000 ALTER TABLE `user_table` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `work_item_table`
--

DROP TABLE IF EXISTS `work_item_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `work_item_table` (
  `idwork_item_table` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(45) DEFAULT NULL,
  `idstatus` int(11) DEFAULT NULL,
  `iduser` int(11) DEFAULT NULL,
  PRIMARY KEY (`idwork_item_table`),
  UNIQUE KEY `idwork_item_table_UNIQUE` (`idwork_item_table`),
  KEY `fk_work_item_status_idx` (`idstatus`),
  KEY `fk_work_item_user_idx` (`iduser`),
  CONSTRAINT `fk_work_item_status` FOREIGN KEY (`idstatus`) REFERENCES `status_table` (`idstatus_table`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_work_item_user` FOREIGN KEY (`iduser`) REFERENCES `user_table` (`iduser_table`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `work_item_table`
--

LOCK TABLES `work_item_table` WRITE;
/*!40000 ALTER TABLE `work_item_table` DISABLE KEYS */;
/*!40000 ALTER TABLE `work_item_table` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-09-29 12:54:20
