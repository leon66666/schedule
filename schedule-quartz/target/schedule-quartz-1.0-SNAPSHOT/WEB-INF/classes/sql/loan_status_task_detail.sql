/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.30
Source Server Version : 50638
Source Host           : 192.168.1.30:3306
Source Database       : hoomxb32

Target Server Type    : MYSQL
Target Server Version : 50638
File Encoding         : 65001

Date: 2018-05-24 18:04:21
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for loan_status_task_detail
-- ----------------------------
DROP TABLE IF EXISTS `loan_status_task_detail`;
CREATE TABLE `loan_status_task_detail` (
  `task_detail_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '任务明细ID',
  `task_id` int(11) unsigned NOT NULL COMMENT '任务ID',
  `loan_id` int(11) unsigned NOT NULL COMMENT '标ID',
  `detail_step` int(11) unsigned NOT NULL COMMENT '任务明细执行步骤',
  `borrower_id` int(11) unsigned DEFAULT NULL COMMENT '融资人ID',
  `business_id` int(11) unsigned DEFAULT NULL COMMENT '涉及到的业务表主键',
  `user_id` int(11) unsigned DEFAULT NULL COMMENT '平台费用帐户',
  `fee_type` varchar(30) DEFAULT NULL COMMENT '平台收取的费用类型',
  `amount` decimal(16,2) DEFAULT '0.00' COMMENT '金额',
  `red_amt` decimal(16,2) DEFAULT '0.00' COMMENT '红包金额',
  `opt_type` varchar(32) NOT NULL COMMENT '任务类型',
  `detail_status` varchar(36) NOT NULL COMMENT '任务明细步骤状态: INIT(初始状态);CMBC_P(银行处理中);CMBC_F(银行处理失败);CMBC_S(银行处理成功);SUCCESS(处理成功);FAILURE(处理失败)',
  `order_id` varchar(32) DEFAULT '' COMMENT '调用接口订单号',
  `serial_no` varchar(32) DEFAULT '' COMMENT '接口返回的流水号',
  `return_msg` varchar(1000) DEFAULT '' COMMENT '返回信息,包括异常信息',
  `note` varchar(1000) DEFAULT '' COMMENT '资金操作日志',
  `remark` varchar(1000) DEFAULT '' COMMENT '备注',
  `version` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '版本戳',
  `create_time` datetime NOT NULL COMMENT '数据库创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '数据库更新时间',
  PRIMARY KEY (`task_detail_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8020 DEFAULT CHARSET=utf8 COMMENT='标放款和流标任务明细';
SET FOREIGN_KEY_CHECKS=1;
