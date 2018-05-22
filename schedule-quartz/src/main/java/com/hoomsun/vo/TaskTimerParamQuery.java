package com.hoomsun.vo;

import javacommon.base.BaseQuery;

import java.sql.Timestamp;

/**
 * TaskTimerParam entity. 
 */

public class TaskTimerParamQuery extends BaseQuery implements java.io.Serializable {
    private static final long serialVersionUID = -9135322273118233767L;
    private Integer id;
    private Integer taskId;
    private String paramKey;
    private String paramValue;
    private String displayName;
    private String taskParamDesc;
    private String creater;
    private Timestamp createrTime;

    // Constructors

    // Property accessors

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getParamKey() {
        return this.paramKey;
    }

    public void setParamKey(String paramKey) {
        this.paramKey = paramKey;
    }

    public String getParamValue() {
        return this.paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getTaskParamDesc() {
        return this.taskParamDesc;
    }

    public void setTaskParamDesc(String taskParamDesc) {
        this.taskParamDesc = taskParamDesc;
    }

    public String getCreater() {
        return this.creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public Timestamp getCreaterTime() {
        return this.createrTime;
    }

    public void setCreaterTime(Timestamp createrTime) {
        this.createrTime = createrTime;
    }

}