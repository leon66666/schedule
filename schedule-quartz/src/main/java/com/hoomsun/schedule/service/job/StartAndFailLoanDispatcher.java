package com.hoomsun.schedule.service.job;

import com.hoomsun.model.LoanStatusTask;
import com.hoomsun.model.LoanStatusTaskDetail;
import com.hoomsun.schedule.service.LoanStatusTaskDetailService;
import com.hoomsun.schedule.service.LoanStatusTaskService;
import com.hoomsun.vo.DetailStep;
import com.hoomsun.vo.LoanTaskStatus;
import com.hoomsun.vo.LoanTaskType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 流标放款任务分发
 * 此类不带事务
 */
@Service
public class StartAndFailLoanDispatcher {

    private static Log log = LogFactory.getLog(StartAndFailLoanDispatcher.class);

    /**
     * version阀值，当大于此值时将Task主任务更新为失败
     */
    private final int THRESHOLD_VERSION = 20;

    @Autowired
    private LoanStatusTaskService taskService;
    @Autowired
    private LoanStatusTaskDetailService taskDetailService;

    /**
     * 处理任务
     */
    public void dealTask(LoanStatusTask task) {
        if (isFailedTask(task.getTaskId())) {
            return;
        }
        //从第一步开始递归执行
        dealTaskByStep(task.getTaskId(), DetailStep.ONE, task.getEndStep(), task.getType());
    }

    /**
     * 按步骤递归处理未处理的任务明细
     *
     * @param taskId      主任务ID
     * @param currentStep 当前任务步骤
     * @param endStep     结束任务步骤
     */
    public void dealTaskByStep(Integer taskId, DetailStep currentStep, DetailStep endStep, LoanTaskType type) {
        List<LoanStatusTaskDetail> list = taskDetailService.getUnSuccessLoanTaskDetails(taskId, currentStep);
        if (!CollectionUtils.isEmpty(list)) {
            for (LoanStatusTaskDetail detail : list) {
                if (LoanTaskType.START == type) {
                    log.info("处理满标任务");
                } else if (LoanTaskType.FAIL == type) {
                    log.info("处理流标任务");
                }
                if (LoanTaskType.CASH == type) {
                    log.info("处理提现任务");
                }
            }
        }
        List<LoanStatusTaskDetail> unResolved = taskDetailService.getUnSuccessLoanTaskDetails(taskId, currentStep);
        if (CollectionUtils.isEmpty(unResolved) && currentStep.getNextStep() != null && currentStep != endStep) {
            dealTaskByStep(taskId, currentStep.getNextStep(), endStep, type);
        }
    }

    /**
     * 任务是否失败任务
     *
     * @param taskId
     * @return
     */
    public boolean isFailedTask(Integer taskId) {
        List<LoanStatusTaskDetail> result = taskDetailService.getUnSuccessLoanTaskDetailByVersion(taskId, THRESHOLD_VERSION);
        if (CollectionUtils.isEmpty(result)) {
            return false;
        } else {
            LoanStatusTask task = taskService.getLoanStatusTaskById(taskId);
            task.setStatus(LoanTaskStatus.FAILURE);
            taskService.update(task);
            return true;
        }
    }
}
