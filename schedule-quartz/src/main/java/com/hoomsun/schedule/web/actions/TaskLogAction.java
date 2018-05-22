package com.hoomsun.schedule.web.actions;

import com.hoomsun.model.TaskLog;
import com.hoomsun.page.Page;
import com.hoomsun.schedule.service.TaskLogService;
import com.hoomsun.vo.TaskLogQuery;
import javacommon.base.BaseStruts2Action;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description 自动任务日志action，为每次任务的运行和停止都记录日志
 * 
 */
public class TaskLogAction extends BaseStruts2Action {

    protected static final String DEFAULT_SORT_COLUMNS = null;

    protected static final String SUCCESS = "/WEB-INF/jsp/log/list.jsp";

    @Autowired
    private TaskLogService taskLogService;

    private TaskLog taskLog;

    /**
     * 日志列表
     */
    public String list() {
        TaskLogQuery query = newQuery(TaskLogQuery.class, DEFAULT_SORT_COLUMNS);
        Page<?> page = taskLogService.findPage(query);
        savePage(page, query);
        return SUCCESS;
    }

    public TaskLog getTaskLog() {
        return taskLog;
    }

    public void setTaskLog(TaskLog taskLog) {
        this.taskLog = taskLog;
    }

    private String subPointIds;

    public String getSubPointIds() {
        return subPointIds;
    }

    public void setSubPointIds(String subPointIds) {
        this.subPointIds = subPointIds;
    }

}
