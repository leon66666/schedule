/**
 * 
 * 
 */
package com.hoomsun.common;

import java.util.EnumSet;

/**
 * 
 */
public enum TaskStatus {

    /**
     * 开始运行
     */
    RUNNING() {

        @Override
        public String toString() {
            return "运行";
        }
    },
    /**
     * 结束运行
     */
    STOPPING() {

        @Override
        public String toString() {
            return "停止";
        }
    };

    // 成功借款的状态集
    public static final EnumSet<TaskStatus> TASK_STATUS = EnumSet.of(TaskStatus.RUNNING, TaskStatus.STOPPING);

    @Override
    public abstract String toString();

}
