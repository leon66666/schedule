package wangzhongqiu.schedule.service;

import wangzhongqiu.common.TaskStatus;
import wangzhongqiu.model.TaskTimer;
import wangzhongqiu.model.TaskTimerParam;
import wangzhongqiu.page.Page;
import wangzhongqiu.schedule.dao.TaskTimerDAO;
import wangzhongqiu.schedule.dao.TaskTimerParamDAO;
import zhongqiu.javautils.UtilTools;
import wangzhongqiu.vo.TaskTimerParamQuery;
import javacommon.base.BaseService;
import javacommon.base.EntityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TaskTimerParamService extends BaseService<TaskTimerParam, Integer> {

    @Autowired
    private TaskTimerParamDAO taskTimerParamDAO;
	@Autowired
	private TaskTimerDAO taskTimerDAO;
	public Page<?> findPage(TaskTimerParamQuery query){
	    return taskTimerParamDAO .findPage(query);
	}
    @Override
    protected EntityDao<?, ?> getEntityDao() {
        return taskTimerParamDAO;
    }
 // 根据id数组批量删除任务
    public void batDelete(String[] ids) throws Exception {
          
               for(String id:ids){
                    if(!UtilTools.isNullOrEmpty(id)){
                        TaskTimerParam taskTimerParam=this.getById(Integer.valueOf(id));
                        if(UtilTools.isNullOrEmpty(taskTimerParam.getTaskId())){
                            taskTimerParamDAO.delete(taskTimerParam);
                        }else{
                            TaskTimer taskTimer=taskTimerDAO.getById(taskTimerParam.getTaskId());
                            if(!UtilTools.isNullOrEmpty(taskTimer)){
                                if(!UtilTools.isNullOrEmpty(taskTimer.getTaskStatus())&&taskTimer.getTaskStatus().equals(TaskStatus.RUNNING)){
                                    throw new Exception("任务在运行中不能删除");
                                }
                                else
                                    taskTimerParamDAO.delete(taskTimerParam);
                        }
                        }
                    }
                }
    }
    
    /**
     * 根据任务id获取任务参数
     * @param taskId 任务id
     * @return map 返回某任务的所有参数
     */
    @Transactional(readOnly=true)
    public Map<String,String> findInitParamByTaskId(Integer taskId){
        Map<String,String> map=new HashMap<String, String>();
        List<Map<String,String>> list=taskTimerParamDAO.findInitParamByTaskId(taskId);
        for(Map<String,String> mapParam:list){
            map.put(mapParam.get("key"), mapParam.get("value"));
        }
        return map;
    }
    /**
     * 根据参数键获取任务参数
     * @param taskId 任务id
     * @return 参数实例
     */
    @Transactional(readOnly=true)
    public TaskTimerParam getByKeyAndTaskId(String key, String taskId){
       return taskTimerParamDAO.getByKeyAndTaskId(key,taskId);
    }
    /**
     * 根据任务Id删除参数
     * @param taskId 任务id
     */
    public void deleteByTaskId(Integer taskId){
        taskTimerParamDAO.deleteByTaskId(taskId);
    }
    
}
