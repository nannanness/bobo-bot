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

 Date: 28/08/2021 19:03:27
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cherrygirl_spy
-- ----------------------------
DROP TABLE IF EXISTS `cherrygirl_spy`;
CREATE TABLE `cherrygirl_spy`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pos` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `neg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `parent` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `children` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `weight` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cherrygirl_spy
-- ----------------------------
INSERT INTO `cherrygirl_spy` VALUES (2, '元芳', '展昭', NULL, NULL, NULL, NULL);
INSERT INTO `cherrygirl_spy` VALUES (3, '麻雀', '乌鸦', NULL, NULL, NULL, NULL);
INSERT INTO `cherrygirl_spy` VALUES (4, '胖子', '肥肉', NULL, NULL, NULL, NULL);
INSERT INTO `cherrygirl_spy` VALUES (5, '眉毛', '胡须', NULL, NULL, NULL, NULL);
INSERT INTO `cherrygirl_spy` VALUES (6, '状元', '冠军', NULL, NULL, NULL, NULL);
INSERT INTO `cherrygirl_spy` VALUES (7, '饺子', '混沌', NULL, NULL, NULL, NULL);
INSERT INTO `cherrygirl_spy` VALUES (8, '端午节', '中秋节', NULL, NULL, NULL, NULL);
INSERT INTO `cherrygirl_spy` VALUES (9, '摩托车', '电动车', NULL, NULL, NULL, NULL);
INSERT INTO `cherrygirl_spy` VALUES (10, '高跟鞋', '增高鞋', NULL, NULL, NULL, NULL);
INSERT INTO `cherrygirl_spy` VALUES (11, '肉夹馍', '夹肉饼', NULL, NULL, NULL, NULL);
INSERT INTO `cherrygirl_spy` VALUES (13, '蜘蛛侠', '钢铁侠', NULL, NULL, NULL, NULL);
INSERT INTO `cherrygirl_spy` VALUES (14, '反弹琵琶', '乱弹棉花', NULL, NULL, NULL, NULL);
INSERT INTO `cherrygirl_spy` VALUES (15, '买一送一', '再来一瓶', NULL, NULL, NULL, NULL);
INSERT INTO `cherrygirl_spy` VALUES (16, '初吻', '初恋', NULL, NULL, NULL, NULL);
INSERT INTO `cherrygirl_spy` VALUES (17, '班主任', '辅导员', NULL, NULL, NULL, NULL);
INSERT INTO `cherrygirl_spy` VALUES (18, '九阴白骨爪', '降龙十八掌', NULL, NULL, NULL, NULL);
INSERT INTO `cherrygirl_spy` VALUES (19, '欲求不满', '饥渴难耐', NULL, NULL, NULL, NULL);
INSERT INTO `cherrygirl_spy` VALUES (20, '仙剑奇侠传', '古剑奇谭', NULL, NULL, NULL, NULL);
INSERT INTO `cherrygirl_spy` VALUES (22, '迪奥娜', '七七', NULL, NULL, NULL, NULL);
INSERT INTO `cherrygirl_spy` VALUES (23, '雷莹术士', '冰莹术士', NULL, NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
