package zsbpj.lccpj.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:    2015年12月15日10:47:52
 * Description:    TimeUtils一个特殊的时间管理类
 */
public class TimeUtils {
    private static final long ONE_MINUTE = 60;
    private static final long ONE_HOUR = 3600;
    private static final long ONE_DAY = 86400;
    private static final long ONE_MONTH = 2592000;
    private static final long ONE_YEAR = 31104000;

    private final static long minute = 60 * 1000;// 1分钟
    private final static long hour = 60 * minute;// 1小时
    private final static long day = 24 * hour;// 1天
    private final static long month = 31 * day;// 月
    private final static long year = 12 * month;// 年


    public static long getCurrentTime() {
        return System.currentTimeMillis();
    }

    public static String getTimestamp() {
        return new Timestamp(getCurrentTime()).toString();
    }

    public static String StrTime(long time) {
        String time_str = time + "";
        time_str = time_str.substring(0, time_str.length() - 3);
        return time_str;
    }

    public static String getAWeek() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateNow = new Date();
        Calendar cl = Calendar.getInstance();
        cl.setTime(dateNow);
        //一周
        cl.add(Calendar.WEEK_OF_YEAR, -1);
        Date dateFrom = cl.getTime();
        return sdf.format(dateFrom);
    }

    public static String getAMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateNow = new Date();
        Calendar cl = Calendar.getInstance();
        cl.setTime(dateNow);
        //一个月
        cl.add(Calendar.MONTH, -1);
        Date dateFrom = cl.getTime();
        return sdf.format(dateFrom);
    }

    public static String getAYear() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateNow = new Date();
        Calendar cl = Calendar.getInstance();
        cl.setTime(dateNow);
        //一年
        cl.add(Calendar.YEAR, -1);
        Date dateFrom = cl.getTime();
        return sdf.format(dateFrom);
    }

    public static String getEndTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateNow = new Date();
        Calendar cl = Calendar.getInstance();
        cl.setTime(dateNow);
        Date dateFrom = cl.getTime();
        return sdf.format(dateFrom);
    }

    public static String getStartTime(String tj) {
        if (tj.equals("最近一周")) {
            return getAWeek();
        } else if (tj.equals("最近一月")) {
            return getAMonth();
        } else if (tj.equals("最近一年")) {
            return getAYear();
        } else {
            return "全部时间";
        }
    }


    /**
     * 距离今天多久
     */
    public static String fromToday(String times) {
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d = df.parse(times);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(d);

            long time = d.getTime() / 1000;
            long now = new Date().getTime() / 1000;
            long ago = now - time;
            if (ago <= ONE_HOUR)
                return ago / ONE_MINUTE + "分钟前";
            else if (ago <= ONE_DAY)
                return ago / ONE_HOUR + "小时" + (ago % ONE_HOUR / ONE_MINUTE)
                        + "分钟前";
            else if (ago <= ONE_DAY * 2)
                return "昨天" + calendar.get(Calendar.HOUR_OF_DAY) + "点"
                        + calendar.get(Calendar.MINUTE) + "分";
            else if (ago <= ONE_DAY * 3)
                return "前天" + calendar.get(Calendar.HOUR_OF_DAY) + "点"
                        + calendar.get(Calendar.MINUTE) + "分";
            else if (ago <= ONE_MONTH) {
                long day = ago / ONE_DAY;
                return day + "天前" + calendar.get(Calendar.HOUR_OF_DAY) + "点"
                        + calendar.get(Calendar.MINUTE) + "分";
            } else if (ago <= ONE_YEAR) {
                long month = ago / ONE_MONTH;
                long day = ago % ONE_MONTH / ONE_DAY;
                return month + "个月" + day + "天前"
                        + calendar.get(Calendar.HOUR_OF_DAY) + "点"
                        + calendar.get(Calendar.MINUTE) + "分";
            } else {
                long year = ago / ONE_YEAR;
                int month = calendar.get(Calendar.MONTH) + 1;// JANUARY which is 0 so month+1
                return year + "年前" + month + "月" + calendar.get(Calendar.DATE) + "日";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getTimeFormatText(String times) {
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = df.parse(times);

            if (date == null) {
                return null;
            }
            long diff = new Date().getTime() - date.getTime();
            long r = 0;
            if (diff > year) {
                r = (diff / year);
                return r + "年前";
            }
            if (diff > month) {
                r = (diff / month);
                return r + "个月前";
            }
            if (diff > day) {
                r = (diff / day);
                return r + "天前";
            }
            if (diff > hour) {
                r = (diff / hour);
                return r + "小时前";
            }
            if (diff > minute) {
                r = (diff / minute);
                return r + "分钟前";
            }
            return "刚刚";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "刚刚";
    }

}
