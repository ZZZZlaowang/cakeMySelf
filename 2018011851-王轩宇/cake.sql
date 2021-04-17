/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50615
 Source Host           : localhost:3306
 Source Schema         : cake

 Target Server Type    : MySQL
 Target Server Version : 50615
 File Encoding         : 65001

 Date: 16/10/2020 14:24:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for 我想要2fororder
-- ----------------------------
DROP TABLE IF EXISTS `我想要2fororder`;
CREATE TABLE `我想要2fororder`  (
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `ifOrder` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of 我想要2fororder
-- ----------------------------

-- ----------------------------
-- Table structure for 我想要2forshop
-- ----------------------------
DROP TABLE IF EXISTS `我想要2forshop`;
CREATE TABLE `我想要2forshop`  (
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `size` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `price` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of 我想要2forshop
-- ----------------------------

-- ----------------------------
-- Table structure for 我想要fororder
-- ----------------------------
DROP TABLE IF EXISTS `我想要fororder`;
CREATE TABLE `我想要fororder`  (
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ifOrder` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of 我想要fororder
-- ----------------------------

-- ----------------------------
-- Table structure for 我想要forshop
-- ----------------------------
DROP TABLE IF EXISTS `我想要forshop`;
CREATE TABLE `我想要forshop`  (
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `size` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `price` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of 我想要forshop
-- ----------------------------

-- ----------------------------
-- Table structure for 趣味额forsell
-- ----------------------------
DROP TABLE IF EXISTS `趣味额forsell`;
CREATE TABLE `趣味额forsell`  (
  `user` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ifOrder` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of 趣味额forsell
-- ----------------------------

-- ----------------------------
-- Table structure for cake
-- ----------------------------
DROP TABLE IF EXISTS `cake`;
CREATE TABLE `cake`  (
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `size` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `price` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `material` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '暂无介绍',
  `packing` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '暂无介绍',
  `brand` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '暂无介绍',
  `place` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '暂无介绍',
  `give` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '暂无介绍',
  `user` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of cake
-- ----------------------------
INSERT INTO `cake` VALUES ('心跳回忆', '100', '500', '由于草莓是季节性水果，部分城市可能用提子等水果代替草莓装饰', '精致蛋糕盒包装，一次性蛋糕用品一套', 'holiland/好利来蛋糕3点前下单可当天送达', '北京,天津,石家庄,唐山,太原,大同,呼伦贝尔,本溪,锦州,鞍山,哈尔滨,长春,铁岭,吉林,延吉,牡丹江,齐齐哈尔,西安,成都,昆明,苏州,兰州', '赠送蜡烛、餐盘、刀叉等一次性蛋糕用品一套。', '趣味额');
INSERT INTO `cake` VALUES ('珍惜', '8', '200', '暂无', '无', '无', '无', '无', '趣味额');
INSERT INTO `cake` VALUES ('臻爱礼盒', '6', '399', '果仁蛋糕+乳脂奶油', '精致蛋糕盒包装，一次性蛋糕用品一套', 'holiland/好利来蛋糕3点前下单可当天送达', '北京,天津,沈阳，太原,大同,本溪,锦州,盘锦,哈尔滨,阜新,吉林,成都,烟台,九江,新余,宜春,上饶,临沂,呼伦贝尔,西安,抚顺,昆明,鞍山,重庆,长春,苏州,东营,合肥,贵阳,郑州', '赠送蜡烛、餐盘、刀叉等一次性蛋糕用品一套', '趣味额');
INSERT INTO `cake` VALUES ('花漾甜心', '8', '379', '原味戚风蛋糕+酸奶提子夹心', '精致蛋糕盒包装，一次性蛋糕用品一套', 'holiland/好利来蛋糕3点前下单可当天送达', '北京,天津,石家庄,沈阳,大同,呼伦贝尔,大连,本溪,锦州,鞍山,抚顺,哈尔滨,长春,阜新,铁岭,朝阳,吉林,延吉,牡丹江,齐齐哈尔,平顶山,烟台,郑州,九江,新余,宜春,上饶,临沂,呼伦贝尔,成都,西安,张家口,重庆,南昌,兰州,唐山,贵阳,呼和浩特,丹东,东营,太原,合肥,邢台,秦皇岛,济南,昆明,苏州,佳木斯,阳泉', '赠送蜡烛、餐盘、刀叉等一次性蛋糕用品一套。', '趣味额');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `user` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `pwd` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `identity` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('我想要', 'qwe', 'buy');
INSERT INTO `users` VALUES ('趣味额', 'qwe', 'sell');
INSERT INTO `users` VALUES ('我想要2', 'qwe', 'buy');

SET FOREIGN_KEY_CHECKS = 1;
