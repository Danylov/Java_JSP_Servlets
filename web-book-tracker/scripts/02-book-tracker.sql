CREATE DATABASE  IF NOT EXISTS `web_book_tracker`;
USE `web_book_tracker`;

--
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;

CREATE TABLE `book` (
  `id`             int(11) NOT NULL AUTO_INCREMENT,
  `bookName`       varchar(45) DEFAULT NULL,
  `author`         varchar(45) DEFAULT NULL,
  `dateOfRelease`  DATE        DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

