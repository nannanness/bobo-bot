/*
 Navicat MySQL Data Transfer

 Source Server         : Mysql
 Source Server Type    : MySQL
 Source Server Version : 50735
 Source Host           : localhost:3306
 Source Schema         : test

 Target Server Type    : MySQL
 Target Server Version : 50735
 File Encoding         : 65001

 Date: 02/09/2021 02:27:15
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cherrygirl_cookies
-- ----------------------------
DROP TABLE IF EXISTS `cherrygirl_cookies`;
CREATE TABLE `cherrygirl_cookies`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `isv` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `cookies` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cherrygirl_cookies
-- ----------------------------
UPDATE `test`.`cherrygirl_cookies` SET `isv` = 'bilibili', `cookies` = '[{\"discard\":false,\"domain\":\".bilibili.com\",\"httpOnly\":false,\"maxAge\":431998,\"name\":\"bili_jct\",\"path\":\"/\",\"secure\":false,\"value\":\"01b4f2baec2ee8fda6fa3e58c8adaa88\",\"version\":0},{\"discard\":false,\"domain\":\".bilibili.com\",\"httpOnly\":true,\"maxAge\":431998,\"name\":\"SESSDATA\",\"path\":\"/\",\"secure\":false,\"value\":\"1c16792b%2C1648982776%2C352bc*a1\",\"version\":0},{\"discard\":false,\"domain\":\".bilibili.com\",\"httpOnly\":false,\"maxAge\":431998,\"name\":\"DedeUserID__ckMd5\",\"path\":\"/\",\"secure\":false,\"value\":\"35458ab73eea1fe7\",\"version\":0},{\"discard\":false,\"domain\":\".bilibili.com\",\"httpOnly\":false,\"maxAge\":431998,\"name\":\"DedeUserID\",\"path\":\"/\",\"secure\":false,\"value\":\"19915093\",\"version\":0}]' WHERE `id` = 1;

SET FOREIGN_KEY_CHECKS = 1;
