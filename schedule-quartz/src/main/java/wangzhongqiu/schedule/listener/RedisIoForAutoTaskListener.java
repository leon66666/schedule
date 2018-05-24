package wangzhongqiu.schedule.listener;

import wangzhongqiu.common.TaskStatus;
import wangzhongqiu.model.TaskTimer;
import wangzhongqiu.schedule.service.CoreServicesFactory;
import wangzhongqiu.schedule.service.TaskTimerService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;

public class RedisIoForAutoTaskListener implements ServletContextListener {
    private static Log log = LogFactory.getLog(RedisIoForAutoTaskListener.class);

    @Override
    public void contextInitialized(ServletContextEvent event) {
        try {
            StdSchedulerFactory.getDefaultScheduler().start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        startTasks();
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        try {
            StdSchedulerFactory.getDefaultScheduler().shutdown();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    private void startTasks() {
        TaskTimerService taskTimerService = (TaskTimerService) CoreServicesFactory.getBean("taskTimerService");
        List<TaskTimer> list = taskTimerService.getTaskTimerByStatus(TaskStatus.RUNNING);
        for (TaskTimer taskTimer : list) {
            taskTimerService.startTaskById(taskTimer.getId(), "", "127.0.0.1");
        }
    }
}
