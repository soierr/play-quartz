package play.quartz;

import org.quartz.Calendar;
import org.quartz.impl.calendar.DailyCalendar;
import org.quartz.impl.calendar.WeeklyCalendar;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.GregorianCalendar;

public class CustomCalendar extends DailyCalendar {

    private  java.util.Calendar calendar = new GregorianCalendar();

    public CustomCalendar(WeeklyCalendar weeklyCalendar, String rangeStartingTime, String rangeEndingTime) {
        super(rangeStartingTime, rangeEndingTime);
        super.setBaseCalendar(weeklyCalendar);
    }

    @Override
    public long getNextIncludedTime(long timeInMillis) {
        Instant instant = Instant.ofEpochSecond(timeInMillis);
        ZonedDateTime timeCalculated = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
        Date date = Date.from(instant);

        while (!isTimeIncluded(date.getTime())){

            timeCalculated = timeCalculated.plus(1, ChronoUnit.DAYS);;
            date = Date.from(timeCalculated.toInstant());
        }

        return date.getTime();
    }

}
