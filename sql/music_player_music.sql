CREATE DATABASE  IF NOT EXISTS `music_player` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `music_player`;
-- MySQL dump 10.13  Distrib 8.0.43, for Win64 (x86_64)
--
-- Host: localhost    Database: music_player
-- ------------------------------------------------------
-- Server version	8.0.43

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
-- Table structure for table `music`
--

DROP TABLE IF EXISTS `music`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `music` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `youtube_url` text,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `music_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `music`
--

LOCK TABLES `music` WRITE;
/*!40000 ALTER TABLE `music` DISABLE KEYS */;
INSERT INTO `music` VALUES (7,6,'TYSON YOSHI -  你不會一輩子的愛上我 Glad That We Met (Official Music Video)','https://www.youtube.com/watch?v=M8vhBqXpXH4','2025-08-20 10:52:55'),(9,6,'Bu$Y & Ye!!ow , Paper Jim - 【戀曲2020 LOVE SONG 2020】Chapter 1 (Official Music Video)','https://www.youtube.com/watch?v=2oZr3uv_ddc','2025-08-20 11:37:24'),(13,6,'ROSÉ ft. Post Malone – \"Tell Me\" (NEOX Music Video)','https://www.youtube.com/watch?v=BK2VRk-KbzQ','2025-08-21 09:11:04'),(16,6,'TVアニメ『ダンダダン』HAYASii「Hunting Soul」【lyric video】','https://www.youtube.com/watch?v=XoodunTw0kw','2025-08-21 10:59:25'),(21,6,'ReoNa「End of Days」× TVアニメ『アークナイツ【焔燼曙明/RISE FROM EMBER】』アニメコラボ Music Video','https://www.youtube.com/watch?v=xYCvpdigzUA','2025-08-22 11:18:28'),(32,7,'ROSÉ ft. Post Malone – \"Tell Me\" (NEOX Music Video)','https://www.youtube.com/watch?v=BK2VRk-KbzQ','2025-08-22 11:53:14'),(34,7,'Bu$Y & Ye!!ow , Paper Jim - 【戀曲2020 LOVE SONG 2020】Chapter 1 (Official Music Video)','https://www.youtube.com/watch?v=2oZr3uv_ddc','2025-08-22 11:54:44'),(36,7,'TIAB X GORDON FLANDERS - 404 Not Found','https://www.youtube.com/watch?v=kvKSi5_pnjk','2025-08-22 11:55:21'),(43,7,'纯享：单依纯《R&B All Night》律动风慵懒惬意 转音舒适大vocal开口跪 | 爆裂舞台 EP01 | Stage Boom | iQiyi精选','https://www.youtube.com/watch?v=V7GdVVrXldk','2025-08-22 12:03:28'),(44,7,'「Renegade」Substantial / X.ARI / Jason Walsh','https://www.youtube.com/watch?v=niqHn6vEwy4','2025-08-22 12:03:45'),(58,8,'何維健 Derrick Hoh【當我知道你們相愛 Acceptance - Dance Version】官方 Official MV','https://www.youtube.com/watch?v=xXKSo6EAj3A','2025-08-22 13:34:33'),(65,8,'理想混蛋 Bestards【離開的一路上 Farewell】Official Music Video','https://www.youtube.com/watch?v=3hLESh77fSg','2025-08-22 13:37:38'),(66,8,'Bu$Y & Ye!!ow , Paper Jim - 【沒注意到你在流眼淚】Starring:鄭人碩、莫允雯(Official Music Video)','https://www.youtube.com/watch?v=asENVVLjAeo','2025-08-22 13:37:53'),(67,8,'Bu$Y & Ye!!ow , Paper Jim - 【傷害你是我的壞習慣】Starring:郭書瑤 (Official Music Video)','https://www.youtube.com/watch?v=XaUGV1Ylp8M','2025-08-22 13:38:17'),(69,8,'LOLLIPOP@F 『怎麼了』官方MV','https://www.youtube.com/watch?v=uZtxOPNtWCY','2025-08-22 13:39:37'),(70,8,'BIG SHOT -- LOLLIPOP@F 官方MV 「那麼厲害」','https://www.youtube.com/watch?v=nRLov-xVYks','2025-08-22 13:40:00'),(81,13,'【鬼滅之刃】鬼滅之刃劇場版-無限城篇-第一章-猗窩座再襲 片尾曲 ED「残酷な夜に輝け」 LiSA |完整版|CC字幕|中日字幕 【4K】','https://www.youtube.com/watch?v=0Zv5p_RdNS0','2025-08-22 14:57:19');
/*!40000 ALTER TABLE `music` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-08-22 16:09:34
