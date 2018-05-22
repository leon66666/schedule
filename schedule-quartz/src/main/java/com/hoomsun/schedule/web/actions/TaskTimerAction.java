package com.hoomsun.schedule.web.actions;

import com.hoomsun.common.TaskStatus;
import com.hoomsun.message.Flash;
import com.hoomsun.model.Staff;
import com.hoomsun.model.TaskTimer;
import com.hoomsun.page.Page;
import com.hoomsun.page.util.HttpTools;
import com.hoomsun.schedule.service.CoreServicesFactory;
import com.hoomsun.schedule.service.TaskLogService;
import com.hoomsun.schedule.service.TaskTimerParamService;
import com.hoomsun.schedule.service.TaskTimerService;
import com.hoomsun.schedule.service.job.BaseJob;
import com.hoomsun.service.cache.impl.RedisServiceImpl;
import com.hoomsun.util.UtilTools;
import com.hoomsun.vo.TaskTimerQuery;
import javacommon.base.BaseStruts2Action;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

/**
 * @description 自动任务action
 * 
 */
public class TaskTimerAction extends BaseStruts2Action {
    protected static final String DEFAULT_SORT_COLUMNS = null;
    protected static final String SUCCESS = "/WEB-INF/jsp/task/list.jsp";
    protected static final String CREATE_JSP = "/WEB-INF/jsp/task/create_task.jsp";
    protected static final String EDIT_JSP = "/WEB-INF/jsp/task/edit_task.jsp";
    private static final long serialVersionUID = 4305445079780144035L;
    @Autowired
    private TaskTimerService taskTimerService;
    @Autowired
    private TaskLogService taskLogService;
    @Autowired
    private TaskTimerParamService taskTimerParamService;
    @Autowired
    private RedisServiceImpl redisServiceImpl;
    private TaskTimer taskTimer;
    private String items;

    /**
     * 任务保存：新建一个自动任务
     */
    public String save() {
        // 获取登陆用户
        Staff user = (Staff) getRequest().getSession().getAttribute("logingUser");
        if (isNullOrEmptyString(user))
            taskTimer.setCreater("");
        else {
            /** 创建者 **/
            taskTimer.setCreater(isNullOrEmptyString(user.getUsername()) ? user.getMail() : user.getUsername());
            /** 创建时间 **/
            taskTimer.setCreateTime(UtilTools.String2Date(UtilTools.Date2String(new Date())));
            taskTimer.setTaskStatus(TaskStatus.STOPPING);
        }
        try {

            taskTimerService.save(taskTimer);
            Flash.current().success(CREATED_SUCCESS);
        } catch (Exception e) {
            Flash.current().error(e.getMessage());

            e.printStackTrace();
        }

        return list();

    }

    /**
     * 任务修改
     */
    public String edit() {
        String taskId = getRequest().getParameter("taskId");
        if (!isNullOrEmptyString(taskId)) {
            taskTimer = taskTimerService.getById(Integer.valueOf(taskId));
        }
        getRequest().setAttribute("taskId", taskId);
        return EDIT_JSP;
    }

    /**
     * 任务更新
     */
    public String update() {
        TaskTimer task = taskTimerService.getById(taskTimer.getId());
        task.setEnable(taskTimer.getEnable());
        task.setTaskClass(taskTimer.getTaskClass());
        task.setTaskName(taskTimer.getTaskName());
        task.setTaskDesc(taskTimer.getTaskDesc());
        if (!UtilTools.isNullOrEmpty(taskTimer.getIsTiming()) && !taskTimer.getIsTiming().equals(task.getIsTiming())) {
            taskTimerParamService.deleteByTaskId(task.getId());
        }
        task.setIsTiming(taskTimer.getIsTiming());
        try {

            taskTimerService.update(task);
            Flash.current().success(UPDATE_SUCCESS);
        } catch (Exception e) {
            Flash.current().error(UPDATE_FAIL);

            e.printStackTrace();
        }
        return list();
    }

    /**
     * 任务创建
     */
    public String create() {
        TaskTimerQuery query = newQuery(TaskTimerQuery.class, DEFAULT_SORT_COLUMNS);
        Page<?> page = taskTimerService.findPage(query);
        savePage(page, query);
        return CREATE_JSP;
    }

    /**
     * 任务删除
     */
    public String delete() {
        String[] itemArray = items.split(",");
        try {
            taskTimerService.batDelete(itemArray);
            Flash.current().success(DELETE_SUCCESS);
        } catch (Exception e) {
            Flash.current().error(e.getMessage());
            e.printStackTrace();
        }
        return list();
    }

    /**
     * 任务列表
     */
    public String list() {
        Integer currPageNumber = 1;
        Integer temp = (Integer)getRequest().getSession().getAttribute("currPageNumber");
        if (temp != null) {
            currPageNumber = temp;
        }
        TaskTimerQuery query = newQuery(TaskTimerQuery.class, DEFAULT_SORT_COLUMNS);
        int pageNumber = query.getPageNumber();
        if (pageNumber == 0) {
            query.setPageNumber(currPageNumber);
        } else {
            getRequest().getSession().setAttribute("currPageNumber", pageNumber);
        }
        Page<?> page = taskTimerService.findPage(query);
        savePage(page, query);
        return SUCCESS;
    }

    /**
     * 启动任务
     */
    public String startTask() {
        String taskId = getRequest().getParameter("taskId");
        if (!isNullOrEmptyString(taskId)) {
            Staff user = (Staff)getRequest().getSession().getAttribute("logingUser");
            String username = (!UtilTools.isNullOrEmpty(user) ? user.getUsername() : "");
            String ip = HttpTools.getIpAddr(getRequest());
            taskTimerService.startTaskById(Integer.valueOf(taskId), username, ip);
        }
        return list();
    }

    /**
     * 停止任务
     */
    public String stopTask() {
        String taskId = getRequest().getParameter("taskId");
        if (!isNullOrEmptyString(taskId)) {
            Staff user = (Staff)getRequest().getSession().getAttribute("logingUser");
            String username = (!UtilTools.isNullOrEmpty(user) ? user.getUsername() : "");
            String ip = HttpTools.getIpAddr(getRequest());
            taskTimerService.stopTaskById(Integer.valueOf(taskId), username, ip);
        }
        return list();
    }

    public void getRunCount() {
        HttpServletRequest request = getRequest();
        HttpServletResponse response = getResponse();
        response.setContentType("application/x-json");
        // 设置响应编码方式
        response.setCharacterEncoding("UTF-8");
        // 获取className 加载对应task Bean
        String className = request.getParameter("className");
        PrintWriter out = null;
        try {
            BaseJob job = (BaseJob)CoreServicesFactory.getBean(className);
            Map<String, Object> mapResult = job.getMapResult();
            JSONObject json = new JSONObject();
            json = JSONObject.fromObject(mapResult);
            out = response.getWriter();
            out.append(json.toString());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** setXXX() and getXXX() **/
    public TaskTimer getTaskTimer() {
        if (isNullOrEmptyString(taskTimer)) {
            this.setTaskTimer(new TaskTimer());
        }
        return taskTimer;
    }

    public void setTaskTimer(TaskTimer taskTimer) {
        this.taskTimer = taskTimer;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public TaskTimerService getTaskTimerService() {
        return taskTimerService;
    }

    public void setTaskTimerService(TaskTimerService taskTimerService) {
        this.taskTimerService = taskTimerService;
    }

    public TaskLogService getTaskLogService() {
        return taskLogService;
    }

    public void setTaskLogService(TaskLogService taskLogService) {
        this.taskLogService = taskLogService;
    }

    public TaskTimerParamService getTaskTimerParamService() {
        return taskTimerParamService;
    }

    public void setTaskTimerParamService(TaskTimerParamService taskTimerParamService) {
        this.taskTimerParamService = taskTimerParamService;
    }
}
