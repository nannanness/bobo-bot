package org.cherrygirl.utils;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

/**
 * @author nannanness
 */
public class TimeUtil {

    public static String todayTostr(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(new Date());
        return date;
    }

    public static String yesterdayToStr(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE,-1);
        Date d = cal.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = format.format(d);
        return dateStr;
    }

    public static int todayTimePoint(){
        return LocalTime.now().getHour();
    }
}
