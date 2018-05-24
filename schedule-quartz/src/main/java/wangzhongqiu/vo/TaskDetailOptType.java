package wangzhongqiu.vo;

/**
 * 任务明细操作类型
 * 放款
 * 普通投标确认
 * 计划投标确认
 * 平台收取费用
 * 解冻融资人资金
 * 完成放款
 * 流标
 * 普通投标撤销
 * 计划投标撤销
 */
public enum TaskDetailOptType {
    /**
     * 投标确认
     */
    BID_HengFengConfirm {
        @Override
        public String toString() {
            return "恒丰存管投标确认";
        }
    },
    /**
     * 更改标的状态
     */
    UPDATE_LOAN {
        @Override
        public String toString() {
            return "更改标的状态";
        }
    },
    /**
     * 投标确认
     */
    BID_CFM {
        @Override
        public String toString() {
            return "投标确认";
        }
    },
    /**
     * 平台收取各项费用
     */
    FEE {
        @Override
        public String toString() {
            return "平台收取费用";
        }
    },
    /**
     * 解冻融资人资金
     */
    UNFREEZE {
        @Override
        public String toString() {
            return "解冻融资人资金";
        }
    },
    /**
     * 完成放款
     */
    FINISH_APPROVE {
        @Override
        public String toString() {
            return "完成放款";
        }
    },
    /**
     * 撤销投标,恒丰确认
     */
    BID_CANCEL_HengFengConfirm {
        @Override
        public String toString() {
            return "恒丰存管流标确认";
        }
    },
    /**
     * 撤销投标
     */
    BID_CANCEL {
        @Override
        public String toString() {
            return "撤销投标";
        }
    },
    /**
     * 解冻优惠券
     */
    UNFREEZE_COUPON {
        @Override
        public String toString() {
            return "解冻优惠券";
        }
    },
    /**
     * 完成流标
     */
    FINISH_CANCEL {
        @Override
        public String toString() {
            return "完成流标";
        }
    },
    /**
     * 完成渠道流标
     */
    FINISH_API_CANCEL {
        @Override
        public String toString() {
            return "完成渠道流标";
        }
    },
    /**
     * 完成提现
     */
    FINISH_CASH {
        @Override
        public String toString() {
            return "完成提现";
        }
    },
    /**
     * 更新存管信息
     */
    UPDATE_INFO {
        @Override
        public String toString() {
            return "更新存管信息";
        }
    },
    /**
     * 冻结借款人要收取的费用
     */
    FREEZE_FEE {
        @Override
        public String toString() {
            return "冻结借款人要收取的费用";
        }
    },
    /**
     * 提现
     */
    WITHDRAW_CASH {
        @Override
        public String toString() {
            return "提现";
        }
    };

    @Override
    public abstract String toString();
}
