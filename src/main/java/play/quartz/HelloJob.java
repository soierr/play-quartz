package play.quartz;

import org.quartz.*;

public class HelloJob implements StatefulJob {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        System.out.println("Hello job has been executed");
    }
}
