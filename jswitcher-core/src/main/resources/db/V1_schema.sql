CREATE DATABASE switcher;

USE switcher;

CREATE TABLE `switcher` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `key` varchar(64) NOT NULL DEFAULT '',
  `open` tinyint(1) NOT NULL DEFAULT '0',
  `describe` varchar(128) DEFAULT NULL,
  `qos` varchar(24) NOT NULL DEFAULT 'API',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `key` (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;