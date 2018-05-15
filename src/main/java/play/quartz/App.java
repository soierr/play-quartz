package play.quartz;

import org.quartz.*;
import org.quartz.core.QuartzScheduler;
import org.quartz.core.QuartzSchedulerResources;
import org.quartz.impl.QuartzServer;
import org.quartz.impl.StdScheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.DailyCalendar;
import org.quartz.impl.calendar.WeeklyCalendar;
import org.quartz.impl.jdbcjobstore.JobStoreCMT;
import org.quartz.impl.jdbcjobstore.JobStoreSupport;
import org.quartz.impl.jdbcjobstore.JobStoreTX;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.matchers.StringMatcher;
import org.quartz.spi.JobStore;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.quartz.DailyTimeIntervalScheduleBuilder.dailyTimeIntervalSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class App{

    public static void main( String[] args )throws Exception{

        WeeklyCalendar weeklyCalendar = new WeeklyCalendar(java.util.Calendar.getInstance().getTimeZone());
        //weeklyCalendar.setDayExcluded(3, true);
        weeklyCalendar.setDayExcluded(3, true);
        weeklyCalendar.setDayExcluded(5, true);

        /*DailyCalendar dailyCalendar = new DailyCalendar(weeklyCalendar,"09:00:00", "23:00:00");
        dailyCalendar.setInvertTimeRange(true);*/

        CustomCalendar customCalendar = new CustomCalendar(weeklyCalendar, "09:00:00", "23:59:00");
        customCalendar.setInvertTimeRange(true);

        //System.out.println(System.currentTimeMillis());
        Instant instant = Instant.now();
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());
        System.out.println(zonedDateTime.toEpochSecond());

        long date = customCalendar.getNextIncludedTime(zonedDateTime.toEpochSecond());

        Date date1 = new Date(date);

        System.out.println(date1);

        ZonedDateTime current = ZonedDateTime.now().minus(35, ChronoUnit.SECONDS);

        Date currendDateStd = Date.from(current.toInstant());

        SchedulerFactory schedulerFactory = new StdSchedulerFactory("quartz.properties");
        Scheduler scheduler = schedulerFactory.getScheduler();

        scheduler.deleteJob(new JobKey("job1", "group1"));

       // scheduler.addCalendar("custom", customCalendar, true, true);

        JobDetail job = newJob(HelloJob.class)
                .withIdentity("job1", "group1")
                .build();

        /*Trigger trigger = newTrigger()
                .withIdentity("trigger1", "alert.group1")
                .modifiedByCalendar("custom")
                .startAt(date1)
                .withSchedule(simpleSchedule().withIntervalInHours(24).withRepeatCount(1))
                .build();*/

        DailyTimeIntervalTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "alert.group1")
                .withSchedule(DailyTimeIntervalScheduleBuilder.dailyTimeIntervalSchedule()
                        .startingDailyAt(new TimeOfDay(16, 00)).withRepeatCount(1).onEveryDay())
                //.modifiedByCalendar("custom")
                .build();

        scheduler.start();

        scheduler.scheduleJob(job, trigger);

        System.out.println("Trigger start time: " + trigger.getStartTime());
        System.out.println("Trigger fire time: " + trigger.getNextFireTime());
        System.out.println("Trigger fire time after: " + trigger.getFireTimeAfter(date1));

    }
}
