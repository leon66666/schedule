package com.hoomsun.schedule.service.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;


/**
 * Created by wzq on 2017/7/24.
 */
@Service
public class BorrowRepayBatchJob extends BaseJob {
    private static Log log = LogFactory.getLog(BorrowRepayBatchJob.class);

    @Override
    public void run() {
        log.info("BorrowRepayBatchJob task start");
        log.info("BorrowRepayBatchJob task end");
    }
}
