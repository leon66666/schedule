package com.hoomsun.common;

import com.hoomsun.model.TaskLog;
import com.hoomsun.model.TaskTimer;
import com.hoomsun.schedule.service.TaskLogService;
import com.hoomsun.schedule.service.TaskTimerService;
import zhongqiu.javautils.UtilTools;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class TaskTimerWriteLog {
    @Autowired
    private TaskTimerService taskTimerService;
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

    public void writeLog(JoinPoint joinpoint, String status, String desc, boolean isBegin) {
        Object obj = null;
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
                taskLog.setCreater("admin");
                // 创建时间
                taskLog.setCreateTime(UtilTools.String2Date(UtilTools.Date2String(new Date())));
                if (isBegin) {
                    // 任务开始运行时间
                    taskLog.setStartTime(UtilTools.String2Date(UtilTools.Date2String(new Date())));
                } else {
                    // 任务结束运行时间
                    taskLog.setEndTime(UtilTools.String2Date(UtilTools.Date2String(new Date())));
                }
                // 封装任务sid
                taskLog.setTaskId(taskTimer.getId());
                // 封装操作ip
                taskLog.setServerIp("127.0.0.1");
                // 封装操作类型
                taskLog.setOperateType(status);
                // 封装描述
                taskLog.setTaskLogDesc("任务: " + taskTimer.getTaskName() + desc);
                taskLogService.save(taskLog);
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
