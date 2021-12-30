/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost:3306
 Source Schema         : jieba

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 30/12/2021 16:28:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for system_key_word
-- ----------------------------
DROP TABLE IF EXISTS `system_key_word`;
CREATE TABLE `system_key_word`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `keyword` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '关键词',
  `word_frequency` bigint(11) NOT NULL DEFAULT 1 COMMENT '词频。值越大，成词概率越高，建议值1-2000',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `keyword`(`keyword`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12382 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统词库' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
