CREATE DATABASE `case_db` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `case_db`;

CREATE TABLE `status_table` (
  `idstatus_table` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`idstatus_table`),
  UNIQUE KEY `idstatus_table_UNIQUE` (`idstatus_table`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `team_table` (
  `idteam_table` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `active` tinyint(4) NOT NULL,
  PRIMARY KEY (`idteam_table`),
  UNIQUE KEY `idteam_table_UNIQUE` (`idteam_table`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

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
  KEY `fk_user_team_idx` (`idteam`),
  CONSTRAINT `fk_user_team` FOREIGN KEY (`idteam`) REFERENCES `team_table` (`idteam_table`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `work_item_table` (
  `idwork_item_table` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(45) DEFAULT NULL,
  `idstatus` int(11) DEFAULT NULL,
  `iduser` int(11) DEFAULT NULL,
  PRIMARY KEY (`idwork_item_table`),
  UNIQUE KEY `idwork_item_table_UNIQUE` (`idwork_item_table`),
  KEY `fk_work_item_status_idx` (`idstatus`),
  CONSTRAINT `fk_work_item_status` FOREIGN KEY (`idstatus`) REFERENCES `status_table` (`idstatus_table`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `issue_table` (
  `idissue_table` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(45) DEFAULT NULL,
  `idwork_item` int(11) NOT NULL,
  PRIMARY KEY (`idissue_table`),
  UNIQUE KEY `idissue_table_UNIQUE` (`idissue_table`),
  KEY `fk_issue_work_item_idx` (`idwork_item`),
  CONSTRAINT `fk_issue_work_item` FOREIGN KEY (`idwork_item`) REFERENCES `work_item_table` (`idwork_item_table`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

INSERT INTO `case_db`.`status_table`
(`idstatus_table`,
`name`)
VALUES
(1,
'Done');


INSERT INTO `case_db`.`status_table`
(`idstatus_table`,
`name`)
VALUES
(2,
'Started');


INSERT INTO `case_db`.`status_table`
(`idstatus_table`,
`name`)
VALUES
(3,
'Unstarted');

INSET INTO `case_db`.`user_table`
