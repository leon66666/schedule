package com.hoomsun.schedule.service;

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
@Transactional
public class ScheduleInitService {

    private static Log log = LogFactory.getLog(ScheduleInitService.class);

    public void init() {
        log.info("init service start...");
        long timer = System.currentTimeMillis();

        log.info("init service end, cost " + ((System.currentTimeMillis() - timer) / 1000D) + "s");
        return;
    }

}
