/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50550
Source Host           : localhost:3306
Source Database       : financehelper

Target Server Type    : MYSQL
Target Server Version : 50550
File Encoding         : 65001

Date: 2018-05-22 16:46:02
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for task_timer
-- ----------------------------
DROP TABLE IF EXISTS `task_timer`;
CREATE TABLE `task_timer` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '任务编号，自增',
  `task_name` varchar(100) DEFAULT NULL COMMENT '任务名称',
  `task_class` varchar(150) NOT NULL COMMENT '任务指向类',
  `task_status` varchar(50) DEFAULT NULL COMMENT '任务状态',
  `is_timing` tinyint(1) DEFAULT '0' COMMENT '是否定时任务',
  `enable` tinyint(1) DEFAULT '1' COMMENT '是否可用',
  `server_ip` varchar(100) DEFAULT NULL COMMENT '服务器ip',
  `version` varchar(50) DEFAULT NULL COMMENT '版本号',
  `task_desc` varchar(255) DEFAULT NULL COMMENT '任务描述',
  `creater` varchar(50) DEFAULT NULL COMMENT '任务创建者',
  `create_time` datetime DEFAULT NULL COMMENT '任务创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=123 DEFAULT CHARSET=utf8 COMMENT='定时任务系统';

-- ----------------------------
-- Records of task_timer
-- ----------------------------
INSERT INTO `task_timer` VALUES ('1', '放款流标任务', 'startAndFailLoanJob', 'RUNNING', '0', '1', null, 'master', '', 'admin', '2016-01-05 15:56:58');
INSERT INTO `task_timer` VALUES ('2', '自动加入还款平台任务', 'autoAddRepayJob', 'RUNNING', '1', '1', null, 'master', '', 'admin', '2017-05-26 20:18:37');
INSERT INTO `task_timer` VALUES ('3', '批量充值还款重试任务', 'borrowRepayBatchJob', 'RUNNING', '0', '1', null, 'master', '', 'admin', null);
SET FOREIGN_KEY_CHECKS=1;
