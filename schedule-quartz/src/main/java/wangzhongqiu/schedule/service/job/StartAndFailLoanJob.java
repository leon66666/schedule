package wangzhongqiu.schedule.service.job;

import wangzhongqiu.model.LoanStatusTask;
import wangzhongqiu.schedule.service.LoanStatusTaskService;
import wangzhongqiu.vo.LoanTaskStatus;
import wangzhongqiu.vo.LoanTaskType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class StartAndFailLoanJob extends BaseJob {
    private static Log log = LogFactory.getLog(StartAndFailLoanJob.class);
    @Autowired
    private StartAndFailLoanDispatcher dispatcher;
    @Autowired
    private LoanStatusTaskService taskService;
    @Override
    public void run() {
        log.info("StartAndFailLoanJob task start");
        // 1、获取处理中的流标和放标任务
        EnumSet<LoanTaskType> typeSet = EnumSet.of(LoanTaskType.START, LoanTaskType.FAIL, LoanTaskType.CASH);
        List<LoanStatusTask> taskList = taskService.getLoanTaskByStatusAndType(typeSet, LoanTaskStatus.PROCESSING);


        int startAndFailLoanJobThreadPoolSize = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(startAndFailLoanJobThreadPoolSize);

        for (LoanStatusTask task : taskList) {
            executorService.execute(new StartAndFailLoanTask(task));
        }

        executorService.shutdown();
        try {
            while (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
                log.info("Waiting StartAndFailLoanJob thread pool close..."); // Waiting thread pool close...
            }
        } catch (InterruptedException ex) { // should not reach here
            ex.printStackTrace();
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        } catch (Exception ex) { // should not reach here
            ex.printStackTrace();
            executorService.shutdownNow();
        }
        log.info("StartAndFailLoanJob task done");
    }

    class StartAndFailLoanTask implements Runnable {
        private LoanStatusTask task;

        public StartAndFailLoanTask(LoanStatusTask task) {
            this.task = task;
        }

        @Override
        public void run() {
            log.info("begin execute task");
            try {
                dispatcher.dealTask(task);
            } catch (Exception e) {
                log.error("执行放款流标任务失败", e);
            }
        }
    }
}
