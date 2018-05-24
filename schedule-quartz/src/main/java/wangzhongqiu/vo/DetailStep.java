package wangzhongqiu.vo;

/**
 * 标任务明细步骤
 */
public enum DetailStep {
    /**
     * 不使用，保证序号跟步骤对应
     */
    ZERO {
        @Override
        public String toString() {
            return "0";
        }

        @Override
        public DetailStep getNextStep() {
            return null;
        }
    },
    /**
     * 步骤1
     */
    ONE {
        @Override
        public String toString() {
            return "1";
        }

        @Override
        public DetailStep getNextStep() {
            return TWO;
        }
    },
    /**
     * 步骤2
     */
    TWO {
        @Override
        public String toString() {
            return "2";
        }

        @Override
        public DetailStep getNextStep() {
            return THREE;
        }
    },
    /**
     * 步骤3
     */
    THREE {
        @Override
        public String toString() {
            return "3";
        }

        @Override
        public DetailStep getNextStep() {
            return FOUR;
        }
    },
    /**
     * 步骤4
     */
    FOUR {
        @Override
        public String toString() {
            return "4";
        }

        @Override
        public DetailStep getNextStep() {
            return FIVE;
        }
    },
    /**
     * 步骤5
     */
    FIVE {
        @Override
        public String toString() {
            return "5";
        }

        @Override
        public DetailStep getNextStep() {
            return SIX;
        }
    },
    /**
     * 步骤6
     */
    SIX {
        @Override
        public String toString() {
            return "6";
        }

        @Override
        public DetailStep getNextStep() {
            return SEVEN;
        }
    },
    /**
     * 步骤7
     */
    SEVEN {
        @Override
        public String toString() {
            return "7";
        }

        @Override
        public DetailStep getNextStep() {
            return EIGHT;
        }
    },
    /**
     * 步骤8
     */
    EIGHT {
        @Override
        public String toString() {
            return "8";
        }

        @Override
        public DetailStep getNextStep() {
            return null;
        }
    };

    @Override
    public abstract String toString();

    /**
     * 获取任务下一步
     */
    public abstract DetailStep getNextStep();
}
