## 登录授权模版

### 简介

项目目标为多系统提供统一登录授权服务。

### 项目简介

##### 1. oauth-common

基础工具包

##### 2. oauth-inner-ikeeper

登录、退出、check token

##### 3. oauth-inner-keeper-dubbo

dubbo调用接口

##### 4. oauth-master-center

具体服务，依赖mysql和redis

### 设计概念

#### 机构

账户登录系统的分类。目前有内部系统、外部系统、和合作机构。

##### 内部系统

管理者和内部用户可访问，其中内部用户需要被每个机构授权。

##### 外部系统

普通用户注册及可访问所有外部系统。

##### 合作机构

需要普通用户授权才可登录。

#### 账户

根据访问系统类型分为管理者、内部用户、外部用户。

##### 管理者

只能访问内部系统，不需要单独授权。

##### 内部用户

只能访问内部系统，且需要系统授权。

##### 外部用户

注册后可以访问所有外部系统；登录合作机构需要用户授权。

### 库

#### 表设计

##### 账户信息表（mc_account）

| 属性        | 类型         | 空   | 默认     | 备注                                            |
| ----------- | ------------ | ---- | -------- | ----------------------------------------------- |
| id          | bigint(20)   | N    | 自增     | 主键                                            |
| nickname    | varchar(50)  | N    | 随机     | 昵称                                            |
| account     | varchar(100) | N    | 唯一     | 用户标记                                        |
| img         | varchar(255) | N    | 默认图片 | 头像地址                                        |
| password    | varchar(255) | N    | 无       | sha256(password+salt)                           |
| salt        | varchar(255) | N    | 随机     | 盐                                              |
| level       | tinyint(2)   | N    | 2        | 用户级别（0：管理者；1：内部用户；2：外部用户） |
| status      | tinyint(2)   | N    | 1        | 状态（1：可用；-1：不可用）                     |
| update_time | bigint(20)   | N    | 0        | 更新时间戳，13位                                |
| create_time | bigint(20)   | N    | 0        | 生成时间戳，13位                                |

##### 客户端信息表（mc_client）



##### 账户授权表（mc_account_bind）

#### SQL

```sql
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for mc_account
-- ----------------------------
DROP TABLE IF EXISTS `mc_account`;
CREATE TABLE `mc_account`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
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

