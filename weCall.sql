/*
 Navicat Premium Data Transfer

 Source Server         : Torino (192.168.10.20)
 Source Server Type    : MariaDB
 Source Server Version : 100211
 Source Host           : 192.168.10.20
 Source Database       : weCall

 Target Server Type    : MariaDB
 Target Server Version : 100211
 File Encoding         : utf-8

 Date: 01/01/2018 11:01:12 AM
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `tg_centralini`
-- ----------------------------
DROP TABLE IF EXISTS `tg_centralini`;
CREATE TABLE `tg_centralini` (
  `Progressivo` int(10) NOT NULL AUTO_INCREMENT,
  `Descrizione` varchar(50) DEFAULT NULL,
  `IndirizzoIP` varchar(20) DEFAULT NULL,
  `Nazione` int(11) DEFAULT NULL,
  `Manager_login` varchar(20) DEFAULT NULL,
  `Manager_password` varchar(20) DEFAULT NULL,
  `Contesto` varchar(50) DEFAULT NULL,
  `Registra_telefonate` bit(1) NOT NULL DEFAULT b'0',
  `Anonimo` bit(1) DEFAULT NULL,
  `ApacheIP` varchar(20) DEFAULT NULL COMMENT 'Indirizzo IP del server su cui recuperare le telefonate eventualmente registrate',
  `ApacheUser` varchar(20) DEFAULT NULL,
  `ApachePass` varchar(20) DEFAULT NULL,
  `Data_inserimento` datetime DEFAULT NULL,
  `Login_inserimento` varchar(20) DEFAULT NULL,
  `Data_variazione` datetime DEFAULT NULL,
  `Login_variazione` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`Progressivo`),
  KEY `i_nazione` (`Nazione`),
  CONSTRAINT `fk_tg_centralini_nazione` FOREIGN KEY (`Nazione`) REFERENCES `tg_nazioni` (`Progressivo`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Table structure for `tg_nazioni`
-- ----------------------------
DROP TABLE IF EXISTS `tg_nazioni`;
CREATE TABLE `tg_nazioni` (
  `Progressivo` int(11) NOT NULL AUTO_INCREMENT,
  `Descrizione` varchar(35) NOT NULL,
  `Abilitata` bit(1) DEFAULT b'0',
  `Sigla` varchar(3) NOT NULL,
  `Prefisso_telefonico` varchar(10) DEFAULT NULL,
  `Anonimo` varchar(6) DEFAULT NULL COMMENT 'Stringa per rendere anonima la chiamata. Dipende dall''operatore utilizzato...Per telecom italia è #31#',
  `Data_inserimento` datetime DEFAULT NULL,
  `Login_inserimento` varchar(20) DEFAULT NULL,
  `Data_variazione` datetime DEFAULT NULL,
  `Login_variazione` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`Progressivo`)
) ENGINE=InnoDB AUTO_INCREMENT=480 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Table structure for `tg_telefoni`
-- ----------------------------
DROP TABLE IF EXISTS `tg_telefoni`;
CREATE TABLE `tg_telefoni` (
  `Progressivo` int(11) NOT NULL AUTO_INCREMENT,
  `Descrizione` varchar(35) DEFAULT NULL,
  `Indirizzo_IP` varchar(12) DEFAULT NULL,
  `Interno` varchar(35) DEFAULT NULL,
  `Login_inserimento` varchar(20) DEFAULT NULL,
  `Data_inserimento` datetime DEFAULT NULL,
  `Login_variazione` varchar(20) DEFAULT NULL,
  `Data_variazione` datetime DEFAULT NULL,
  PRIMARY KEY (`Progressivo`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

-- ----------------------------
--  Table structure for `tg_utenti`
-- ----------------------------
DROP TABLE IF EXISTS `tg_utenti`;
CREATE TABLE `tg_utenti` (
  `Utente` varchar(20) NOT NULL,
  `Passwd` varchar(32) NOT NULL,
  `Verifica` varchar(32) NOT NULL,
  `Tipo` char(1) NOT NULL DEFAULT 'U',
  `Cognome` varchar(50) NOT NULL,
  `Nome` varchar(50) NOT NULL,
  `Interno_telefonico` varchar(5) DEFAULT NULL,
  `Canale_telefonico` varchar(10) DEFAULT NULL,
  `Centralino_utilizzato` int(10) NOT NULL DEFAULT 1,
  `Colore_chiamata` varchar(10) DEFAULT '.' COMMENT 'aqua, black, blue, fuchsia, gray, green, lime, maroon, navy, olive, orange, purple, red, silver, teal, white, yellow',
  `Numero_badge` varchar(30) DEFAULT '',
  `Abilitato` bit(1) NOT NULL DEFAULT b'0',
  `Nazione` int(11) DEFAULT 106,
  `Avatar` varchar(255) DEFAULT NULL,
  `Data_cambio_passwd` datetime DEFAULT NULL,
  `Data_abilitazione` datetime DEFAULT NULL,
  `Data_disabilitazione` datetime DEFAULT NULL,
  `Data_ultimo_login` datetime DEFAULT NULL,
  `Data_inserimento` datetime DEFAULT NULL,
  `Data_variazione` datetime DEFAULT NULL,
  `Login_inserimento` varchar(20) DEFAULT NULL,
  `Login_variazione` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`Utente`),
  UNIQUE KEY `i_tg_utenti` (`Utente`),
  KEY `i_Nazione` (`Nazione`),
  KEY `i_Centralino_utilizzato` (`Centralino_utilizzato`),
  CONSTRAINT `FK_Centralino` FOREIGN KEY (`Centralino_utilizzato`) REFERENCES `tg_centralini` (`Progressivo`),
  CONSTRAINT `FK_Nazione` FOREIGN KEY (`Nazione`) REFERENCES `tg_nazioni` (`Progressivo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;

SET FOREIGN_KEY_CHECKS = 1;
