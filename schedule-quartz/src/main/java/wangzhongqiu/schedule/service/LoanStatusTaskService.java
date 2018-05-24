package wangzhongqiu.schedule.service;


import wangzhongqiu.model.LoanStatusTask;
import wangzhongqiu.schedule.dao.LoanStatusTaskBaseDao;
import wangzhongqiu.vo.LoanTaskStatus;
import wangzhongqiu.vo.LoanTaskType;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.EnumSet;
import java.util.List;


/**
 * 标状态相关任务服务
 */
@Service
@Transactional
public class LoanStatusTaskService {
    private Logger log = Logger.getLogger(getClass());

    @Autowired
    private LoanStatusTaskBaseDao taskDao;

    public LoanStatusTask getLoanStatusTaskById(Integer taskId) {
        return taskDao.get(taskId);
    }

    public void update(LoanStatusTask task) {
        taskDao.update(task);
    }

    public List<LoanStatusTask> getLoanTaskByStatusAndType(EnumSet<LoanTaskType> typeSet, LoanTaskStatus... status) {
        return taskDao.getLoanTaskByStatusAndType(typeSet, status);
    }

    public void updateLoanStatusTask(Integer taskId, LoanTaskStatus status, String remark) {
        taskDao.updateLoanStatusTask(taskId, status, remark);
    }
}
