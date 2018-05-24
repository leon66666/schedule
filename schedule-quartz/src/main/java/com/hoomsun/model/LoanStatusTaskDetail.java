package com.hoomsun.model;

import com.hoomsun.vo.DetailStep;
import com.hoomsun.vo.FeeType;
import com.hoomsun.vo.LoanTaskDetailStatus;
import com.hoomsun.vo.TaskDetailOptType;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 流标、放标任务明细表
 */
@Entity
@Table(name = "loan_status_task_detail")
public class LoanStatusTaskDetail implements java.io.Serializable, Cloneable {

    private static final long serialVersionUID = -6507255925695406962L;

    /**
     * 自增主键
     */
    private Integer taskDetailId;
    /**
     * 任务ID
     */
    private Integer taskId;
    /**
     * 标ID
     */
    private Integer loanId;
    /**
     * 任务明细所处步骤
     */
    private DetailStep detailStep;
    /**
     * 融次人ID
     */
    private Integer borrowerId;
    /**
     * 业务表主键，每种类型的任务明细是确定的
     * 放款相关的任务明细都是loan_lender_record表
     */
    private Integer businessId;
    /**
     * 平台收取费用的费用帐户
     */
    private Integer userId;
    /**
     * 平台收取的费用类型
     */
    private FeeType feeType;
    /**
     * 投标金额
     */
    private double amount;
    /**
     * 红包金额
     */
    private double redAmt;
    /**
     * 任务明细操作类型
     */
    private TaskDetailOptType optType;
    /**
     * 任务明细状态
     */
    private LoanTaskDetailStatus detailStatus;
    /**
     * 调用存管接口时生成的订单ID
     */
    private String orderId;
    /**
     * 存管接口返回的流水号
     */
    private String serialNo;
    /**
     * 返回信息,包括异常信息
     */
    private String returnMsg;
    /**
     * 注释
     */
    private String note;
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
    @Column(name = "task_detail_id", nullable = false)
    public Integer getTaskDetailId() {
        return taskDetailId;
    }

    public void setTaskDetailId(Integer taskDetailId) {
        this.taskDetailId = taskDetailId;
    }

    @Column(name = "task_id")
    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "detail_step")
    public DetailStep getDetailStep() {
        return detailStep;
    }

    public void setDetailStep(DetailStep detailStep) {
        this.detailStep = detailStep;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "detail_status")
    public LoanTaskDetailStatus getDetailStatus() {
        return detailStatus;
    }

    public void setDetailStatus(LoanTaskDetailStatus detailStatus) {
        this.detailStatus = detailStatus;
    }

    @Column(name = "return_msg")
    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    @Column(name = "note")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    @Version
    @Column(name = "version")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void setUpdateTime() {
        this.updateTime = Calendar.getInstance().getTime();
    }

    @Column(name = "borrower_id")
    public Integer getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(Integer borrowerId) {
        this.borrowerId = borrowerId;
    }

    @Column(name = "business_id")
    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    @Column(name = "user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Column(name = "amount")
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Column(name = "red_amt")
    public double getRedAmt() {
        return redAmt;
    }

    public void setRedAmt(double redAmt) {
        this.redAmt = redAmt;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "opt_type")
    public TaskDetailOptType getOptType() {
        return optType;
    }

    public void setOptType(TaskDetailOptType optType) {
        this.optType = optType;
    }

    @Column(name = "order_id")
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Column(name = "loan_id")
    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

    @Column(name = "fee_type")
    @Enumerated(EnumType.STRING)
    public FeeType getFeeType() {
        return feeType;
    }

    public void setFeeType(FeeType feeType) {
        this.feeType = feeType;
    }

    @Column(name = "serial_no")
    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    /**
     * 构建一条理财子帐号投标确认任务
     */
    public static LoanStatusTaskDetail buildFinanceBidCfmTaskDetail(Integer loanId, Integer taskId, Integer borrowerId, Integer userId, Integer businessId, double amount,
                                                                    String note, String orderId, String serialNo) {
        LoanStatusTaskDetail taskDetail = buildBaseTaskDetail(loanId, taskId, borrowerId);
        taskDetail.setUserId(userId);
        taskDetail.setBusinessId(businessId);
        taskDetail.setOptType(TaskDetailOptType.BID_CFM);
        taskDetail.setDetailStep(DetailStep.THREE);
        taskDetail.setOrderId(orderId);
        //改为订单号，因为存管单笔确认接口不再使用流水号确认，统一改为订单号确认
        taskDetail.setSerialNo(orderId);
        taskDetail.setAmount(amount);
        taskDetail.setNote(note == null ? "" : note);
        return taskDetail;
    }

    /**
     * 构建一条普通投标确认任务
     */
    public static LoanStatusTaskDetail buildBidCfmTaskDetail(Integer loanId, Integer taskId, Integer borrowerId, Integer businessId, Integer userId, double amount,
                                                             double redAmt, String note, String orderId, String serialNo) {
        LoanStatusTaskDetail taskDetail = buildBaseTaskDetail(loanId, taskId, borrowerId);
        taskDetail.setBusinessId(businessId);
        taskDetail.setUserId(userId);
        taskDetail.setOptType(TaskDetailOptType.BID_CFM);
        taskDetail.setDetailStep(DetailStep.THREE);
        taskDetail.setAmount(amount);
        taskDetail.setRedAmt(redAmt);
        taskDetail.setNote(note == null ? "" : note);
        taskDetail.setOrderId(orderId);
        //改为订单号，因为存管单笔确认接口不再使用流水号确认，统一改为订单号确认
        taskDetail.setSerialNo(orderId);
        return taskDetail;
    }

    /**
     * 构建一条平台收取费用任务
     *
     * @param loanId
     * @param taskId
     * @param borrowerId
     * @param specialUserId
     * @param amount
     * @param note
     * @param feeType
     * @return
     */
    public static LoanStatusTaskDetail buildFeeTaskDetail(Integer loanId, Integer taskId, Integer borrowerId, Integer specialUserId, double amount,
                                                          String note, FeeType feeType) {
        LoanStatusTaskDetail taskDetail = new LoanStatusTaskDetail();
        taskDetail.setTaskId(taskId);
        taskDetail.setLoanId(loanId);
        taskDetail.setBorrowerId(borrowerId);
        taskDetail.setUserId(specialUserId);
        taskDetail.setOptType(TaskDetailOptType.FEE);
        taskDetail.setDetailStep(DetailStep.SIX);
        taskDetail.setDetailStatus(LoanTaskDetailStatus.INIT);
        taskDetail.setFeeType(feeType);
        taskDetail.setAmount(amount);
        taskDetail.setReturnMsg("");
        taskDetail.setNote(note == null ? "" : note);
        taskDetail.setCreateTime(Calendar.getInstance().getTime());
        taskDetail.setUpdateTime();

        return taskDetail;
    }

    public static LoanStatusTaskDetail buildFREEZE_FEETaskDetail(Integer loanId, Integer taskId, Integer borrowerId, double amount,
                                                                 String note) {
        LoanStatusTaskDetail taskDetail = new LoanStatusTaskDetail();
        taskDetail.setTaskId(taskId);
        taskDetail.setLoanId(loanId);
        taskDetail.setBorrowerId(borrowerId);
        taskDetail.setOptType(TaskDetailOptType.FREEZE_FEE);
        taskDetail.setDetailStep(DetailStep.FIVE);
        taskDetail.setDetailStatus(LoanTaskDetailStatus.INIT);
        taskDetail.setAmount(amount);
        taskDetail.setReturnMsg("");
        taskDetail.setNote(note == null ? "" : note);
        taskDetail.setCreateTime(Calendar.getInstance().getTime());
        taskDetail.setUpdateTime();

        return taskDetail;
    }

    public static LoanStatusTaskDetail buildUnfreezeTaskDetail(Integer loanId, Integer taskId, Integer borrowerId, double amount, String note) {
        LoanStatusTaskDetail taskDetail = new LoanStatusTaskDetail();
        taskDetail.setTaskId(taskId);
        taskDetail.setLoanId(loanId);
        taskDetail.setBorrowerId(borrowerId);
        taskDetail.setOptType(TaskDetailOptType.UNFREEZE);
        taskDetail.setDetailStep(DetailStep.FOUR);
        taskDetail.setDetailStatus(LoanTaskDetailStatus.INIT);
        taskDetail.setAmount(amount);
        taskDetail.setReturnMsg("");
        taskDetail.setNote(note == null ? "" : note);
        taskDetail.setCreateTime(Calendar.getInstance().getTime());
        taskDetail.setUpdateTime();

        return taskDetail;
    }

    /**
     * 生成一条撤销交易子任务
     */
    public static LoanStatusTaskDetail buildBidCancelTaskDetail(Integer loanId, Integer taskId, Integer borrowerId, Integer userId, Integer businessId, double amount, double redAmt, String note, String orderId, String serialNo) {
        LoanStatusTaskDetail taskDetail = buildBaseTaskDetail(loanId, taskId, borrowerId);
        taskDetail.setBusinessId(businessId);
        taskDetail.setOptType(TaskDetailOptType.BID_CANCEL);
        taskDetail.setDetailStep(DetailStep.FOUR);
        taskDetail.setDetailStatus(LoanTaskDetailStatus.INIT);
        taskDetail.setAmount(amount);
        taskDetail.setUserId(userId);
        taskDetail.setRedAmt(redAmt);
        taskDetail.setNote(note == null ? "" : note);
        taskDetail.setOrderId(orderId);
        //统一改为用订单号撤销
        taskDetail.setSerialNo(orderId);
        return taskDetail;
    }

    /**
     * 生成一条完成放款任务明细
     */
    public static LoanStatusTaskDetail buildHengFengConfirmTaskDetail(Integer loanId, Integer taskId, String note) {
        LoanStatusTaskDetail taskDetail = new LoanStatusTaskDetail();
        taskDetail.setTaskId(taskId);
        taskDetail.setLoanId(loanId);
        taskDetail.setOptType(TaskDetailOptType.BID_HengFengConfirm);
        taskDetail.setDetailStep(DetailStep.ONE);
        taskDetail.setDetailStatus(LoanTaskDetailStatus.INIT);
        taskDetail.setAmount(0);
        taskDetail.setReturnMsg("");
        taskDetail.setNote(note == null ? "" : note);
        taskDetail.setCreateTime(Calendar.getInstance().getTime());
        taskDetail.setUpdateTime();

        return taskDetail;
    }

    /**
     * 生成一条完成放款任务明细
     */
    public static LoanStatusTaskDetail buildUPDATE_LOANTaskDetail(Integer loanId, Integer taskId, String note) {
        LoanStatusTaskDetail taskDetail = new LoanStatusTaskDetail();
        taskDetail.setTaskId(taskId);
        taskDetail.setLoanId(loanId);
        taskDetail.setOptType(TaskDetailOptType.UPDATE_LOAN);
        taskDetail.setDetailStep(DetailStep.TWO);
        taskDetail.setDetailStatus(LoanTaskDetailStatus.INIT);
        taskDetail.setAmount(0);
        taskDetail.setReturnMsg("");
        taskDetail.setNote(note == null ? "" : note);
        taskDetail.setCreateTime(Calendar.getInstance().getTime());
        taskDetail.setUpdateTime();
        return taskDetail;
    }

    public static LoanStatusTaskDetail buildUpdateLoanTaskDetail(Integer loanId, Integer taskId, String note) {
        LoanStatusTaskDetail taskDetail = new LoanStatusTaskDetail();
        taskDetail.setTaskId(taskId);
        taskDetail.setLoanId(loanId);
        taskDetail.setOptType(TaskDetailOptType.UPDATE_LOAN);
        taskDetail.setDetailStep(DetailStep.THREE);
        taskDetail.setDetailStatus(LoanTaskDetailStatus.INIT);
        taskDetail.setAmount(0);
        taskDetail.setReturnMsg("");
        taskDetail.setNote(note == null ? "" : note);
        taskDetail.setCreateTime(Calendar.getInstance().getTime());
        taskDetail.setUpdateTime();
        return taskDetail;
    }

    /**
     * 生成一条完成放款任务明细
     */
    public static LoanStatusTaskDetail buildFinishApproveTaskDetail(Integer loanId, Integer taskId, Integer borrowerId, String note) {
        LoanStatusTaskDetail taskDetail = new LoanStatusTaskDetail();
        taskDetail.setTaskId(taskId);
        taskDetail.setLoanId(loanId);
        taskDetail.setBorrowerId(borrowerId);
        taskDetail.setOptType(TaskDetailOptType.FINISH_APPROVE);
        taskDetail.setDetailStep(DetailStep.SEVEN);
        taskDetail.setDetailStatus(LoanTaskDetailStatus.INIT);
        taskDetail.setAmount(0);
        taskDetail.setReturnMsg("");
        taskDetail.setNote(note == null ? "" : note);
        taskDetail.setCreateTime(Calendar.getInstance().getTime());
        taskDetail.setUpdateTime();

        return taskDetail;
    }

    private static LoanStatusTaskDetail buildBaseTaskDetail(Integer loanId, Integer taskId, Integer borrowerId) {
        LoanStatusTaskDetail taskDetail = new LoanStatusTaskDetail();
        taskDetail.setTaskId(taskId);
        taskDetail.setLoanId(loanId);
        taskDetail.setBorrowerId(borrowerId);
        taskDetail.setDetailStatus(LoanTaskDetailStatus.INIT);
        taskDetail.setAmount(0);
        taskDetail.setReturnMsg("");
        taskDetail.setCreateTime(Calendar.getInstance().getTime());
        taskDetail.setUpdateTime();
        return taskDetail;
    }

    public static LoanStatusTaskDetail buildTaskDetail(Integer loanId, Integer taskId, Integer borrowerId, TaskDetailOptType optTyp, DetailStep step, String note, String remark) {
        LoanStatusTaskDetail taskDetail = buildBaseTaskDetail(loanId, taskId, borrowerId);
        taskDetail.setOptType(optTyp);
        taskDetail.setDetailStep(step);
        taskDetail.setNote(note == null ? "" : note);
        taskDetail.setRemark(remark);
        return taskDetail;
    }

    public static LoanStatusTaskDetail buildTaskDetail(Integer loanId, Integer taskId, Integer borrowerId, double amount, TaskDetailOptType optTyp, DetailStep step, String note, String remark) {
        LoanStatusTaskDetail taskDetail = buildTaskDetail(loanId, taskId, borrowerId, optTyp, step, note, remark);
        taskDetail.setAmount(amount);
        return taskDetail;
    }

    /**
     * 生成一条理财子帐号任务明细
     */
    public static LoanStatusTaskDetail buildFinancePlanTaskDetail(Integer loanId, Integer taskId, Integer borrowerId, Integer businessId, TaskDetailOptType optTyp, DetailStep step, String note) {
        LoanStatusTaskDetail taskDetail = buildBaseTaskDetail(loanId, taskId, borrowerId);
        taskDetail.setOptType(optTyp);
        taskDetail.setDetailStep(step);
        taskDetail.setBusinessId(businessId);
        taskDetail.setNote(note == null ? "" : note);
        return taskDetail;
    }

    /**
     * 生成一条理财子帐号任务明细
     */
    public static LoanStatusTaskDetail buildFinancePlanTaskDetail(Integer loanId, Integer taskId, Integer borrowerId, Integer userId, Integer businessId, TaskDetailOptType optTyp, DetailStep step, String note, double amount, double redAmount, String orderId, String serialNo) {
        LoanStatusTaskDetail taskDetail = buildBaseTaskDetail(loanId, taskId, borrowerId);
        taskDetail.setAmount(amount);
        taskDetail.setRedAmt(redAmount);
        taskDetail.setOptType(optTyp);
        taskDetail.setDetailStep(step);
        taskDetail.setUserId(userId);
        taskDetail.setBusinessId(businessId);
        taskDetail.setNote(note == null ? "" : note);
        taskDetail.setOrderId(orderId);
        taskDetail.setSerialNo(serialNo);
        return taskDetail;
    }


    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}
