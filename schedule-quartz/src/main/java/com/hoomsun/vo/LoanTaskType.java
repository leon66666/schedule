package com.hoomsun.vo;

/**
 * 标任务类型
 */
public enum LoanTaskType {
	/**
	 * 放款任务
	 */
	START{
		@Override
		public String toString() {
			return "放款任务";
		}
	},
	/**
	 *	提现任务
	 */
	CASH{
		@Override
		public String toString() {
			return "提现任务";
		}
	},
	/**
	 * 流标任务
	 */
	FAIL{
		@Override
		public String toString() {
			return "流标任务";
		}
	},
	/**
	 * 结标任务
	 */
	CLOSED{
		@Override
		public String toString() {
			return "结标任务";
		}
	},
	/**
	 * 逾期任务
	 */
	OVER_DUE{
		@Override
		public String toString() {
			return "逾期任务";
		}
	},
	/**
	 * 还款信息推送任务
	 */
	REPAY{
		@Override
		public String toString() {
			return "还款信息推送任务";
		}
	},
	/**
	 * 坏帐垫付信息推送任务
	 */
	BAD_REPAY{
		@Override
		public String toString() {
			return "坏帐垫付信息推送任务";
		}
	},
	/**
	 * 资产对帐表删除任务
	 */
	DEL_SHARE{
		@Override
		public String toString() {
			return "资产对帐表删除任务";
		}
	},
	/**
	 * 角色信息推送任务
	 */
	ROLE_INFO{
		@Override
		public String toString() {
			return "角色信息推送任务";
		}
	};
	
	@Override
	public abstract String toString();
}
