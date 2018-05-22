/*
 * 
 * 
 */
package com.hoomsun.schedule.web.actions;

import com.hoomsun.model.Staff;
import com.hoomsun.model.TaskTimer;
import com.hoomsun.schedule.service.TaskTimerService;
import com.hoomsun.service.UserService;
import com.hoomsun.util.StringUtil;
import javacommon.base.BaseStruts2Action;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * 定时任务管理的Action,用于控制一个任务的运行情况<p/>
 * url: /schedule/taskMgmt.action<br/>
 * param: userName  password taskName  operation<br/>
 * method: POST<br/>
 * 
 */
public class TaskMgmtAction extends BaseStruts2Action {
    private Log logger = LogFactory.getLog(TaskMgmtAction.class);
    
    @Autowired
    private TaskTimerService taskTimerService;
    @Autowired
    private UserService userService;
    
    // 用户名
    private String userName;
    // 密码
    private String password;
    // 任务名
    private String taskName;
    // 操作类型:START STOP  RESTART RESET_PARAM
    private String operation;
    // 任务时间,用逗号分隔开,如:11:00,12:00,13:00
    private String param;
    // 参数类型: timingDate/delayDate/intervalDate,默认是timingDate
    private String paramType = "timingDate";
    
    private Map jsonResult = new HashMap();;
    
    /*
     * 用户登录 <p/>
     */
    @Override
    public String execute() {
        try {
            // 检查用户是否有登录权限
            // 用户名, 密码 
            try {
                Staff user = userService.loginForInternal(userName, password);
            }
            catch(Exception ex) {
                logger.error(ex, ex);
                jsonResult.put("status", 0);
                jsonResult.put("message", "用户名或密码不正确");
                return "jsonResult";
            }
            
            if (StringUtil.isEmpty(taskName) || StringUtil.isEmpty(operation)) {
                jsonResult.put("status", 0);
                jsonResult.put("message", "任务名称或操作类型不正确");
                return "jsonResult";
            }
            
            String ip = "";
            // 任务名称  操作类型:启动 关闭 重启
            TaskTimer task = taskTimerService.getByTaskClass(taskName);
            if (task == null) {
                throw new RuntimeException("没有找到任务:" + taskName);
            }
            
            operation = operation.toUpperCase();
            if ("START".equals(operation)) {
                taskTimerService.startTaskById(task.getId(), userName, ip);
            }
            else if ("STOP".equals(operation)) {
                taskTimerService.stopTaskById(task.getId(), userName, ip);
            }
            else if ("RESTART".equals(operation)) {
                taskTimerService.stopTaskById(task.getId(), userName, ip);
                taskTimerService.startTaskById(task.getId(), userName, ip);
            }
            else if ("RESET_PARAM".equals(operation)) {
                if (StringUtils.isEmpty(param)) {
                    throw new RuntimeException("任务参数不能为空");
                }
                if (StringUtils.isEmpty(paramType)) {
                    paramType = "timingDate";
                }
                // timingDate/delayDate/intervalDate
                if (!paramType.equals("timingDate") && !paramType.equals("delayDate") && !paramType.equals("intervalDate")) {
                    throw new RuntimeException("参数类型不正确:" + paramType);
                }
                
                taskTimerService.resetParam(task.getId(), paramType, param, userName);
            }
            else {
                throw new RuntimeException("非法的操作:" + operation);
            }
            
            jsonResult.put("status", "1");
            jsonResult.put("message", "操作成功");
            return "jsonResult";
            
        } catch (Exception e) {
            logger.error(e, e);
            jsonResult.put("status", "0");
            jsonResult.put("message", e.getMessage());
            return "jsonResult";
        }
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Map getJsonResult() {
        return jsonResult;
    }

    public void setJsonResult(Map jsonResult) {
        this.jsonResult = jsonResult;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getParamType() {
        return paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }

}
