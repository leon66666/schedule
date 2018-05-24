package wangzhongqiu.vo;

import java.util.EnumSet;

/**
 * 标任务明细状态
 */
public enum LoanTaskDetailStatus {
	/**
	 * 初始状态
	 */
	INIT{
		@Override
		public String toString() {
			return "初始状态";
		}
	},
	/**
	 * 存管银行处理中
	 */
	ESCROW_P{
		@Override
		public String toString() {
			return "存管银行处理中";
		}
	},
	/**
	 * 存管银行处理失败
	 */
	ESCROW_F{
		@Override
		public String toString() {
			return "存管银行处理失败";
		}
	},
	/**
	 * 存管银行处理成功
	 */
	ESCROW_S{
		@Override
		public String toString() {
			return "存管银行处理成功";
		}
	}
	,
	/**
	 * 存管银行未知状态
	 */
	ESCROW_R{
		@Override
		public String toString() {
			return "存管银行未知状态";
		}
	},
	/**
	 * 红小宝处理失败
	 */
	FAILURE{
		@Override
		public String toString() {
			return "红小宝处理失败";
		}
	},
	/**
	 * 红小宝处理成功
	 */
	SUCCESS{
		@Override
		public String toString() {
			return "红小宝处理成功";
		}
	};
	
	/**
	 * 未最终成功状态集
	 */
	public static final EnumSet<LoanTaskDetailStatus> UnSuccessSet = EnumSet.of(INIT, ESCROW_P, ESCROW_F, ESCROW_S, FAILURE);
	
	@Override
	public abstract String toString();
}
