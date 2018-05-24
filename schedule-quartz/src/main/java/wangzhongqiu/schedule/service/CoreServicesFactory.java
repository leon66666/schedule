package wangzhongqiu.schedule.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public class CoreServicesFactory {
    private static Log log = LogFactory.getLog(CoreServicesFactory.class);
    private static WebApplicationContext wac;
    
    public static Object getBean(String beanName) {
        if (wac == null) {
            log.info("Initializaing web application context");
            wac = ContextLoader.getCurrentWebApplicationContext();
        }
        if (wac != null) {
            return wac.getBean(beanName);
        } else {
            log.error("Can't initialize web application context");
            return null;
        }
    }
}
