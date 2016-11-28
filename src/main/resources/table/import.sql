DROP DATABASE IF EXISTS springsecurity;

CREATE DATABASE springsecurity CHARACTER SET UTF8;

USE springsecurity;

-- ---------------
-- 用户表结构
-- ---------------
CREATE TABLE `user` (
  `id` INT(11) PRIMARY KEY AUTO_INCREMENT,
  `username` VARCHAR(100),
  `password` VARCHAR(100),
  `attempt_times` INT(5) DEFAULT 0,
  `last_attempt_date` DATETIME,
  `account_expired_date` DATETIME,
  `credentials_expired_date` DATETIME,
  `enable` INT(1),
  `role_id` INT(11)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ---------------
-- 用户表数据  用户名跟密码相同，通过BCryptPasswordEncoder(9)加密了
-- ---------------
INSERT INTO `user` VALUES (1, 'admin', '$2a$09$4UUZm8xhX0YDwGKnXPlTmev7Tp5uVk9jAX47mm0dX0J1HPp6ecjz2', 0, NULL, '2017-11-15', '2017-11-15', 1, 1);
INSERT INTO `user` VALUES (2, 'user', '$2a$09$fzdTS9caotxl7n27R3G.wuxQV5P/yJs5fa.Y2g1wz1/I5uzi4MEkG', 0, NULL, '2017-11-15', '2017-11-15', 1, 2);
INSERT INTO `user` VALUES (3, '111111', '$2a$09$A61UpCdR35PAq9omdTOnxOt4pIvuxESujsmrdETemyXKN4FDJF/sC', 0, NULL, '2016-11-15', '2016-11-15', 1, 2);

-- ----------------------------
-- 角色表结构
-- ----------------------------
CREATE TABLE `role` (
  `id` int(11) PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,  -- 'ROLE_'为前缀
  `description` varchar(50) DEFAULT NULL,
  UNIQUE KEY `name_unique_index` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- 角色记录
-- ----------------------------
INSERT INTO `role` VALUES (1, 'ROLE_ADMIN', '管理员');
INSERT INTO `role` VALUES (2, 'ROLE_USER', '普通用户');


