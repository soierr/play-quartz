package play.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class HelloJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        System.out.println("From job fire time: " + jobExecutionContext.getFireTime());
        /*System.out.println("From job next fire time: " + jobExecutionContext.getNextFireTime());
        System.out.println("From job scheduled next fire time: " + jobExecutionContext.getScheduledFireTime());*/
    }
}
