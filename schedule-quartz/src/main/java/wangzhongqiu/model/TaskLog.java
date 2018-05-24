package wangzhongqiu.model;

import javacommon.base.BaseEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * TaskLog entity. 
 */
@Entity
@Table(name = "task_log")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class TaskLog extends BaseEntity implements java.io.Serializable {
    private static final long serialVersionUID = -3165262268387398669L;
    private Integer id;
    private Integer taskId;
    private Date startTime;
    private Date endTime;
    private String version;
    private String serverIp;
    private String taskLogDesc;
    private String operateType;
    private Date createTime;
    private String creater;

    // Constructors

    /** default constructor */
    public TaskLog() {
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

    @Column(name = "start_time", length = 19)
    public Date getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Column(name = "end_time", length = 19)
    public Date getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Column(name = "operate_type")
    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    @Column(name = "version", length = 50)
    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Column(name = "server_ip", length = 50)
    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    @Column(name = "task_log_desc")
    public String getTaskLogDesc() {
        return this.taskLogDesc;
    }

    public void setTaskLogDesc(String desc) {
        this.taskLogDesc = desc;
    }

    @Column(name = "create_time", length = 19)
    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createrTime) {
        this.createTime = createrTime;
    }

    @Column(name = "creater", length = 50)
    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

}