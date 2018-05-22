package com.hoomsun.model;

import com.hoomsun.common.TaskStatus;
import javacommon.base.BaseEntity;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * TaskTimer entity.
 * 
 * 
 */
@Entity
@Table(name = "task_timer")
public class TaskTimer extends BaseEntity implements java.io.Serializable, Cloneable {
    /**
     * 
     */
    private static final long serialVersionUID = -7705278687189345765L;
    //alias
    public static final String TABLE_ALIAS = "自动任务";
    public static final String ALIAS_ID = "任务编号";
    public static final String ALIAS_TASK_NAME = "任务名称";
    public static final String ALIAS_TASK_CLASS = "任务类名";
    public static final String ALIAS_TASK_STATUS = "任务状态";
    public static final String ALIAS_ENABLE = "是否启用";
    public static final String ALIAS_IS_TIMING = "是否定时任务";
    public static final String ALIAS_SERVER_IP = "任务地址";
    public static final String ALIAS_VERSION = "任务版本";
    public static final String ALIAS_TASK_DESC = "任务描述";
    // Fields
    private Integer id;
    private String taskName; // 任务名称
    private String taskClass; // 任务对应类
    private TaskStatus taskStatus; // 任务状态
    private Integer isTiming; // 是否定时任务
    private Integer enable;
    private String serverIp; // 任务所在服务器IP
    private String version; // 任务对应版本
    private String taskDesc; // 任务描述
    private String creater; // 任务创建人
    private Date createTime; // 任务创建时间

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "task_name", length = 100)
    public String getTaskName() {
        return this.taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    @Column(name = "task_class", nullable = false, length = 150)
    public String getTaskClass() {
        return this.taskClass;
    }

    public void setTaskClass(String taskClass) {
        this.taskClass = taskClass;
    }

    @Column(name = "task_status")
    @Enumerated(EnumType.STRING)
    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    @Column(name = "server_ip", length = 100)
    public String getServerIp() {
        return this.serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    @Column(name = "version", length = 50)
    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Column(name = "enable")
    public Integer getEnable() {
        return enable;
    }

    @Column(name = "is_timing")
    public Integer getIsTiming() {
        return isTiming;
    }

    public void setIsTiming(Integer isTiming) {
        this.isTiming = isTiming;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    @Column(name = "task_desc")
    public String getTaskDesc() {
        return this.taskDesc;
    }

    public void setTaskDesc(String desc) {
        this.taskDesc = desc;
    }

    @Column(name = "creater", length = 50)
    public String getCreater() {
        return this.creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    @Column(name = "create_time", length = 19)
    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return this.getTaskName();
    }

}