package wangzhongqiu.vo;



public enum FeeType {

    /**
     * 分成费
     */
	PartnerFee() {

        @Override
        public String toString() {
            return "分成费";
        }
    },
    /**
     * 交易费
     */
    ServiceFee() {

        @Override
        public String toString() {
            return "交易费";
        }
    },
    /**
     * 服务费
     */
    GuaranteeFee() {

        @Override
        public String toString() {
            return "服务费";
        }
    },
    /**
     * 实地认证费
     */
    FieldAuditRecharge() {

        @Override
        public String toString() {
            return "实地认证费";
        }
    },
    /**
     * 委托代查费用
     */
    EntrustRecharge() {

        @Override
        public String toString() {
            return "委托代查费用";
        }
    };

    @Override
    public abstract String toString();
}
