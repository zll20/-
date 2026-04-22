-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: localhost    Database: code
-- ------------------------------------------------------
-- Server version	8.0.41

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `config`
--

DROP TABLE IF EXISTS `config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `config` (
                          `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                          `name` varchar(100) NOT NULL COMMENT '配置参数名称',
                          `value` varchar(200) DEFAULT NULL COMMENT '配置参数值',
                          `url` varchar(500) DEFAULT NULL COMMENT 'url',
                          `type` int DEFAULT NULL COMMENT '参数类型',
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb3 COMMENT='配置文件';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `config`
--

LOCK TABLES `config` WRITE;
/*!40000 ALTER TABLE `config` DISABLE KEYS */;
INSERT INTO `config` VALUES (1,'picture1','upload/picture1.jpg',NULL,1),(2,'picture2','upload/picture2.jpg',NULL,1),(3,'picture3','upload/picture3.jpg',NULL,1),(11,'baidu','{\"appId\":\"49214550\",\"apiKey\":\"7Otjpv2kn0ljQk45qXOXh5MO\",\"secretKey\":\"BMfbXRbTIVaB4C3SbRTtGqDv1wHDvyXS\"}',NULL,2),(13,'aliyun','{\"accessKeyId\":\"LTAI5tFUSkPQR7FpEY8U7tY7\",\"accessKeySecret\":\"ZJrNPnyem4IT5HGOWPxl8nx7GRINw9\"}',NULL,2),(21,'bLoginBackgroundImg','',NULL,3),(22,'bRegisterBackgroundImg','',NULL,3),(23,'bIndexBackgroundImg','',NULL,3),(24,'bTopLogo','',NULL,3),(25,'bHomeLogo','',NULL,3),(29,'appLoginBackgroundImg','',NULL,3),(30,'appRegisterBackgroudImg','',NULL,3),(31,'appLoginLogo','',NULL,3),(32,'appRegLogo','',NULL,3);
/*!40000 ALTER TABLE `config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dancifenlei`
--

DROP TABLE IF EXISTS `dancifenlei`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dancifenlei` (
                               `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                               `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               `dancifenlei` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '单词分类',
                               PRIMARY KEY (`id`),
                               UNIQUE KEY `dancifenlei` (`dancifenlei`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='单词分类';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dancifenlei`
--

LOCK TABLES `dancifenlei` WRITE;
/*!40000 ALTER TABLE `dancifenlei` DISABLE KEYS */;
INSERT INTO `dancifenlei` VALUES (9,'2026-04-06 10:37:54','名词'),(10,'2026-04-06 10:37:54','动词'),(11,'2026-04-06 10:37:54','形容词'),(12,'2026-04-06 10:37:54','副词'),(13,'2026-04-06 10:37:54','代词'),(14,'2026-04-06 10:37:54','介词'),(15,'2026-04-06 10:37:54','连词'),(16,'2026-04-06 10:37:54','感叹词');
/*!40000 ALTER TABLE `dancifenlei` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `danciku`
--

DROP TABLE IF EXISTS `danciku`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `danciku` (
                           `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                           `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                           `danci` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '单词',
                           `jibie` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '级别',
                           `dancifenlei` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '单词分类',
                           `shiyi` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '释义',
                           `liju` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '例句',
                           `fengmiantu` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '封面图',
                           `clicktime` datetime DEFAULT NULL COMMENT '最近点击时间',
                           `discussnum` int DEFAULT '0' COMMENT '评论数',
                           `storeupnum` int DEFAULT '0' COMMENT '收藏数',
                           `duyin` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '读音',
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='单词库';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `danciku`
--

LOCK TABLES `danciku` WRITE;
/*!40000 ALTER TABLE `danciku` DISABLE KEYS */;
INSERT INTO `danciku` VALUES (1,'2026-03-14 15:58:01','abundant','四级','名词','丰富的、充裕的','Theareahasabundantnaturalresources.','upload/danciku_abundant1.jpg,upload/danciku_abundant2.jpg,upload/danciku_abundant3.jpg','2026-03-14 23:58:01',0,1,'upload/1775470882221_387.mp3'),(2,'2026-03-14 15:58:01','authority','四级','感叹词','权威、当局','Thegovernmenthastheauthoritytomakelaws.','upload/danciku_authority1.jpg,upload/danciku_authority2.jpg,upload/danciku_authority3.jpg','2026-03-14 23:58:01',0,2,'upload/1775470859746_935.mp3'),(3,'2026-03-14 15:58:01','available','四级','名词','可获得的','Theproductisavailableinstoresnow.','upload/danciku_available1.jpg,upload/danciku_available2.jpg,upload/danciku_available3.jpg','2026-03-14 23:58:01',0,3,'upload/1775470835950_39.mp3'),(4,'2026-03-14 15:58:01','analyze','四级','连词','分析、研究','Thedataneedstobeanalyzedcarefully.','upload/danciku_analyze1.jpg,upload/danciku_analyze2.jpg,upload/danciku_analyze3.jpg','2026-03-14 23:58:01',0,4,'upload/1775470814370_851.mp3'),(5,'2026-03-14 15:58:01','develop','四级','动词短语','发展、开发','Thecompanyplanstodevelopnewproducts.','upload/danciku_develop1.jpg,upload/danciku_develop2.jpg,upload/danciku_develop3.jpg','2026-03-14 23:58:01',0,5,'upload/1775470789901_186.mp3'),(6,'2026-03-14 15:58:01','contradict','四级','代词','反驳、与...矛盾','Hiswordscontradicthisactions.','upload/danciku_contradict1.jpg,upload/danciku_contradict2.jpg,upload/danciku_contradict3.jpg','2026-03-14 23:58:01',0,6,'upload/1775470740916_361.mp3'),(7,'2026-03-14 15:58:01','determine','四级','名词','确定、决定','Weneedtodeterminethecauseoftheproblem.','upload/danciku_determine1.jpg,upload/danciku_determine2.jpg,upload/danciku_determine3.jpg','2026-03-14 23:58:01',0,7,'upload/1775449925905_835.mp3'),(8,'2026-03-14 15:58:01','diminish','四级','动词','减少、降低','Theimpactofthestormhasdiminished.','upload/danciku_diminish1.jpg,upload/danciku_diminish2.jpg,upload/danciku_diminish3.jpg','2026-03-14 23:58:01',0,8,'upload/1775386312445_461.mp3'),(9,'2026-04-06 10:49:51','abnormal','六级','形容词','不正常的','The test results showed some abnormal levels of certain hormones','',NULL,0,0,'upload/1775472590272_783.mp3'),(10,'2026-04-06 12:53:02','abnormal','六级','动词','废除，取消','The government decided to abolish the outdated tax law.','',NULL,0,0,'upload/1775479969242_665.mp3'),(11,'2026-04-06 12:55:26','abrupt','六级','形容词','突然的,唐突的,无礼的,不连贯的','The meeting came to an abrupt end when the fire alarm went off.','',NULL,0,0,'upload/1775480078272_824.mp3'),(12,'2026-04-06 12:56:31','absurd','六级','形容词','荒谬的，不合理的','It\'s absurd to think that you can learn a language in a week.','',NULL,0,0,'upload/1775480174682_863.mp3'),(13,'2026-04-06 13:05:13','apple ','小学','名词','苹果','I eat an apple every day.','upload/1775482177588_403.png',NULL,0,0,'upload/1775480694306_374.mp3'),(14,'2026-04-06 13:05:48','book ','小学','名词','书','This is my new book.','upload/1775482324858_477.png',NULL,0,0,'upload/1775480731939_74.mp3'),(15,'2026-04-06 13:06:31','run','小学','动词','跑步','They run in the park.','upload/1775482354889_79.png',NULL,0,0,'upload/1775480770541_622.mp3'),(16,'2026-04-06 13:07:09','happy','小学','形容词','开心的','She is very happy today.','upload/1775482364237_23.png',NULL,0,0,'upload/1775480809007_813.mp3'),(17,'2026-04-06 13:07:50','school','小学','名词','学校','I go to school on foot.','upload/1775482374313_344.png',NULL,0,0,'upload/1775480851375_482.mp3'),(18,'2026-04-06 13:09:14','decide','初中','动词','决定','I decide to study hard.','upload/1775482491137_721.png',NULL,0,0,'upload/1775480938497_679.mp3'),(19,'2026-04-06 13:10:00','improve ','初中','动词','提高','He wants to improve his English.','upload/1775482451562_252.png',NULL,0,0,'upload/1775480978388_273.mp3'),(20,'2026-04-06 13:10:57','problem','初中','名词','问题','We can solve this problem.','upload/1775484758633_878.png',NULL,0,0,'upload/1775481042084_402.mp3'),(21,'2026-04-06 13:13:23','analyze','高中','动词','分析','We analyze the data carefully.','upload/1775482635336_582.png',NULL,0,0,'upload/1775481166666_321.mp3'),(22,'2026-04-06 13:14:34','benefit','高中','动词','受益','Reading benefits us a lot.','upload/1775482645522_584.png',NULL,0,0,'upload/1775481234879_568.mp3'),(23,'2026-04-06 13:15:21','challenge ','高中','动词',' 挑战','Life is full of challenges.','upload/1775482653535_121.png',NULL,0,0,'upload/1775481294368_719.mp3'),(24,'2026-04-06 13:16:02','accumulate','考研','动词',' 积累 ','We accumulate experience over time.','upload/1775482697518_424.png',NULL,0,0,'upload/1775481361735_363.mp3'),(25,'2026-04-06 13:16:43','competent','考研','形容词','有能力的 ','He is competent for the task.','upload/1775482707079_367.png',NULL,0,0,'upload/1775481383971_174.mp3'),(26,'2026-04-06 13:17:22','distinguish','考研','动词','区分 ','It is hard to distinguish them.','upload/1775482715374_39.png',NULL,0,0,'upload/1775481423204_942.mp3'),(27,'2026-04-06 13:18:02','implement','考研','动词','实施 ','We will implement the new plan.','upload/1775482742821_101.png',NULL,0,0,'upload/1775577091183_122.png'),(28,'2026-04-07 11:41:00','2','初中','感叹词','1','1','',NULL,0,0,'upload/1775574312223_233.png');
/*!40000 ALTER TABLE `danciku` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `discussdanciku`
--

DROP TABLE IF EXISTS `discussdanciku`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `discussdanciku` (
                                  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                  `refid` bigint NOT NULL COMMENT '关联表id',
                                  `userid` bigint NOT NULL COMMENT '用户id',
                                  `avatarurl` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '头像',
                                  `nickname` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户名',
                                  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '评论内容',
                                  `reply` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '回复内容',
                                  `thumbsupnum` int DEFAULT '0' COMMENT '赞',
                                  `crazilynum` int DEFAULT '0' COMMENT '踩',
                                  `istop` int DEFAULT '0' COMMENT '置顶(1:置顶,0:非置顶)',
                                  `tuserids` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '赞用户ids',
                                  `cuserids` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '踩用户ids',
                                  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='单词库评论';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `discussdanciku`
--

LOCK TABLES `discussdanciku` WRITE;
/*!40000 ALTER TABLE `discussdanciku` DISABLE KEYS */;
/*!40000 ALTER TABLE `discussdanciku` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exampaper`
--

DROP TABLE IF EXISTS `exampaper`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exampaper` (
                             `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                             `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '试卷名称',
                             `time` int NOT NULL COMMENT '考试时长(分钟)',
                             `status` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '启用' COMMENT '试卷状态',
                             `examnum` int DEFAULT '1' COMMENT '限考次数',
                             PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='英语测试';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exampaper`
--

LOCK TABLES `exampaper` WRITE;
/*!40000 ALTER TABLE `exampaper` DISABLE KEYS */;
INSERT INTO `exampaper` VALUES (1,'2026-03-14 15:58:01','十万个为什么',60,'1',1),(2,'2026-03-14 16:01:24','四级测试',20,'1',1);
/*!40000 ALTER TABLE `exampaper` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `examquestion`
--

DROP TABLE IF EXISTS `examquestion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `examquestion` (
                                `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                `paperid` bigint NOT NULL COMMENT '试卷id',
                                `papername` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '试卷名称',
                                `questionname` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '试题名称',
                                `options` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '选项，json字符串',
                                `score` bigint DEFAULT '0' COMMENT '分值',
                                `answer` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '正确答案',
                                `analysis` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '答案解析',
                                `type` bigint DEFAULT NULL COMMENT '试题类型，0：单选题 1：多选题 2：判断题 3：填空题（暂不考虑多项填空）4:主观题',
                                `sequence` bigint DEFAULT NULL COMMENT '试题排序，值越大排越前面',
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='试题';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `examquestion`
--

LOCK TABLES `examquestion` WRITE;
/*!40000 ALTER TABLE `examquestion` DISABLE KEYS */;
INSERT INTO `examquestion` VALUES (1,'2026-03-14 15:58:01',1,'十万个为什么','下面动物不属于昆虫的是（）。','[{\"text\":\"A.苍蝇\",\"code\":\"A\"},{\"text\":\"B.蜜蜂\",\"code\":\"B\"},{\"text\":\"C.蜂鸟\",\"code\":\"C\"}]',20,'C','蜂鸟',0,1),(2,'2026-03-14 15:58:01',1,'十万个为什么','油着火后可以用水扑灭。','[{\"text\":\"A.对\",\"code\":\"A\"},{\"text\":\"B.错\",\"code\":\"B\"}]',20,'B','油着火后不可以用水扑灭',2,2),(3,'2026-03-14 15:58:01',1,'十万个为什么','地球是个球体，中间是（ ）。','[]',30,'赤道','赤道',3,3),(4,'2026-03-14 15:58:01',1,'十万个为什么','下面动物中会流汗的有（ ）。','[{\"text\":\"A.马\",\"code\":\"A\"},{\"text\":\"B.猫\",\"code\":\"B\"},{\"text\":\"C.狗\",\"code\":\"C\"}]',30,'A,B','狗不会流汗',1,4),(5,'2026-03-14 15:58:01',1,'十万个为什么','世界上为什么不存在完美的圆？','[]',30,'','目前没有证据表明现实中存在完美的圆',4,5);
/*!40000 ALTER TABLE `examquestion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `examquestionbank`
--

DROP TABLE IF EXISTS `examquestionbank`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `examquestionbank` (
                                    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                    `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                    `questionname` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '试题名称',
                                    `options` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '选项，json字符串',
                                    `score` bigint DEFAULT '0' COMMENT '分值',
                                    `answer` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '正确答案',
                                    `analysis` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '答案解析',
                                    `type` bigint DEFAULT NULL COMMENT '试题类型，0：单选题 1：多选题 2：判断题 3：填空题（暂不考虑多项填空）4:主观题',
                                    `sequence` bigint DEFAULT NULL COMMENT '试题排序，值越大排越前面',
                                    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='试题库';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `examquestionbank`
--

LOCK TABLES `examquestionbank` WRITE;
/*!40000 ALTER TABLE `examquestionbank` DISABLE KEYS */;
INSERT INTO `examquestionbank` VALUES (1,'2026-03-14 15:58:01','下面动物不属于昆虫的是（）。','[{\"text\":\"A.苍蝇\",\"code\":\"A\"},{\"text\":\"B.蜜蜂\",\"code\":\"B\"},{\"text\":\"C.蜂鸟\",\"code\":\"C\"}]',20,'C','蜂鸟',0,1),(2,'2026-03-14 15:58:01','油着火后可以用水扑灭。','[{\"text\":\"A.对\",\"code\":\"A\"},{\"text\":\"B.错\",\"code\":\"B\"}]',20,'B','油着火后不可以用水扑灭',2,2),(3,'2026-03-14 15:58:01','地球是个球体，中间是（ ）。','[]',30,'赤道','赤道',3,3),(4,'2026-03-14 15:58:01','下面动物中会流汗的有（ ）。','[{\"text\":\"A.马\",\"code\":\"A\"},{\"text\":\"B.猫\",\"code\":\"B\"},{\"text\":\"C.狗\",\"code\":\"C\"}]',30,'A,B','狗不会流汗',1,4),(5,'2026-03-14 15:58:01','世界上为什么不存在完美的圆？','[]',30,'','目前没有证据表明现实中存在完美的圆',4,5);
/*!40000 ALTER TABLE `examquestionbank` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `examrecord`
--

DROP TABLE IF EXISTS `examrecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `examrecord` (
                              `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                              `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                              `username` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
                              `paperid` bigint NOT NULL COMMENT '试卷id',
                              `papername` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '试卷名称',
                              `questionid` bigint NOT NULL COMMENT '试题id',
                              `questionname` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '试题名称',
                              `type` bigint DEFAULT NULL COMMENT '试题类型',
                              `ismark` bigint DEFAULT NULL COMMENT '是否批卷',
                              `options` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '选项，json字符串',
                              `score` bigint DEFAULT '0' COMMENT '分值',
                              `answer` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '正确答案',
                              `analysis` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '答案解析',
                              `myscore` bigint DEFAULT '0' COMMENT '试题得分',
                              `myanswer` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '考生答案',
                              `examno` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '考试编号',
                              `userid` bigint DEFAULT NULL COMMENT '用户id',
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='考试记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `examrecord`
--

LOCK TABLES `examrecord` WRITE;
/*!40000 ALTER TABLE `examrecord` DISABLE KEYS */;
/*!40000 ALTER TABLE `examrecord` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `forum`
--

DROP TABLE IF EXISTS `forum`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `forum` (
                         `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                         `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                         `title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '帖子标题',
                         `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '帖子内容',
                         `parentid` bigint DEFAULT NULL COMMENT '父节点id',
                         `username` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户名',
                         `avatarurl` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '头像',
                         `isdone` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '状态',
                         `istop` int DEFAULT '0' COMMENT '是否置顶',
                         `toptime` datetime DEFAULT NULL COMMENT '置顶时间',
                         `cover` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '封面',
                         `isanon` int DEFAULT '0' COMMENT '是否匿名(1:是,0:否)',
                         `delflag` int DEFAULT '0' COMMENT '是否删除(1:是,0:否)',
                         `sfsh` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '待审核' COMMENT '是否审核',
                         `shhf` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '审核回复',
                         `userid` bigint DEFAULT NULL COMMENT '用户id',
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='互动学习';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `forum`
--

LOCK TABLES `forum` WRITE;
/*!40000 ALTER TABLE `forum` DISABLE KEYS */;
INSERT INTO `forum` VALUES (1,'2026-03-14 15:58:01','帖子标题1','帖子内容1',0,'用户名1','upload/forum_avatarurl1.jpg,upload/forum_avatarurl2.jpg,upload/forum_avatarurl3.jpg','开放',0,'2026-03-14 23:58:01','upload/forum_cover1.jpg,upload/forum_cover2.jpg,upload/forum_cover3.jpg',1,0,'是','',1),(2,'2026-03-14 15:58:01','帖子标题2','帖子内容2',0,'用户名2','upload/forum_avatarurl2.jpg,upload/forum_avatarurl3.jpg,upload/forum_avatarurl4.jpg','开放',0,'2026-03-14 23:58:01','upload/forum_cover2.jpg,upload/forum_cover3.jpg,upload/forum_cover4.jpg',2,0,'是','',2),(3,'2026-03-14 15:58:01','帖子标题3','帖子内容3',0,'用户名3','upload/forum_avatarurl3.jpg,upload/forum_avatarurl4.jpg,upload/forum_avatarurl5.jpg','开放',0,'2026-03-14 23:58:01','upload/forum_cover3.jpg,upload/forum_cover4.jpg,upload/forum_cover5.jpg',3,0,'是','',3),(4,'2026-03-14 15:58:01','帖子标题4','帖子内容4',0,'用户名4','upload/forum_avatarurl4.jpg,upload/forum_avatarurl5.jpg,upload/forum_avatarurl6.jpg','开放',0,'2026-03-14 23:58:01','upload/forum_cover4.jpg,upload/forum_cover5.jpg,upload/forum_cover6.jpg',4,0,'是','',4),(5,'2026-03-14 15:58:01','帖子标题5','帖子内容5',0,'用户名5','upload/forum_avatarurl5.jpg,upload/forum_avatarurl6.jpg,upload/forum_avatarurl7.jpg','开放',0,'2026-03-14 23:58:01','upload/forum_cover5.jpg,upload/forum_cover6.jpg,upload/forum_cover7.jpg',5,0,'是','',5),(6,'2026-03-14 15:58:01','帖子标题6','帖子内容6',0,'用户名6','upload/forum_avatarurl6.jpg,upload/forum_avatarurl7.jpg,upload/forum_avatarurl8.jpg','开放',0,'2026-03-14 23:58:01','upload/forum_cover6.jpg,upload/forum_cover7.jpg,upload/forum_cover8.jpg',6,0,'是','',6),(7,'2026-03-14 15:58:01','帖子标题7','帖子内容7',0,'用户名7','upload/forum_avatarurl7.jpg,upload/forum_avatarurl8.jpg,upload/forum_avatarurl1.jpg','开放',0,'2026-03-14 23:58:01','upload/forum_cover7.jpg,upload/forum_cover8.jpg,upload/forum_cover1.jpg',7,0,'是','',7),(8,'2026-03-14 15:58:01','帖子标题8','帖子内容8',0,'用户名8','upload/forum_avatarurl8.jpg,upload/forum_avatarurl1.jpg,upload/forum_avatarurl2.jpg','开放',0,'2026-03-14 23:58:01','upload/forum_cover8.jpg,upload/forum_cover1.jpg,upload/forum_cover2.jpg',8,0,'是','',8);
/*!40000 ALTER TABLE `forum` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `popupremind`
--

DROP TABLE IF EXISTS `popupremind`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `popupremind` (
                               `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                               `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               `userid` bigint NOT NULL COMMENT '发布人id',
                               `role` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '角色',
                               `title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标题',
                               `type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '个人' COMMENT '类型',
                               `brief` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '简介',
                               `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '内容',
                               `remindtime` datetime DEFAULT NULL COMMENT '提醒时间',
                               PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='弹窗提醒';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `popupremind`
--

LOCK TABLES `popupremind` WRITE;
/*!40000 ALTER TABLE `popupremind` DISABLE KEYS */;
INSERT INTO `popupremind` VALUES (1,'2026-03-14 15:58:01',1,'角色1','标题1','个人','简介1','内容1','2026-03-14 23:58:01'),(2,'2026-03-14 15:58:01',2,'角色2','标题2','个人','简介2','内容2','2026-03-14 23:58:01'),(3,'2026-03-14 15:58:01',3,'角色3','标题3','个人','简介3','内容3','2026-03-14 23:58:01'),(4,'2026-03-14 15:58:01',4,'角色4','标题4','个人','简介4','内容4','2026-03-14 23:58:01'),(5,'2026-03-14 15:58:01',5,'角色5','标题5','个人','简介5','内容5','2026-03-14 23:58:01'),(6,'2026-03-14 15:58:01',6,'角色6','标题6','个人','简介6','内容6','2026-03-14 23:58:01'),(7,'2026-03-14 15:58:01',7,'角色7','标题7','个人','简介7','内容7','2026-03-14 23:58:01'),(8,'2026-03-14 15:58:01',8,'角色8','标题8','个人','简介8','内容8','2026-03-14 23:58:01'),(9,'2026-04-06 07:48:45',11,'学生','1','个人','','<p>1</p>','2026-04-06 15:49:02');
/*!40000 ALTER TABLE `popupremind` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smsregistercode`
--

DROP TABLE IF EXISTS `smsregistercode`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `smsregistercode` (
                                   `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                   `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                   `mobile` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '手机',
                                   `role` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色',
                                   `code` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '验证码',
                                   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='短信验证码';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smsregistercode`
--

LOCK TABLES `smsregistercode` WRITE;
/*!40000 ALTER TABLE `smsregistercode` DISABLE KEYS */;
INSERT INTO `smsregistercode` VALUES (1,'2026-03-14 15:58:01','手机1','角色1','验证码1'),(2,'2026-03-14 15:58:01','手机2','角色2','验证码2'),(3,'2026-03-14 15:58:01','手机3','角色3','验证码3'),(4,'2026-03-14 15:58:01','手机4','角色4','验证码4'),(5,'2026-03-14 15:58:01','手机5','角色5','验证码5'),(6,'2026-03-14 15:58:01','手机6','角色6','验证码6'),(7,'2026-03-14 15:58:01','手机7','角色7','验证码7'),(8,'2026-03-14 15:58:01','手机8','角色8','验证码8'),(9,'2026-04-06 09:22:28','13020715030','学生','655306'),(10,'2026-04-06 09:36:14','13020715030','学生','496214');
/*!40000 ALTER TABLE `smsregistercode` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `storeup`
--

DROP TABLE IF EXISTS `storeup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `storeup` (
                           `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                           `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                           `userid` bigint NOT NULL COMMENT '用户id',
                           `refid` bigint DEFAULT NULL COMMENT '商品id',
                           `tablename` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '表名',
                           `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
                           `picture` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '图片',
                           `type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '1' COMMENT '类型',
                           `inteltype` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '推荐类型',
                           `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='收藏表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `storeup`
--

LOCK TABLES `storeup` WRITE;
/*!40000 ALTER TABLE `storeup` DISABLE KEYS */;
INSERT INTO `storeup` VALUES (1,'2026-04-06 07:49:24',11,9,'popupremind','1',NULL,'61',NULL,NULL);
/*!40000 ALTER TABLE `storeup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `study_record`
--

DROP TABLE IF EXISTS `study_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `study_record` (
                                `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                `xuesheng_zhanghao` varchar(50) NOT NULL COMMENT '学生账号',
                                `module` varchar(30) NOT NULL COMMENT '学习模块：danciku=单词库 tingli=听力 wenkan=文刊',
                                `study_time` int NOT NULL DEFAULT '0' COMMENT '本次学习时长(秒)',
                                `word_count` int NOT NULL DEFAULT '0' COMMENT '本次学习单词数',
                                `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='学生学习记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `study_record`
--

LOCK TABLES `study_record` WRITE;
/*!40000 ALTER TABLE `study_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `study_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tingli`
--

DROP TABLE IF EXISTS `tingli`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tingli` (
                          `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                          `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                          `mingcheng` varchar(200) DEFAULT NULL COMMENT '名称',
                          `jibie` varchar(200) DEFAULT NULL COMMENT '级别',
                          `shiyinshipin` varchar(500) DEFAULT NULL COMMENT '听力音频',
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb3 COMMENT='听力';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tingli`
--

LOCK TABLES `tingli` WRITE;
/*!40000 ALTER TABLE `tingli` DISABLE KEYS */;
INSERT INTO `tingli` VALUES (28,'2026-04-06 14:40:24','2015年06月四级听力第1套','四级','upload/1775486418049_313.mp3'),(29,'2026-04-06 14:41:14','2015年12月四级听力第1套','四级','upload/1775486471380_855.mp3'),(30,'2026-04-06 14:41:47','2015年12月四级听力第2套','四级','upload/1775486505014_62.mp3'),(31,'2026-04-06 14:42:13','2015年12月四级听力第3套','四级','upload/1775486530172_120.mp3'),(32,'2026-04-06 14:42:37','2017年06月四级听力第1套','四级','upload/1775486548381_156.mp3'),(33,'2026-04-06 14:43:01','一年级课文听力 Unit 5 People around us','小学','upload/1775486579152_522.mp3'),(34,'2026-04-06 14:43:22','一年级课文听力 Unit 1 Hello','小学','upload/1775486591917_956.mp3'),(35,'2026-04-06 14:43:39','二年级课文听力 Unit 3 Helpful hands','小学','upload/1775486610420_62.mp3'),(36,'2026-04-06 14:43:52','三年级课文听力Story time','小学','upload/1775486631153_141.mp3'),(37,'2026-04-06 14:44:23','四年级课文听力Unit 3 ','小学','upload/1775486661500_861.mp3'),(38,'2026-04-06 14:50:22','七年级课文听力Unit1','初中','upload/1775487004842_377.mp3'),(39,'2026-04-06 14:50:40','八年级课文听力Unit2','初中','upload/1775487029614_928.mp3'),(40,'2026-04-06 14:50:55','九年级课文听力Unit1','初中','upload/1775487047352_577.mp3'),(41,'2026-04-06 14:54:07','高一Unit+1+Listening+and+speaking+2','高中','upload/1775487231541_890.mp3'),(42,'2026-04-06 14:54:36','高一Unit+1+Listening+and+speaking+3','高中','upload/1775487263857_492.mp3'),(43,'2026-04-06 14:54:51','高二Unit+1+Listening+and+speaking+3','高中','upload/1775487289398_72.mp3'),(44,'2026-04-06 14:55:05','高三Unit+1+Reading+and+Thinking','高中','upload/1775487302882_809.mp3'),(45,'2026-04-06 14:59:36','2014年12月六级真题（二）.MP3','六级','upload/1775487564899_482.crdownload'),(46,'2026-04-06 15:00:16','2014年12月六级真题（一）','六级','upload/1775487614699_193.mp3'),(47,'2026-04-06 15:00:35','2025年12月六级听力音频（第一套）','六级','upload/1775487631986_712.MP3'),(48,'2026-04-06 15:19:15','考研听力1','考研','upload/1775488751819_364.mp3'),(49,'2026-04-06 15:19:27','考研听力2','考研','upload/1775488760906_924.mp3'),(50,'2026-04-06 15:19:40','考研听力3','考研','upload/1775488774518_121.mp3');
/*!40000 ALTER TABLE `tingli` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `token`
--

DROP TABLE IF EXISTS `token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `token` (
                         `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                         `userid` bigint NOT NULL COMMENT '用户id',
                         `username` varchar(100) NOT NULL COMMENT '用户名',
                         `tablename` varchar(100) DEFAULT NULL COMMENT '表名',
                         `role` varchar(100) DEFAULT NULL COMMENT '角色',
                         `token` varchar(500) NOT NULL COMMENT '密码',
                         `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
                         `expiratedtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '过期时间',
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COMMENT='token表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `token`
--

LOCK TABLES `token` WRITE;
/*!40000 ALTER TABLE `token` DISABLE KEYS */;
INSERT INTO `token` VALUES (1,1,'admin','users','管理员','fip6ev9xhkpk763u24pg27vmg94w31rp','2026-03-14 16:00:39','2026-04-08 05:55:23'),(2,11,'e10adc3949ba59abbe56e057f20f883e','xuesheng','学生','a3z7f4miu7479216ki0zmcbqjncbsvkz','2026-04-05 10:42:35','2026-04-06 09:03:36'),(3,1775467368462,'0997','xuesheng','学生','td3of5dlxmvp3fpieq9ljsjsnwdtx6nu','2026-04-06 09:23:28','2026-04-07 16:52:59');
/*!40000 ALTER TABLE `token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
                         `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                         `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                         `username` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
                         `password` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
                         `role` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '管理员' COMMENT '角色',
                         `image` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '头像',
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='管理员';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'2026-03-14 15:58:01','admin','admin','管理员','upload/image1.jpg');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wenkan`
--

DROP TABLE IF EXISTS `wenkan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wenkan` (
                          `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                          `mingcheng` varchar(200) DEFAULT NULL COMMENT '名称',
                          `jibie` varchar(50) DEFAULT NULL COMMENT '级别',
                          `wenkanneirong` longtext COMMENT '文刊内容',
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文刊';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wenkan`
--

LOCK TABLES `wenkan` WRITE;
/*!40000 ALTER TABLE `wenkan` DISABLE KEYS */;
INSERT INTO `wenkan` VALUES (2,'my brother','小学','<p>245. my brother</p><p>I‘ve a brother. He is a good brother. He studies very well and he isvery look after me.</p><p>One day, I was ill.He took me to the hospital.The doctors and thenurses said:you have a good brother!I said:Yes,he is a good brother .And mybrother was shy.</p><p>Oh,I‘m forget to tell you what my brother looks like.</p><p>He is very tall and thin. His favourest sport is play basketball andhe like play the guitar. He play them is very well.</p><p>我的兄弟</p><p>我有一个兄弟。他是一个好兄弟。他学习很好，他很照顾我。</p><p>有一天，我病了。他带我去了医院医生和护士说:你有一个好兄弟!我说:是的，他是一个好兄弟。我兄弟很害羞</p><p>哦，我忘了告诉你我兄弟长什么样了。</p><p>他又高又瘦。他最喜欢的运动是打篮球，他喜欢弹吉他。他打篮球和弹吉他都很好。</p>'),(3,'my spring festival holiday','小学','<p><span style=\"color: rgb(51, 51, 51);\">    I had a wonderfulspring festival holiday.I went to the park with my friends.We met at eight o‘clock at the school gate.We went to the park by bike.It was a sunnyday.We sat under a big tree and talkedwith each other.We talked about some movies andfunny things.Then we hadhamburgers and some drinks for lunch.Then we played some games. We went home at 5pm.Although we were all fell tired,we were very happy.It was great fun! </span></p><p><span style=\"color: rgb(51, 51, 51);\">  我度过了一个愉快的春节。我和朋友去了公园。我们八点在校门口碰面，然后我们就骑自行车去公园。那是个晴朗的日子，我们坐在一棵大树下互相聊天。我们聊了一些电影和趣事。然后我们午餐就去吃了些汉堡喝了些饮料。接着我们玩了些游戏。 下午五点我们就回家了。尽管我们都觉得很累，但是我们很开心。真是太有趣了！</span></p>'),(4,'My Uncle','小学','<p><span style=\"color: rgb(51, 51, 51);\">&nbsp;have an uncle, he is so kind to me and I like him so much. My uncle looks very young, he is busy with his work. But when he goes back on business, he will bring me the gift. Sometimes I will play basketball with him or we watch the game together, we share our opinion. My uncle is like my brother. </span></p><p><span style=\"color: rgb(51, 51, 51);\">我有一个叔叔，他对我很好，我很喜欢他。我的叔叔看起来很年轻，他总是忙于工作。但是当他出差回来了，会给我带礼物。有时候我会和他一起打篮球或者我们一起看比赛，彼此分享意见。我的叔叔就像是我的兄弟。</span></p>'),(5,'我梦想中的高中生活','初中','<p class=\"ql-align-center\"><br></p><p>My dream high school life is a harmonious blend of academic excellence and personal growth. I envision waking up early every morning, filled with enthusiasm for the day\'s learning adventures. The school campus, adorned with lush green trees and colorful flowers, sets the perfect backdrop for my intellectual pursuits.</p><p>我梦想中的高中生活是学术卓越与个人成长的完美结合。我想象自己每天早上早起，对一天的学习充满热情。校园里绿树成荫，鲜花盛开，为我的求知欲提供了完美的背景。</p><p>Classes are engaging and interactive, where teachers inspire us not just to memorize facts but to think critically and creatively. We delve deep into subjects like science, literature, and mathematics, fostering a curiosity that drives us to explore beyond textbooks. Group discussions and debates encourage us to express our opinions confidently, nurturing our communication skills.</p><p>课堂引人入胜，互动性强，老师不仅鼓励我们记住事实，还鼓励我们批判性和创造性地思考。我们深入研究科学、文学和数学等学科，培养好奇心，驱使我们探索课本以外的知识。小组讨论和辩论鼓励我们自信地表达自己的观点，培养我们的沟通技巧。</p><p>Beyond academics, my dream high school emphasizes extracurricular activities. I picture myself participating in the school\'s debate club, honing my public speaking skills and learning to argue with grace. Additionally, I aspire to join the robotics team, where we collaborate to build innovative machines, fostering teamwork and creativity.</p><p>除了学术，我梦想中的高中还强调课外活动。我想象自己参加学校的辩论俱乐部，磨练我的公开演讲技巧，学会优雅地辩论。此外，我渴望加入机器人团队，在那里我们合作制造创新机器，培养团队合作精神和创造力。</p><p>Friendships forged during lunch breaks and breaks between classes are invaluable. We share jokes, dreams, and aspirations, creating a supportive community that uplifts each other. The annual sports day and cultural festival bring us all together, showcasing our talents and strengthening our bond as a school family.</p><p>午休和课间休息时建立的友谊是无价的。我们分享笑话、梦想和抱负，创造一个互相支持、互相鼓励的社区。一年一度的运动会和文化节将我们聚集在一起，展示我们的才华，加强我们作为一个学校大家庭的联系。</p><p>Lastly, my dream high school fosters a culture of kindness and respect. We learn to embrace diversity, valuing each other\'s unique perspectives and backgrounds. This environment fosters empathy and prepares us to be responsible citizens of the world.</p><p>最后，我梦想中的高中培养了一种善良和尊重的文化。我们学会接受多样性，重视彼此独特的观点和背景。这种环境培养了同理心，让我们成为负责任的世界公民。</p><p>In summary, my dream high school life is a vibrant tapestry woven with rigorous academics, enriching extracurriculars, cherished friendships, and a culture of inclusivity. It\'s a place where I can grow intellectually, emotionally, and socially, laying a solid foundation for my future endeavors.</p><p>总之，我梦想中的高中生活是一幅充满活力的画卷，由严谨的学术、丰富的课外活动、珍贵的友谊和包容的文化交织而成。在这里，我可以成长为智力、情感和社交方面的人才，为我未来的事业奠定坚实的基础。</p>'),(6,'Going to School in America 在美国上学','初中','<p>On August 14, 1999, I, Bobby Huang, moved from China to the United States of America. I was eight years old at that time, and only in the second grade. I\'m now in the eighth grade. Next year I will be at high school.</p><p>1999年8月14日，我黄博比，从中国来到美国。那时我8岁，刚上二年级。现在我上8年级，明年我将上高中。</p><p>The grading system in America is almost the same as the one in China: the best grade you can get is an A, then B, then C, then D, and last F. F stands for failing. A grade of C in America is considered an average grade.</p><p>美国的评分系统和中国的几乎一样：你能得到的最好的成绩是A，其次B，再次C，再次D，最后是F。F级代表不及格。在美国C级被认为是平均成绩。</p><p>The school year is separated into four quarters in America. We only have to go to school 180 days a year. We don\'t have to go to school on Saturdays either. There is at least one day break every month, because in America there\'s at least one holiday every month. For instance, Labor Day, Columbus Day (10/12, the day Columbus discovered the Americas), Halloween (10/31, the day that all the ghosts come back alive again), Thanksgiving Day (11/24), Christmas Day (12/25), New Year\'s Day (1/1), Valentine\'s Day (2/14, a day that we all show love to each other) and Mother\'s Day (a day that we show our appreciation to our mothers).</p><p>在美国，一学年被分成4个学期。一年我们只需要上学180天。星期六我们也不用上学。每个月至少有一天的休假，因为在美国每个月至少有一个节日。例如，劳动节，哥伦布节（在10月12日，哥伦布发现美洲的那一天），万圣节（在10月31日，那一天所有的鬼神重回到人间），感恩节（在11月24日），圣诞节（在12月25日），新年（在1月1日），情人节（在2月14日，我们互相表达爱意的一天）和母亲节（向母亲表达感激之情的一天）。</p><p>The really long break is the Christmas vacation. It\'s two weeks. After 180 days of school we have a three-month summer vacation! The best thing about the summer vacation is that we don\'t have to worry about anything, we don\'t even have homework.</p><p>真正的长假是圣诞节假期，有2个星期之久。经过180天的学习后，我们有一个长达3个月的暑假。关于暑假最好的事情就是，我们不用再为任何事情担心，甚至不再有家庭作业。</p><p>One really bad thing about American schools is that you have to walk to the teacher\'s room instead of our own classroom, so we don\'t have the same classes with the same students. But in all the schools in America, the students have a lot of fun in school. In my school we have different activities every Friday, such as singing, sports and seeing movies. We also hold a dance every other month. The dance hall is where we play music and students dance with each other. The school also provides cookies and drinks.</p><p>美国学校中一个真正不好的方面就是，你必须到老师的办公室，而不是我们自己的教室，因此我们不能和同样的学生拥有相同的教室。但是在美国所有的学校里，学生在校可以享受许多乐趣。在我就读的学校里，每周五有不同的活动，比如唱歌、体育活动和看电影。我们每2个月举办一次舞会。舞会上我们演奏音乐，和每一个人跳舞。学校也提供点心和饮料。</p><p>The main courses that I have to take are history, physical education, science, language, reading and math. We get to pick two classes of our choice. I choose \"music\" and \"computer\". In music class we listen to all kinds of songs, for example, Jazz, country, R&amp;B, etc. We can talk about any singer that we love. I also enjoy the computer class. The other classes are drama, Spanish, leadership, guitar, band (you can play any instruments you\'d like), journalism (learning to write for a newspaper, and you can also write for the school newspaper) and so on.</p><p>我们的主要课程有历史、体育、科学、语言、阅读和数学。我们可以选修2门课程。我选择的是音乐和电脑。音乐课上我们听各种各样的歌曲，比如爵士乐，乡村音乐，R&amp;B等。我们可以谈论任何一个我们喜欢的歌手。我也喜欢电脑课。其他选修课有戏剧、西班牙语、领导艺术、吉他、乐队（你可以演奏你喜欢的任何乐器）和新闻（学习为报纸写文章，你也可以向校报投稿）等等。</p><p>Bobby Huang</p><p>黄博比</p>'),(7,'《Choice in My Life》','初中','<p><span style=\"color: rgb(51, 51, 51);\">Ladies and gentlemen, teachers, my fellow students:</span></p><p>I am very excited and feel it\'s a great honor to stand here to deliver my speech. what i\'m going to talk about is \"choice in my life\"</p><p>I can still remember the first time i failed in a singing contest. i immediately ran to my grandma, shedding lots of tears of course! i cried loudly and could hardly utter a word!</p><p>Then, badly needed comforting words and touch came from grandma. i gradually regained my sense of pride and a sensible control of my feelings. yet, head down, i said: \"grandma, i have no choice, and i have to give up, right?\" \"look at me, my dear!\"she said gently. i looked up, straight into her eyes. what i saw was a shining, sweet, and loving stream from the bottom of her heart! then, there came the gentle voice of hers: \"no, no, no…\" her head shaking, while eyes smiling. \"why don\'t you give more attention to the details?\" she continued.</p><p>No other words were needed any more! i started practicing and rehearsing once again, paying special attention to those little things that i used to ignore. as a consequence, my overall performance was quickly elevated to a new high! without much trouble, in another singing contest held months later, i won!</p><p>What do i learn from this experience? never give up lightly! life\'s full of opportunities, and lots of them! to realize your dreams and fulfill your potential, you must learn to work hard and smart and steer your lifeboat wisely! as a li ling\'s sportswear commercial goes \"everything is possible!\" but not without sweat, devotion, and tears sometimes!</p><p>life is a long journey, during which we are surely going to face many challenges, experience lots of failures, and equally, many opportunities are waving to us, while sweet rewards are waiting for us! to transform these opportunities into fruitful results, or turn those challenges and failures into valuable assets, we must learn to seize the good ones and not to retreat lightly in front of difficulties.</p><p>Winners can never be quitters!</p><p>Thank you all!</p>'),(8,'青春终将散场','高中','<p>Youth will press</p><p>Saying goodbye to childhood,we step into another important time in the pace of young,facing new situations,dealing with different problems……</p><p>everyone has his ownunderstanding of young,it is a period of time of beauty and wonders,only after you have experienced the sour ,sweet ,bitter and salty can you really become a person of significance.</p><p>thre time of young is limitted,it may pass by without your attention,and when you discover what has happened ,it is always too late.grasping the young well means a better time is waiting for you in the near future,or the situation may be opposite .</p><p>having a view on these great men in the history of hunmanbeing,they all made full use of their youth time ,to do things that are useful to society,to the whole mankind,and as a cosquence ,they are remembered by later generations,admired by everyone.so do something in the time of young,although you may not get achievements as these greatmen did ,though not for the whole word,just for youeself,for those around!</p><p>the young is just like blooming flowers,they are so beautiful when blooming,they make people feel happy,but with time passing by,after they withers ,moet people think they are ugly.</p><p>and so it is the same with young,we are enthusiastic when we are young,then we may lose our passion when getting older and older.</p><p>so we must treasure it ,don\'t let the limitted time pass by ,leaving nothing of significance.</p><h3><br></h3><p>青春终将散场</p><p>告别童年，我们步入了青春的步伐的另一个重要时刻，面临新的形势，处理不同的问题......</p><p>每个人对年轻，都有他自己的理解，这是一段美丽和奇迹，只有在你经历了酸甜苦辣，你真的能成为一个人的意义。</p><p>青春的时光是有限的，稍不注意转瞬即逝，当你发现已不再年轻时，为时已晚矣。所以我们要把握年轻，一个更加美好的时光在不久的将来等着你，否则你将失去这些。</p><p>过去的那些伟人，他们都充分利用了自己的青春时光，做一些对社会是有益的，对整个人类，作为一个时代的创造者，他们被后人的每个人所铭记。所以年轻的时候要做好自己，即使你可能做不到这些伟人的成就，但至少可以为为自己，为周围的人做点什么!</p><p>年轻就像盛开的花朵，他们是如此美丽盛开的时候，他们让人们感到满意，但随着时间的推移，岁月会让你变老.</p><p>年轻人，在我们热情年轻时候，保持你的激情，不要在激情中变老。</p><p>所以我们一定要珍惜它，不要让有限的青春白白逝去，没有留下任何的意义。</p>'),(9,'Save resources, better tomorrow','高中','<p><span style=\"color: rgb(51, 51, 51);\">Respected teachers, my fellow students. Today my topic is save resources, better tomorrow.</span></p><p>As we all know, the pollution problem may be one of the greatest problems in the world. Please look out of the windows! You may easily find: We have more cars but less petrol. We eat more kinds of food, but have fewer kinds of animals; we have higher buildings and wider highways, but narrower points of view; we spend more, but enjoy less, why? Because the natural resources are limited.However, People don\'t cherish what they have until it\'s gone.</p><p>But what shall we do to save the resources? We can bring cotton bags with us instead of asking for plastic bags when we go shopping. When we are doing exercises in class we can use the both sides of paper to write on. We can recycle empty bottles and tins. We shouldn’t use the wooden chopsticks.</p><p>Water, valued fresh water, the gift of our generous nature. It is the blood of the Earth. It’s one of the most valued things and it’s the beginning of all the lives. We can’t live without water. Nowadays, many people are getting sick because of lacking water. Many animals are dying of thirst. Many rich fields are becoming drier and drier and no longer can be used to grow rice. So to save water, we should use not too much water on the washing. We should remember to turn off the taps when we leave the toilet. We should use water for many times.</p><p>Pollution is one of the biggest problems in the world. People always pollute the earth. The more people, the more pollution.As teenagers, we should ride bikes instead of going by car or by bus.</p><p>If we do like that, our life will be better in the future. The earth is our only home. We should try our best to protect it. In this way, we can ‘see a world in a grain of sand’, ‘a heaven in wild flowers’ and we can also see our bright future from all colorful lives around us! Ours sprit is all for one, and one for all to make our homestead better. That’s all. Thanks for listening!</p>'),(10,'我国可再生能源实现跨越式发展','四级','<p><span style=\"color: rgb(51, 51, 51);\">China\'s total installed capacity for renewable energy generation rose to 1.1 billion kilowatts during the last 10 years, with generation capacity of hydropower, wind, solar and biomass ranking top in the world according to the National Energy Administration.</span></p><p>据国家能源局数据显示，十年间，我国可再生能源发电总装机达到11亿千瓦，水电、风电、光伏发电、生物质发电装机规模稳居世界第一。</p><p>The combined installed capacity of wind and solar power has reached 670 million kW, almost 90 times that of 2012, it said.</p><p>其中，风电光伏并网装机合计6.7亿千瓦，是2012年的近90倍。</p><p>During the 14th Five-Year Plan (2021-25) period, China\'s renewable energy generation capacity is expected to account for more than 50 percent of the total and the generation capacity for wind and solar power will double, the NEA said.</p><p>“十四五”时期（2021-2025年），我国可再生能源发电量增量在全社会用电量增量中的占比将超过50%，风电和太阳能发电量将实现翻倍。</p>'),(11,'冬奥场馆“冰丝带”对外开放','四级','<p><span style=\"color: rgb(51, 51, 51);\">The Beijing 2022 Winter Olympics\' National Speed Skating Oval, also known as the \"Ice Ribbon\", opened to the public on Saturday.</span></p><p>7月9日，作为北京2022年冬奥会标志性场馆，国家速滑馆“冰丝带”正式面向社会开放。</p><p>Visitors can take photos with Beijing 2022 mascot Bing Dwen Dwen and tour the Champions Wall.</p><p>游客们可以和北京2022年冬奥会吉祥物冰墩墩合影，还可以参观冠军墙。</p><p>They can also enjoy a skating experience on 12,000 square meters of the \"fastest ice\" at sea level.</p><p>他们还可以在1.2万平方米的冰面上体验冬奥“最快的冰”。</p><p>Stadium also features a podium for visitors to simulate the medal award ceremony.</p><p>场馆里还保留了领奖台，游客可以体验颁奖仪式。</p>'),(12,'增进记忆最有力的办法','六级','<p>Scientists have found that regardless of type of test/exam you are going to take, you stand the best chance of succeeding if you revise with practice tests. As a demonstration, consider the following experiment:</p><p><span style=\"color: rgb(0, 128, 128);\">科学家已经发现，不论所要参加的考试属于什么类型，如果用模拟题来复习，都最有可能取得好成绩。要说明这一点，让我们考虑下面的试验：</span></p><p>Students attended a 20-minute Statistics lecture, which was divided into 4 equally long sessions. After each session, the first group took a practice test (without feedback), the second group had to re-study the lecture material and the third group performed mental arithmetic. All students were assessed with a final test after the lecture.</p><p><span style=\"color: rgb(0, 128, 128);\">学生被要求参加一堂20分钟的统计学讲座，整个讲座被分成等时长的四节。在每一节结束后，第一组学生做一份练习测试（但没有反馈），第二组学生复习讲座材料，第三组学生进行心算。在全部讲座结束后，通过最终的测试对所有学生进行评估。</span></p><p>Although the re-reading group had more exposure to the material, their final test performance was substantially worse than that of the testing group (by 30%). Moreover, students who re-studied the lecture material did not perform any better than students who did a completely unrelated arithmetic task.</p><p><span style=\"color: rgb(0, 128, 128);\">尽管复习讲座的第二组学生面对学习材料的时间更长，但他们在最终测试中的表现却明显比做练习测试的第一组差（低了30%），甚至也没有好过进行完全无关的心算练习的第三组学生。</span></p><p>A wealth of research has shown that testing is more effective at improving retention and test performance than re-studying, even if no feedback is provided. This is called the ‘testing effect’. However, not all tests are created equal. You will greatly benefit from practice testing only if you revise with tests using a particular retrieval mode – recall.</p><p><span style=\"color: rgb(0, 128, 128);\">大量研究已经表明，即使在没有反馈的情况下，测试也能够比复习更有效地提高记忆持久性和考试成绩，这被称为“测验效应”。然而，并不是所有测验都能达到同等的效果。只有利用一种特定的信息检索模式——回想，通过测验进行复习时，才能从中获益。</span></p><p>Recall is a way of retrieving a memory trace when you do not see the correct answer and do not have any options to choose from. Example recall questions could be: “What is the population of Canada?”, or “What is the German word for Monday?”</p><p><span style=\"color: rgb(0, 128, 128);\">回想是在没有看到正确答案，而且没有任何选项可供选择的情况下检索记忆痕迹的方法。例如，回想的问题可以是：“加拿大的人口多少？”或者“星期一在德语中怎么说？”</span></p><p>The opposite of recall is recognition, which is a way of retrieving a memory trace when you see the correct answer or a set of options that includes the correct answer. Example recognition questions could be: “Is the capital of Canada Ottawa or Montréal?”, or “Is the German word for Monday Dienstag or Montag?”.</p><p><span style=\"color: rgb(0, 128, 128);\">回想的反面是再认，是在见到正确答案或一些包含正确答案的选项时检索记忆痕迹的方法。例如，再认的问题可以是：“加拿大的首都是渥太华还是蒙特利尔？”或者“星期一在德语中是Dienstag还是Montag？”</span></p>'),(13,'访问大脑中的记忆','六级','<p>Retrieval is the mechanism of accessing information stored in memory. Successful retrieval of a memory trace hinges on its associations with cues. A cue is anything that is connected to the memory trace (physical object, situation, time period, word, question). Scientists believe that memories are retrieved through the process of ‘spreading activation’. Once a cue is activated in the brain, the activation spreads from the cue to the target memory. A single memory trace can be connected to an infinite number of cues. If none of the relevant cues is activated, the memory trace cannot be retrieved, even though it may be well stored in memory.</p><p><span style=\"color: rgb(0, 128, 128);\">检索是访问记忆中存储信息的机制。记忆痕迹的成功检索取决于它与线索的联系。线索指的是与记忆痕迹相联系的任何事物（可以是有形的物体、情境、时间段、单词、问题）。科学家认为，记忆是通过“激活扩散”的过程来检索的。当一个线索在大脑中被激活以后，激活信号就会从线索传递到目标记忆。一个记忆痕迹可以与无数线索相连。如果任何相关的线索都没有被激活，那么即使完好无缺地存储在记忆中，对应的记忆痕迹也无法被检索到。</span></p><p>As an example, try to remember the capitals of the following countries while covering the rest of the page:</p><p><span style=\"color: rgb(0, 128, 128);\">例如，盖住本页的其余部分，试着回想下列国家的首都：</span></p><p>·South Korea</p><p><span style=\"color: rgb(0, 128, 128);\">·韩国</span></p><p>·Syria</p><p><span style=\"color: rgb(0, 128, 128);\">·叙利亚</span></p><p>·Denmark</p><p><span style=\"color: rgb(0, 128, 128);\">·丹麦</span></p><p>·Colombia</p><p><span style=\"color: rgb(0, 128, 128);\">·哥伦比亚</span></p><p>·Afghanistan</p><p><span style=\"color: rgb(0, 128, 128);\">·阿富汗</span></p><p>·Thailand</p><p><span style=\"color: rgb(0, 128, 128);\">·泰国</span></p><p>·Venezuela</p><p><span style=\"color: rgb(0, 128, 128);\">·委内瑞拉</span></p><p>·Turkey</p><p><span style=\"color: rgb(0, 128, 128);\">·土耳其</span></p><p>Could you remember all of the capitals? Do you feel that you know their names and may be able to remember them later? You may be experiencing the tip-of-the-tongue phenomenon: you know that you know something but still can\'t remember it. Now try the same exercise with a little help:</p><p><span style=\"color: rgb(0, 128, 128);\">你能想起所有这些首都吗？有没有感到自己知道它们的名字，也许过会儿就能想起来？你或许也遇到过这种就在嘴边却说不出来的现象：你知道自己知道某件事，但依然无法想起来。下面试着在一点小帮助下再做同样的练习：</span></p><p>·South Korea S</p><p><span style=\"color: rgb(0, 128, 128);\">·韩国　首</span></p><p>·Syria D</p><p><span style=\"color: rgb(0, 128, 128);\">·叙利亚　大</span></p><p>·Denmark C</p><p><span style=\"color: rgb(0, 128, 128);\">·丹麦　哥</span></p><p>·Colombia B</p><p><span style=\"color: rgb(0, 128, 128);\">·哥伦比亚　波</span></p><p>·Afghanistan K</p><p><span style=\"color: rgb(0, 128, 128);\">·阿富汗　喀</span></p><p>·Thailand B</p><p><span style=\"color: rgb(0, 128, 128);\">·泰国　曼</span></p><p>·Venezuela C</p><p><span style=\"color: rgb(0, 128, 128);\">·委内瑞拉　加</span></p><p>·Turkey A</p><p><span style=\"color: rgb(0, 128, 128);\">·土耳其　安</span></p><p>Did you remember all of them now? You most likely remembered more of them. This is because the starting letter functions as a suitable cue that is connected to the capital\'s name. When the cue is provided, the memory trace storing the capital\'s name becomes automatically activated.</p><p><span style=\"color: rgb(0, 128, 128);\">现在你记起它们了吗？很可能这回记起了更多，因为第一个字就是一个合适的线索，关联到首都的名称。提供了这个线索后，存储首都名称的记忆痕迹自动就被激活了。</span></p><p>How does the process of retrieval function and what are its implications for learning? What can we do to maximize our chances of successfully retrieving information?</p><p><span style=\"color: rgb(0, 128, 128);\">检索记忆的过程是如何工作的？对于学习又有什么启示呢？我们能够做些什么来尽可能增加成功检索信息的几率呢？</span></p><p class=\"ql-align-right\">——节选自《记忆的科学》</p><p><br></p>'),(14,'ROCK ME TO SLEEP By Elizabeth Akers Allen','考研','<p>BACKWARD, turn backward, O Time, in your flight,</p><p>Make me a child again just for to-night!</p><p>Mother, come back from the echoless shore,</p><p>Take me again to your heart as of yore;</p><p>Kiss from my forehead the furrows of care,</p><p>Smooth the few silver threads out of my hair;</p><p>Over my slumbers your loving watch keep; —</p><p>Rock me to sleep, mother, —rock me to sleep!</p><p>Backward, flow backward, O tide of the years!</p><p>I am so weary of toil and of tears, —</p><p>Toil without recompense, tears all in vain, —</p><p>Take them, and give me my childhood again!</p><p>I have grown weary of dust and decay, —</p><p>Weary of flinging my soul-wealth away;</p><p>Weary of sowing for others to reap; —</p><p>Rock me to sleep, mother, —rock me to sleep!</p><p>Tired of the hollow, the base, the untrue,</p><p>Mother, O mother, my heart calls for you!</p><p>Many a summer the grass has grown green,</p><p>Blossomed and faded, our faces between:</p><p>Yet, with strong yearning and passionate pain,</p><p>Long I to-night for your presence again.</p><p>Come from the silence so long and so deep; —</p><p>Rock me to sleep, mother, —rock me to sleep!</p><p>Over my heart, in the days that are flown,</p><p>No love like mother-love ever has shone;</p><p>No other worship abides and endures, —</p><p>Faithful, unselfish, and patient like yours:</p><p>None like a mother can charm away pain</p><p>From the sick soul and the world-weary brain.</p><p>Slumber’s soft calms o’er my heavy lids creep; —</p><p>Rock me to sleep, mother, —rock me to sleep!</p><p>Come, let your brown hair, just lighted with gold,</p><p>Fall on your shoulders again as of old;</p><p>Let it drop over my forehead to-night,</p><p>Shading my faint eyes away from the light;</p><p>For with its sunny-edged shadows once more</p><p>Haply will throng the sweet visions of yore;</p><p>Lovingly, softly, its bright billows sweep; —</p><p>Rock me to sleep, mother, —rock me to sleep!</p><p>Mother, dear mother, the years have been long</p><p>Since I last listened your lullaby song:</p><p>Sing, then, and unto my soul it shall seem</p><p>Womanhood’s years have been only a dream.</p><p>Clasped to your heart in a loving embrace,</p><p>With your light lashes just sweeping my face,</p><p>Never hereafter to wake or to weep; —</p><p>Rock me to sleep, mother,—rock me to sleep!</p>'),(15,'THE RAINBOW By William Wordsworth','考研','<p>MY heart leaps up when I behold</p><p>A rainbow in the sky:</p><p>So was it when my life began;</p><p>So is it now I am a man;</p><p>So be it when I shall grow old,</p><p>Or let me die!</p><p>The Child is father of the Man;</p><p>I could wish my days to be</p><p>Bound each to each by natural piety.</p><p><br></p>');
/*!40000 ALTER TABLE `wenkan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `xuesheng`
--

DROP TABLE IF EXISTS `xuesheng`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `xuesheng` (
                            `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
                            `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `zhanghao` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '账号',
                            `nicheng` varchar(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '昵称',
                            `mima` varchar(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '密码',
                            `xingming` varchar(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '姓名',
                            `xingbie` varchar(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '性别',
                            `touxiang` longtext COLLATE utf8mb4_unicode_ci COMMENT '头像',
                            `youxiang` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邮箱',
                            `mobile` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机号',
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `zhanghao` (`zhanghao`)
) ENGINE=InnoDB AUTO_INCREMENT=1775468531541 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='学生';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `xuesheng`
--

LOCK TABLES `xuesheng` WRITE;
/*!40000 ALTER TABLE `xuesheng` DISABLE KEYS */;
INSERT INTO `xuesheng` VALUES (1775467368462,'2026-04-06 09:22:48','0997','丰富','123456','呃呃','男','upload/1775469813068_438.png','','13020715030'),(1775468531540,'2026-04-06 09:42:11','1379','土豆','123456','王一','男','upload/1775468506868_917.png','','12345678901');
/*!40000 ALTER TABLE `xuesheng` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `xuexijihua`
--

DROP TABLE IF EXISTS `xuexijihua`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `xuexijihua` (
                              `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                              `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                              `xingming` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '姓名',
                              `xuehao` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '学号',
                              `riqi` date DEFAULT NULL COMMENT '日期',
                              `xuexishizhang` double DEFAULT NULL COMMENT '学习时长',
                              `xuexirenwu` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '学习任务',
                              `fuxijihua` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '复习计划',
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='学习计划';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `xuexijihua`
--

LOCK TABLES `xuexijihua` WRITE;
/*!40000 ALTER TABLE `xuexijihua` DISABLE KEYS */;
INSERT INTO `xuexijihua` VALUES (9,'2026-04-07 05:22:23','呃呃',NULL,'2026-04-07',2,'学习10个单词','复习50个单词');
/*!40000 ALTER TABLE `xuexijihua` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-04-08 13:47:32
