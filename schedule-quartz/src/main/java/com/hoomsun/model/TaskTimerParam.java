package com.hoomsun.model;

import javacommon.base.BaseEntity;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * TaskTimerParam entity.
 * 
 * 
 */
@Entity
@Table(name = "task_timer_param")
public class TaskTimerParam extends BaseEntity implements java.io.Serializable {
    //alias
    public static final String TABLE_ALIAS = "参数列表";
    public static final String ALIAS_ID = "参数编号";
    public static final String ALIAS_TASK_ID = "所对应任务";
    public static final String ALIAS_PARAM_KEY = "参数键（单位：ms）";
    public static final String ALIAS_PARAM_KEY_TIMING = "每日执行时间（单位：h）";
    public static final String ALIAS_PARAM_VALUE = "参数值";
    public static final String ALIAS_DISPLAY_NAME = "参数显示名字";
    public static final String ALIAS_PARAM_DESC = "参数描述";
    // Fields
    private static final long serialVersionUID = -5752889925466419515L;
    private Integer id;
    private Integer taskId; // 参数所对应任务
    private String paramKey; // 参数key
    private String paramValue; // 参数value
    private String displayName; // 参数页面实现名字
    private String taskParamDesc; // 参数描述
    private String creater; // 参数创建时间
    private Date createTime; // 参数创建时间

    // Constructors

    /** default constructor */
    public TaskTimerParam() {
    }

    // Property accessors
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "task_id")
    public Integer getTaskId() {
        return this.taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    @Column(name = "param_key", length = 100)
    public String getParamKey() {
        return this.paramKey;
    }

    public void setParamKey(String paramKey) {
        this.paramKey = paramKey;
    }

    @Column(name = "param_value", length = 100)
    public String getParamValue() {
        return this.paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    @Column(name = "display_name", length = 150)
    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Column(name = "task_param_desc")
    public String getTaskParamDesc() {
        return this.taskParamDesc;
    }

    public void setTaskParamDesc(String desc) {
        this.taskParamDesc = desc;
    }

    @Column(name = "creater", length = 100)
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

}