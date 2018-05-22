/*
 * 
 * 
 */
package com.hoomsun.schedule.web.actions;

import com.hoomsun.message.Flash;
import com.hoomsun.model.TaskTimer;
import com.hoomsun.model.TaskTimerParam;
import com.hoomsun.page.Page;
import com.hoomsun.schedule.service.TaskTimerParamService;
import com.hoomsun.schedule.service.TaskTimerService;
import com.hoomsun.vo.TaskTimerParamQuery;
import javacommon.base.BaseStruts2Action;
import org.springframework.beans.factory.annotation.Autowired;
import zhongqiu.javautils.UtilTools;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @description 自动任务参数action
 * 
 */
public class TaskTimerParamAction extends BaseStruts2Action {
    protected static final String DEFAULT_SORT_COLUMNS = null;
    protected static final String SUCCESS = "/WEB-INF/jsp/param/list.jsp";
    protected static final String CREATE_JSP = "/WEB-INF/jsp/param/create_param.jsp";
    protected static final String EDIT_JSP = "/WEB-INF/jsp/param/edit_param.jsp";
    private static final long serialVersionUID = 4305445079780144035L;
    @Autowired
    private TaskTimerParamService taskTimerParamService;
    @Autowired
    private TaskTimerService taskTimerService;
    private TaskTimerParam taskTimerParam;
    private Integer taskId;
    public String items;

    /**
     * 参数保存
     */
    public String save() {
        HttpServletRequest request = getRequest();
        // 获取登陆用户
        String paramKey = request.getParameter("taskTimerParam.paramKey");
        String paramValue = request.getParameter("taskTimerParam.paramValue");
        String taskId = request.getParameter("taskId");
        if (UtilTools.isAllNotNullOrEmpty(taskId, paramKey)) {
            TaskTimerParam taskTimerParamTemp = taskTimerParamService.getByKeyAndTaskId(paramKey, taskId);

            if (!UtilTools.isNullOrEmpty(taskTimerParamTemp)) {
                taskTimerParamTemp.setParamValue(paramValue);
                taskTimerParamService.update(taskTimerParamTemp);
            } else {
                String displayName = request.getParameter("taskTimerParam.displayName");
                String taskIdStr = request.getParameter("taskId");
                taskTimerParam.setDisplayName(displayName);
                taskTimerParam.setParamKey(paramKey);
                taskTimerParam.setParamValue(paramValue);
                if (!isNullOrEmptyString(taskIdStr)) {
                    taskTimerParam.setTaskId(Integer.valueOf(taskIdStr));
                }
                // 创建时间
                taskTimerParam.setCreateTime(UtilTools.String2Date(UtilTools.Date2String(new Date())));
                taskTimerParamService.save(taskTimerParam);
                this.setTaskId(taskTimerParam.getTaskId());
            }
            Flash.current().success(CREATED_SUCCESS); // 存放在Flash中的数据,在下一次http请求中仍然可以读取数据,error()用于显示错误消息
        } else {
            Flash.current().error("创建失败");
        }

        return list();
    }

    /**
     * 参数修改
     */
    public String edit() {
        String taskIdStr = getRequest().getParameter("taskId");
        if (!isNullOrEmptyString(taskIdStr)) {
            TaskTimer taskTimer = taskTimerService.getById(Integer.parseInt(taskIdStr));
            getRequest().setAttribute("task", taskTimer);
        }
        String paramId = getRequest().getParameter("paramId");
        if (!isNullOrEmptyString(paramId)) {
            taskTimerParam = taskTimerParamService.getById(Integer.valueOf(paramId));
            this.setTaskId(taskTimerParam.getTaskId());
            getRequest().setAttribute("taskId", taskIdStr);
            getRequest().setAttribute("taskTimerParam", taskTimerParam);
        }
        return EDIT_JSP;
    }

    /**
     * 参数保存
     */
    public String update() {
        String displayName = getRequest().getParameter("taskTimerParam.displayName");
        String paramKey = getRequest().getParameter("taskTimerParam.paramKey");
        String paramValue = getRequest().getParameter("taskTimerParam.paramValue");
        String IdStr = getRequest().getParameter("taskTimerParam.id");
        String paramDesc = getRequest().getParameter("taskTimerParam.taskParamDesc");
        if (!isNullOrEmptyString(IdStr)) {
            TaskTimerParam task = taskTimerParamService.getById(Integer.valueOf(IdStr));
            task.setDisplayName(displayName);
            task.setParamKey(paramKey);
            task.setParamValue(paramValue);
            task.setTaskParamDesc(paramDesc);
            try {

                taskTimerParamService.update(task);
                Flash.current().success(UPDATE_SUCCESS); // 存放在Flash中的数据,在下一次http请求中仍然可以读取数据,error()用于显示错误消息
                this.setTaskId(task.getTaskId());
            } catch (Exception e) {
                Flash.current().error(UPDATE_FAIL);

                e.printStackTrace();
            }

        }

        return list();
    }

    /**
     * 参数创建
     */
    public String create() {
        String taskIdStr = getRequest().getParameter("taskId");
        if (!isNullOrEmptyString(taskIdStr)) {
            TaskTimer taskTimer = taskTimerService.getById(Integer.parseInt(taskIdStr));
            getRequest().setAttribute("task", taskTimer);
        }
        getRequest().setAttribute("taskId", taskIdStr);
        return CREATE_JSP;
    }

    /**
     * 任务删除
     */
    public String delete() {
        if (!isNullOrEmptyString(items)) {
            items = items.replaceAll("on,", "");
        }
        String[] itemArray = items.split(",");
        try {
            Flash.current().success(DELETE_SUCCESS); // 存放在Flash中的数据,在下一次http请求中仍然可以读取数据,error()用于显示错误消息
            taskTimerParamService.batDelete(itemArray);
        } catch (Exception e) {
            
            e.printStackTrace();
        }
        Flash.current().success(DELETE_SUCCESS); // 存放在Flash中的数据,在下一次http请求中仍然可以读取数据,error()用于显示错误消息
        return list();
    }

    /**
     * 参数列表
     */
    public String list() {
        String taskIdStr = getRequest().getParameter("taskId");
        TaskTimerParamQuery query = newQuery(TaskTimerParamQuery.class, DEFAULT_SORT_COLUMNS);
        if (!isNullOrEmptyString(taskIdStr)) {
            query.setTaskId(Integer.parseInt(taskIdStr));
        } else {
            query.setTaskId(!isNullOrEmptyString(taskId) ? taskId : null);
        }
        Page<?> page = taskTimerParamService.findPage(query);
        savePage(page, query);
        getRequest().setAttribute("taskId", taskId);
        return SUCCESS;
    }

    /*** setXXX() and getXXX() ***/
    public TaskTimerParam gettaskTimerParam() {
        return taskTimerParam;
    }

    public void setTaskTimerParam(TaskTimerParam taskTimerParam) {
        this.taskTimerParam = taskTimerParam;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

}
