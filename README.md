## 登录授权模版

#### 简介

项目目标为多系统提供统一登录授权服务。

#### 项目简介

##### 1. oauth-common

基础工具包

##### 2. oauth-inner-ikeeper

登录、退出、check token

##### 3. oauth-inner-keeper-dubbo

dubbo调用接口

##### 4. oauth-master-center

具体服务，依赖mysql和redis



#### 库

##### 账户信息表（mc_account）

##### 客户端信息表（mc_client）

##### 账户授权表（mc_account_bind）

##### SQL

```sql
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for mc_account
-- ----------------------------
DROP TABLE IF EXISTS `mc_account`;
CREATE TABLE `mc_account`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `salt` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `level` tinyint(2) UNSIGNED NOT NULL,
  `status` tinyint(2) UNSIGNED NOT NULL,
  `update_time` bigint(20) NOT NULL,
  `create_time` bigint(20) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic STORAGE DISK;

-- ----------------------------
-- Table structure for mc_account_bind
-- ----------------------------
DROP TABLE IF EXISTS `mc_account_bind`;
CREATE TABLE `mc_account_bind`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `client_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `authority` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `auth_start_time` bigint(20) NOT NULL,
  `auth_stop_time` bigint(20) NOT NULL,
  `update_time` bigint(20) NOT NULL,
  `create_time` bigint(20) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic STORAGE DISK;

-- ----------------------------
-- Table structure for mc_client
-- ----------------------------
DROP TABLE IF EXISTS `mc_client`;
CREATE TABLE `mc_client`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `client_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `client_secret` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `redirect_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `status` tinyint(2) UNSIGNED NOT NULL,
  `update_time` bigint(20) NOT NULL,
  `create_time` bigint(20) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic STORAGE DISK;

SET FOREIGN_KEY_CHECKS = 1;
```

