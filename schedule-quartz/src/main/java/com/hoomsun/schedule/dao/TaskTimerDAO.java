package com.hoomsun.schedule.dao;

import com.hoomsun.common.Constants;
import com.hoomsun.common.TaskStatus;
import com.hoomsun.model.TaskTimer;
import com.hoomsun.page.Page;
import com.hoomsun.util.ResourceBundleUtil;
import com.hoomsun.util.UtilTools;
import com.hoomsun.vo.TaskTimerQuery;
import javacommon.base.BaseHibernateDao;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

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
		//检查任务应该在哪台schedule执行
    	String currentHost = ResourceBundleUtil.getStringValue(Constants.TASK_IS_MASTER_KEY, Constants.TASK_CONFIG_FILE_NAME, null);
    	String slaveJobs = ResourceBundleUtil.getStringValue(Constants.SLAVE_JOBS, Constants.TASK_CONFIG_FILE_NAME, null);

    	String taskClassRange = "''";
    	if(StringUtils.isNotBlank(slaveJobs)){
    		String[] jobs = slaveJobs.split(",");
    		for(int i=0;i<jobs.length;i++){
    			taskClassRange = taskClassRange + ",'" + jobs[i] + "'";
    		}
    	}

        String sql = "select t from TaskTimer t where 1=1 ";
        //currentHost为MASTER
    	if(Constants.SCHEDULE_JOB_MASTER.toUpperCase().equals(currentHost.toUpperCase())){
    		sql = sql + "and t.taskClass not in ("+taskClassRange+") ";
		}
    	//currentHost为SLAVE
    	if(Constants.SCHEDULE_JOB_SLAVE.toUpperCase().equals(currentHost.toUpperCase())){
    		sql = sql + "and t.taskClass in ("+taskClassRange+") ";
		}
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
        if(UtilTools.isNullOrEmpty(query.getTaskName())) {
            sql2.append(" and  t.taskName like '%[taskName]%'");
        }

        return pageQuery(sql,query);
    }
	public String getTaskNameById(Integer id){
        TaskTimer  taskTimer=this.getById(id);
        log.debug(taskTimer.getTaskName());
        return !UtilTools.isNullOrEmpty(taskTimer)?taskTimer.getTaskName():"";
    }
    @Override
    public Class<TaskTimer> getEntityClass() {
        return TaskTimer.class;
    }
    public TaskStatus getRunning(){
        return TaskStatus.RUNNING;
    }
    public TaskStatus getStoping(){
        return TaskStatus.STOPPING;
    }

    /**
     * 根据状态获取某一状态的任务
     * @param status 状态
     * @return 某一状态下的任务集合
     */
    @SuppressWarnings("unchecked")
    public List<TaskTimer> getTaskTimerByStatus(TaskStatus status){
        return getHibernateTemplate().find("from TaskTimer t where t.taskStatus=?",status);
    }

    /**
     * 获取所有任务
     * @return 所有任务的集合
     */
    @SuppressWarnings("unchecked")
    public List<TaskTimer> getAllTaskTimers() {
        return getHibernateTemplate().find("from TaskTimer");
    }

    /**
     * 根据任务对应的类名获取任务实例
     * @param className 任务对应类名
     * @return 任务实例
     */
    @SuppressWarnings("unchecked")
    public TaskTimer getTaskTimerByClassName(String className){

        List<TaskTimer> list= getHibernateTemplate().find("from TaskTimer t where t.taskClass=?",className);
        return UtilTools.isNullOrEmpty(list)?null:list.get(0);
    }
    public String getCurrentHost(){
        String isMaste= ResourceBundleUtil.getStringValue(Constants.TASK_IS_MASTER_KEY, Constants.TASK_CONFIG_FILE_NAME, null);
        return UtilTools.isNullOrEmpty(isMaste)?"master":isMaste;
    }
}