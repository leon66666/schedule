package com.hoomsun.schedule.service.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

@Service
public class StartAndFailLoanJob extends BaseJob {
    private static Log log = LogFactory.getLog(StartAndFailLoanJob.class);

    @Override
    public void run() {
        log.info("StartAndFailLoanJob task start");
        log.info("StartAndFailLoanJob task done");
    }
}
