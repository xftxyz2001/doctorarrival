-- 
-- 公共模块
-- 
CREATE DATABASE `doctorarrival_common` CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci';
USE `doctorarrival_common`;
-- 数据字典表
CREATE TABLE `dict` (
    `id` bigint NOT NULL COMMENT 'id',
    `parent_id` bigint NOT NULL COMMENT '上级id',
    `value` varchar(127) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '值',
    `dict_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '编码',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_dict_code`(`dict_code` ASC) USING BTREE,
    INDEX `idx_parent_id`(`parent_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '数据字典表' ROW_FORMAT = Dynamic;
-- 
-- 医院模块
-- 
CREATE DATABASE `doctorarrival_hospital` CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci';
USE `doctorarrival_hospital`;
-- 医院设置表
CREATE TABLE `hospital_set` (
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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '医院设置表' ROW_FORMAT = Dynamic;
-- 
-- 用户模块
-- 
CREATE DATABASE `doctorarrival_user` CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci';
USE `doctorarrival_user`;
-- 用户信息表
CREATE TABLE `user_info` (
    `id` bigint NOT NULL COMMENT 'id',
    `phone` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号',
    `openid` varchar(127) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '微信openid',
    `nick_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '昵称',
    `name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '姓名',
    `certificates_type` tinyint NULL DEFAULT NULL COMMENT '证件类型（1：身份证，2：军官证，3：护照）',
    `certificates_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '证件编号',
    `certificates_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '证件路径',
    `auth_status` tinyint NOT NULL DEFAULT 0 COMMENT '状态（0：未认证，1：认证中，2：认证成功，-1：认证失败）',
    `auth_time` datetime NULL DEFAULT NULL COMMENT '认证有效期（结束时间）',
    `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态（0：不可用，1：可用）',
    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '逻辑删除（0:未删除，1:已删除）',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;
-- 登录记录表
CREATE TABLE `user_login_record` (
    `id` bigint NOT NULL COMMENT 'id',
    `user_id` bigint NOT NULL DEFAULT 0 COMMENT '用户id',
    `ip` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '登录ip',
    `user_agent` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '登录ua',
    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '逻辑删除（0:未删除，1:已删除）',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '登录记录表' ROW_FORMAT = Dynamic;
-- 病人信息表
CREATE TABLE `patient` (
    `id` bigint NOT NULL COMMENT 'id',
    `user_id` bigint NOT NULL COMMENT '用户id',
    `name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '姓名',
    `phone` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号',
    `certificates_type` tinyint NULL DEFAULT NULL COMMENT '证件类型（1：身份证，2：军官证，3：护照）',
    `certificates_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '证件编号',
    `gender` tinyint NULL DEFAULT NULL COMMENT '性别（0：女，1：男）',
    `is_marry` tinyint NULL DEFAULT NULL COMMENT '是否结婚（0：否，1：是）',
    `birthday` date NULL DEFAULT NULL COMMENT '出生日期',
    `is_insured` tinyint NULL DEFAULT NULL COMMENT '是否有医保（0：否，1：是）',
    `card_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '就诊卡号',
    `contacts_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系人姓名',
    `contacts_phone` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系人手机',
    `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态（0：不可用，1：可用）',
    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '逻辑删除（0:未删除，1:已删除）',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '病人信息表' ROW_FORMAT = Dynamic;
-- 
-- 订单模块
-- 
CREATE DATABASE `doctorarrival_order` CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci';
USE `doctorarrival_order`;
-- 订单表
CREATE TABLE `order_info` (
    `id` bigint NOT NULL COMMENT 'id',
    `user_id` bigint NOT NULL COMMENT '用户id',
    `hospital_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '医院编号',
    `hospital_name` varchar(127) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '医院名称',
    `department_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '科室编号',
    `department_name` varchar(127) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '科室名称',
    `doctor_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '医生名称',
    `doctor_title` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '医生职称',
    `schedule_id` bigint NOT NULL COMMENT '排班id',
    `reserve_date` datetime NULL DEFAULT NULL COMMENT '预约时间',
    `patient_id` bigint NOT NULL COMMENT '就诊人id',
    `patient_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '就诊人名称',
    `patient_phone` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '就诊人手机',
    `amount` int NULL DEFAULT NULL COMMENT '订单金额（分）',
    `order_status` tinyint NOT NULL DEFAULT 0 COMMENT '订单状态（-1：已关闭，0：待支付，1：已支付，2：待退款，3：已退款，4：已完成）',
    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '逻辑删除（0:未删除，1:已删除）',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单表' ROW_FORMAT = Dynamic;
