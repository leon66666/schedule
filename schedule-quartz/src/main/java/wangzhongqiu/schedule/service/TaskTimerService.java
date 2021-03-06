package wangzhongqiu.schedule.service;

import wangzhongqiu.common.TaskStatus;
import wangzhongqiu.message.Flash;
import wangzhongqiu.model.TaskTimer;
import wangzhongqiu.model.TaskTimerParam;
import wangzhongqiu.page.Page;
import wangzhongqiu.schedule.dao.TaskTimerDAO;
import wangzhongqiu.schedule.dao.TaskTimerParamDAO;
import wangzhongqiu.schedule.service.job.BaseJob;
import wangzhongqiu.util.Constants;
import wangzhongqiu.vo.TaskTimerQuery;
import javacommon.base.BaseService;
import javacommon.base.EntityDao;
import org.apache.commons.lang.StringUtils;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zhongqiu.javautils.UtilTools;

import java.util.Date;
import java.util.List;

import static org.quartz.CronScheduleBuilder.dailyAtHourAndMinute;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

@Service
@Transactional
public class TaskTimerService extends BaseService<TaskTimer, Integer> {

    @Autowired
    private TaskTimerDAO taskTimerDAO;
    @Autowired
    private TaskTimerParamDAO timerParamDao;
    @Autowired
    private TaskLogService taskLogService;
    @Autowired
    private ScheduleInitService scheduleInitService;
    private Scheduler scheduler;

    public TaskTimerService() {
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public Page<?> findPage(TaskTimerQuery query) {
        return taskTimerDAO.findPage(query);
    }

    @Override
    public List<TaskTimer> findAll() {
        return taskTimerDAO.findAll();
    }

    /**
     * 根据任务对应的类名获取任务实例
     *
     * @param className 任务对应类名
     */
    @Transactional(readOnly = true)
    public TaskTimer getTaskTimerByClassName(String className) {
        return taskTimerDAO.getTaskTimerByClassName(className);
    }

    // 根据id数组批量删除任务
    public void batDelete(String[] ids) throws Exception {
        for (String id : ids) {
            if (!UtilTools.isNullOrEmpty(id)) {
                TaskTimer taskTimer = this.getById(Integer.valueOf(id));
                if (!UtilTools.isNullOrEmpty(taskTimer)) {
                    TaskStatus taskStatus = taskTimer.getTaskStatus();
                    if (!UtilTools.isNullOrEmpty(taskStatus) && taskStatus.equals(TaskStatus.RUNNING)) {
                        throw new RuntimeException("任务在运行中不能删除");
                    } else {
                        taskTimerDAO.delete(taskTimer);
                    }
                }
            }
        }
    }

    public String getTaskNameById(Integer id) {
        TaskTimer taskTimer = this.getById(id);
        log.debug(taskTimer.getTaskName());
        return !UtilTools.isNullOrEmpty(taskTimer) ? taskTimer.getTaskName() : "";
    }

    /**
     * 根据状态获取某一状态的任务
     *
     * @param status 状态
     */
    @Transactional(readOnly = true)
    public List<TaskTimer> getTaskTimerByStatus(TaskStatus status) {
        return taskTimerDAO.getTaskTimerByStatus(status);
    }

    /**
     * 获取所有任务
     *
     * @return 所有任务的集合
     */
    @Transactional(readOnly = true)
    public List<TaskTimer> getAllTaskTimers() {
        return taskTimerDAO.getAllTaskTimers();
    }

    public TaskTimer getByTaskClass(String taskName) {
        return taskTimerDAO.findByProperty("taskClass", taskName);
    }

    @Override
    protected EntityDao<?, ?> getEntityDao() {
        return taskTimerDAO;
    }

    /**
     * 重置一个定时任务的参数
     *
     * @param id          任务ID
     * @param paramKey    参数类型 timingDate/delayDate/intervalDate
     * @param param_value 参数值
     * @param staff       操作人
     */
    public void resetParam(Integer id, String paramKey, String param_value, String staff) {
        if (id == null || StringUtils.isEmpty(paramKey) || StringUtils.isEmpty(param_value)) {
            throw new RuntimeException("参数不正确:部分参数为空");
        }

        // 删掉某个任务的参数
        timerParamDao.deleteByTaskId(id);
        // 增加参数
        appendParam(id, paramKey, param_value, staff);
    }

    /**
     * 增加一个定时任务的参数
     *
     * @param id          任务ID
     * @param paramKey    参数类型 timingDate/delayDate/intervalDate
     * @param param_value 参数值
     * @param staff       操作人
     */
    public void appendParam(Integer id, String paramKey, String param_value, String staff) {
        if (id == null || StringUtils.isEmpty(paramKey) || StringUtils.isEmpty(param_value)) {
            throw new RuntimeException("参数不正确:部分参数为空");
        }

        // 增加参数
        TaskTimerParam param = new TaskTimerParam();
        param.setTaskId(id);
        param.setParamKey(paramKey);
        param.setParamValue(param_value);
        param.setCreateTime(new Date());
        param.setCreater(staff);
        timerParamDao.save(param);
    }

    public void startTaskById(Integer id, String username, String ip) {

        try {
            // 获取待执行的任务
            TaskTimer taskTimer = getById(id);

            if (UtilTools.isNullOrEmpty(taskTimer)) {
                if (!UtilTools.isNullOrEmpty(Flash.current())) {
                    Flash.current().error("任务:" + taskTimer.getTaskClass() + "不存在");
                }
                throw new Exception("该任务不存在");
            } else if (UtilTools.isNullOrEmpty(taskTimer.getTaskStatus()) || UtilTools.isNullOrEmpty(taskTimer.getTaskClass())) {
                if (!UtilTools.isNullOrEmpty(Flash.current())) {
                    Flash.current().error("任务:" + taskTimer.getTaskClass() + "不合法");
                }
                throw new Exception("该任务不合法");
            } else if (TaskStatus.RUNNING.equals(taskTimer.getTaskStatus().toString())) {
                if (!UtilTools.isNullOrEmpty(Flash.current())) {
                    Flash.current().error("任务:" + taskTimer.getTaskClass() + "正在运行");
                }
                throw new Exception("该任务正在运行");
            } else {
                Object obj = null;
                try {
                    obj = CoreServicesFactory.getBean(taskTimer.getTaskClass());
                } catch (BeansException e) {
                    if (!UtilTools.isNullOrEmpty(Flash.current())) {
                        Flash.current().error("无法找到任务类：" + taskTimer.getTaskClass());
                    }
                    return;
                }
                if (UtilTools.isNullOrEmpty(obj)) {
                    if (!UtilTools.isNullOrEmpty(Flash.current())) {
                        Flash.current().error("任务:" + taskTimer.getTaskClass() + "不合法");
                    }
                    throw new Exception("该任务不合法");
                } else {
                    BaseJob job = (BaseJob) obj;
                    job.reSet();
                    if (!(taskTimer.getIsTiming() == 1)) { // 非定时任务
                        Long delayDate = null;
                        Long intervalDate = null;
                        delayDate = job.getDelayDate(Constants.AUTO_TASK_DELAY_DATE_KEY);
                        intervalDate = job.getIntervalDate(Constants.AUTO_TASK_INTERVAL_DATE_KEY);
                        JobDetail jobDetail = newJob(job.getClass()).withIdentity(taskTimer.getTaskClass(), Scheduler.DEFAULT_GROUP).build();
                        Trigger trigger = newTrigger()
                                .withIdentity(intervalDate + "_" + taskTimer.getTaskClass(), Scheduler.DEFAULT_GROUP)
                                .startAt(new Date(new Date().getTime() + delayDate))
                                .withSchedule(simpleSchedule().withIntervalInMilliseconds(intervalDate).repeatForever())
                                .build();
                        scheduler.scheduleJob(jobDetail, trigger);
                        log.info("已调度非定时任务" + taskTimer.getTaskClass());
                    } else { // 定时任务
                        List<BaseJob.TimingDate> timingDates = job.getTimingDates(Constants.AUTO_TASK_TIMING_DATE_KEY);
                        if (!UtilTools.isNullOrEmpty(timingDates)) {
                            JobDetail jobDetail = newJob(job.getClass())
                                    .withIdentity(taskTimer.getTaskClass(), Scheduler.DEFAULT_GROUP)
                                    .storeDurably(true)
                                    .build();
                            scheduler.addJob(jobDetail, false);
                            for (BaseJob.TimingDate timingDate : timingDates) {
                                Trigger trigger = newTrigger()
                                        .forJob(jobDetail)
                                        .withIdentity(timingDate.hour + "_" + timingDate.min + "_" + taskTimer.getTaskClass(), Scheduler.DEFAULT_GROUP)
                                        .withSchedule(dailyAtHourAndMinute(timingDate.hour, timingDate.min))
                                        .build();
                                scheduler.scheduleJob(trigger);
                            }
                            log.info("已调度定时任务" + taskTimer.getTaskClass());
                        } else {
                            if (!UtilTools.isNullOrEmpty(Flash.current())) {
                                Flash.current().error("任务:" + taskTimer.getTaskClass() + "为定时任务，请设置定时时间");
                            }
                            return;
                        }
                    }
                    // 设置相关信息
                    taskTimer.setTaskStatus(TaskStatus.RUNNING);
                    taskTimer.setVersion("master");
                    if (!UtilTools.isNullOrEmpty(Flash.current())) {
                        Flash.current().clear();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopTaskById(Integer id, String username, String ip) {
        try {
            // 获取待执行的任务
            TaskTimer taskTimer = getById(Integer.valueOf(id));
            if (UtilTools.isNullOrEmpty(taskTimer)) {
                throw new Exception("该任务不存在");
            } else if (UtilTools.isNullOrEmpty(taskTimer.getTaskStatus())
                    || UtilTools.isNullOrEmpty(taskTimer.getTaskClass())) {
                throw new Exception("该任务不合法");
            } else if (TaskStatus.STOPPING.equals(taskTimer.getTaskStatus().toString())) {
                throw new Exception("该任务已经停止");
            } else {
                // 获取要运行的任务类
                Object obj = CoreServicesFactory.getBean(taskTimer.getTaskClass());
                if (UtilTools.isNullOrEmpty(obj)) {
                    throw new Exception("该任务不合法");
                } else {
                    BaseJob job = (BaseJob) obj;
                    if (!(taskTimer.getIsTiming() == 1)) { // 非定时任务
                        Long intervalDate = job.getIntervalDate(Constants.AUTO_TASK_INTERVAL_DATE_KEY);
                        TriggerKey triggerKey = new TriggerKey(intervalDate + "_" + taskTimer.getTaskClass());
                        scheduler.unscheduleJob(triggerKey);
                        log.info("已取消非定时任务" + taskTimer.getTaskClass());
                    } else { // 定时任务
                        List<BaseJob.TimingDate> timingDates = job.getTimingDates(Constants.AUTO_TASK_TIMING_DATE_KEY);
                        if (!UtilTools.isNullOrEmpty(timingDates)) {
                            for (BaseJob.TimingDate timingDate : timingDates) {
                                TriggerKey triggerKey = new TriggerKey(timingDate.hour + "_" + timingDate.min + "_" + taskTimer.getTaskClass());
                                scheduler.unscheduleJob(triggerKey);
                            }
                            scheduler.deleteJob(new JobKey(taskTimer.getTaskClass()));
                            log.info("已取消定时任务" + taskTimer.getTaskClass());
                        }
                    }
                    taskTimer.setTaskStatus(TaskStatus.STOPPING);
                    // 如果任务启动不是当前机器，不能重启
                    String isMaster = "master";
                    if (!isMaster.equalsIgnoreCase(taskTimer.getVersion())) {
                        return;
                    }
                    taskTimer.setVersion(isMaster);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
