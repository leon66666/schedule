package com.hoomsun.schedule.dao;


import com.hoomsun.model.TaskLog;
import com.hoomsun.page.Page;
import com.hoomsun.vo.TaskLogQuery;
import javacommon.base.BaseHibernateDao;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * A data access object (DAO) providing persistence and search support for
 * TaskTimer entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * 
 */
@Repository
public class TaskLogDAO extends BaseHibernateDao<TaskLog, Integer> {
	private TaskLog taskLog; 
	@Autowired
    public void init(SessionFactory sessionFactory) {
        setSessionFactory(sessionFactory);
    }
    public TaskLog getTaskLog() {
        return taskLog;
    }


    public void setTaskLog(TaskLog taskLog) {
        this.taskLog = taskLog;
    }


    public Page<?> findPage(TaskLogQuery query) {
        //XsqlBuilder syntax,please see http://code.google.com/p/rapid-xsqlbuilder
        // [column]为字符串拼接, {column}为使用占位符. [column]为使用字符串拼接,如username='[username]',偷懒时可以使用字符串拼接 
        // [column] 为PageRequest的属性
        String sql = "select t,tt.taskName from TaskLog t,TaskTimer tt where t.taskId=tt.id "
                + "/~ and tt.taskName like '%[taskName]%' ~/"
                + "/~ and t.startTime = {startTime} ~/"
                + "/~ and t.operateType = {operateType} ~/"
                + "/~ and t.endTime = {endTime} ~/"
                + "/~ and t.version = {version} ~/"
                + "/~ order by [sortColumns] ~/";

        
        return pageQuery(sql,query);
    }
    @Override
    public Class<TaskLog> getEntityClass() {
        return TaskLog.class;
    }
}