package com.hoomsun.vo;

/**
 * 标任务状态
 */
public enum LoanTaskStatus {
	/**
	 * 正在处理中
	 */
	PROCESSING{
		@Override
		public String toString() {
			return "正在处理中";
		}
	},
	/**
	 * 任务失败
	 */
	FAILURE{
		@Override
		public String toString() {
			return "任务失败";
		}
	},
	/**
	 * 任务成功
	 */
	SUCCESS{
		@Override
		public String toString() {
			return "任务成功";
		}
	};
	
	@Override
	public abstract String toString();
}
