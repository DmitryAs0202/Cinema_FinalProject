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
-- Table structure for table `available_tickets`
--

DROP TABLE IF EXISTS `available_tickets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `available_tickets` (
  `ticket_id` int NOT NULL AUTO_INCREMENT,
  `owner_name` varchar(45) NOT NULL DEFAULT 'null',
  `film_id` int DEFAULT NULL,
  `seat_number` int NOT NULL,
  `price` int NOT NULL,
  `is_available` varchar(45) NOT NULL DEFAULT 'true',
  PRIMARY KEY (`ticket_id`),
  KEY `film_id_idx` (`film_id`),
  CONSTRAINT `film_id` FOREIGN KEY (`film_id`) REFERENCES `films` (`film_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=131 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `available_tickets`
--

LOCK TABLES `available_tickets` WRITE;
/*!40000 ALTER TABLE `available_tickets` DISABLE KEYS */;
INSERT INTO `available_tickets` VALUES (1,'null',1,12,6,'true'),(2,'null',1,2,6,'true'),(3,'null',1,6,8,'true'),(8,'null',2,46,8,'true'),(9,'null',1,29,6,'true'),(11,'null',2,2,10,'true'),(12,'null',2,3,10,'true'),(13,'null',2,4,10,'true'),(14,'null',2,5,10,'true'),(15,'null',2,6,25,'true'),(16,'null',2,7,25,'true'),(17,'null',3,1,10,'true'),(18,'null',3,2,10,'true'),(19,'null',3,3,10,'true'),(23,'null',3,7,10,'true'),(24,'null',3,8,10,'true'),(26,'null',3,10,10,'true'),(27,'null',3,11,25,'true'),(29,'null',3,13,25,'true'),(30,'null',3,14,25,'true'),(31,'null',3,15,25,'true'),(32,'null',1,30,10,'true'),(33,'null',1,31,10,'true'),(34,'null',1,32,10,'true'),(35,'null',1,33,10,'true'),(37,'null',1,35,10,'true'),(46,'null',5,1,10,'true'),(47,'null',5,2,10,'true'),(48,'null',5,3,10,'true'),(49,'null',5,4,10,'true'),(50,'null',5,5,10,'true'),(51,'null',5,6,10,'true'),(52,'null',5,7,10,'true'),(53,'null',5,8,10,'true'),(54,'null',5,9,10,'true'),(56,'null',5,11,10,'true'),(58,'null',5,13,10,'true'),(59,'null',5,14,10,'true'),(60,'null',5,15,10,'true'),(61,'null',5,16,25,'true'),(62,'null',5,17,25,'true'),(64,'null',5,19,25,'true'),(65,'null',5,20,25,'true'),(66,'null',6,1,10,'true'),(67,'null',6,2,10,'true'),(68,'null',6,3,10,'true'),(69,'null',6,4,10,'true'),(70,'null',6,5,10,'true'),(71,'null',6,6,10,'true'),(72,'null',6,7,10,'true'),(74,'null',6,9,10,'true'),(75,'null',6,10,10,'true'),(77,'null',6,12,10,'true'),(78,'null',6,13,10,'true'),(79,'null',6,14,10,'true'),(80,'null',6,15,10,'true'),(81,'null',6,16,25,'true'),(84,'null',6,19,25,'true'),(85,'null',7,1,10,'true'),(86,'null',7,2,10,'true'),(87,'null',7,3,10,'true'),(88,'null',7,4,10,'true'),(89,'null',7,5,10,'true'),(90,'null',7,6,10,'true'),(91,'null',7,7,10,'true'),(92,'null',7,8,10,'true'),(93,'null',7,9,10,'true'),(94,'null',7,10,10,'true'),(96,'null',7,12,10,'true'),(98,'null',7,14,10,'true'),(99,'null',7,15,10,'true'),(100,'null',7,16,25,'true'),(101,'null',7,17,25,'true'),(102,'null',7,18,25,'true'),(103,'null',7,19,25,'true'),(105,'null',5,21,10,'true'),(106,'null',5,22,10,'true'),(107,'null',5,23,10,'true'),(108,'null',5,24,10,'true'),(109,'null',5,25,10,'true'),(110,'null',5,26,25,'true'),(111,'null',8,1,10,'true'),(112,'null',8,2,10,'true'),(113,'null',8,3,10,'true'),(114,'null',8,4,10,'true'),(115,'null',8,5,10,'true'),(116,'null',8,6,10,'true'),(121,'null',8,11,10,'true'),(122,'null',8,12,10,'true'),(123,'null',8,13,10,'true'),(124,'null',8,14,10,'true'),(125,'null',8,15,10,'true'),(126,'null',8,16,25,'true'),(127,'null',8,17,25,'true'),(128,'null',8,18,25,'true'),(129,'null',8,19,25,'true'),(130,'null',8,20,25,'true');
/*!40000 ALTER TABLE `available_tickets` ENABLE KEYS */;
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
