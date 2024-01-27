CREATE DATABASE IF NOT EXISTS doctorarrival_hospital;
USE doctorarrival_hospital;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for hospital_set
-- ----------------------------
DROP TABLE IF EXISTS `hospital_set`;
CREATE TABLE `hospital_set`  (
  `id` bigint NOT NULL COMMENT 'id',
  `hospital_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '医院编号',
  `hospital_name` varchar(127) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '医院名称',
  `api_url` varchar(127) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'api基础路径',
  `sign_key` varchar(511) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '签名秘钥',
  `contacts_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系人',
  `contacts_phone` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系人手机',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态（0：不可用，1：可用）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '逻辑删除（0:未删除，1:已删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_hospital_code`(`hospital_code` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '医院设置表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of hospital_set
-- ----------------------------
INSERT INTO `hospital_set` VALUES (1748938738984574977, '8999', '医院8999', 'http://mock:8999/da', 'MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxrtGU70c4J0fFKx19yDRSKcNr+KkZivkO/wafupruaJuA8BSgAZmgqoYYyHyzJoz87Af7K4BLC8S6uyy1YxXBGehjP3j+WlmlLAVwbMZURvC8BTCv4Ruhr+wRSHfT9kO97eFFQeGScJmCir3kfCwKbJXwOXebf2Kr5ot8a1DbjlvFoyFmBWt9dj50hgzk+VMr7yrAGYxvALCZxxAYY9dpFYjgWW01NW8ngdrH6Q0HdxADZkayPVfwaLQax9hhrCE3THMpuzz/R8VfNl5Cfv+bpXLLgYzz7w+ntlKSNRrRR53Y/jtAPa+wKu4MDubOMNXtVx7GELdnAXJOellq96RdQIDAQAB', '联系人', '18764738999', 1, '2024-01-21 13:21:02', '2024-01-21 13:21:02', 0);

SET FOREIGN_KEY_CHECKS = 1;
