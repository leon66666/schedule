package wangzhongqiu.schedule.service;

import wangzhongqiu.model.TaskLog;
import wangzhongqiu.page.Page;
import wangzhongqiu.schedule.dao.TaskLogDAO;
import wangzhongqiu.vo.TaskLogQuery;
import javacommon.base.BaseService;
import javacommon.base.EntityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TaskLogService extends BaseService<TaskLog, Integer> {

	@Autowired
	private TaskLogDAO taskLogDAO;
	
	@Transactional(readOnly=true)
	public Page<?> findPage(TaskLogQuery query){
        return taskLogDAO .findPage(query);
    }
    @Override
    protected EntityDao<?, ?> getEntityDao() {
        return taskLogDAO;
    }
}
