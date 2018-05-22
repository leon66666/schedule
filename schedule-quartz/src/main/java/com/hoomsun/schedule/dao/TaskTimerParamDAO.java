package com.hoomsun.schedule.dao;

import com.hoomsun.common.Constants;
import com.hoomsun.model.TaskTimerParam;
import com.hoomsun.page.Page;
import com.hoomsun.util.UtilTools;
import com.hoomsun.vo.TaskTimerParamQuery;
import javacommon.base.BaseHibernateDao;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * A data access object (DAO) providing persistence and search support for
 * TaskTimerParam entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * 
 */
@Repository
public class TaskTimerParamDAO extends BaseHibernateDao<TaskTimerParam, Integer> {
    private TaskTimerParam taskTimerParam; 
    @Autowired
    public void init(SessionFactory sessionFactory) {
        setSessionFactory(sessionFactory);
    }
   public TaskTimerParam getTaskTimerParamr() {
        return taskTimerParam;
    }
    public void setTaskTimerParam(TaskTimerParam taskTimerParam) {
        this.taskTimerParam = taskTimerParam;
    }

    public Page<?> findPage(TaskTimerParamQuery query) {
        //XsqlBuilder syntax,please see http://code.google.com/p/rapid-xsqlbuilder
        // [column]为字符串拼接, {column}为使用占位符. [column]为使用字符串拼接,如username='[username]',偷懒时可以使用字符串拼接 
        // [column] 为PageRequest的属性
        String sql = "select t from TaskTimerParam t where 1=1 "
                + "/~ and t.taskId like {taskId} ~/"
                + "/~ and t.paramKey like '%[paramKey]%' ~/"
                + "/~ and t.displayName like '%[displayName]%' ~/"
                + "/~ and t.creater = {creater} ~/"
                + "/~ order by [sortColumns] ~/";

        
        return pageQuery(sql,query);
    }
    @SuppressWarnings("unchecked")
    public void deleteByTaskId(Integer taskId){
        List<TaskTimerParam> list=getHibernateTemplate().find("from TaskTimerParam t where t.taskId=?",taskId);
        for(TaskTimerParam taskTimerParam:list){
            getHibernateTemplate().delete(taskTimerParam);
        }
//        getSession().createQuery(hql).setParameter(0, taskId);
        
    }
    /**
     * 根据任务id获取任务参数
     * @param taskId 任务id
     */
    @SuppressWarnings("unchecked")
    public List<Map<String,String>> findParamByTaskId(Integer taskId){
        return getHibernateTemplate().find("select new map(t.paramKey as key,t.paramValue as value) from TaskTimerParam t where t.taskId=? ",taskId);
    }
    /**
     * 根据任务id获取任务参数
     * @param className 类名称
     */
    @SuppressWarnings("unchecked")
    public List<Map<String,String>> findParamByClassName(String className){
        return getHibernateTemplate().find("select new map(t.paramKey as key,t.paramValue as value) from TaskTimerParam t,TaskTimer tt where t.taskId=tt.id and tt.taskClass=? ",className);
    }
    /**
     * 根据任务id获取初始任务参数
     * @param taskId 任务id
     */
    @SuppressWarnings("unchecked")
    public List<Map<String,String>> findInitParamByTaskId(Integer taskId){
        String sql="select new map(t.paramKey as key,t.paramValue as value) from TaskTimerParam t where t.taskId=? and( t.paramKey=? or t.paramKey=? )";
      Object[] objParam={taskId,Constants.AUTO_TASK_DELAY_DATE_KEY,Constants.AUTO_TASK_INTERVAL_DATE_KEY};
        return getHibernateTemplate().find(sql,objParam);
}
    
    @Override
    public Class<?> getEntityClass() {
        return TaskTimerParam.class;
    }
    /**
     * 根据参数键获取任务参数
     * @param taskId 任务id
     */
    @SuppressWarnings("unchecked")
    public TaskTimerParam getByKeyAndTaskId(String key, String taskId){
        List<TaskTimerParam> list= getHibernateTemplate().find(" from TaskTimerParam t where t.paramKey=? and t.taskId=? ",key, Integer.valueOf(taskId));
        if(UtilTools.isNullOrEmpty(list)){
            return null;
        }else{
           return list.get(0);
        }
    }
}