package wangzhongqiu.model;

import wangzhongqiu.vo.DetailStep;
import wangzhongqiu.vo.LoanTaskStatus;
import wangzhongqiu.vo.LoanTaskType;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "loan_status_task")
public class LoanStatusTask implements java.io.Serializable, Cloneable {

    private static final long serialVersionUID = 5318665301872259117L;

    /**
     * 自增主键
     */
    private Integer taskId;
    /**
     * 标ID
     */
    private Integer loanId;
    /**
     * 借款人ID
     */
    private Integer borrowerId;
    /**
     * 任务类型
     */
    private LoanTaskType type;
    /**
     * 任务共几步，即任务结束步骤
     */
    private DetailStep endStep;
    /**
     * 任务状态
     */
    private LoanTaskStatus status;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 版本戳
     */
    private int version;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "task_id", nullable = false)
    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    @Column(name = "loan_id")
    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

    @Column(name = "borrower_id")
    public Integer getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(Integer borrowerId) {
        this.borrowerId = borrowerId;
    }

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    public LoanTaskType getType() {
        return type;
    }

    public void setType(LoanTaskType type) {
        this.type = type;
    }

    @Column(name = "end_step")
    @Enumerated(EnumType.ORDINAL)
    public DetailStep getEndStep() {
        return endStep;
    }

    public void setEndStep(DetailStep endStep) {
        this.endStep = endStep;
    }

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    public LoanTaskStatus getStatus() {
        return status;
    }

    public void setStatus(LoanTaskStatus status) {
        this.status = status;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_time")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Version
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setUpdateTime() {
        this.updateTime = Calendar.getInstance().getTime();
    }

    /**
     * 构建一个放标任务，分三步：交易确认、平台收取费用、解冻融资人实现放款金额
     *
     * @param loanId
     * @param borrowerId
     * @return
     */
    public static LoanStatusTask buildStartLoanTask(Integer loanId, Integer borrowerId) {
        LoanStatusTask task = new LoanStatusTask();
        task.setLoanId(loanId);
        task.setBorrowerId(borrowerId);
        task.setType(LoanTaskType.START);
        task.setRemark(LoanTaskType.START.toString());
        task.setEndStep(DetailStep.EIGHT);
        task.setStatus(LoanTaskStatus.PROCESSING);
        task.setCreateTime(Calendar.getInstance().getTime());
        task.setUpdateTime();
        return task;
    }

    /**
     * 构建一个提现任务
     * step1:资金从借款人到机构用户
     * step2:完成提现
     *
     * @param loanId
     * @param borrowerId
     * @return
     */
    public static LoanStatusTask buildCashTask(Integer loanId, Integer borrowerId) {
        LoanStatusTask task = new LoanStatusTask();
        task.setLoanId(loanId);
        task.setBorrowerId(borrowerId);
        task.setType(LoanTaskType.CASH);
        task.setRemark(LoanTaskType.CASH.toString());
        task.setEndStep(DetailStep.TWO);
        task.setStatus(LoanTaskStatus.PROCESSING);
        task.setCreateTime(Calendar.getInstance().getTime());
        task.setUpdateTime();
        return task;
    }

    /**
     * 构建一个流标任务
     *
     * @param loanId
     * @param borrowerId
     * @return
     */
    public static LoanStatusTask buildFailLoanTask(Integer loanId, Integer borrowerId, String finalReason) {
        LoanStatusTask task = new LoanStatusTask();
        task.setLoanId(loanId);
        task.setBorrowerId(borrowerId);
        task.setType(LoanTaskType.FAIL);
        String remark = LoanTaskType.FAIL.toString();
        if (finalReason != null && !"".equals(finalReason)) {
            remark = finalReason;
        }
        task.setRemark(remark);
        task.setEndStep(DetailStep.SIX);
        task.setStatus(LoanTaskStatus.PROCESSING);
        task.setCreateTime(Calendar.getInstance().getTime());
        task.setUpdateTime();
        return task;
    }
}
