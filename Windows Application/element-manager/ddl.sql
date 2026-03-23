CREATE DATABASE `pbl` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
use pbl;

-- pbl.`element` definition
CREATE TABLE `element` (
  `ele_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '엘리멘트 이름',
  `ele_rate` int DEFAULT '0' COMMENT '엘리멘트 평점',
  `ele_id` int NOT NULL,
  `ele_exp` varchar(200) DEFAULT NULL,
  `ele_memo` varchar(200) DEFAULT NULL,
  `ele_addr` varchar(100) DEFAULT NULL,
  `user_id` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`ele_id`),
  KEY `element_FK` (`user_id`),
  CONSTRAINT `element_FK` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- pbl.eletag definition
CREATE TABLE `eletag` (
  `ele_id` int NOT NULL,
  `tag_id` varchar(100) NOT NULL,
  KEY `eletag_FK` (`ele_id`),
  KEY `eletag_FK_1` (`tag_id`),
  CONSTRAINT `eletag_FK` FOREIGN KEY (`ele_id`) REFERENCES `element` (`ele_id`),
  CONSTRAINT `eletag_FK_1` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- pbl.question definition
CREATE TABLE `question` (
  `q_title` varchar(50) NOT NULL,
  `q_cont` varchar(200) NOT NULL,
  `q_type` varchar(20) DEFAULT NULL,
  `q_id` int NOT NULL,
  `user_id` varchar(30) NOT NULL,
  PRIMARY KEY (`q_id`),
  KEY `question_FK` (`user_id`),
  CONSTRAINT `question_FK` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- pbl.request definition
CREATE TABLE `request` (
  `type_name` varchar(20) NOT NULL,
  `type_cont` varchar(200) NOT NULL,
  `type_source` varchar(100) DEFAULT NULL,
  `req_id` int NOT NULL,
  `user_id` varchar(30) NOT NULL,
  PRIMARY KEY (`req_id`),
  KEY `request_FK` (`user_id`),
  CONSTRAINT `request_FK` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- pbl.tag definition
CREATE TABLE `tag` (
  `tag_name` varchar(100) NOT NULL,
  `tag_id` varchar(100) NOT NULL,
  PRIMARY KEY (`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- pbl.`user` definition
CREATE TABLE `user` (
  `user_name` varchar(20) NOT NULL,
  `user_id` varchar(30) NOT NULL,
  `user_pw` varchar(100) NOT NULL,
  `user_sex` varchar(100) NOT NULL DEFAULT '남',
  `user_email` varchar(100) NOT NULL,
  `user_phone` varchar(20) DEFAULT NULL,
  `user_addr` varchar(100) DEFAULT NULL,
  `introduce` varchar(200) DEFAULT NULL,
  `op_check` int NOT NULL DEFAULT '1' COMMENT '1=일반, 2=운용자',
  `user_state` varchar(10) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;