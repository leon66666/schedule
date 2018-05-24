package wangzhongqiu.schedule.dao;

import wangzhongqiu.model.LoanStatusTask;
import wangzhongqiu.vo.LoanTaskStatus;
import wangzhongqiu.vo.LoanTaskType;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.util.EnumSet;
import java.util.List;

@Repository
public class LoanStatusTaskBaseDao extends AbstractBaseDao<LoanStatusTask> {

    @Override
    protected Class<LoanStatusTask> getEntityClass() {
        return LoanStatusTask.class;
    }

    /**
     * 根据任务状态查询任务列表
     *
     * @param status
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<LoanStatusTask> getLoanTaskByStatus(LoanTaskStatus... status) {
        if (status == null || status.length == 0) {
            throw new NullPointerException("LoanTaskStatus is not null");
        }

        String hql = "from LoanStatusTask where status in (:status)";
        return this.getSession().createQuery(hql).setParameterList("status", status).list();
    }

    @SuppressWarnings("unchecked")
    public List<LoanStatusTask> getLoanTaskByStatusAndType(EnumSet<LoanTaskType> typeSet, LoanTaskStatus... status) {
        if (status == null || status.length == 0) {
            throw new NullPointerException("LoanTaskStatus is not null");
        }

        if (typeSet == null || typeSet.size() == 0) {
            throw new NullPointerException("LoanTaskType is not null");
        }

        String hql = "from LoanStatusTask where status in (:status) and type in (:types)";
        return this.getSession().createQuery(hql).setParameterList("status", status).setParameterList("types", typeSet.toArray()).list();
    }

    @SuppressWarnings("unchecked")
    public List<LoanStatusTask> getLoanTaskByLoanIdAndType(Integer loanId, LoanTaskType type) {
        String hql = "from LoanStatusTask where loanId=:loanId and type=:type";
        return this.getSession().createQuery(hql).setParameter("loanId", loanId).setParameter("type", type).list();
    }

    /**
     * 新增一条任务，并返回主键
     *
     * @param task
     * @return
     */
    public LoanStatusTask add(LoanStatusTask task) {
        if (task != null) {
            getHibernateTemplate().save(task);
        }
        return task;
    }

    public void updateLoanStatusTask(Integer taskId, LoanTaskStatus status, String remark) {
        LoanStatusTask task = get(taskId);
        if (status != null) {
            task.setStatus(status);
        }
        if (remark != null && !"".equals(remark)) {
            task.setRemark(remark);
        }
        getHibernateTemplate().update(task);
    }

    public LoanStatusTask updateInsert(LoanStatusTask task) {
        List<LoanStatusTask> taskList = getLoanTaskByLoanIdAndType(task.getLoanId(), task.getType());
        if (taskList != null && taskList.size() > 0) {
            LoanStatusTask newTask = taskList.get(0);
            task.setTaskId(newTask.getTaskId());
            BeanUtils.copyProperties(task, newTask);
            update(newTask);
            return newTask;
        } else {
            return add(task);
        }
    }

    /**
     * 根据loanId查询流标原因
     *
     * @param loanId
     * @return
     */
    public LoanStatusTask findFailReasonByLoanId(Integer loanId) {
        String hql = "from LoanStatusTask where loanId=:loanId";
        return (LoanStatusTask) this.getSession().createQuery(hql).setParameter("loanId", loanId).uniqueResult();
    }

}
