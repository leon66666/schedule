package com.hoomsun.schedule.listener;

import com.hoomsun.common.Constants;
import com.hoomsun.common.TaskStatus;
import com.hoomsun.model.TaskTimer;
import com.hoomsun.schedule.service.CoreServicesFactory;
import com.hoomsun.schedule.service.ScheduleInitService;
import com.hoomsun.schedule.service.TaskTimerService;
import com.hoomsun.service.cache.impl.RedisServiceImpl;
import com.hoomsun.util.LoginUtil;
import com.hoomsun.util.ResourceBundleUtil;
import com.hoomsun.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RedisIoForAutoTaskListener implements ServletContextListener {
    private static Log log = LogFactory.getLog(RedisIoForAutoTaskListener.class);

    private String machineType = ResourceBundleUtil.getStringValue(Constants.TASK_IS_MASTER_KEY,
            Constants.TASK_CONFIG_FILE_NAME, null);
    private String currentHost = ResourceBundleUtil.getStringValue(Constants.TASK_IS_MASTER_KEY,
    		Constants.TASK_CONFIG_FILE_NAME, null);
	private String slaveJobs = ResourceBundleUtil.getStringValue(Constants.SLAVE_JOBS,
			Constants.TASK_CONFIG_FILE_NAME, null);

    public void contextInitialized(ServletContextEvent event) {
        try {
            StdSchedulerFactory.getDefaultScheduler().start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        clearRedisFlags();
        startTasks();
    }

    public void contextDestroyed(ServletContextEvent event) {
        clearRedisFlags();
        try {
            StdSchedulerFactory.getDefaultScheduler().shutdown();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    private void startTasks() {
        TaskTimerService taskTimerService = (TaskTimerService)CoreServicesFactory.getBean("taskTimerService");
        List<TaskTimer> list = taskTimerService.getTaskTimerByStatus(TaskStatus.RUNNING);
        for (TaskTimer taskTimer : list) {
            taskTimerService.startTaskById(taskTimer.getId(), machineType, LoginUtil.loginIp);
        }
    }

    private void clearRedisFlags() {
        TaskTimerService taskTimerService = (TaskTimerService)CoreServicesFactory.getBean("taskTimerService");
        RedisServiceImpl redisService = (RedisServiceImpl)CoreServicesFactory.getBean("redisServiceImpl");
        ScheduleInitService scheduleInitService = (ScheduleInitService)CoreServicesFactory.getBean("scheduleInitService");

        List<TaskTimer> list = taskTimerService.getAllTaskTimers();

    	Map<String,String> jobsMap = new HashMap<String,String>();
    	if(StringUtils.isNotBlank(slaveJobs)){
    		String[] jobs = slaveJobs.split(",");
    		for(int i=0;i<jobs.length;i++){
    			jobsMap.put(jobs[i], "");
    		}
    	}

    	if (Constants.SCHEDULE_JOB_MASTER.toUpperCase().equals(currentHost.toUpperCase())) {
            for (TaskTimer taskTimer : list) {
                // 如果是master 则不在slaveJobs中的任务要clear
                if (jobsMap.get(taskTimer.getTaskClass()) == null) {
            		redisService.del(Constants.RUNNING_KEYPREFIX_IN_REDIS, taskTimer.getTaskClass());
                    redisService.del(Constants.KEYPREFIX_STOP_FLAG_IN_REDIS_, taskTimer.getTaskClass());

                    // 清除多线程还款借款人同步锁的标记
                    Set<String> repayBorrowerSyncLockSet = redisService.keys(Constants.REPAY_BORROWER_SYNC_LOCK + "*");
                    if (StringUtil.isNotEmpty(repayBorrowerSyncLockSet)) {
                        for (String repayBorrowerSyncLockKey : repayBorrowerSyncLockSet) {
                            long result3 = redisService.del(repayBorrowerSyncLockKey);
                            if (result3 > 0) {
                                log.info("schedule init service 处理多线程还款借款人同步锁" + repayBorrowerSyncLockKey + " cleaned");
                            }
                        }
                    }

                    // 如果是多线程还款任务 则清一下redis相关的内容
                    if (Constants.BORROW_REPAY_TASK_LAUNCH_JOB.equals(taskTimer.getTaskClass())) {
                		try {
                            scheduleInitService.init();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                	}
                }
            }
    	}

        if (Constants.SCHEDULE_JOB_SLAVE.toUpperCase().equals(currentHost.toUpperCase())) {
            for (TaskTimer taskTimer : list) {
                // 如果是slave 则在slaveJobs中的任务要clear
                if (jobsMap.get(taskTimer.getTaskClass()) != null) {
            		redisService.del(Constants.RUNNING_KEYPREFIX_IN_REDIS, taskTimer.getTaskClass());
                    redisService.del(Constants.KEYPREFIX_STOP_FLAG_IN_REDIS_, taskTimer.getTaskClass());

                    // 如果是多线程还款任务 则清一下redis相关的内容
                    if (Constants.BORROW_REPAY_TASK_LAUNCH_JOB.equals(taskTimer.getTaskClass())) {
                		try {
                            scheduleInitService.init();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                	}
            	}
            }
        }
    }
}
