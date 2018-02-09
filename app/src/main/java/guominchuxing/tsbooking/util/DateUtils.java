package guominchuxing.tsbooking.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by admin on 2017/2/13.
 */

public class DateUtils {
    private static final String TAG = "DateUtils";
    public static final String TODAY_STRING = "今天";
//    public static final String YESTERDAY_STRING = "昨日";
//    public static final String THE_DAY_BEFORE_YESTERDAY_STRING = "前日";

    /**
     * 一天中的毫秒数
     */
    public static final long MILLISECONDS_DAY = 86400000L;

    public static Date parse(String dateStr, String format){
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
             date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String format(Date date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    public static Date addOneMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, 1);
        return cal.getTime();
    }

    public static Date subOneMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, -1);
        return cal.getTime();
    }

    public static Date getFistDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    /**
     * 获取指定日期的当月最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDate(Date date) {
        Date m = addOneMonth(date);
        Date first = getFistDate(m);
        Calendar cal = Calendar.getInstance();
        cal.setTime(first);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }

    /**
     * 昨天
     * @param date
     * @return
     */
    public static Date getYesdayDate(Date date){
    Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE,-1);
        return cal.getTime();
    }
    /**
     * 得到几天前的时间
     * @param d
     * @param day
     * @return
     */
    public static Date getDateBefore(Date d,int day){
        Calendar now =Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE,now.get(Calendar.DATE)-day);
        return now.getTime();
    }

    /**
     * 明天
     * @param date
     * @return
     */
    public static Date getTomorrowDate(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE,1);
        return cal.getTime();
    }

    /**
     * 本月第一天
     * @param date
     * @return
     */
   public static Date getFirstCurrentMonth(Date date){
       Calendar cal = Calendar.getInstance();
       cal.setTime(date);
       cal.set(Calendar.DAY_OF_MONTH,1);
        return cal.getTime();
    }

    /**
     * 上月第一天
     * @param date
     * @return
     */
    public static Date getFirstLastMonth(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH,-1);
        cal.set(Calendar.DAY_OF_MONTH,1);
        return cal.getTime();
    }
    /**
     * 2017-02-15 17:00:00
     *
     * @param str
     * @return 17:00
     */
    public static String subTime(String str) {
        String newStr = str.substring(11, 16);
        return newStr;
    }


    /**
     * 解析特定日期格式，返回今日、昨日、前日、X月X日 星期X
     *
     * @param dateStr xml日期格式如： 2015-05-15T23:40:45+08:00  2017-02-15 17:00:00
     * @return
     */
//    public static String FriendlyDate(String dateStr) {
//        try {
//            Date compareDate = new SimpleDateFormat("MM-dd").parse(dateStr);
//            return FriendlyDate(compareDate);
//        } catch (ParseException e) {
//            return "";
//        }
//    }
//    public static String FriendlyDate(Date compareDate) {
//        Date nowDate = new Date();
//        long l = nowDate.getTime()-compareDate.getTime();
//        int dayDiff= (int) (l/1000/60/60/24);
//        if (dayDiff == 0)
//            return TODAY_STRING;
//        else
//            return new SimpleDateFormat("M月d日 E").format(compareDate);
//    }
//    public static int daysOfTwo(Date originalDate, Date compareDateDate) {
//        return daysOfTwo(originalDate.getTime() / MILLISECONDS_DAY, compareDateDate.getTime() / MILLISECONDS_DAY);
//    }
//    public static int daysOfTwo(long originalDay, long compareDay) {
//        return (int) (originalDay - compareDay);
//    }

    /**
     * 2017-02-15 17:00:00
     * @param dateStr
     * @return 2月15日 17:00
     */
    public static String hanDate(String dateStr){
        try {
            Date oldDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr);

            return new SimpleDateFormat("M月d日 HH:mm").format(oldDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 日期比较
     * @param taskTime
     * @return
     */
    public static int compareDate(String taskTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date();
        int i = 0;
        try {
            Date taskDate = sdf.parse(taskTime);
            i = (int) (curDate.getTime()-taskDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return i;
    }

    /**
     * 提前一小时
     * @return
     */
//    public static Date addTime(){
//
//        return calendar.getTime();
//    }

    public static int compareTime(String taskTime){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, -1);
        Date date = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        int i = 0;
        try {
            Date taskDate = sdf.parse(taskTime);
            long l = (date.getTime()-taskDate.getTime());
            i = (int) (l / 60 * 60 * 1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return i;
    }

    public static int daysBetween(Date smdate) throws ParseException
    {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        smdate=sdf.parse(sdf.format(smdate));
        Date bdate=sdf.parse(sdf.format(new Date()));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);

        return Integer.parseInt(String.valueOf(between_days));
    }
    /**
     * 计算两个日期之间相差的天数
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate,Date bdate) throws ParseException
    {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        smdate=sdf.parse(sdf.format(smdate));
        bdate=sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);

        return Integer.parseInt(String.valueOf(between_days));
    }
    /**
     *字符串的日期格式的计算
     */
    public static int daysBetween(String smdate) throws ParseException{
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(smdate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(sdf.format(new Date())));
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600);

        return Integer.parseInt(String.valueOf(between_days));
    }
    /**
     *字符串的日期格式的计算
     */
    public static int daysBetween(String smdate,String bdate) throws ParseException{
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(smdate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(bdate));
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);

        return Integer.parseInt(String.valueOf(between_days));
    }

}
