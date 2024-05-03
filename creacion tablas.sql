CREATE DATABASE  IF NOT EXISTS `integrador` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `integrador`;
-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: integrador
-- ------------------------------------------------------
-- Server version	8.0.36

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `generos`
--

DROP TABLE IF EXISTS `generos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `generos` (
  `id_genero` int NOT NULL AUTO_INCREMENT,
  `genero` varchar(45) NOT NULL,
  PRIMARY KEY (`id_genero`),
  UNIQUE KEY `id_genero_UNIQUE` (`id_genero`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `generos`
--

LOCK TABLES `generos` WRITE;
/*!40000 ALTER TABLE `generos` DISABLE KEYS */;
INSERT INTO `generos` VALUES (1,'Drama'),(2,'Comedia'),(3,'Acción'),(4,'Ciencia Ficción'),(5,'Aventura');
/*!40000 ALTER TABLE `generos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pelicula_genero`
--

DROP TABLE IF EXISTS `pelicula_genero`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pelicula_genero` (
  `id` int NOT NULL AUTO_INCREMENT,
  `codigo_pelicula` int NOT NULL,
  `id_genero` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_codigo_pelicula_idx` (`codigo_pelicula`),
  KEY `fk_id_genero_idx` (`id_genero`),
  CONSTRAINT `fk_codigo_pelicula` FOREIGN KEY (`codigo_pelicula`) REFERENCES `peliculas` (`codigo`),
  CONSTRAINT `fk_id_genero` FOREIGN KEY (`id_genero`) REFERENCES `generos` (`id_genero`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pelicula_genero`
--

LOCK TABLES `pelicula_genero` WRITE;
/*!40000 ALTER TABLE `pelicula_genero` DISABLE KEYS */;
INSERT INTO `pelicula_genero` VALUES (4,7,1),(5,9,2),(6,10,4),(7,11,3),(8,11,1),(9,11,4),(10,13,5);
/*!40000 ALTER TABLE `pelicula_genero` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `peliculas`
--

DROP TABLE IF EXISTS `peliculas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `peliculas` (
  `codigo` int NOT NULL AUTO_INCREMENT,
  `titulo` varchar(100) NOT NULL,
  `url_sitio` varchar(200) NOT NULL,
  `url_img` varchar(45) DEFAULT NULL,
  `generos` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  UNIQUE KEY `codigo_UNIQUE` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `peliculas`
--

LOCK TABLES `peliculas` WRITE;
/*!40000 ALTER TABLE `peliculas` DISABLE KEYS */;
INSERT INTO `peliculas` VALUES (7,'Titanic','www.imdb.com/title/tt0120338/','titanic.webp','Drama'),(9,'Esperando la carroza','www.imdb.com/title/tt0089108/?ref_=nv_sr_srsg_0_tt_4_nm_0_q_esperando%2520la%2520carroza','esp_la_carroza.jpg','Comedia'),(10,'Star Wars: Episode IV - A New Hope','www.imdb.com/title/tt0076759/?ref_=nv_sr_srsg_0_tt_8_nm_0_q_star%2520wars%2520episode%25204','sw_ep_4.jpg','Ciencia Ficción'),(11,'I Am Legend','www.imdb.com/title/tt0480249/?ref_=nv_sr_srsg_0_tt_7_nm_1_q_soy%2520leyenda','soy_leyenda.jpg','Acción, Drama, Ciencia Ficción'),(13,'Fallout','https://www.imdb.com/title/tt12637874/?ref_=ls_mv_desc','fallout.webp','Aventura');
/*!40000 ALTER TABLE `peliculas` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-03 12:51:22
