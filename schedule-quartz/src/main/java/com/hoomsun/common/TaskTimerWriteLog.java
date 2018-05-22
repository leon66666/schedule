package com.hoomsun.common;

import com.hoomsun.model.TaskLog;
import com.hoomsun.model.TaskTimer;
import com.hoomsun.schedule.service.TaskLogService;
import com.hoomsun.schedule.service.TaskTimerService;
import com.hoomsun.service.cache.impl.RedisServiceImpl;
import com.hoomsun.util.LoginUtil;
import com.hoomsun.util.ResourceBundleUtil;
import com.hoomsun.util.UtilTools;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class TaskTimerWriteLog {
    @Autowired
    private TaskTimerService taskTimerService;
    @Autowired
    private RedisServiceImpl redisServiceImpl;
    @Autowired
    private TaskLogService taskLogService;

    private String logDesc = new String();

    public void before(JoinPoint joinpoint) {
        logDesc = " 开始运行";
        writeLog(joinpoint, TaskStatus.RUNNING.name(), logDesc, true);
    }

    public void after(JoinPoint joinpoint) {
        logDesc = " 停止运行";
        writeLog(joinpoint, TaskStatus.STOPPING.name(), logDesc, false);
    }

    public void writeLog(JoinPoint joinpoint, String status, String desc, boolean isBegin){
        Object obj=null;
        try {
            // 获取拦截的类
            obj = ReflectUtils.getTarget(joinpoint.getThis());
            // 反射获取拦截的类名
            String className = obj.getClass().getName();
            // 获取类在spring容器中的注入名
            className = ReflectUtils.getClassNameInIoc(className);
            TaskTimer taskTimer = taskTimerService.getTaskTimerByClassName(className);
            if (!UtilTools.isNullOrEmpty(taskTimer)) {
                TaskLog taskLog = new TaskLog();
                // 创建者
                taskLog.setCreater(!UtilTools.isNullOrEmpty(LoginUtil.user) ? LoginUtil.user.getUsername() : "");
                // 创建时间
                taskLog.setCreateTime(UtilTools.String2Date(UtilTools.Date2String(new Date())));
                if (isBegin) {
                    // 任务开始运行时间
                    taskLog.setStartTime(UtilTools.String2Date(UtilTools.Date2String(new Date())));
                } else {
                    // 任务结束运行时间
                    taskLog.setEndTime(UtilTools.String2Date(UtilTools.Date2String(new Date())));
                }
                // 获取是否master
                String isMaster = ResourceBundleUtil.getStringValue(Constants.TASK_IS_MASTER_KEY, Constants.TASK_CONFIG_FILE_NAME, null);
                isMaster = UtilTools.isNullOrEmpty(isMaster) ? "master" : isMaster;
                // 封装是否master
                taskLog.setVersion(isMaster);
                // 封装任务sid
                taskLog.setTaskId(taskTimer.getId());
                // 封装操作ip
                taskLog.setServerIp(LoginUtil.loginIp);
                // 封装操作类型
                taskLog.setOperateType(status);
                // 封装描述
                taskLog.setTaskLogDesc("任务: " + taskTimer.getTaskName() + desc);
                taskLogService.save(taskLog);
            }
        } catch (SecurityException e) {
//            logDesc = (isBegin ? " 开始运行异常" : " 结束运行时异常") + e.getMessage();
//            writeLog(joinpoint, TaskStatus.RUNNING.name(), logDesc, isBegin);
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
//            logDesc = (isBegin ? " 开始运行异常" : " 结束运行时异常") + e.getMessage();
//            writeLog(joinpoint, TaskStatus.RUNNING.name(), logDesc, isBegin);
            e.printStackTrace();
        } catch (Exception e) {
//            logDesc = (isBegin ? " 开始运行异常" : " 结束运行时异常") + e.getMessage();
//            writeLog(joinpoint, TaskStatus.RUNNING.name(), logDesc, isBegin);
            e.printStackTrace();
        }
    }
}
