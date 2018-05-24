package wangzhongqiu.schedule.dao;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: 郑栋文
 * @Description:
 * @Date: Created in 15:36 2017/8/30
 */
@Repository
public class StatisticalDao extends HibernateDaoSupport {

    private static SimpleDateFormat YYYY_MM_DD_HH_MM_SS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat YYYYMMDD = new SimpleDateFormat("yyyyMMdd");

    @Autowired
    public void init(SessionFactory sessionFactory) {
        setSessionFactory(sessionFactory);
    }

    /**
     * 获取 bigDecimal 值
     *
     * @param sql
     * @return
     */
    private BigDecimal getBigDecimal(String sql) {
        Query query = getSession().createSQLQuery(sql);
        BigDecimal bigDecimal = (BigDecimal) query.uniqueResult();
        if (bigDecimal != null) {
            return bigDecimal;
//            return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_EVEN);
        }
        return BigDecimal.ZERO;
    }

    /**
     * 获取数量
     *
     * @param sql
     * @return
     */
    private int getCount(String sql) {
        Query query = getSession().createSQLQuery(sql);
        BigInteger num = (BigInteger) query.uniqueResult();
        return num.intValue();
    }


//    -- 全站新访UV	全站新用户独立访问数（去除cookie）
//            -- 前端弄
//
//    -- 网站UV	全站新老用户独立访问数（去除cookie）
//            -- 前端弄

    /**
     * 用户注册短信条数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public int getSMSRegisterCount(Date startDate, Date endDate) {
        String sql = "select count(*) from message m, message_history mh " +
                "where m.message_uuid=mh.message_uuid and status='DONE' " +
                "and m.content like '%5分钟内输入有效。您正在注册账户，如非本人操作请致电400-1552-888.%'" +
                "and m.create_time >= '" + YYYY_MM_DD_HH_MM_SS.format(startDate) + "' and m.create_time < '" + YYYY_MM_DD_HH_MM_SS.format(endDate) + "'";
        return getCount(sql);
    }

    /**
     * 注册用户数	期间注册用户中理财用户
     * select count(userId) from user_ where registerTime >= '2017-07-01' and registerTime < '2017-08-31' and intention = 'LENDER';
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public int getRegisterLenderCount(Date startDate, Date endDate) {
        String sql = "select count(userId) from user_ where registerTime >= '" + YYYY_MM_DD_HH_MM_SS.format(startDate) + "' and registerTime < '" + YYYY_MM_DD_HH_MM_SS.format(endDate) + "' and intention = 'LENDER'";
        return getCount(sql);
    }

    /**
     * 开户、实名、绑卡数	期间完成开户的理财用户
     * select count(userId) from user_ u where registerTime >= '2017-07-01' and registerTime < '2017-08-31' and intention = 'LENDER' and idPassed = 1 and account_id is not null;
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public int getOpenAccLenderCount(Date startDate, Date endDate) {
        String sql = "select count(userId) from user_ u where registerTime >= '" + YYYY_MM_DD_HH_MM_SS.format(startDate) + "' and registerTime < '" + YYYY_MM_DD_HH_MM_SS.format(endDate) + "' and intention = 'LENDER' and idPassed = 1 and account_id is not null";
        return getCount(sql);
    }

    /**
     * 首次充值用户数	首次充值用户（这期间内）
     * select
     * (select count(userId) from user_loan_record_history where recharge_amount > 0 and check_date = '20170828')
     * -
     * (select count(userId) from user_loan_record_history where recharge_amount > 0 and check_date = '20170827');
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public int getFirstRechargeUserCount(Date startDate, Date endDate) {
        String sql = "select"
                + " (select count(userId) from user_loan_record_history where recharge_amount > 0 and check_date = '" + YYYYMMDD.format(endDate) + "')"
                + "- (select count(userId) from user_loan_record_history where recharge_amount > 0 and check_date = '" + YYYYMMDD.format(startDate) + "')";
        return getCount(sql);
    }

    /**
     * 首次成交用户数	首次成交（购买理财产品）用户（散标、红利计划）
     * select
     * (select count(userId) from user_loan_record_history where has_ever_invest_loan = 1 and check_date = '20170828')
     * -
     * (select count(userId) from user_loan_record_history where has_ever_invest_loan = 1 and check_date = '20170827');
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public int getFirstDealUserCount(Date startDate, Date endDate) {
        String sql = "select "
                + "(select count(userId) from user_loan_record_history where has_ever_invest_loan = 1 and check_date = '" + YYYYMMDD.format(endDate) + "')"
                + "- (select count(userId) from user_loan_record_history where has_ever_invest_loan = 1 and check_date = '" + YYYYMMDD.format(startDate) + "')";

        return getCount(sql);
    }

    /**
     * 登录用户数	期间发生登录官网的用户人数
     * select count(userId) from user_ where last_login_time >= '2017-07-01' and last_login_time < '2017-08-31' and intention = 'LENDER';
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public int getLoginLenderCount(Date startDate, Date endDate) {
        String sql = "select count(userId) from user_ where last_login_time >= '"
                + YYYY_MM_DD_HH_MM_SS.format(startDate) + "' and last_login_time < '"
                + YYYY_MM_DD_HH_MM_SS.format(endDate) + "' and intention = 'LENDER'";
        return getCount(sql);
    }

    /**
     * 成交用户数	期间购买所有理财产品的去重用户数（散标+红利）
     * select count(user_id) from (
     * select distinct userId as user_id from loan_lender_record where lendTime >= '2017-07-01' and lendTime < '2017-08-31'
     * union
     * select distinct user_id from finance_plan_sub_point where create_time >= '2017-07-01' and create_time < '2017-08-31'
     * ) uu;
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public int getDealUserDistinctCount(Date startDate, Date endDate) {
        String sql = "select count(user_id) from ("
                + "select distinct userId as user_id from loan_lender_record where lendTime >= '" + YYYY_MM_DD_HH_MM_SS.format(startDate) + "' and lendTime < '" + YYYY_MM_DD_HH_MM_SS.format(endDate) + "'"
                + " union "
                + "select distinct user_id from finance_plan_sub_point where create_time >= '" + YYYY_MM_DD_HH_MM_SS.format(startDate) + "' and create_time < '" + YYYY_MM_DD_HH_MM_SS.format(endDate) + "'"
                + ") uu";
        return getCount(sql);
    }

    /**
     * 散标	其中购买散标投资人数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public int getInvestLoanLenderCount(Date startDate, Date endDate) {
        String sql = "select count(user_id) from (" +
                " select distinct userId as user_id from loan_lender_record where lendTime >= '"
                + YYYY_MM_DD_HH_MM_SS.format(startDate) + "' and lendTime < '" + YYYY_MM_DD_HH_MM_SS.format(endDate) + "'" +
                " ) uu;";
        return getCount(sql);
    }

    /**
     * 3 6 12月期	其中购买3 6 12月期红利计划人数
     * select count(user_id) from (
     * select distinct user_id from finance_plan_sub_point fpsp, finance_plan fp where fpsp.finance_plan_id = fp.id and fp.lock_period = 3 and fpsp.create_time >= '2017-07-01' and fpsp.create_time < '2017-08-31'
     * ) uu;
     *
     * @param phase
     * @param startDate
     * @param endDate
     * @return
     */
    public int getInvestFinancePlanCount(int phase, Date startDate, Date endDate) {
        String sql = "select count(user_id) from ("
                + "select distinct user_id from finance_plan_sub_point fpsp, finance_plan fp where fpsp.finance_plan_id = fp.id and fp.lock_period = " + phase
                + " and fpsp.create_time >= '" + YYYY_MM_DD_HH_MM_SS.format(startDate) + "' and fpsp.create_time < '" + YYYY_MM_DD_HH_MM_SS.format(endDate) + "'" +
                ") uu";
        return getCount(sql);
    }

    /**
     * 期间购买所有理财产品的成交金额（散标+红利）
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public BigDecimal getDealFinancialProductAmount(Date startDate, Date endDate) {
        String sql = "select IFNULL((SELECT sum(ll.initial_amount) FROM loan_lender AS ll LEFT JOIN loan AS l ON l.loanId = ll.loan" +
                "           WHERE l. STATUS IN ('3', '6') AND ll.lender_type <> 'FINANCEPLAN_BID' AND ll.lendTime >= '" + YYYYMMDD.format(startDate) + "'" +
                "           AND ll.lendTime < '" + YYYYMMDD.format(endDate) + "'), 0) " +
                " + IFNULL((select sum(fpbr.amount) from finance_plan_buyer_record fpbr LEFT JOIN finance_plan fp ON fpbr.finance_plan_id = fp.id " +
                " where fp.lock_period in (3, 6, 12) and fpbr.create_time >= '" + YYYYMMDD.format(startDate) + "' and fpbr.create_time < '" + YYYYMMDD.format(endDate) + "'),0)";
        return getBigDecimal(sql);
    }

    /**
     * 其中购买散标投资成交金额
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public BigDecimal getInvestLoanAmount(Date startDate, Date endDate) {
        String sql = "SELECT " +
                " sum(ll.initial_amount) " +
                " FROM " +
                " loan_lender AS ll " +
                " LEFT JOIN loan AS l ON l.loanId = ll.loan" +
                " WHERE" +
                " l. STATUS IN ('3', '6')" +
                " AND ll.lender_type <> 'FINANCEPLAN_BID'" +
                " AND ll.lendTime >= '" + YYYYMMDD.format(startDate) + "'" +
                " AND ll.lendTime < '" + YYYYMMDD.format(endDate) + "'";
        return getBigDecimal(sql);
    }

    /**
     * 其中购买3 6 12 月期红利计划金额
     *
     * @param phase
     * @param startDate
     * @param endDate
     * @return
     */
    public BigDecimal getInvestFinancePlanAmount(int phase, Date startDate, Date endDate) {
        String sql = "select sum(fpbr.amount) from finance_plan_buyer_record fpbr LEFT JOIN finance_plan fp ON fpbr.finance_plan_id = fp.id" +
                "                 where fp.lock_period ='" + phase + "'" +
                "                 and fpbr.create_time >= '" + YYYYMMDD.format(startDate) + "'" +
                "                 and fpbr.create_time < '" + YYYYMMDD.format(endDate) + "'";
        return getBigDecimal(sql);
    }

    /**
     * 兑付金额	期间到期的计划总金额（不含利息）
     * select sum(final_amount) from finance_plan_sub_point fpsp, finance_plan fp where fpsp.finance_plan_id = fp.id AND fp.end_locking_time >= '2017-07-01' and fp.end_locking_time < '2018-08-31'
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public BigDecimal getExpireFinancePlanAmount(Date startDate, Date endDate) {
        String sql = "select sum(final_amount) from finance_plan_sub_point fpsp, finance_plan fp where fpsp.finance_plan_id = fp.id AND fp.end_locking_time >= '"
                + YYYY_MM_DD_HH_MM_SS.format(startDate) + "' and fp.end_locking_time < '" + YYYY_MM_DD_HH_MM_SS.format(endDate) + "'";
        return getBigDecimal(sql);
    }


    /**
     * 兑付金额	3 6 12月期	其中包含3 6 12月期计划总金额（不含利息）
     * select sum(final_amount) from finance_plan_sub_point fpsp, finance_plan fp where fpsp.finance_plan_id = fp.id AND fp.lock_period = 3 and fp.end_locking_time >= '2017-07-01' and fp.end_locking_time < '2018-08-31'
     *
     * @param phase
     * @param startDate
     * @param endDate
     * @return
     */
    public BigDecimal getPhasePaymentAmount(int phase, Date startDate, Date endDate) {
        String sql = "select sum(final_amount) from finance_plan_sub_point fpsp, finance_plan fp where fpsp.finance_plan_id = fp.id AND fp.lock_period = "
                + phase
                + " and fp.end_locking_time >= '" + YYYY_MM_DD_HH_MM_SS.format(startDate) + "' and fp.end_locking_time < '" + YYYY_MM_DD_HH_MM_SS.format(endDate) + "'";
        return getBigDecimal(sql);
    }


    /**
     * 用户账户余额	期末用户账户可用余额
     * select sum(availablePoints + frozenPoints) from point p, user_ u where p.user_ = u.userId and p.user_ > 136682 and u.intention = 'LENDER';
     *
     * @return
     */
    public BigDecimal getLenderAccBalance() {
        String sql = "select sum(availablePoints + frozenPoints) from point p, user_ u where p.user_ = u.userId and p.user_ > 136682 and u.intention = 'LENDER';";
        return getBigDecimal(sql);
    }


    /**
     * 计划余额	期末计划子账户可用余额
     * select sum(available_principal ) from finance_plan_sub_point;
     *
     * @return
     */
    public BigDecimal getFinancePlanEndingBalance() {
        String sql = "select sum(available_principal) from finance_plan_sub_point";
        return getBigDecimal(sql);
    }


    /**
     * 3 6 12月期余额	期末3 6 12月期子账户可用余额
     * select sum(available_principal) from finance_plan_sub_point fpsp, finance_plan fp
     * where fpsp.finance_plan_id = fp.id and fp.lock_period = 3;
     *
     * @param phase
     * @return
     */
    public BigDecimal getPhaseFinancePlanEndingBalance(int phase) {
        String sql = "select sum(available_principal) from finance_plan_sub_point fpsp, finance_plan fp where fpsp.finance_plan_id = fp.id and fp.lock_period = " + phase;
        return getBigDecimal(sql);
    }


    /**
     * 充值金额	期间理财人充值金额
     * select sum(amount) from recharge_log r, user_ u
     * where r.user_ = u.userId
     * and rechargeTime >= '2017-07-01'
     * and rechargeTime < '2017-08-31'
     * and u.intention = 'LENDER';
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public BigDecimal getLenderRechargeAmount(Date startDate, Date endDate) {
        String sql = "select sum(amount) from recharge_log r, user_ u where r.user_ = u.userId and rechargeTime >= '"
                + YYYY_MM_DD_HH_MM_SS.format(startDate) + "' and rechargeTime < '"
                + YYYY_MM_DD_HH_MM_SS.format(endDate) + "' and u.intention = 'LENDER'";
        return getBigDecimal(sql);
    }

    /**
     * 充值笔数	期间理财人充值笔数
     * select count(rechargeLogId) from recharge_log r, user_ u
     * where r.user_ = u.userId
     * and rechargeTime >= '2017-07-01'
     * and rechargeTime < '2017-08-31'
     * and u.intention = 'LENDER';
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public int getLenderRechargeCount(Date startDate, Date endDate) {
        String sql = "select count(rechargeLogId) from recharge_log r, user_ u where r.user_ = u.userId and rechargeTime >= '"
                + YYYY_MM_DD_HH_MM_SS.format(startDate) + "' and rechargeTime < '"
                + YYYY_MM_DD_HH_MM_SS.format(endDate) + "' and u.intention = 'LENDER';";
        return getCount(sql);
    }


    /**
     * 提现金额	期间理财人总提现金额
     * select sum(amount) from cash_draw_log c, user_ u where
     * c.user_ = u.userId
     * and applyTime >= '2017-07-01'
     * and applyTime < '2017-08-31'
     * and cash_type = 'WEB_DRAWCASH'
     * and cash_draw_status = 'CASHDRAW_SUCCESS'
     * and u.intention = 'LENDER';
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public BigDecimal getLenderCashDrawAmount(Date startDate, Date endDate) {
        String sql = "select sum(amount) from cash_draw_log c, user_ u where c.user_ = u.userId and applyTime >= '"
                + YYYY_MM_DD_HH_MM_SS.format(startDate) + "' and applyTime < '"
                + YYYY_MM_DD_HH_MM_SS.format(endDate) + "' and cash_type = 'WEB_DRAWCASH' and cash_draw_status = 'CASHDRAW_SUCCESS' and u.intention = 'LENDER'";
        return getBigDecimal(sql);
    }


    /**
     * 提现笔数	期间理财人提现总笔数
     * select count(cashDrawId) from cash_draw_log c, user_ u
     * where c.user_ = u.userId
     * and applyTime >= '2017-07-01'
     * and applyTime < '2017-08-31'
     * and cash_type = 'WEB_DRAWCASH'
     * and cash_draw_status = 'CASHDRAW_SUCCESS'
     * and u.intention = 'LENDER';
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public int getLenderCashDrawCount(Date startDate, Date endDate) {
        String sql = "select count(cashDrawId) from cash_draw_log c, user_ u where c.user_ = u.userId and applyTime >= '"
                + YYYY_MM_DD_HH_MM_SS.format(startDate) + "' and applyTime < '"
                + YYYY_MM_DD_HH_MM_SS.format(endDate) + "' and cash_type = 'WEB_DRAWCASH' and cash_draw_status = 'CASHDRAW_SUCCESS' and u.intention = 'LENDER'";
        return getCount(sql);
    }


    /**
     * 借款成交笔数	期间借款人借款笔数
     * select count(loanId) from loan
     * where status not in (2, 12)
     * and passTime >= '2017-07-01'
     * and passTime < '2017-08-31';
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public int getDurationBorrowingsCount(Date startDate, Date endDate) {
        String sql = "select count(loanId) from loan where status not in (2, 12) and passTime >= '"
                + YYYY_MM_DD_HH_MM_SS.format(startDate) + "' and passTime < '" + YYYY_MM_DD_HH_MM_SS.format(endDate) + "'";
        return getCount(sql);
    }


    /**
     * 借款成交金额	借款人借款总金额
     * select sum(amount) from loan
     * where status not in (2, 12)
     * and passTime >= '2017-07-01'
     * and passTime < '2017-08-31';
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public BigDecimal getDurationBorrowingAmount(Date startDate, Date endDate) {
        String sql = "select sum(amount) from loan where status not in (2, 12) and passTime >= '"
                + YYYY_MM_DD_HH_MM_SS.format(startDate) + "' and passTime < '"
                + YYYY_MM_DD_HH_MM_SS.format(endDate) + "'";
        return getBigDecimal(sql);
    }


    /**
     * 还款笔数	期间借款人还款笔数
     * select count(id) from borrow_repay_task
     * where repay_request_time >= '2017-07-01' and repay_request_time <= '2017-08-31';
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public int getRepaymentCount(Date startDate, Date endDate) {
        String sql = "select count(id) from borrow_repay_task where repay_request_time >= '"
                + YYYY_MM_DD_HH_MM_SS.format(startDate) + "' and repay_request_time <= '"
                + YYYY_MM_DD_HH_MM_SS.format(endDate) + "'";
        return getCount(sql);
    }


    /**
     * 还款本金	期间借款人还款本金
     * select sum(principal) from borrow_repay_record
     * where dueDate >= '2017-07-01' and dueDate < '2017-10-31';
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public BigDecimal getRepaymentPrincipal(Date startDate, Date endDate) {
        String sql = "select sum(principal) from borrow_repay_record where dueDate >= '"
                + YYYY_MM_DD_HH_MM_SS.format(startDate) + "' and dueDate < '"
                + YYYY_MM_DD_HH_MM_SS.format(endDate) + "'";
        return getBigDecimal(sql);
    }


    /**
     * 还款利息	期间借款人还款利息
     * select sum(interest) from borrow_repay_record
     * where dueDate >= '2017-07-01' and dueDate < '2017-10-31';
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public BigDecimal getRepaymentInterestAmount(Date startDate, Date endDate) {
        String sql = "select sum(interest) from borrow_repay_record where dueDate >= '"
                + YYYY_MM_DD_HH_MM_SS.format(startDate) + "' and dueDate < '"
                + YYYY_MM_DD_HH_MM_SS.format(endDate) + "'";
        return getBigDecimal(sql);
    }


    /**
     * 净增资产本金余额	期间新增借款成交本金-期间借款还款本金
     * select
     * (select sum(amount) from loan where status not in (2, 12) and passTime >= '2017-07-01' and passTime < '2017-08-31')
     * -
     * (select IFNULL(sum(principal),0) from borrow_repay_record where dueDate >= '2017-07-01' and dueDate < '2017-10-31')
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public BigDecimal getNetPrincipalBalance(Date startDate, Date endDate) {
        String sql = "select" +
                "(select sum(amount) from loan where status not in (2, 12) and passTime >= '" + YYYY_MM_DD_HH_MM_SS.format(startDate) + "' and passTime < '" + YYYY_MM_DD_HH_MM_SS.format(endDate) + "')"
                + "-"
                + "(select IFNULL(sum(principal),0) from borrow_repay_record where dueDate >= '" + YYYY_MM_DD_HH_MM_SS.format(startDate) + "' and dueDate < '" + YYYY_MM_DD_HH_MM_SS.format(endDate) + "')";
        return getBigDecimal(sql);
    }


    /**
     * 资产本金余额	期末借款剩余本金
     * select sum(finalPrincipal) from borrow_repay_record where dueDate >= '2017-07-01';
     *
     * @param endDate
     * @return
     */
    public BigDecimal getEndingPrincipalBalance(Date endDate) {
        String sql = "select sum(principal) from borrow_repay_record where dueDate >= '"
                + YYYY_MM_DD_HH_MM_SS.format(endDate) + "'";
        return getBigDecimal(sql);
    }

    /**
     * 计划资金存量	未到期计划实际加入金额总和
     * select sum(final_amount) from finance_plan_sub_point fpsp where status in ('INPROGRESS', 'EXITING');
     *
     * @return
     */
    public BigDecimal getNotDueFinancePlanAmount() {
        String sql = "select sum(final_amount) from finance_plan_sub_point fpsp where status in ('INPROGRESS', 'EXITING')";
        return getBigDecimal(sql);
    }


    /**
     * 3 6 12月期存量	其中3 6 12月期未到期计划实际加入金额总和
     * select sum(final_amount) from finance_plan_sub_point fpsp, finance_plan fp where fpsp.status in ('INPROGRESS', 'EXITING') and fp.lock_period = 3
     *
     * @param phase
     * @return
     */
    public BigDecimal getPhaseFinancePlanNotDueActualJoinAmount(int phase) {
        String sql = "select sum(final_amount) from finance_plan_sub_point fpsp, finance_plan fp where fpsp.finance_plan_id = fp.id and fpsp.status in ('INPROGRESS', 'EXITING') and fp.lock_period = " + phase;
        return getBigDecimal(sql);
    }

}
