package com.libtop.bigmomzhang.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by LianTu on 2016-9-30.
 */

public class DateUtil
{

    public static final long ONE_MINUTE_MILLIONS = 60 * 1000;
    public static final long ONE_HOUR_MILLIONS = 60 * ONE_MINUTE_MILLIONS;
    public static final long ONE_DAY_MILLIONS = 24 * ONE_HOUR_MILLIONS;

    /**
     * 将时间戳转换为用于显示的格式(单位为豪秒)
     *
     * @param timestampInMillis
     * @return String 一般型如"MM-dd HH:mm"的格式
     */
    public static String transformToShow(long timestampInMillis)
    {
        String timeStr;
        SimpleDateFormat sdf = (SimpleDateFormat) SimpleDateFormat.getInstance();
        Date date = new Date(timestampInMillis);
        Date currentDate = new Date();
        long durationTime = currentDate.getTime() - date.getTime();

        if (durationTime <= 10 * ONE_MINUTE_MILLIONS) // 十分钟内
        {
            timeStr = "刚刚";
        }
        else if (durationTime < ONE_HOUR_MILLIONS) // 一时内
        {
            timeStr = (durationTime / ONE_MINUTE_MILLIONS) + "分钟前";
        }
        else if (isSameYear(date, currentDate))
        {
            int dayStatus = calculateDayStatus(date, currentDate);
            if (dayStatus == 0) // 今天
            {
                timeStr = (durationTime / ONE_HOUR_MILLIONS) + "小时前";
            }
            else if (dayStatus == -1) // 昨天
            {
                sdf.applyPattern("HH:mm");
                timeStr = "昨天 " + sdf.format(date);
            }
            else
            {
                sdf.applyPattern("MM-dd HH:mm");
                timeStr = sdf.format(date);
            }
        }
        else
        {
            sdf.applyPattern("yyyy-MM-dd HH:mm");
            timeStr = sdf.format(date);
        }

        return timeStr;
    }

    /**
     * 判断俩日期是否为同一年
     *
     * @param targetTime
     * @param compareTime
     * @return boolean
     */
    public static boolean isSameYear(Date targetTime, Date compareTime)
    {
        Calendar tarCalendar = Calendar.getInstance();
        tarCalendar.setTime(targetTime);
        int tarYear = tarCalendar.get(Calendar.YEAR);

        Calendar compareCalendar = Calendar.getInstance();
        compareCalendar.setTime(compareTime);
        int comYear = compareCalendar.get(Calendar.YEAR);

        return tarYear == comYear;
    }

    /**
     * 计算俩日期相差的天数(俩日期需为同一年)
     *
     * @param targetTime
     * @param compareTime
     * @return int 若为负数,则targetTime比compareTime日期要早.
     */
    public static int calculateDayStatus(Date targetTime, Date compareTime)
    {
        Calendar tarCalendar = Calendar.getInstance();
        tarCalendar.setTime(targetTime);
        int tarDayOfYear = tarCalendar.get(Calendar.DAY_OF_YEAR);

        Calendar compareCalendar = Calendar.getInstance();
        compareCalendar.setTime(compareTime);
        int comDayOfYear = compareCalendar.get(Calendar.DAY_OF_YEAR);

        return tarDayOfYear - comDayOfYear;
    }
}
