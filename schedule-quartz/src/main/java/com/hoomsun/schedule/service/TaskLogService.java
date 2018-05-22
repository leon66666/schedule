package com.hoomsun.schedule.service;


import com.hoomsun.exception.ServiceException;
import com.hoomsun.model.TaskLog;
import com.hoomsun.page.Page;
import com.hoomsun.schedule.dao.TaskLogDAO;
import com.hoomsun.vo.TaskLogQuery;
import javacommon.base.BaseService;
import javacommon.base.EntityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = ServiceException.class)
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
