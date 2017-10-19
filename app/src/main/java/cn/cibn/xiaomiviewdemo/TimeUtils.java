package cn.cibn.xiaomiviewdemo;

import android.annotation.SuppressLint;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * TimeUtils
 */
public class TimeUtils {

    private static TimeZone timezone = TimeZone.getTimeZone("GMT+8:00");

    public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat DATE_FORMAT_DATE = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat TIME_TOADY = new SimpleDateFormat("yyyyMMdd");
    public static final SimpleDateFormat HOME_TIME_FORMAT = new SimpleDateFormat("HH:mm");
    public static final SimpleDateFormat WEIGHT_TIME_FORMAT = new SimpleDateFormat("MM月dd日 HH:mm");

    private TimeUtils() {
        throw new AssertionError();
    }

    /**
     * long time to string
     *
     * @param timeInMillis
     * @param dateFormat
     * @return
     */
    public static String getTime(long timeInMillis, SimpleDateFormat dateFormat) {
        return dateFormat.format(new Date(timeInMillis));
    }

    /**
     * string time to string
     *
     * @param timeInMillis
     * @param dateFormat
     * @return
     */
    public static String getTime(String timeInMillis, SimpleDateFormat dateFormat) {
        return dateFormat.format(new Date(Long.parseLong(timeInMillis)));
    }

    /**
     * long time to string, format is {@link #DEFAULT_DATE_FORMAT}
     *
     * @param timeInMillis
     * @return
     */
    public static String getTime(long timeInMillis) {
        return getTime(timeInMillis, DEFAULT_DATE_FORMAT);
    }

    /**
     * get current time in milliseconds
     *
     * @return
     */
    public static long getCurrentTimeInLong() {
        return System.currentTimeMillis();
    }

    /**
     * get current time in milliseconds, format is {@link #DEFAULT_DATE_FORMAT}
     *
     * @return
     */
    public static String getCurrentTimeInString() {
        return getTime(getCurrentTimeInLong());
    }

    /**
     * get current time in milliseconds
     *
     * @return
     */
    public static String getCurrentTimeInString(SimpleDateFormat dateFormat) {
        return getTime(getCurrentTimeInLong(), dateFormat);
    }

    public static String getHomeTimeInString() {
        return getTime(getCurrentTimeInLong(), HOME_TIME_FORMAT);
    }

    public static long timeString2Long(String timeInMillis) {
        return Long.parseLong(timeInMillis);
    }

    // 显示时间 HH:mm:ss
    public static String getCurTime(long systime) {
        SimpleDateFormat sf = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
        sf.setTimeZone(timezone);
        Date curDate = new Date(systime);
        String time = sf.format(curDate);

        return time;
    }

    // 显示时间 HH:mm
    public static String getCurTime2(long systime) {
        SimpleDateFormat sf = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        sf.setTimeZone(timezone);
        Date curDate = new Date(systime);
        String time = sf.format(curDate);

        return time;
    }

    //运动目标日期转换yyyyMMdd
    public static long startTime3(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date(time);
        return Long.valueOf(formatter.format(date));
    }

    // 显示时间 MM-dd
    public static String getCurTime3() {
        SimpleDateFormat sf = new SimpleDateFormat("MM-dd", Locale.ENGLISH);
        sf.setTimeZone(timezone);
        Date curDate = new Date(getCurrentTimeInLong());
        String time = sf.format(curDate);

        return time;
    }

    // 显示时间 HH:mm:ss
//    public static String getCurTime2(long systime) {
//        SimpleDateFormat sf = new SimpleDateFormat("HH小时mm分", Locale.ENGLISH);
//        sf.setTimeZone(timezone);
//        Date curDate = new Date(systime);
//        String time = sf.format(curDate);
//		time = time.replaceAll("小时",App.getInstance().getString(R.string.video_details_shi)); //将*换掉
//		time = time.replaceAll("分",App.getInstance().getString(R.string.video_details_fen));
//		
//		return time;
//	}

    //示例：transferStringDateToLong("MM/dd/yyyy HH:mm:ss","05/02/2016 21:08:00")
    @SuppressLint("SimpleDateFormat")
    public static Long transferStringDateToLong(String formatDate, String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(formatDate);
        Date dt = sdf.parse(date);
        return dt.getTime();
    }

    //获取当前年月日
    public static String getTodayTime() {
        return TIME_TOADY.format(new Date());
    }

    public static int getYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public static int getMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    public static int getDate() {
        return Calendar.getInstance().get(Calendar.DATE);
    }

    public static int getHour() {
        return Calendar.getInstance().get(Calendar.HOUR);
    }

    public static int getMinute() {
        return Calendar.getInstance().get(Calendar.MINUTE);
    }

    public static int getSecond() {
        return Calendar.getInstance().get(Calendar.SECOND);
    }

    /**
     * 格式化输入的年月日
     *
     * @param year  the year minus 1900.
     * @param month the month between 0-11.
     * @param date  the day of the month between 1-31.
     * @return
     */
    public static String getBleTime(int year, int month, int date) {

        return TIME_TOADY.format(new Date(year, month, date));
    }

    /**
     * 获取指定日期前 count天的日期
     *
     * @param year   the year minus 1900.
     * @param month  the month between 0-11.
     * @param date   the day of the month between 1-31.
     * @param dayNum 前几天的日期
     * @return
     */
    public static String getSpecifiedDayBefore(int year, int month, int date, int dayNum) {
        Calendar c = Calendar.getInstance();
        Date mdate = new Date(year, month, date);
        c.setTime(mdate);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - dayNum);
        String dayBefore = new SimpleDateFormat("yyyyMMdd").format(c.getTime());
        return dayBefore;
    }

    /**
     * 获取今日前 count天的日期
     *
     * @param dayNum 前几天
     * @return
     */
    public static String getSpecifiedDayBefore(int dayNum) {
        Calendar c = Calendar.getInstance();
        Date mdate = new Date();
        c.setTime(mdate);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - dayNum);
        String dayBefore = new SimpleDateFormat("yyyyMMdd").format(c.getTime());
        return dayBefore;
    }

    /**
     * 获取今日前 count天的日期
     *
     * @param dayNum 前几天
     * @return
     */
    public static Date getDayBeforeDate(int dayNum) {
        Calendar c = Calendar.getInstance();
        Date mdate = new Date();
        c.setTime(mdate);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - dayNum);
        return c.getTime();
    }

    public static int getYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.YEAR);
    }

    public static int getMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MONTH) + 1;
    }

    public static int getDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DATE);
    }

    //获取指定date日期的格式化后的string
    public static String getDateStr(Date date) {
        return TIME_TOADY.format(date);
    }

    //分钟转小时
    public static String minuteToHour(int minute) {
        if (minute<0) {
            return "0分";
        }
        int hour = minute / 60;
        int min = minute % 60;
        if (hour == 0) {
            return min + "分";
        } else if (hour != 0 && min == 0) {
            return hour + "小时";
        } else {
            return hour + "小时" + min + "分";
        }
    }


    //分钟转小时
    public static String hour(int minute) {
        int hour = minute / 60;
        int min = minute % 60;
        if (hour == 0) {
            return min + "分";
        } else if (hour != 0 && min == 0) {
            return hour + "";
        } else {
            return hour + ":" + min + "";
        }
    }

    /**
     *  深睡眠百分比
     * @param d 深睡眠时间 （分钟）
     * @param n 浅睡眠时间
     * @return 深睡眠百分比
     */
    public static String toPercent(int d, int n){
        if ((n+d)==0||n<0||d<0) {
            return"0%";
        }
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(0);
        String result = numberFormat.format((float) d / (float) (d+n) * 100);
        return result+"%";
    }

    //获取当前年月日int
    public static int getTodayTimeInt(){
        return Integer.valueOf(getTodayTime());
    }

    /**
     * 获取今日前 count天的日期
     * @param dayNum  前几天
     * @return
     */
    public static int getSpecifiedDayBeforeInt(int dayNum){
        Calendar c = Calendar.getInstance();
        Date mdate = new Date();
        c.setTime(mdate);
        int day=c.get(Calendar.DATE);
        c.set(Calendar.DATE,day-dayNum);
        String dayBefore=new SimpleDateFormat("yyyyMMdd").format(c.getTime());
        return Integer.valueOf(dayBefore);
    }

}
