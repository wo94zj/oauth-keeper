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

普通用户注册即可访问所有外部系统。

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
| nickname    | varchar(50)  | N    | 规则生成 | 昵称                                            |
| img         | varchar(255) | N    | 默认图片 | 头像地址                                        |
| password    | varchar(255) | N    | 无       | sha256(password+salt)                           |
| salt        | varchar(255) | N    | 随机     | 盐                                              |
| phone       | char(11)     | N    |          | 手机号                                          |
| level       | tinyint(2)   | N    | 2        | 用户级别（0：管理者；1：内部用户；2：外部用户） |
| status      | tinyint(2)   | N    | 1        | 状态（1：可用；-1：不可用）                     |
| update_time | bigint(20)   | N    | 0        | 更新时间戳，13位                                |
| create_time | bigint(20)   | N    | 0        | 生成时间戳，13位                                |

##### 三方绑定（mc_account_thirdparty）

| 属性       | 类型         | 空   | 默认 | 备注                      |
| ---------- | ------------ | ---- | ---- | ------------------------- |
| id         | bigint(20)   | N    |      |                           |
| account_id | bigint(20)   | N    |      | 用户标记                  |
| open_id    | varchar(255) | N    |      | 三方标记                  |
| nickname   | varchar(50)  |      |      | 昵称                      |
| sex        | tinyint(2)   |      |      | 0：保密，1：男；2：女     |
| img        | varchar(255) |      |      |                           |
| type       | enum         | N    |      | QQ；WX；ALI               |
| status     | tinyint(2)   | N    |      | 状态（1：绑定；-1：解绑） |
| updateTime | bigint(20)   | N    |      |                           |
| createTime | bigint(20)   | N    |      |                           |

##### 机构信息表（mc_client）

| 属性          | 类型         | 空   | 默认 | 备注                                              |
| ------------- | ------------ | ---- | ---- | ------------------------------------------------- |
| id            | bigint(20)   | N    | 自增 | 主键                                              |
| client_code   | varchar(50)  | N    |      | 机构标识                                          |
| client_secret | varchar(255) | N    |      | 机构分配密钥                                      |
| name          | varchar(100) | N    |      | 机构描述                                          |
| redirect_url  | varchar(255) | N    |      | 机构地址                                          |
| type          | enum         | N    |      | UNION：合作机构；INNER：内部系统；EXTER：外部系统 |
| status        | tinyint(2)   | N    | 1    | 状态（1：可用；-1：不可用）                       |
| update_time   | bigint(20)   | N    | 0    | 更新时间戳，13位                                  |
| create_time   | bigint(20)   | N    | 0    | 生成时间戳，13位                                  |

##### 账户授权表（mc_account_bind）

| 属性            | 类型         | 空   | 默认 | 备注                          |
| --------------- | ------------ | ---- | ---- | ----------------------------- |
| id              | bigint(20)   | N    | 自增 | 主键                          |
| account_id      | bigint(20)   | N    |      | 用户标记                      |
| client_code     | varchar(50)  | N    |      | 机构标识                      |
| authority       | varchar(255) | N    |      | 权限，多个用英文逗号隔开      |
| auth_start_time | bigint(20)   | N    | 0    | 授权时间                      |
| auth_stop_time  | bigint(20)   | N    | 0    | 授权失效时间，写0代表永久授权 |
| update_time     | bigint(20)   | N    | 0    | 更新时间戳，13位              |
| create_time     | bigint(20)   | N    | 0    | 生成时间戳，13位              |

> 1. 内部用户访问内部系统需要管理者授权
> 2. 外部用户登录合作机构需要用户授权

#### SQL

```sql

```

