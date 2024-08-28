package org.cherrygirl.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

/**
 * @author nannanness
 */
public class CalenderUtil {

    public static java.sql.Date getTodaySqlTime() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar ca = Calendar.getInstance();
        String day = ca.get(Calendar.YEAR) + "-" + (ca.get(Calendar.MONTH) + 1) + "-" + ca.get(Calendar.DATE);
        return new java.sql.Date(sdf.parse(day).getTime()) ;
    }

    public static boolean oneMonthDifference(long before, long now) throws ParseException {
        Timestamp beforeTime = new Timestamp(before);
        LocalDateTime beforeLocalDateTime = beforeTime.toLocalDateTime();
        Timestamp nowTime = new Timestamp(now);
        LocalDateTime nowLocalDateTime = nowTime.toLocalDateTime();

        // 判断是否超过一个月
        boolean isMoreThanAMonth = ChronoUnit.MONTHS.between(beforeLocalDateTime, nowLocalDateTime) > 1;
        System.out.println("是否超过一个月：" + isMoreThanAMonth);
        return isMoreThanAMonth;
    }

    /**
     * 解析周几（数字）对应的日期
     * @param weekDay
     * @return
     */
    public static String parseWeekday(int weekDay){
        int today = getTodayWeekNumber();
        int num = weekDay - today;
        String date = null;
        if(num == 0){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            date = dateFormat.format(new Date());
        }else if(num > 0){
            date = getNumberDate(num);
        }
        return date;
    }

    /**
     * 根据数字获取日期
     * @param num
     * @return
     */
    public static String getNumberDate(int num){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        calendar.add(Calendar.DATE, num);
        return dateFormat.format(calendar.getTime());
    }

    /**
     * 获取今天对应周几（数字）
     * @return
     */
    public static Integer getTodayWeekNumber(){
        Calendar now = Calendar.getInstance();
        //一周第一天是否为星期天
        boolean isFirstSunday = (now.getFirstDayOfWeek() == Calendar.SUNDAY);
        //获取周几
        int weekDay = now.get(Calendar.DAY_OF_WEEK);
        //若一周第一天为星期天，则-1
        if(isFirstSunday){
            weekDay = weekDay - 1;
            if(weekDay == 0){
                weekDay = 7;
            }
        }
        return weekDay;
    }
}
