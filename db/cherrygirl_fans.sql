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

 Date: 03/09/2021 01:18:40
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cherrygirl_fans
-- ----------------------------
DROP TABLE IF EXISTS `cherrygirl_fans`;
CREATE TABLE `cherrygirl_fans`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `uid` bigint(20) NULL DEFAULT NULL,
  `fans` bigint(20) NULL DEFAULT NULL,
  `nums` bigint(20) NULL DEFAULT NULL,
  `stat_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cherrygirl_fans
-- ----------------------------
INSERT INTO `test`.`cherrygirl_fans` (`id`, `uid`, `fans`, `nums`, `stat_time`) VALUES (37, 672328094, 935403, 5419, '2021-09-05');
INSERT INTO `test`.`cherrygirl_fans` (`id`, `uid`, `fans`, `nums`, `stat_time`) VALUES (38, 672346917, 354026, 1077, '2021-09-05');
INSERT INTO `test`.`cherrygirl_fans` (`id`, `uid`, `fans`, `nums`, `stat_time`) VALUES (39, 672353429, 363878, 1224, '2021-09-05');
INSERT INTO `test`.`cherrygirl_fans` (`id`, `uid`, `fans`, `nums`, `stat_time`) VALUES (40, 672342685, 396356, 8755, '2021-09-05');
INSERT INTO `test`.`cherrygirl_fans` (`id`, `uid`, `fans`, `nums`, `stat_time`) VALUES (41, 351609538, 317838, 993, '2021-09-05');

INSERT INTO `test`.`cherrygirl_fans` (`id`, `uid`, `fans`, `nums`, `stat_time`) VALUES (42, 672328094, 1087076, 2604, '2021-10-05');
INSERT INTO `test`.`cherrygirl_fans` (`id`, `uid`, `fans`, `nums`, `stat_time`) VALUES (43, 672346917, 392219, 2677, '2021-10-05');
INSERT INTO `test`.`cherrygirl_fans` (`id`, `uid`, `fans`, `nums`, `stat_time`) VALUES (44, 672353429, 400454, 1921, '2021-10-05');
INSERT INTO `test`.`cherrygirl_fans` (`id`, `uid`, `fans`, `nums`, `stat_time`) VALUES (45, 672342685, 435012, 2021, '2021-10-05');
INSERT INTO `test`.`cherrygirl_fans` (`id`, `uid`, `fans`, `nums`, `stat_time`) VALUES (46, 351609538, 357349, 2334, '2021-10-05');

SET FOREIGN_KEY_CHECKS = 1;
