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

 Date: 03/09/2021 01:18:47
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cherrygirl_up
-- ----------------------------
DROP TABLE IF EXISTS `cherrygirl_up`;
CREATE TABLE `cherrygirl_up`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `uid` bigint(20) NULL DEFAULT NULL,
  `roomId` bigint(20) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cherrygirl_up
-- ----------------------------
INSERT INTO `cherrygirl_up` VALUES (1, 672328094, 22637261, '嘉然');
INSERT INTO `cherrygirl_up` VALUES (2, 672346917, 22625025, '向晚');
INSERT INTO `cherrygirl_up` VALUES (3, 672353429, 22632424, '贝拉');
INSERT INTO `cherrygirl_up` VALUES (4, 672342685, 22625027, '乃琳');
INSERT INTO `cherrygirl_up` VALUES (5, 351609538, 22634198, '\r\n珈乐');

SET FOREIGN_KEY_CHECKS = 1;
