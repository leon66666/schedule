package com.hoomsun.schedule.dao;

import com.hoomsun.common.TaskStatus;
import com.hoomsun.model.TaskTimer;
import com.hoomsun.page.Page;
import com.hoomsun.vo.TaskTimerQuery;
import javacommon.base.BaseHibernateDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import zhongqiu.javautils.UtilTools;

import java.util.List;

@Repository
public class TaskTimerDAO extends BaseHibernateDao<TaskTimer, Integer> {
    private static final Log log = LogFactory.getLog(TaskTimerDAO.class);
    private TaskTimer taskTemer;

    @Autowired
    public void init(SessionFactory sessionFactory) {
        setSessionFactory(sessionFactory);
    }

    public TaskTimer getTaskTemer() {
        return taskTemer;
    }

    public void setTaskTemer(TaskTimer taskTemer) {
        this.taskTemer = taskTemer;
    }

    public Page<?> findPage(TaskTimerQuery query) {
        //XsqlBuilder syntax,please see http://code.google.com/p/rapid-xsqlbuilder
        // [column]为字符串拼接, {column}为使用占位符. [column]为使用字符串拼接,如username='[username]',偷懒时可以使用字符串拼接
        // [column] 为PageRequest的属性

        String sql = "select t from TaskTimer t where 1=1 ";
        sql = sql
                + "/~ and t.taskName like '%[taskName]%' ~/"
                + "/~ and t.taskClass like '%[taskClass]%' ~/"
                + "/~ and t. isTiming; = {isTiming;} ~/"
                + "/~ and t.creater = {creater} ~/"
                + "/~ and t.createTime = {createTime} ~/"
                + "/~ and t.enable = {enable} ~/"
                + "/~ order by [sortColumns] ~/";

        //生成sql2的原因是为了不喜欢使用xsqlbuilder的同学，请修改生成器模板，删除本段的生成
        StringBuilder sql2 = new StringBuilder("select t from TaskTimer t where 1=1 ");
        if (UtilTools.isNullOrEmpty(query.getTaskName())) {
            sql2.append(" and  t.taskName like '%[taskName]%'");
        }

        return pageQuery(sql, query);
    }

    public String getTaskNameById(Integer id) {
        TaskTimer taskTimer = this.getById(id);
        log.debug(taskTimer.getTaskName());
        return !UtilTools.isNullOrEmpty(taskTimer) ? taskTimer.getTaskName() : "";
    }

    @Override
    public Class<TaskTimer> getEntityClass() {
        return TaskTimer.class;
    }

    public TaskStatus getRunning() {
        return TaskStatus.RUNNING;
    }

    public TaskStatus getStoping() {
        return TaskStatus.STOPPING;
    }

    /**
     * 根据状态获取某一状态的任务
     *
     * @param status 状态
     * @return 某一状态下的任务集合
     */
    @SuppressWarnings("unchecked")
    public List<TaskTimer> getTaskTimerByStatus(TaskStatus status) {
        return getHibernateTemplate().find("from TaskTimer t where t.taskStatus=?", status);
    }

    /**
     * 获取所有任务
     *
     * @return 所有任务的集合
     */
    public List<TaskTimer> getAllTaskTimers() {
        return getHibernateTemplate().find("from TaskTimer");
    }

    /**
     * 根据任务对应的类名获取任务实例
     *
     * @param className 任务对应类名
     * @return 任务实例
     */
    public TaskTimer getTaskTimerByClassName(String className) {

        List<TaskTimer> list = getHibernateTemplate().find("from TaskTimer t where t.taskClass=?", className);
        return UtilTools.isNullOrEmpty(list) ? null : list.get(0);
    }
}