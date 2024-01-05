CREATE DATABASE IF NOT EXIST `quiz`;

CREATE TABLE IF NOT EXIST `question` (
  `quiz_num` int NOT NULL,
  `num` int NOT NULL,
  `title` varchar(45) NOT NULL,
  `type` varchar(20) NOT NULL,
  `is_necessary` tinyint DEFAULT '0',
  `option` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`quiz_num`,`num`)
);

