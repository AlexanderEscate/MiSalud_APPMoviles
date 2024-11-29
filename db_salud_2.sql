-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: localhost    Database: db_salud
-- ------------------------------------------------------
-- Server version	8.0.33

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
-- Table structure for table `servicios`
--

DROP TABLE IF EXISTS `servicios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `servicios` (
  `id_servicio` int NOT NULL AUTO_INCREMENT,
  `nombre_servicio` varchar(100) NOT NULL,
  `descripcion` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id_servicio`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `servicios`
--

LOCK TABLES `servicios` WRITE;
/*!40000 ALTER TABLE `servicios` DISABLE KEYS */;
INSERT INTO `servicios` VALUES (1,'Consulta Pediátrica','Atención médica especializada para niños'),(2,'Examen de Laboratorio','Análisis clínicos de sangre, orina, y más'),(3,'Terapia Física','Rehabilitación para la movilidad y aliviar el dolor'),(4,'Atención de Emergencias','Servicios inmediatos para casos urgentes'),(5,'Chequeo Cardiológico','Revisión integral de la salud del corazón'),(6,'Odontoligía','Tratamiento para corregir la alineación de los dientes'),(7,'Consulta Ginecológica','Atención médica especializada en la salud femenina'),(8,'Cirugía Ambulatoria','Procedimientos quirúrgicos menores sin hospitalización'),(9,'Ultrasonido','Imágenes de diagnóstico por ultrasonido no invasivo'),(10,'Nutrición y Dietética','Asesoramiento en alimentación saludable y balanceada'),(11,'Medicina Interna','Diagnóstico y tratamiento de enfermedades complejas.'),(12,'Psicología Clínica','Atención profesional para la salud mental'),(13,'Chequeo Prenatal','Cuidado médico especializado durante el embarazo');
/*!40000 ALTER TABLE `servicios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `id_usuario` int NOT NULL AUTO_INCREMENT,
  `nombres` varchar(100) NOT NULL,
  `apellidos` varchar(100) NOT NULL,
  `correo` varchar(150) NOT NULL,
  `contrasenia` varchar(255) NOT NULL,
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `correo` (`correo`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'Juan','Pérez','jp.perez@example.com','67890'),(2,'Ana','García','ana.garcia@example.com','123'),(3,'Luis','Torres','luis.torres@example.com','123'),(4,'María','López','maria.lopez@example.com','123'),(5,'Carlos','Ramírez','carlos.ramirez@example.com','123'),(13,'Juan','Péreza','jp.perez4444@example.com','67890'),(15,'n','n','n@gmail.com','1'),(16,'a','a','a@gmail.com','123');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-29  4:37:19