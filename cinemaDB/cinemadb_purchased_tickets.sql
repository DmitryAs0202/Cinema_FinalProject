-- MySQL dump 10.13  Distrib 8.0.11, for Win64 (x86_64)
--
-- Host: localhost    Database: cinemadb
-- ------------------------------------------------------
-- Server version	8.0.21

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `purchased_tickets`
--

DROP TABLE IF EXISTS `purchased_tickets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `purchased_tickets` (
  `id` int NOT NULL,
  `owner_name` varchar(45) NOT NULL,
  `film_id` int NOT NULL,
  `seat_number` int NOT NULL,
  `price` int NOT NULL,
  `is_available` varchar(45) NOT NULL DEFAULT 'false',
  PRIMARY KEY (`id`,`owner_name`),
  KEY `filmID_idx` (`film_id`),
  KEY `owner_idx` (`owner_name`),
  CONSTRAINT `filmID` FOREIGN KEY (`film_id`) REFERENCES `films` (`film_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `owner` FOREIGN KEY (`owner_name`) REFERENCES `users` (`login`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchased_tickets`
--

LOCK TABLES `purchased_tickets` WRITE;
/*!40000 ALTER TABLE `purchased_tickets` DISABLE KEYS */;
INSERT INTO `purchased_tickets` VALUES (5,'Nicola1',2,23,6,'false'),(20,'Nicola1',3,4,10,'false'),(21,'Nicola1',3,5,10,'false'),(22,'John',3,6,10,'false'),(25,'VladimirVoronov',3,9,10,'false'),(28,'Jack',3,12,25,'false'),(36,'Alex',1,34,10,'false'),(38,'Jack',1,36,25,'false'),(55,'Kate22',5,10,10,'false'),(57,'Ann',5,12,10,'false'),(73,'John',6,8,10,'false'),(76,'George12',6,11,10,'false'),(82,'Kate22',6,17,25,'false'),(83,'Kate22',6,18,25,'false'),(104,'VladimirVoronov',7,20,25,'false'),(117,'Alex',8,7,10,'false'),(118,'Alex',8,8,10,'false'),(119,'Ann',8,9,10,'false'),(120,'Ann',8,10,10,'false');
/*!40000 ALTER TABLE `purchased_tickets` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-10-13 10:23:43
