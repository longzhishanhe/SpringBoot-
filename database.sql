-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: 2018011192
-- ------------------------------------------------------
-- Server version	8.0.19

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES gbk */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES (1,'刘宏远','lhy'),(2,'刘宇轩','lyx'),(3,'root','root');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `commodity`
--

DROP TABLE IF EXISTS `commodity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `commodity` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `price` int NOT NULL,
  `type` varchar(45) NOT NULL,
  `shopId` int NOT NULL,
  `damage_level` int NOT NULL COMMENT '损坏程度，10为全新，0为完全损坏',
  `status` tinyint DEFAULT '1' COMMENT '0已下架（商家手动下架或插入时默认），1在售卖（商家上线商品后需要经过管理员审核后才状态可变为1），-1已售罄（goods_count==0时）',
  `number` int NOT NULL DEFAULT '0',
  `total` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `forShop_idx` (`shopId`),
  CONSTRAINT `forShop` FOREIGN KEY (`shopId`) REFERENCES `shop` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `commodity`
--

LOCK TABLES `commodity` WRITE;
/*!40000 ALTER TABLE `commodity` DISABLE KEYS */;
INSERT INTO `commodity` VALUES (1,'甜甜花',100,'食物',7,10,1,40,1000),(2,'清心',50,'药物',7,8,1,48,100),(3,'杏仁豆腐',90,'食物',5,9,0,60,99),(4,'钩钩果',10,'药物',6,10,1,70,500),(5,'宵灯',100,'玩具',4,10,1,10,30),(6,'浮石',999,'玩具',7,10,1,0,10),(7,'原石',648,'奢侈品',7,10,1,160,160),(8,'琉璃百合',999,'药物',7,500,1,100,100),(9,'123',123,'123',7,9,0,123,123),(10,'32423424',6546,'3213',7,2,0,324,324),(11,'炸鸡',123,'吃的',7,5,1,45,45),(12,'花朵',456,'花',7,6,1,23,23),(21,'测试1',123,'测试1',7,3,1,3,3),(22,'test',2,'==',16,4,1,43,100),(23,'测试1',123,'测试1',16,7,1,99,100);
/*!40000 ALTER TABLE `commodity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shop`
--

DROP TABLE IF EXISTS `shop`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shop` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `sex` tinyint NOT NULL DEFAULT '1',
  `account` varchar(45) DEFAULT NULL,
  `level` int NOT NULL DEFAULT '1' COMMENT '商家等级，用于收费1级收0.1%，2级收0.2%，3级收0.5%，4级收0.75%，5级收1%',
  `status` tinyint NOT NULL DEFAULT '-1' COMMENT '用户状态，0表示用户正常，-1表示用户还为通过管理员验证，1表示用户账号异常,2是已拒绝',
  `money` int NOT NULL DEFAULT '0' COMMENT '商铺钱包',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shop`
--

LOCK TABLES `shop` WRITE;
/*!40000 ALTER TABLE `shop` DISABLE KEYS */;
INSERT INTO `shop` VALUES (4,'群玉阁','qyg','110',0,'12312',1,2,0),(5,'万民堂','wmt','886',1,'63242',5,0,0),(6,'天使的馈赠','tsdkz','120',0,'41192',3,2,0),(7,'shop','shop','111',1,'1341',2,0,100),(8,'行秋','xq','1234',1,'8752783',1,-1,0),(9,'123','123','undefined',0,'undefined',1,-1,0),(10,'qqqqq','qqqqq','12345',1,'12345',1,-1,0),(11,'233','22323','3333',0,'3432423',1,-1,0),(12,'88','88','88',0,'88',1,-1,0),(13,'432','34','34',0,'43',1,-1,0),(14,'000','000','000',0,'999',1,0,0),(15,'lyx321','lyx321','111111',1,'111111',1,0,0),(16,'shop1','shop1','123',1,'123',1,0,123);
/*!40000 ALTER TABLE `shop` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shop_car`
--

DROP TABLE IF EXISTS `shop_car`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shop_car` (
  `id` int NOT NULL AUTO_INCREMENT,
  `commodityId` int NOT NULL,
  `userId` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `cF_idx` (`commodityId`),
  KEY `uF_idx` (`userId`),
  CONSTRAINT `cF` FOREIGN KEY (`commodityId`) REFERENCES `commodity` (`id`),
  CONSTRAINT `uF` FOREIGN KEY (`userId`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shop_car`
--

LOCK TABLES `shop_car` WRITE;
/*!40000 ALTER TABLE `shop_car` DISABLE KEYS */;
INSERT INTO `shop_car` VALUES (4,2,3),(25,2,10);
/*!40000 ALTER TABLE `shop_car` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction`
--

DROP TABLE IF EXISTS `transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaction` (
  `id` int NOT NULL AUTO_INCREMENT,
  `commodityId` int NOT NULL,
  `userId` int NOT NULL,
  `status` int NOT NULL DEFAULT '0' COMMENT '待发货0、发货中1、已签收2',
  PRIMARY KEY (`id`),
  KEY `FK_user_deal_idx` (`userId`),
  KEY `FK_commodity_deal_idx` (`commodityId`),
  CONSTRAINT `FK_commodity_deal` FOREIGN KEY (`commodityId`) REFERENCES `commodity` (`id`),
  CONSTRAINT `FK_user_deal` FOREIGN KEY (`userId`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='交易记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction`
--

LOCK TABLES `transaction` WRITE;
/*!40000 ALTER TABLE `transaction` DISABLE KEYS */;
INSERT INTO `transaction` VALUES (1,5,4,2),(2,11,4,2),(3,2,4,1),(4,2,4,1),(5,4,4,1),(6,21,4,2),(7,11,4,1),(8,1,4,0),(9,1,4,0),(10,1,4,0),(11,1,4,0),(12,23,11,2);
/*!40000 ALTER TABLE `transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `sex` tinyint NOT NULL DEFAULT '1',
  `account` varchar(45) DEFAULT NULL,
  `money` int DEFAULT '0',
  `integral` int DEFAULT '0',
  `email` varchar(45) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  `status` tinyint DEFAULT '-1' COMMENT '0表示用户正常，-1表示用户还为通过管理员验证，1表示用户账号异常,2是已拒绝',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'刻晴','kq','2314',0,'21522',5000,9999,'gsgr@qq.com','璃月',-1),(2,'凝光','ng','21415',0,'2142',100100,131242,'saf@163.com','璃月',0),(3,'迪卢克','dlk','1241',1,'1653',21312,41214,'faf@163.com','蒙德',0),(4,'user','user','453',1,'3124',6310,33219,'ttt@qq.com','北京',0),(5,'空','user','414',1,'1234215',200,0,'tew@qq.com','提瓦特',0),(6,'荧','user','123',0,'14113335',0,0,'ttq@qq.com','提瓦特',-1),(7,'荧','user','123',0,'14113335',0,0,'ttq@qq.com','提瓦特',2),(8,'荧','user','123',0,'14113335',0,0,'ttq@qq.com','提瓦特',0),(9,'e','11231','',1,'',100,0,'231','2132',0),(10,'lyx123','lyx123','',1,'',10651,0,'1111111','111111',0),(11,'buy1','buy1','',1,'',9877,9877,'123','广州',0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-06-28 16:27:45
