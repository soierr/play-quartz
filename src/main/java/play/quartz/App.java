package play.quartz;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class App{

    public static void main( String[] args )throws Exception{

        JobDetail job = newJob(HelloJob.class)
                .withIdentity("job1", "group1")
                .build();

        ZonedDateTime current = ZonedDateTime.now().minus(70, ChronoUnit.SECONDS);
        //ZonedDateTime current = ZonedDateTime.now();

        Date currendDateStd = Date.from(current.toInstant());

        System.out.println(currendDateStd);

        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();



        Trigger trigger = newTrigger()
                .withIdentity("trigger1", "group1")
                .startAt(currendDateStd)
                .withSchedule(simpleSchedule().withIntervalInSeconds(10).repeatForever().withMisfireHandlingInstructionNextWithRemainingCount())
                .build();

        scheduler.start();

        scheduler.scheduleJob(job, trigger);

/*        System.out.println("From main next fire time: " + trigger.getNextFireTime());
        System.out.println("============");*/

        Thread.sleep(120000);

        scheduler.shutdown();
    }
}
