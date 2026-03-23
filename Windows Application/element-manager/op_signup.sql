-- pbl.op_signup definition

CREATE TABLE `op_signup` (
  `user_id` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `confirm_kno` varchar(10) DEFAULT NULL,
  KEY `op_signup_FK` (`user_id`),
  CONSTRAINT `op_signup_FK` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;