package com.hoomsun.schedule.service;

import com.hoomsun.common.Constants;
import com.hoomsun.exception.ServiceException;
import com.hoomsun.service.LoanService;
import com.hoomsun.service.cache.RedisService;
import com.hoomsun.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * schedule启动时初始化用
 * 
 */
@Service
@Transactional(rollbackFor = ServiceException.class)
public class ScheduleInitService {

    private static Log log = LogFactory.getLog(ScheduleInitService.class);

    @Autowired
    private RedisService redisService;
    @Autowired
    private LoanService loanService;

    public void init() {
        log.info("init service start...");
        long timer = System.currentTimeMillis();

        // 清除是否有此债权正在申请未处理的操作 查询redis是否有loanLenderId锁
        Set<String> loanTransferForApplyPlatFormSet = redisService.keys(Constants.LOANTRANSFER_FOR_APPLY_PLATFORM_ + "*");
        if (StringUtil.isNotEmpty(loanTransferForApplyPlatFormSet)) {
            for (String loanTransferForApplyPlatFormKey : loanTransferForApplyPlatFormSet) {
                Long result = redisService.del(loanTransferForApplyPlatFormKey);
                if (result != null && result > 0) {
                    log.info("schedule init service 债权正在申请未处理的操作标记" + Constants.LOANTRANSFER_FOR_APPLY_PLATFORM_ + " " + loanTransferForApplyPlatFormKey + " " + Constants.LOANTRANSFER_FOR_APPLY_PLATFORM_ + " cleaned");
                }
            }
        }

        log.info("init service end, cost " + ((System.currentTimeMillis() - timer) / 1000D) + "s");
        return;
    }

}
