/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50550
Source Host           : localhost:3306
Source Database       : financehelper

Target Server Type    : MYSQL
Target Server Version : 50550
File Encoding         : 65001

Date: 2018-05-22 16:46:11
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for task_timer_param
-- ----------------------------
DROP TABLE IF EXISTS `task_timer_param`;
CREATE TABLE `task_timer_param` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '参数编号，自增',
  `task_id` int(11) DEFAULT NULL COMMENT '任务id',
  `param_key` varchar(100) DEFAULT NULL COMMENT '参数key',
  `param_value` varchar(100) DEFAULT NULL COMMENT '参数对应value',
  `display_name` varchar(150) DEFAULT NULL COMMENT '参数页面显示名字',
  `task_param_desc` varchar(255) DEFAULT NULL COMMENT '参数描述',
  `creater` varchar(100) DEFAULT NULL COMMENT '参数创建人',
  `create_time` datetime DEFAULT NULL COMMENT '参数创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_task_timer_param_task_id` (`task_id`)
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=utf8 COMMENT='定时任务系统运行参数';

-- ----------------------------
-- Records of task_timer_param
-- ----------------------------
INSERT INTO `task_timer_param` VALUES ('1', '1', 'timingDate', '11:00,13:00,15:00,17:00', null, '', 'admin', '2017-11-15 18:03:09');
INSERT INTO `task_timer_param` VALUES ('2', '2', 'intervalDate', '10000', null, '间隔10秒', 'admin', '2017-07-18 11:32:01');
INSERT INTO `task_timer_param` VALUES ('3', '3', 'delayDate', '600000', '', '间隔10分钟', 'admin', '2015-10-27 19:38:21');
SET FOREIGN_KEY_CHECKS=1;
