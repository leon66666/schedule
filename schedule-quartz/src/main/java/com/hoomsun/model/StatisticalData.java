package com.hoomsun.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: 郑栋文
 * @Description:
 * @Date: Created in 15:35 2017/8/30
 */
public class StatisticalData implements Serializable {


    private static final long serialVersionUID = 438610084169974794L;
    // 注册用户数	期间注册用户中理财用户
    private Integer registerLenderCount;

    // 开户、实名、绑卡数	期间完成开户的理财用户
    private Integer openAccLenderCount;

    // 首次充值用户数	首次充值用户（这期间内）
    private Integer firstRechargeUserCount;

    // 首次成交用户数	首次成交（购买理财产品）用户（散标、红利计划）
    private Integer firstDealUserCount;

    // 网站UV	全站新老用户独立访问数（去除cookie）
    // 前端弄

    // 登录用户数	期间发生登录官网的用户人数
    private Integer loginLenderCount;

    // 成交用户数	期间购买所有理财产品的去重用户数（散标+红利）
    private Integer dealUserDistinctCount;

    // 散标	其中购买散标投资人数
    private Integer investLoanLenderCount;

    // 3 6 12月期	其中购买3 6 12月期红利计划人数
    private Integer investThreeFinancePlanCount;
    private Integer investSixFinancePlanCount;
    private Integer investTwelveFinancePlanCount;

    // 兑付金额	期间到期的计划总金额（不含利息）
    private BigDecimal expireFinancePlanAmount;

    // 兑付金额	3 6 12月期	其中包含3 6 12月期计划总金额（不含利息）
    private BigDecimal threePhasePaymentAmount;
    private BigDecimal sixPhasePaymentAmount;
    private BigDecimal twelvePhasePaymentAmount;

    // 用户账户余额	期末用户账户可用余额
    private BigDecimal lenderAccBalance;

    // 计划余额	期末计划子账户可用余额
    private BigDecimal financePlanEndingBalance;

    // 3 6 12月期余额	期末3 6 12月期子账户可用余额
    private BigDecimal threePhaseFinancePlanEndingBalance;
    private BigDecimal sixPhaseFinancePlanEndingBalance;
    private BigDecimal twelvePhaseFinancePlanEndingBalance;

    // 充值金额	期间理财人充值金额
    private BigDecimal lenderRechargeAmount;
    //select sum(amount) from recharge_log r, user_ u where r.user_ = u.userId and rechargeTime >= '2017-07-01' and rechargeTime < '2017-08-31' and u.intention = 'LENDER';

    // 充值笔数	期间理财人充值笔数
    private Integer lenderRechargeCount;
    //select count(rechargeLogId) from recharge_log r, user_ u where r.user_ = u.userId and rechargeTime >= '2017-07-01' and rechargeTime < '2017-08-31' and u.intention = 'LENDER';

    // 提现金额	期间理财人总提现金额
    private BigDecimal lenderCashDrawAmount;
    //select sum(amount) from cash_draw_log c, user_ u where c.user_ = u.userId and applyTime >= '2017-07-01' and applyTime < '2017-08-31' and cash_type = 'WEB_DRAWCASH' and cash_draw_status = 'CASHDRAW_SUCCESS' and u.intention = 'LENDER';

    // 提现笔数	期间理财人提现总笔数
    private Integer lenderCashDrawCount;
    //select count(cashDrawId) from cash_draw_log c, user_ u where c.user_ = u.userId and applyTime >= '2017-07-01' and applyTime < '2017-08-31' and cash_type = 'WEB_DRAWCASH' and cash_draw_status = 'CASHDRAW_SUCCESS' and u.intention = 'LENDER';

    // 借款成交笔数	期间借款人借款笔数
    private Integer durationBorrowingsCount;

    // 借款成交金额	借款人借款总金额
    private BigDecimal durationBorrowingAmount;

    // 还款笔数	期间借款人还款笔数
    private Integer repaymentCount;

    // 还款本金	期间借款人还款本金
    private BigDecimal repaymentPrincipal;

    // 还款利息	期间借款人还款利息
    private BigDecimal repaymentInterestAmount;

    // 净增资产本金余额	期间新增借款成交本金-期间借款还款本金
    private BigDecimal netPrincipalBalance;

    // 资产本金余额	期末借款剩余本金
    private BigDecimal endingPrincipalBalance;
    //select sum(finalPrincipal) from borrow_repay_record where dueDate >= '2017-07-01' and dueDate < '2017-10-31';

    // 计划资金存量	未到期计划实际加入金额总和
    private BigDecimal notDueFinancePlanAmount;

    // 3 6 12月期存量	其中3 6 12月期未到期计划实际加入金额总和
    private BigDecimal threePhaseFinancePlanNotDueActualJoinAmount;
    private BigDecimal sixPhaseFinancePlanNotDueActualJoinAmount;
    private BigDecimal twelvePhaseFinancePlanNotDueActualJoinAmount;

    public Integer getRegisterLenderCount() {
        return registerLenderCount;
    }

    public void setRegisterLenderCount(Integer registerLenderCount) {
        this.registerLenderCount = registerLenderCount;
    }

    public Integer getOpenAccLenderCount() {
        return openAccLenderCount;
    }

    public void setOpenAccLenderCount(Integer openAccLenderCount) {
        this.openAccLenderCount = openAccLenderCount;
    }

    public Integer getFirstRechargeUserCount() {
        return firstRechargeUserCount;
    }

    public void setFirstRechargeUserCount(Integer firstRechargeUserCount) {
        this.firstRechargeUserCount = firstRechargeUserCount;
    }

    public Integer getFirstDealUserCount() {
        return firstDealUserCount;
    }

    public void setFirstDealUserCount(Integer firstDealUserCount) {
        this.firstDealUserCount = firstDealUserCount;
    }

    public Integer getLoginLenderCount() {
        return loginLenderCount;
    }

    public void setLoginLenderCount(Integer loginLenderCount) {
        this.loginLenderCount = loginLenderCount;
    }

    public Integer getDealUserDistinctCount() {
        return dealUserDistinctCount;
    }

    public void setDealUserDistinctCount(Integer dealUserDistinctCount) {
        this.dealUserDistinctCount = dealUserDistinctCount;
    }

    public Integer getInvestLoanLenderCount() {
        return investLoanLenderCount;
    }

    public void setInvestLoanLenderCount(Integer investLoanLenderCount) {
        this.investLoanLenderCount = investLoanLenderCount;
    }

    public Integer getInvestThreeFinancePlanCount() {
        return investThreeFinancePlanCount;
    }

    public void setInvestThreeFinancePlanCount(Integer investThreeFinancePlanCount) {
        this.investThreeFinancePlanCount = investThreeFinancePlanCount;
    }

    public Integer getInvestSixFinancePlanCount() {
        return investSixFinancePlanCount;
    }

    public void setInvestSixFinancePlanCount(Integer investSixFinancePlanCount) {
        this.investSixFinancePlanCount = investSixFinancePlanCount;
    }

    public Integer getInvestTwelveFinancePlanCount() {
        return investTwelveFinancePlanCount;
    }

    public void setInvestTwelveFinancePlanCount(Integer investTwelveFinancePlanCount) {
        this.investTwelveFinancePlanCount = investTwelveFinancePlanCount;
    }

    public BigDecimal getExpireFinancePlanAmount() {
        return expireFinancePlanAmount;
    }

    public void setExpireFinancePlanAmount(BigDecimal expireFinancePlanAmount) {
        this.expireFinancePlanAmount = expireFinancePlanAmount;
    }

    public BigDecimal getThreePhasePaymentAmount() {
        return threePhasePaymentAmount;
    }

    public void setThreePhasePaymentAmount(BigDecimal threePhasePaymentAmount) {
        this.threePhasePaymentAmount = threePhasePaymentAmount;
    }

    public BigDecimal getSixPhasePaymentAmount() {
        return sixPhasePaymentAmount;
    }

    public void setSixPhasePaymentAmount(BigDecimal sixPhasePaymentAmount) {
        this.sixPhasePaymentAmount = sixPhasePaymentAmount;
    }

    public BigDecimal getTwelvePhasePaymentAmount() {
        return twelvePhasePaymentAmount;
    }

    public void setTwelvePhasePaymentAmount(BigDecimal twelvePhasePaymentAmount) {
        this.twelvePhasePaymentAmount = twelvePhasePaymentAmount;
    }

    public BigDecimal getLenderAccBalance() {
        return lenderAccBalance;
    }

    public void setLenderAccBalance(BigDecimal lenderAccBalance) {
        this.lenderAccBalance = lenderAccBalance;
    }

    public BigDecimal getFinancePlanEndingBalance() {
        return financePlanEndingBalance;
    }

    public void setFinancePlanEndingBalance(BigDecimal financePlanEndingBalance) {
        this.financePlanEndingBalance = financePlanEndingBalance;
    }

    public BigDecimal getThreePhaseFinancePlanEndingBalance() {
        return threePhaseFinancePlanEndingBalance;
    }

    public void setThreePhaseFinancePlanEndingBalance(BigDecimal threePhaseFinancePlanEndingBalance) {
        this.threePhaseFinancePlanEndingBalance = threePhaseFinancePlanEndingBalance;
    }

    public BigDecimal getSixPhaseFinancePlanEndingBalance() {
        return sixPhaseFinancePlanEndingBalance;
    }

    public void setSixPhaseFinancePlanEndingBalance(BigDecimal sixPhaseFinancePlanEndingBalance) {
        this.sixPhaseFinancePlanEndingBalance = sixPhaseFinancePlanEndingBalance;
    }

    public BigDecimal getTwelvePhaseFinancePlanEndingBalance() {
        return twelvePhaseFinancePlanEndingBalance;
    }

    public void setTwelvePhaseFinancePlanEndingBalance(BigDecimal twelvePhaseFinancePlanEndingBalance) {
        this.twelvePhaseFinancePlanEndingBalance = twelvePhaseFinancePlanEndingBalance;
    }

    public BigDecimal getLenderRechargeAmount() {
        return lenderRechargeAmount;
    }

    public void setLenderRechargeAmount(BigDecimal lenderRechargeAmount) {
        this.lenderRechargeAmount = lenderRechargeAmount;
    }

    public Integer getLenderRechargeCount() {
        return lenderRechargeCount;
    }

    public void setLenderRechargeCount(Integer lenderRechargeCount) {
        this.lenderRechargeCount = lenderRechargeCount;
    }

    public BigDecimal getLenderCashDrawAmount() {
        return lenderCashDrawAmount;
    }

    public void setLenderCashDrawAmount(BigDecimal lenderCashDrawAmount) {
        this.lenderCashDrawAmount = lenderCashDrawAmount;
    }

    public Integer getLenderCashDrawCount() {
        return lenderCashDrawCount;
    }

    public void setLenderCashDrawCount(Integer lenderCashDrawCount) {
        this.lenderCashDrawCount = lenderCashDrawCount;
    }

    public Integer getDurationBorrowingsCount() {
        return durationBorrowingsCount;
    }

    public void setDurationBorrowingsCount(Integer durationBorrowingsCount) {
        this.durationBorrowingsCount = durationBorrowingsCount;
    }

    public BigDecimal getDurationBorrowingAmount() {
        return durationBorrowingAmount;
    }

    public void setDurationBorrowingAmount(BigDecimal durationBorrowingAmount) {
        this.durationBorrowingAmount = durationBorrowingAmount;
    }

    public Integer getRepaymentCount() {
        return repaymentCount;
    }

    public void setRepaymentCount(Integer repaymentCount) {
        this.repaymentCount = repaymentCount;
    }

    public BigDecimal getRepaymentPrincipal() {
        return repaymentPrincipal;
    }

    public void setRepaymentPrincipal(BigDecimal repaymentPrincipal) {
        this.repaymentPrincipal = repaymentPrincipal;
    }

    public BigDecimal getRepaymentInterestAmount() {
        return repaymentInterestAmount;
    }

    public void setRepaymentInterestAmount(BigDecimal repaymentInterestAmount) {
        this.repaymentInterestAmount = repaymentInterestAmount;
    }

    public BigDecimal getNetPrincipalBalance() {
        return netPrincipalBalance;
    }

    public void setNetPrincipalBalance(BigDecimal netPrincipalBalance) {
        this.netPrincipalBalance = netPrincipalBalance;
    }

    public BigDecimal getEndingPrincipalBalance() {
        return endingPrincipalBalance;
    }

    public void setEndingPrincipalBalance(BigDecimal endingPrincipalBalance) {
        this.endingPrincipalBalance = endingPrincipalBalance;
    }

    public BigDecimal getNotDueFinancePlanAmount() {
        return notDueFinancePlanAmount;
    }

    public void setNotDueFinancePlanAmount(BigDecimal notDueFinancePlanAmount) {
        this.notDueFinancePlanAmount = notDueFinancePlanAmount;
    }

    public BigDecimal getThreePhaseFinancePlanNotDueActualJoinAmount() {
        return threePhaseFinancePlanNotDueActualJoinAmount;
    }

    public void setThreePhaseFinancePlanNotDueActualJoinAmount(BigDecimal threePhaseFinancePlanNotDueActualJoinAmount) {
        this.threePhaseFinancePlanNotDueActualJoinAmount = threePhaseFinancePlanNotDueActualJoinAmount;
    }

    public BigDecimal getSixPhaseFinancePlanNotDueActualJoinAmount() {
        return sixPhaseFinancePlanNotDueActualJoinAmount;
    }

    public void setSixPhaseFinancePlanNotDueActualJoinAmount(BigDecimal sixPhaseFinancePlanNotDueActualJoinAmount) {
        this.sixPhaseFinancePlanNotDueActualJoinAmount = sixPhaseFinancePlanNotDueActualJoinAmount;
    }

    public BigDecimal getTwelvePhaseFinancePlanNotDueActualJoinAmount() {
        return twelvePhaseFinancePlanNotDueActualJoinAmount;
    }

    public void setTwelvePhaseFinancePlanNotDueActualJoinAmount(BigDecimal twelvePhaseFinancePlanNotDueActualJoinAmount) {
        this.twelvePhaseFinancePlanNotDueActualJoinAmount = twelvePhaseFinancePlanNotDueActualJoinAmount;
    }
}
