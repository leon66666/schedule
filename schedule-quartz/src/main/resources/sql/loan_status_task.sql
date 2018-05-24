/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.30
Source Server Version : 50638
Source Host           : 192.168.1.30:3306
Source Database       : hoomxb32

Target Server Type    : MYSQL
Target Server Version : 50638
File Encoding         : 65001

Date: 2018-05-24 18:04:13
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for loan_status_task
-- ----------------------------
DROP TABLE IF EXISTS `loan_status_task`;
CREATE TABLE `loan_status_task` (
  `task_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '任务ID',
  `loan_id` int(11) unsigned NOT NULL COMMENT '标ID',
  `borrower_id` int(11) unsigned NOT NULL COMMENT '借款人用户ID',
  `type` char(10) NOT NULL COMMENT '任务类型 放标任务:START；流标任务:FAIL',
  `end_step` int(11) unsigned NOT NULL COMMENT '任务结束步骤，即说明任务共几步',
  `status` varchar(10) NOT NULL COMMENT '任务状态: PROCESSING(正在处理中);FAILURE(任务失败);SUCCESS(任务成功)',
  `remark` varchar(1000) DEFAULT '' COMMENT '备注',
  `version` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '版本戳',
  `create_time` datetime NOT NULL COMMENT '数据库创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '数据库更新时间',
  PRIMARY KEY (`task_id`)
) ENGINE=InnoDB AUTO_INCREMENT=25738 DEFAULT CHARSET=utf8 COMMENT='放款和流标任务表';
SET FOREIGN_KEY_CHECKS=1;
