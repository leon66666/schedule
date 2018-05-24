package com.hoomsun.schedule.dao;

import com.hoomsun.model.LoanStatusTaskDetail;
import com.hoomsun.vo.DetailStep;
import com.hoomsun.vo.LoanTaskDetailStatus;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LoanStatusTaskDetailBaseDao extends AbstractBaseDao<LoanStatusTaskDetail> {

    @Override
    protected Class<LoanStatusTaskDetail> getEntityClass() {
        return LoanStatusTaskDetail.class;
    }

    /**
     * 新增一条任务明细
     *
     * @param taskDetail
     * @return
     */
    public LoanStatusTaskDetail add(LoanStatusTaskDetail taskDetail) {
        if (taskDetail != null) {
            getHibernateTemplate().save(taskDetail);
        }
        return taskDetail;
    }

    public void updateLoanStatusTaskDetail(Integer taskDetailId, LoanTaskDetailStatus status, String remark, String returnMsg, String orderId, String serialNo) {
        LoanStatusTaskDetail taskDetail = get(taskDetailId);
        if (status != null) {
            taskDetail.setDetailStatus(status);
        }
        if (remark != null && !"".equals(remark)) {
            taskDetail.setRemark(remark);
        }
        if (returnMsg != null && !"".equals(returnMsg)) {
            taskDetail.setReturnMsg(returnMsg);
        }
        if (orderId != null) {
            taskDetail.setOrderId(orderId);
        }
        if (serialNo != null && !"".equals(serialNo)) {
            taskDetail.setSerialNo(serialNo);
        }
        taskDetail.setUpdateTime();
        getHibernateTemplate().update(taskDetail);
    }

    public void updateLoanStatusTaskDetail(Integer taskDetailId, LoanTaskDetailStatus status, String remark, String returnMsg) {
        updateLoanStatusTaskDetail(taskDetailId, status, remark, returnMsg, null, null);
    }

    public void updateLoanStatusTaskDetailNew(Integer taskDetailId, LoanTaskDetailStatus status, String remark, String returnMsg, String orderId, String serialNo) {
        LoanStatusTaskDetail taskDetail = get(taskDetailId);
        if (status != null) {
            taskDetail.setDetailStatus(status);
        }
        if (remark != null && !"".equals(remark)) {
            taskDetail.setRemark(remark);
        }
        if (returnMsg != null && !"".equals(returnMsg)) {
            taskDetail.setReturnMsg(returnMsg);
        }
        taskDetail.setOrderId(orderId);
        taskDetail.setSerialNo(serialNo);
        taskDetail.setUpdateTime();
        getHibernateTemplate().update(taskDetail);
    }

    /**
     * 根据任务明细状态查询任务明细列表
     *
     * @param status
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<LoanStatusTaskDetail> getLoanTaskDetailByStatus(LoanTaskDetailStatus... status) {
        if (status == null || status.length == 0) {
            throw new NullPointerException("LoanTaskDetailStatus is not null");
        }
        List<LoanStatusTaskDetail> result = null;
        StringBuilder sql = new StringBuilder("from LoanStatusTaskDetail where detailStatus in (");
        for (int i = 0; i < status.length; i++) {
            LoanTaskDetailStatus s = status[i];
            if (i == 0) {
                sql.append("'" + s.name() + "'");
            } else {
                sql.append(",'" + s.name() + "'");
            }
        }
        sql.append(")");
        result = getHibernateTemplate().find(sql.toString());

        return result;
    }

    /**
     * 根据任务明细类型和状态查询任务明细
     *
     * @param detailStep
     * @param status
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<LoanStatusTaskDetail> getLoanTaskDetailByStepAndStatus(DetailStep detailStep, LoanTaskDetailStatus... status) {
        if (detailStep == null) {
            throw new NullPointerException("DetailStep is not null");
        }
        if (status == null || status.length == 0) {
            throw new NullPointerException("LoanTaskDetailStatus is not null");
        }
        List<LoanStatusTaskDetail> result = null;
        StringBuilder sql = new StringBuilder("from LoanStatusTaskDetail where detailStatus in (");
        for (int i = 0; i < status.length; i++) {
            LoanTaskDetailStatus s = status[i];
            if (i == 0) {
                sql.append("'" + s.name() + "'");
            } else {
                sql.append(",'" + s.name() + "'");
            }
        }
        sql.append(")");
        sql.append(" and detailStep = ?");
        result = getHibernateTemplate().find(sql.toString(), detailStep);

        return result;
    }

    /**
     * 根据任务ID获取待处理的任务明细
     *
     * @param taskId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<LoanStatusTaskDetail> getUnResolvedLoanTaskDetailByTaskId(Integer taskId) {
        if (taskId == null) {
            throw new NullPointerException("TaskId is not null");
        }
        List<LoanStatusTaskDetail> result = null;
        StringBuilder sql = new StringBuilder("from LoanStatusTaskDetail where taskId = ? ");
        sql.append(" and detailStatus in('INIT', 'FAILURE')");
        result = getHibernateTemplate().find(sql.toString(), taskId);

        return result;
    }

    /**
     * 根据条件检索任务明细
     *
     * @param taskId
     * @param detailStep
     * @param status
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<LoanStatusTaskDetail> getLoanTaskDetailByConditions(Integer taskId, DetailStep detailStep, LoanTaskDetailStatus... status) {
        if (status == null || status.length == 0) {
            throw new NullPointerException("LoanTaskDetailStatus is not null");
        }
        List<LoanStatusTaskDetail> result = null;
        StringBuilder sql = new StringBuilder("from LoanStatusTaskDetail where detailStatus in (");
        for (int i = 0; i < status.length; i++) {
            LoanTaskDetailStatus s = status[i];
            if (i == 0) {
                sql.append("'" + s.name() + "'");
            } else {
                sql.append(",'" + s.name() + "'");
            }
        }
        sql.append(")");
        if (taskId != null) {
            sql.append(" and taskId = " + taskId.toString());
        }
        if (detailStep != null) {
            sql.append(" and detailStep = '" + detailStep.toString() + "'");
        }

        result = getHibernateTemplate().find(sql.toString());

        return result;
    }

    /**
     * 根据任务ID及当前步骤查询未完成的任务明细
     *
     * @param taskId
     * @param detailStep
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<LoanStatusTaskDetail> getUnSuccessLoanTaskDetails(Integer taskId, DetailStep detailStep) {
        StringBuilder hql = new StringBuilder("from LoanStatusTaskDetail where detailStatus in (:status)");
        if (taskId != null) {
            hql.append(" and taskId = " + taskId.toString());
        }
        if (detailStep != null) {
            hql.append(" and detailStep = '" + detailStep.toString() + "'");
        }

        return this.getSession().createQuery(hql.toString()).setParameterList("status", LoanTaskDetailStatus.UnSuccessSet.toArray()).list();
    }

    /**
     * 查询version版本号大于阀值的任务明细
     *
     * @param taskId
     * @param version
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<LoanStatusTaskDetail> getUnSuccessLoanTaskDetailByVersion(Integer taskId, int version) {
        StringBuilder hql = new StringBuilder("from LoanStatusTaskDetail where detailStatus in (:status)");
        if (taskId != null) {
            hql.append(" and taskId = " + taskId.toString());
        }
        hql.append(" and version > " + version);

        return this.getSession().createQuery(hql.toString()).setParameterList("status", LoanTaskDetailStatus.UnSuccessSet.toArray()).list();
    }
}
