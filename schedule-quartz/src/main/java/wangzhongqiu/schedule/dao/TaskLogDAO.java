package wangzhongqiu.schedule.dao;

import wangzhongqiu.model.TaskLog;
import wangzhongqiu.page.Page;
import wangzhongqiu.vo.TaskLogQuery;
import javacommon.base.BaseHibernateDao;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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