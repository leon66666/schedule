package com.hoomsun.schedule.service.job;

import com.hoomsun.common.Constants;
import com.hoomsun.common.ReflectUtils;
import com.hoomsun.schedule.dao.TaskTimerParamDAO;
import com.hoomsun.schedule.service.CoreServicesFactory;
import com.hoomsun.service.cache.impl.RedisServiceImpl;
import com.hoomsun.util.UtilTools;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseJob implements Job {
    private static Log log = LogFactory.getLog(BaseJob.class);
    /**
     * 这些日志执行太频繁,减少输出次数
     */
    private static Map<String, Integer> logEscapeList = new HashMap(){{
        put("updateDelayJob", 0);
        put("refreshDelayPointJob", 0);
        put("updateFinancePlanByFullJob", 0);
        put("autoLoanTransferListJob", 0);
        put("startAndFailLoanJob", 0);
        put("transactionStatusJob", 0);
        put("updateAccountBalanceJob", 0);
    }};

    protected int isTiming;
    // 延迟时间
    private Long delayDate = Constants.AUTO_TASK_DEFAULT_DELAY_DATE_VALUE;
    // 间隔时间
    private Long intervalDate = Constants.AUTO_TASK_DEFAULT_INTERVAL_DATE_VALUE;
    // 定时时间
    private List<TimingDate> timingDates = new ArrayList<TimingDate>();
    // 定时时间类
    public class TimingDate {
        public TimingDate(Integer hour, Integer min) {
            this.hour = hour;
            this.min = min;
        }
        public Integer hour;
        public Integer min;
    }
    // 运行次数
    protected Integer count = 0;
    // 任务执行结果
    protected Map<String, Object> mapResult = new HashMap<String, Object>();
    // 任务参数
    protected Map<String, String> mapPram;
    // 任务执行上下文
    protected JobExecutionContext context;
    
    /**
     * 查看列表是否存在该参数，存在的话返回参数值
     * 
     * @param paramName 参数对应key
     * @return 参数值
     */
    public String isHaveParam(String paramName) {
        if (UtilTools.isNullOrEmpty(getMapPram())) {
            return null;
        }
        return !UtilTools.isNullOrEmpty(getMapPram().get(paramName)) ? getMapPram().get(paramName) : null;
    }
    
    /**
     * 更新运行次数
     */
    public void updateRunCount() {
        // 封装运行次数
        String className = this.getClassName();
        Map<String, Object> mr = this.getMapResult();
        mr.put("className", className);
        mr.put(className, ++this.count);
        if (!logEscapeList.keySet().contains(className)) { // 防止频繁执行的任务刷屏
            log.info("任务" + className + "执行了" + mr.get(className) + "次");
        } else {
            if (logEscapeList.get(className) % 100 == 0) {
                log.info("任务" + className + "已经执行了" + mr.get(className) + "次");
            }
            logEscapeList.put(className, logEscapeList.get(className) + 1);
        }
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        setJobExecutionContext(context);
        String className = getClassName();
        RedisServiceImpl redisServiceImpl = (RedisServiceImpl)CoreServicesFactory.getBean("redisServiceImpl");
        BaseJob job = (BaseJob)CoreServicesFactory.getBean(className);
        try {
            // 如果此任务已经在运行中则直接返回，并且不刷新运行次数
            String isStop = redisServiceImpl.getStr(Constants.KEYPREFIX_STOP_FLAG_IN_REDIS_, className);
            if (!UtilTools.isNullOrEmpty(isStop) && Boolean.FALSE.toString().equalsIgnoreCase(isStop)) {
                return;
            }
            
            redisServiceImpl.setStr(Constants.KEYPREFIX_STOP_FLAG_IN_REDIS_, className, "false", 60 * 60 * 24 * 7);
            if (!logEscapeList.keySet().contains(className)) { // 防止频繁执行的任务刷屏
                log.info("执行任务：" + className);
            }
            job.setJobExecutionContext(context);
            job.run();
            job.updateRunCount();
            redisServiceImpl.del(Constants.KEYPREFIX_STOP_FLAG_IN_REDIS_, className);
        } catch (Exception e) {
            log.error("任务执行出错", e);
            try {
                redisServiceImpl.del(Constants.KEYPREFIX_STOP_FLAG_IN_REDIS_, className);
            } catch (Exception ex) {
                log.error("无法连接Redis", ex);
            }
        }
    }

    /**
     * 记录日志aop由此方法切入
     * 具体的任务类需要在此方法中实现业务逻辑
     */
    public abstract void run();
    
    public String getClassName() {
        return ReflectUtils.getClassNameInIoc(this.getClass().getName());
    }
    
    /**
     * 获取参数列表
     */
    public Map<String, String> getMapPram() {
        TaskTimerParamDAO taskTimerParamDAO = (TaskTimerParamDAO)CoreServicesFactory.getBean("taskTimerParamDAO");
        // 获取参数列表
        String className = getClassName();
        mapPram = new HashMap<String, String>();
        List<Map<String, String>> list = taskTimerParamDAO.findParamByClassName(className);
        // 封装参数
        for (Map<String, String> mapParam : list) {
            mapPram.put(mapParam.get("key"), mapParam.get("value"));
        }
        return mapPram;
    }
    
    /**
     * 为参数列表赋值
     */
    public void setMapPram(Map<String, String> mapPram) {
        this.mapPram = mapPram;
    }
    
    /**
     * 设置定时任务执行时间
     */
    public void setTimingDates(List<TimingDate> timingDates) {
        this.timingDates = timingDates;
    }
    
    /**
     * 获取定时任务执行时间
     */
    public List<TimingDate> getTimingDates() {
        return this.timingDates;
    }
    
    /**
     * 获取定时任务执行时间
     */
    public List<TimingDate> getTimingDates(String timingDateKey) {
        timingDates.clear();
        String str = isHaveParam(timingDateKey);
        if (!UtilTools.isNullOrEmpty(str)) {
            String[] arr = str.split(",");
            if (!UtilTools.isNullOrEmpty(arr)) {
                for (String timingDateStr : arr) {
                    try {
                        String[] temp = timingDateStr.split(":");
                        int hour = 0, min = 0;
                        try {
                            hour = Integer.valueOf(temp[0]) % 24;
                            if (temp.length > 1) {
                                min = Integer.valueOf(temp[1]) % 60;
                            }
                        } catch (Exception e) {
                            log.error("定时时间格式错误", e);
                        }
                        timingDates.add(new TimingDate(hour, min));
                    } catch (Exception e) {
                        log.error("定时时间格式错误", e);
                    }
                }
            }
        }
        return timingDates;
    }
    
    /**
     * 设置任务延迟执行时间
     */
    public void setDelayDate(Long delayDate) {
        this.delayDate = delayDate;
    }
    
    /**
     * 获取任务延迟执行时间
     */
    public Long getDelayDate() {
        return this.delayDate;
    }

    /**
     * 获取任务延迟执行时间
     */
    public Long getDelayDate(String delayDateKey) {
        // 任务的延迟执行时间
        String delaydateStr = isHaveParam(delayDateKey);
        if (UtilTools.isNullOrEmpty(delaydateStr))
            return getDelayDate();
        return Long.valueOf(delaydateStr);
    }
    
    /**
     * 设置任务间隔执行时间
     */
    public void setIntervalDate(Long intervalDate) {
        this.intervalDate = intervalDate;
    }
    
    /**
     * 获取任务间隔执行时间
     */
    public Long getIntervalDate() {
        return this.intervalDate;
    }
    
    /**
     * 获取任务间隔执行时间
     */
    public Long getIntervalDate(String intervalDateKey) {
        // 总是尝试获取最新的参数，为空则使用默认值
        String str = isHaveParam(intervalDateKey);
        if (UtilTools.isNullOrEmpty(str)) {
            return getIntervalDate();
        }
        return Long.valueOf(str);
    }

    /**
     * 重置任务执行次数
     */
    public void reSet() {
        this.count = 0;
        Map<String, Object> mr = this.getMapResult();
        mr.put("className", getClassName());
        mr.put(getClassName(), 0);
    }
    
    /**
     * 获取任务运行反馈map
     */
    public Map<String, Object> getMapResult() {
        return this.mapResult;
    }
    
    public void setJobExecutionContext(JobExecutionContext context) {
        this.context = context;
    }
    
    public JobExecutionContext getJobExecutionContext() {
        return this.context;
    }
}
