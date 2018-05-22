package com.hoomsun.schedule.service.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;


/**
 * Created by wzq on 2017/5/25.
 */
@Service
public class AutoAddRepayJob extends BaseJob {
    private static Log log = LogFactory.getLog(AutoAddRepayJob.class);

    @Override
    public void run() {
        try {
            log.info("AutoAddRepayJob task start");
            log.info("AutoAddRepayJob task end");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
