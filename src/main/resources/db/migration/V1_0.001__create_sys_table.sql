-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `password` varchar(20) DEFAULT NULL,
  `sex` varchar(5) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `status` int(11) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;


INSERT INTO `user` VALUES ('1', 'admin', 'admin', '男', '028-123456', '1');
INSERT INTO `user` VALUES ('2', '123456', 'admin', '女', '123', '1');
INSERT INTO `user` VALUES ('3', 'b_admin', 'admin', '男', '13408504490', '1');

DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `url` varchar(255) NOT NULL,
  `method` varchar(10) NOT NULL,
  `order_num` float DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `user_resource`;
CREATE TABLE `user_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `resource_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;

INSERT INTO `resource` VALUES ('1', '用户管理', '/user/page', 'POST', '3', '1', null, null, '1');
INSERT INTO `resource` VALUES ('2', '添加用户', '/user', 'POST', null, '0', null, '1', '1');
INSERT INTO `resource` VALUES ('3', '修改用户', '/user', 'PUT', null, '0', null, '1', '1');
INSERT INTO `resource` VALUES ('4', '删除用户', '/user', 'DELETE', null, '0', null, '1', '1');
INSERT INTO `user_resource` VALUES ('1', '2', '1');
INSERT INTO `user_resource` VALUES ('2', '2', '2');
INSERT INTO `user_resource` VALUES ('3', '2', '3');
INSERT INTO `user_resource` VALUES ('4', '2', '4');

INSERT INTO `user_resource` VALUES ('5', '3', '1');
INSERT INTO `user_resource` VALUES ('6', '3', '2');
INSERT INTO `user_resource` VALUES ('7', '3', '3');
INSERT INTO `user_resource` VALUES ('8', '3', '4');