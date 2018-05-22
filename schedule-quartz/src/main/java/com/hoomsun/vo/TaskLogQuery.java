package com.hoomsun.vo;

import javacommon.base.BaseQuery;

import java.util.Date;

/**
 * TaskLog entity.
 * 
 * 
 */
public class TaskLogQuery extends BaseQuery implements java.io.Serializable {
    private static final long serialVersionUID = 6598399550186562310L;
    private Integer id;
    private Integer taskId;
    private Date startTime;
    private Date endTime;
    private String version;
    private String taskLogDesc;
    private Date createrTime;
    private String taskName;
    private String operateType;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTaskId() {
        return this.taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Date getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTaskLogDesc() {
        return this.taskLogDesc;
    }

    public void setTaskLogDesc(String desc) {
        this.taskLogDesc = desc;
    }

    public Date getCreaterTime() {
        return this.createrTime;
    }

    public void setCreaterTime(Date createrTime) {
        this.createrTime = createrTime;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

}