
-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(126) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `sex` int(11) NULL DEFAULT NULL COMMENT '性别 0-男 1-女',
  `actual_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `email` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'email',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
  `post_id` int(11) NULL DEFAULT NULL COMMENT '职务ID',
  `post_name` varchar(64) NULL DEFAULT NULL COMMENT '职务名称',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `status` int(2) NULL DEFAULT 1 COMMENT '状态，0-禁用，1-启用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 88 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Compact;


-- ----------------------------
-- Table structure for menu_right
-- ----------------------------
DROP TABLE IF EXISTS `menu_right`;
CREATE TABLE `menu_right`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(11) NULL DEFAULT NULL COMMENT '父节点id',
  `module` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '系统名称',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '节点名称',
  `method` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '方法（get、post等）',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '访问地址',
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标样式',
  `status` int(1) NULL DEFAULT NULL COMMENT '状态，0-禁用，1-启用',
  `grades` int(11) NULL DEFAULT NULL COMMENT '层级',
  `seq` int(11) NULL DEFAULT NULL COMMENT '排序号',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 288 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单权限表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `parent_id` bigint(11) NULL DEFAULT NULL COMMENT '父节点id',
  `role_index` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所有父级节点和当前节点',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称描述',
  `role_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '系统的角色名称',
  `seq` int(11) NULL DEFAULT NULL COMMENT '角色排序',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 61 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `role_id` bigint(11) NOT NULL COMMENT '角色id',
  `user_id` bigint(11) NOT NULL COMMENT '用户id'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for role_right
-- ----------------------------
DROP TABLE IF EXISTS `role_right`;
CREATE TABLE `role_right`  (
  `role_id` bigint(11) NOT NULL COMMENT '角色id',
  `menu_id` bigint(11) NOT NULL COMMENT '权限id'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;


-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `log_type` int(11) NULL DEFAULT NULL COMMENT '日志类型  根据系统模块来定义日志类型，采用一级菜单的ID，0为默认值',
  `operation` int(11) NULL DEFAULT NULL COMMENT '操作类型： 添加-1 删除-2 更新-3 查看-4 登录-5 登出-6 导入-7 导出-8 0为默认值',
  `log_user` bigint(11) NULL DEFAULT NULL COMMENT '操作人员ID',
  `log_ip` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '访问IP',
  `log_method` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求方法',
  `log_params` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '请求参数',
  `log_desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日志描述',
  `log_time` bigint(32) NULL DEFAULT NULL COMMENT '响应时间',
  `exception_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '异常码',
  `exception_detail` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '异常描述',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1249 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统日志表' ROW_FORMAT = Compact;

-- ----------------------------
-- 日期：2019-4-26 10:14:59
-- oauth2 客户端信息明细表
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details`  (
  `client_id` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `resource_ids` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `client_secret` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `scope` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `authorized_grant_types` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `web_server_redirect_uri` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `authorities` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `access_token_validity` int(11) NULL DEFAULT NULL,
  `refresh_token_validity` int(11) NULL DEFAULT NULL,
  `additional_information` varchar(4096) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `autoapprove` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;


-------------------------初始化数据--------------------------

INSERT INTO `oauth_client_details` VALUES ('janche', 'sso-oauth2-server', '$2a$10$4ZpXaJyS4/oLUCsIDz1kCO7T7g9LtVq8hD1E0PJQwVfvI48U.RF7.', 'all', 'authorization_code, refresh_token', 'http://localhost:9999/login', NULL, 7200, 60, NULL, 'true', '2019-07-18 10:26:29');

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', '$2a$10$4ZpXaJyS4/oLUCsIDz1kCO7T7g9LtVq8hD1E0PJQwVfvI48U.RF7.', '李四', 0, '18382100123', '5625@qq.com', '航利中心', 4, '总经理', 1, '2018-09-09 15:03:18', '2019-01-15 10:06:47');
INSERT INTO `user` VALUES (2, 'test', '$2a$10$4ZpXaJyS4/oLUCsIDz1kCO7T7g9LtVq8hD1E0PJQwVfvI48U.RF7.', '张三', 1, '13696002518', 'sdf435@libii.com', '天府新谷', 2, '部门经理', 1, '2019-07-19 11:00:24', NULL);

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 1, '1', 'ROLE_超级管理员', '超级管理员', '超级管理员', NULL, '2018-11-21 16:58:11', '2019-01-10 13:19:59');
INSERT INTO `role` VALUES (2, 1, '1,2', 'ROLE_系统管理员', '系统管理员', '系统管理员', NULL, '2018-11-21 16:58:07', '2019-01-21 17:22:34');
INSERT INTO `role` VALUES (3, 1, '1,3', 'ROLE_普通用户', '普通用户', '普通用户', NULL, '2019-07-19 11:24:54', NULL);

-- ----------------------------
-- Records of menu_right
-- ----------------------------
INSERT INTO `menu_right` VALUES (1, 0, 0, '首页', '', '/home', '', 1, NULL, 1, '2019-01-09 17:09:08', NULL);
INSERT INTO `menu_right` VALUES (2, 1, 2, '权限管理系统', '', '/permission', NULL, 1, NULL, 1, '2019-07-18 16:20:23', NULL);
INSERT INTO `menu_right` VALUES (20, 2, 2, '用户管理', '', '/permission/userManagement', '', 2, NULL, 1, '2019-01-09 17:09:08', NULL);
INSERT INTO `menu_right` VALUES (21, 2, 2, '角色管理', '', '/permission/roleManagement', '', 2, NULL, 1, '2019-01-09 17:09:08', NULL);
INSERT INTO `menu_right` VALUES (22, 2, 2, '权限管理', '', '/permission/rightManagement', NULL, 2, NULL, 1, '2019-07-18 16:23:22', NULL);
INSERT INTO `menu_right` VALUES (23, 2, 2, '日志管理', '', '/permission/logManagement', '', 2, NULL, 1, '2019-01-09 17:09:08', NULL);
INSERT INTO `menu_right` VALUES (100, 1, 1, '获取当前登录用户', 'POST', '/user/sso', '', 3, NULL, 1, '2019-01-09 17:09:08', NULL);
INSERT INTO `menu_right` VALUES (101, 20, 2, '获取在线用户数', 'GET', '/user/online/userNum', '', 3, NULL, 1, '2019-01-09 17:09:08', NULL);

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 1);
INSERT INTO `user_role` VALUES (2, 2);

-- ----------------------------
-- Records of role_right
-- ----------------------------
INSERT INTO `role_right` VALUES (2, 1);
INSERT INTO `role_right` VALUES (2, 2);
INSERT INTO `role_right` VALUES (2, 20);
INSERT INTO `role_right` VALUES (2, 21);
INSERT INTO `role_right` VALUES (2, 22);
INSERT INTO `role_right` VALUES (2, 23);
INSERT INTO `role_right` VALUES (2, 100);
INSERT INTO `role_right` VALUES (2, 101);