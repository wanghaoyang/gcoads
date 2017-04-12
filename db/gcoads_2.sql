-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: gcoads
-- ------------------------------------------------------
-- Server version	5.7.17-log

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
-- Table structure for table `t_educationallevel`
--

DROP TABLE IF EXISTS `t_educationallevel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_educationallevel` (
  `elid` int(11) NOT NULL AUTO_INCREMENT,
  `educationallevel` varchar(45) NOT NULL,
  PRIMARY KEY (`elid`),
  UNIQUE KEY `educationallevel_UNIQUE` (`educationallevel`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_educationallevel`
--

LOCK TABLES `t_educationallevel` WRITE;
/*!40000 ALTER TABLE `t_educationallevel` DISABLE KEYS */;
INSERT INTO `t_educationallevel` VALUES (5,'博士毕业生'),(2,'大专毕业生'),(1,'无'),(3,'本科毕业生'),(4,'硕士毕业生');
/*!40000 ALTER TABLE `t_educationallevel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_graduate`
--

DROP TABLE IF EXISTS `t_graduate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_graduate` (
  `gid` int(11) NOT NULL AUTO_INCREMENT,
  `xuehao` char(15) NOT NULL,
  `studentname` varchar(45) DEFAULT NULL,
  `studentgender` varchar(1) DEFAULT NULL,
  `biyeshijian` date DEFAULT NULL,
  `xueyuan` varchar(45) DEFAULT NULL,
  `xibie` varchar(45) DEFAULT NULL,
  `banji` varchar(45) DEFAULT NULL,
  `gstatus` varchar(10) DEFAULT NULL,
  `elid` int(2) DEFAULT NULL,
  PRIMARY KEY (`gid`),
  UNIQUE KEY `xuehao_UNIQUE` (`xuehao`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_graduate`
--

LOCK TABLES `t_graduate` WRITE;
/*!40000 ALTER TABLE `t_graduate` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_graduate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_student`
--

DROP TABLE IF EXISTS `t_student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_student` (
  `sid` int(11) NOT NULL AUTO_INCREMENT,
  `kaoshenghao` char(32) NOT NULL,
  `shenfenzhenghao` char(32) NOT NULL,
  `xuehao` char(15) NOT NULL,
  `studentname` varchar(45) NOT NULL,
  `studentgender` varchar(1) NOT NULL,
  `minzu` varchar(10) NOT NULL,
  `zhengzhimianmao` varchar(45) NOT NULL,
  `zhuanye` varchar(45) NOT NULL,
  `zhuanyefangxiang` varchar(45) DEFAULT NULL,
  `peiyangfangshi` varchar(45) DEFAULT NULL,
  `xuezhi` int(11) NOT NULL,
  `ruxueshijian` date NOT NULL,
  `biyeshijian` date NOT NULL,
  `shifanshengleibie` varchar(15) NOT NULL,
  `xueyuan` varchar(45) NOT NULL,
  `xibie` varchar(45) NOT NULL,
  `banji` varchar(45) NOT NULL,
  `chushengriqi` varchar(45) NOT NULL,
  `shengyuansuozaidi` varchar(45) NOT NULL,
  `email` varchar(50) NOT NULL,
  `address` varchar(200) NOT NULL,
  PRIMARY KEY (`sid`),
  UNIQUE KEY `kaoshenghao_UNIQUE` (`kaoshenghao`),
  UNIQUE KEY `shenfenzhenghao_UNIQUE` (`shenfenzhenghao`),
  UNIQUE KEY `xuehao_UNIQUE` (`xuehao`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_student`
--

LOCK TABLES `t_student` WRITE;
/*!40000 ALTER TABLE `t_student` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_test`
--

DROP TABLE IF EXISTS `t_test`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_test` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `test_date` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_test`
--

LOCK TABLES `t_test` WRITE;
/*!40000 ALTER TABLE `t_test` DISABLE KEYS */;
INSERT INTO `t_test` VALUES (1,'2017-04-11'),(2,'2017-04-11');
/*!40000 ALTER TABLE `t_test` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_user`
--

DROP TABLE IF EXISTS `t_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_user` (
  `num` int(11) NOT NULL AUTO_INCREMENT,
  `uid` char(32) NOT NULL,
  `loginname` varchar(50) NOT NULL,
  `loginpass` varchar(50) NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  `role` varchar(20) NOT NULL,
  `status` tinyint(1) NOT NULL,
  `activationCode` char(64) DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL,
  PRIMARY KEY (`num`),
  UNIQUE KEY `loginname` (`loginname`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user`
--

LOCK TABLES `t_user` WRITE;
/*!40000 ALTER TABLE `t_user` DISABLE KEYS */;
INSERT INTO `t_user` VALUES (1,'14A63D16828E440FB41BB0BC0711967C','aaaaaa','000000','1111111@qq.com','企业用户',0,'8FD1BAA0D7E84A4F87C8C84FB28EAAEC9D9DD935FF2D42BFB5A45E31B9614BF4',0),(2,'19D020E3283F4DA99252491B0BA7A4E5','bbbbbb','bbbbbb','2222222@qq.com','企业用户',1,'56F496174DE14E35A43D8056F48FE6EB43031F94D5E74D03BA3E77E13A10A801',0),(3,'23B54EBA829E4637833B8A3BF6709851','kkkkkk','kkkkkk','1111113@qq.com','企业用户',1,'0AB3B8A5FB8B4E7B93A32E17AC21852BC59DE9412A6F4BADA9781BB9D99F65E5',0),(4,'25C74A2FBAFD46E5838D91AD39C88C08','oooooo','oooooo','1111117@qq.com','企业用户',1,'A1C5AF2CBC6F45C595F2CB862E25F0198F810CD3CE934C8A8E23308122CE78B3',0),(5,'27B1F1AE50B24934BE85A85EF3F392DB','mmmmmm','mmmmmm','1111115@qq.com','企业用户',1,'7346E24C96DC434E8A1B54F03ACDAC9339A0AB82991149CCA7647D294991EB24',0),(6,'5AB626806B1D4A2295EE10394BAF456F','iiiiii','iiiiii','999999@qq.com','企业用户',1,'ECB297DC6E8148DBA4A0C73710BCE577DD46925CFCB0471E8CF48F46847671F4',0),(7,'5F0BA350CA174186BD72D7D617D44899','eeeeee','eeeeee','555555@qq.com','企业用户',1,'7D15CF57E0DA44DC8D86E9B3DDEF94C6568D5387AA714545B4A2B33156E35C92',0),(8,'6ECA90D8AF934723842435FB35CAA20B','pppppp','pppppp','1111118@qq.com','企业用户',1,'BB2008A3ABFF410CA907343BE998FFC5BD95757C23B747AAB31602DFF09CD7A8',0),(9,'72B5C8C5AE7C4C0F817D900F26F44C3A','cccccc','cccccc','333333@qq.com','企业用户',1,'289DB7E919B04D94B9B5CBADD5F31B13811C667E1BF14A8DA69AEC7426A9CA56',0),(10,'8DED8B0262B94A81857A0260A36C9AC7','llllll','000000','1111114@qq.com','企业用户',1,'AC2E43DFA6B14A1AA9C41A0165E924EFCF00E156C9734C519AC5A8D5C75302EE',0),(11,'9C346E1A1E2640F9A994E52C7EB3828D','hhhhhh','hhhhhh','888888@qq.com','企业用户',1,'539AA93B24F54FF68EACC22C8556EC71F9F0D1128B77462C89338BA65DB72386',0),(12,'B1F02C60F4484EB6884C5B83F69569AA','jjjjjj','jjjjjj','1111112@qq.com','企业用户',1,'0F3521AAED0A45EBB84CA8F96A899EB10D423D67C1EE4F22BD5DC845E160A90C',0),(13,'C16EE139AD9947AEA4424EEC3D9D62D5','ffffff','ffffff','666666@qq.com','企业用户',1,'EBB2B0922DCB4A8E80CB29FB981B242E1695D6B3BC07474081E4D8C2E84207C9',0),(14,'C329A27104A94CF0A26FA341791E105D','nnnnnn','nnnnnn','1111116@qq.com','企业用户',1,'964FCB2FEB204EEFAEEE451921C9B960928EBFC9CBDC46C2AF211924371DE888',0),(15,'E6D3C72DD2F44581A4F4D74058D49BF9','gggggg','gggggg','777777@qq.com','企业用户',1,'EB239A9DF06849A2AADE5E55B6B8A71496F27392170445629B99F27BA09E56CD',0),(16,'E8D8C44D59C940D8ACE60F4E6982442A','admin','admin','1811513837@qq.com','管理员',1,'70D7FBAC04A340FCB7DA2410A0E9E06A40EBE9D04AF94B83A1EFEE3F50D2F5ED',0),(17,'F4E6AD0C0B8F42ACA1C7B37F36122A6E','euser','111111','1141029815@qq.com','企业用户',1,'B2F3DB6698B6425BB80CF4126EEDE4F2E19A4DA8734149B8B9A625D1AF80D2BC',0),(18,'F9E3B95662BF4A79B0624930D96F3BCE','dddddd','dddddd','444444@qq.com','企业用户',1,'DB106334E1544D95A36FE83822948BBDF0D56AC60109432798684D07F76B020C',1);
/*!40000 ALTER TABLE `t_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-04-12 11:19:57
