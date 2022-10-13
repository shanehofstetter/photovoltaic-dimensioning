package ch.shanehofstetter.pvdimension.util;

import java.time.LocalDate;
import java.time.Month;
import java.util.Date;

/**
 * @author Shane Hofstetter : shane.hofstetter@gmail.com<br>
 * ch.shanehofstetter.pvdimension.util
 */
public class DateTimeUtil {

    /**
     * Convert long time format to amount of hours with decimals
     * 1h 30min = 1.5h
     *
     * @param time the time to get the hours from
     * @return amount of hours
     */
    public static double getHoursFromTime(long time) {
        long timeInSeconds = time / 1000;
        long hours, minutes;
        hours = timeInSeconds / 3600;
        timeInSeconds = timeInSeconds - (hours * 3600);
        minutes = timeInSeconds / 60;
        return hours + minutes / 60.0;
    }

    /**
     * Subtracts an amount of minutes from the given time
     *
     * @param time    time to subtract from
     * @param minutes the minutes to subtract
     * @return new time
     */
    public static long subtractMinutesFromTime(long time, int minutes) {
        long milliseconds = minutes * 60 * 1000;
        return time - milliseconds;
    }

    /**
     * add an amount of minutes to the given time
     *
     * @param time    time to add to
     * @param minutes minutes to add
     * @return new time
     */
    public static long addMinutesToTime(long time, int minutes) {
        long milliseconds = minutes * 60 * 1000;
        return time + milliseconds;
    }

    /**
     * @param time the time to format
     * @return Time-String as HH:MM
     */
    public static String formatTime(long time) {
        //noinspection MalformedFormatString
        return String.format("%tH:%<tM", new Date(time));
    }

    /**
     * @param month Month
     * @return Number of days in given month
     */
    public static int getNumberOfDaysInMonth(Month month) {
        LocalDate date = LocalDate.of(LocalDate.now().getYear(), month, 1);
        return date.lengthOfMonth();
    }
}
