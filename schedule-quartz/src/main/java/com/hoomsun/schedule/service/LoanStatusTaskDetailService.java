package com.hoomsun.schedule.service;

import com.hoomsun.model.LoanStatusTaskDetail;
import com.hoomsun.schedule.dao.LoanStatusTaskDetailBaseDao;
import com.hoomsun.vo.DetailStep;
import com.hoomsun.vo.LoanTaskDetailStatus;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class LoanStatusTaskDetailService {

    private static Log log = LogFactory.getLog(LoanStatusTaskDetailService.class);
    @Autowired
    private LoanStatusTaskDetailBaseDao taskDetailBaseDao;

    public LoanStatusTaskDetail getById(Integer taskDetailId) {
        return taskDetailBaseDao.get(taskDetailId);
    }

    public void update(LoanStatusTaskDetail taskDetail) {
        taskDetailBaseDao.update(taskDetail);
    }

    public List<LoanStatusTaskDetail> getUnSuccessLoanTaskDetails(Integer taskId, DetailStep detailStep) {
        return taskDetailBaseDao.getUnSuccessLoanTaskDetails(taskId, detailStep);
    }

    public List<LoanStatusTaskDetail> getUnSuccessLoanTaskDetailByVersion(Integer taskId, int version) {
        return taskDetailBaseDao.getUnSuccessLoanTaskDetailByVersion(taskId, version);
    }

    public List<LoanStatusTaskDetail> getLoanTaskDetailByConditions(Integer taskId, DetailStep detailStep, LoanTaskDetailStatus... status) {
        return taskDetailBaseDao.getLoanTaskDetailByConditions(taskId, detailStep, status);
    }

    public List<LoanStatusTaskDetail> getUnResolvedLoanTaskDetailByTaskId(Integer taskId) {
        return taskDetailBaseDao.getUnResolvedLoanTaskDetailByTaskId(taskId);
    }

    public void updateLoanStatusTaskDetail(Integer taskDetailId, LoanTaskDetailStatus status, String remark, String returnMsg, String orderId, String serialNo) {
        taskDetailBaseDao.updateLoanStatusTaskDetail(taskDetailId, status, remark, returnMsg, orderId, serialNo);
    }

    public void updateLoanStatusTaskDetail(Integer taskDetailId, LoanTaskDetailStatus status, String remark, String returnMsg) {
        updateLoanStatusTaskDetail(taskDetailId, status, remark, returnMsg, null, null);
    }

    public void updateLoanStatusTaskDetailNew(Integer taskDetailId, LoanTaskDetailStatus status, String remark, String returnMsg, String orderId, String serialNo) {
        taskDetailBaseDao.updateLoanStatusTaskDetailNew(taskDetailId, status, remark, returnMsg, orderId, serialNo);
    }
}
