/*******************************************************************************
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 ******************************************************************************/

package com.cdkj.util;


import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * DateKit.
 */
public final class DateUtil {

    /**
     * 默认的日期格式 .
     */
    public static final String DEFAULT_REGEX_YYYYMMDD = "yyyyMMdd";
    public static final int DIFF_UNIT_DAY = 1;
    public static final int DIFF_UNIT_HOUR = 2;
    public static final int DIFF_UNIT_MIN = 3;
    public static final int DIFF_UNIT_SS = 4;
    /**
     * 默认的DateFormat 实例
     */
    private static final EPNDateFormat DEFAULT_FORMAT_YYYYMMDD = new EPNDateFormat(DEFAULT_REGEX_YYYYMMDD);
    /**
     * 默认的日期格式 .
     */
    public static String DEFAULT_DATE_REGEX = "yyyy-MM-dd";
    /**
     * 默认的DateFormat 实例
     */
    private static final EPNDateFormat DEFAULT_FORMAT = new EPNDateFormat(DEFAULT_DATE_REGEX);
    /**
     * 默认的日期格式 .
     */
    public static String DEFAULT_DATE_TIME_RFGFX = "yyyy-MM-dd HH:mm:ss";
    /**
     * 默认的DateFormat 实例
     */
    private static final EPNDateFormat DEFAULT_FORMAT_YYYY_MM_DD_HH_MIN_SS = new EPNDateFormat(DEFAULT_DATE_TIME_RFGFX);
    protected static Logger logger = LoggerFactory.getLogger(DateUtil.class);
    private static Map<String, EPNDateFormat> formatMap = new HashMap<>();

    static {
        formatMap.put(DEFAULT_DATE_REGEX, DEFAULT_FORMAT);
        formatMap.put(DEFAULT_DATE_TIME_RFGFX, DEFAULT_FORMAT_YYYY_MM_DD_HH_MIN_SS);
        formatMap.put(DEFAULT_REGEX_YYYYMMDD, DEFAULT_FORMAT_YYYYMMDD);
    }

    private DateUtil() {

    }

    public static void setDateFromat(String dateFormat) {
        if (dateFormat.isEmpty())
            throw new IllegalArgumentException("dateFormat can not be blank.");
        DEFAULT_DATE_REGEX = dateFormat;
    }

    public static void setTimeFromat(String timeFormat) {
        if (timeFormat.isEmpty())
            throw new IllegalArgumentException("timeFormat can not be blank.");
        DEFAULT_DATE_TIME_RFGFX = timeFormat;
    }

    public static Date toDate(String dateStr) {
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_TIME_RFGFX);
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            logger.error("run error", e);
        }
        return date;
    }

    public static String toStr(Date date) {
        return toStr(date, DEFAULT_DATE_REGEX);
    }

    public static String toStr(Date date, String format) {
        return getDateFormat(format).format(date);
    }

    /**
     * 获取2个时间的时间差
     *
     * @param begin    开始时间
     * @param end      结尾时间
     * @param diffUnit 1-day 2-hour 3-min 4-ss
     * @return
     */
    public static long getTimeDiff(String begin, String end, int diffUnit) {
        Date d1 = toDate(begin);
        Date d2 = toDate(end);
        //除以1000是为了转换成秒
        long between = (d1.getTime() - d2.getTime()) / 1000;

        switch (diffUnit) {
            case DateUtil.DIFF_UNIT_DAY:
                return between / (24 * 60 * 60);
            case DateUtil.DIFF_UNIT_HOUR:
                return between / (60 * 60);
            case DateUtil.DIFF_UNIT_MIN:
                return between / 60;
            case DateUtil.DIFF_UNIT_SS:
                return between;
        }

        return Long.MIN_VALUE;
    }

    /**
     * 时间对象格式化成String ,等同于java.text.DateFormat.format();
     *
     * @param date 需要格式化的时间对象
     *             <p>
     *             2014年5月5日 下午12:00:00 flyfox 330627517@qq.com
     * @return 转化结果
     */
    public static String format(Date date) {
        return DEFAULT_FORMAT.format(date);
    }

    public static Date format(String date, String regex) {
        SimpleDateFormat sdf = new SimpleDateFormat(regex);
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            logger.error("run error", e);
        }
        return getDate();
    }

    /**
     * 时间对象格式化成String ,等同于java.text.SimpleDateFormat.format();
     *
     * @param date  需要格式化的时间对象
     * @param regex 定义格式的字符串
     *              <p>
     *              2014年5月5日 下午12:00:00 flyfox 330627517@qq.com
     * @return 转化结果
     */
    public static String format(Date date, String regex) {
        return getDateFormat(regex).format(date);
    }

    private static EPNDateFormat getDateFormat(String regex) {
        EPNDateFormat fmt = formatMap.get(regex);
        if (fmt == null) {
            fmt = new EPNDateFormat(regex);
            formatMap.put(regex, fmt);
        }
        return fmt;
    }

    /**
     * 主要是给milano使用，数据库只认java.sql.*
     *
     * @param date
     * @return
     */
    public static Timestamp getSqlTimestamp(Date date) {
        if (null == date) {
            date = new Date();
        }
        return getSqlTimestamp(date.getTime());
    }

    /**
     * 主要是给milano使用，数据库只认java.sql.*
     *
     * @param time
     * @return
     */
    public static Timestamp getSqlTimestamp(long time) {
        return new Timestamp(time);
    }

    /**
     * 尝试解析时间字符串 ,if failed return null;
     *
     * @param time 2014年5月5日 下午12:00:00
     * @return
     */
    public static Date parseByAll(String time) {
        Date stamp = null;
        if (time == null || "".equals(time))
            return null;
        Pattern p3 = Pattern.compile("\\b\\d{2}[.-]\\d{1,2}([.-]\\d{1,2}){0,1}\\b");
        if (p3.matcher(time).matches()) {
            time = (time.charAt(0) == '1' || time.charAt(0) == '0' ? "20" : "19") + time;
        }

        stamp = parse(time, "yyyy-MM-ddHH:mm:ss");
        if (stamp == null) {
            stamp = parse(time, "yyyy-MM-dd");
        }
        if (stamp == null) {
            stamp = parse(time, "yyyy.MM.dd");
        }
        if (stamp == null) {
            stamp = parse(time, "yyyy-MM");
        }
        if (stamp == null) {
            stamp = parse(time, "yyyy.MM");
        }
        if (stamp == null) {
            stamp = parse(time, "yyyy-MM-dd");
        }
        if (stamp == null) {
            stamp = parse(time, "yy-MM-dd");
        }
        if (stamp == null) {
            stamp = parse(time, "yyyy.MM.dd");
        }
        if (stamp == null) {
            stamp = parse(time, "yyyy-MM.dd");
        }
        if (stamp == null) {
            stamp = parse(time, "yyyy.MM-dd");
        }
        if (stamp == null) {
            stamp = parse(time, "yyyyMMdd");
        }
        if (stamp == null) {
            stamp = parse(time, "yyyy年MM月dd日");
        }
        if (stamp == null) {
            stamp = parse(time, "yyyyMM");
        }
        if (stamp == null) {
            stamp = parse(time, "yyyy年MM月");
        }
        if (stamp == null) {
            stamp = parse(time, "yyyy");
        }
        if (stamp == null) {
            stamp = parse(time, "yyyy年");
        }
        return stamp;
    }

    /**
     * 解析字符串成时间 ,遇到错误返回null不抛异常
     *
     * @param source 2014年5月5日 下午12:00:00 flyfox 330627517@qq.com
     * @return 解析结果
     */
    public static Date parse(String source) {
        try {
            return DEFAULT_FORMAT.parse(source);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 解析字符串成时间 ,遇到错误返回null不抛异常
     *
     * @param source
     * @param regex  2014年5月5日 下午12:00:00 flyfox 330627517@qq.com
     * @return 解析结果
     */
    public static Date parse(String source, String regex) {
        try {
            EPNDateFormat fmt = getDateFormat(regex);
            return fmt.parse(source);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 取得当前时间的Date对象 ;
     * <p>
     * 2014年5月5日 下午12:00:00 flyfox 330627517@qq.com
     *
     * @return
     */
    public static Date getNowDate() {
        return new Date(System.currentTimeMillis());
    }

    public static Date getDate() {
        return new Date(System.currentTimeMillis());
    }

    /**
     * 获取当前时间字符串
     *
     * @return
     */
    public static String getNow() {
        return getNow(DEFAULT_DATE_TIME_RFGFX);
    }

    /**
     * 获取当前日期字符串
     *
     * @return
     */
    public static String today() {
        return toStr(new Date());
    }

    /**
     * 获取当前时间的时间戳，单位 毫秒
     *
     * @return
     */
    public static long getDateByTime() {
        return new Date().getTime();
    }

    /**
     * 获取当前时间的时间戳，单位 秒
     *
     * @return
     */
    public static long getDateTime() {
        long time = System.currentTimeMillis();
        String timeStr = String.valueOf(time);
        timeStr = timeStr.substring(0, timeStr.length() - 3);
        return Long.parseLong(timeStr);
    }

    /**
     * 获取当前时间字符串
     * <p>
     * 2014年7月4日 下午11:47:10 flyfox 330627517@qq.com
     *
     * @param regex 格式表达式
     * @return
     */
    public static String getNow(String regex) {
        return format(getNowDate(), regex);
    }

    /**
     * 两个日期的时间差，返回"X天X小时X分X秒"
     *
     * @param begin
     * @param end
     * @return
     */
    public static String getBetween(Date begin, Date end) {
        long between = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒
        long day = between / (24 * 3600);
        long hour = between % (24 * 3600) / 3600;
        long minute = between % 3600 / 60;
        long second = between % 60 / 60;

        StringBuilder sb = new StringBuilder();
        sb.append(day);
        sb.append("天");
        sb.append(hour);
        sb.append("小时");
        sb.append(minute);
        sb.append("分");
        sb.append(second);
        sb.append("秒");

        return sb.toString();
    }

    /**
     * 返回两个日期之间隔了多少小时
     *
     * @param end
     * @return
     */
    public static int getDateHourSpace(Date start, Date end) {
        int hour = (int) ((start.getTime() - end.getTime()) / 3600 / 1000);
        return hour;
    }

    /**
     * 返回两个日期之间隔了多少天
     *
     * @param start
     * @param end
     * @return
     */
    public static int getDateDaySpace(Date start, Date end) {
        int day = getDateHourSpace(start, end) / 24;
        return day;
    }

    /**
     * 得到某一天是星期几
     *
     * @param date 日期字符串
     * @return String 星期几
     */
    @SuppressWarnings("static-access")
    public static String getDateInWeek(Date date) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayIndex = calendar.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY;
        if (dayIndex < 0) {
            dayIndex = 0;
        }
        return weekDays[dayIndex];
    }

    /**
     * 日期减去多少个小时
     *
     * @param date
     * @param hourCount 多少个小时
     * @return
     */
    public static Date getDateReduceHour(Date date, long hourCount) {
        long time = date.getTime() - 3600 * 1000 * hourCount;
        Date dateTemp = new Date();
        dateTemp.setTime(time);
        return dateTemp;
    }

    /**
     * 日期区间分割
     *
     * @param start
     * @param end
     * @param splitCount
     * @return
     */
    public static List<Date> getDateSplit(Date start, Date end, long splitCount) {
        long startTime = start.getTime();
        long endTime = end.getTime();
        long between = endTime - startTime;

        long count = splitCount - 1l;
        long section = between / count;

        List<Date> list = new ArrayList<Date>();
        list.add(start);

        for (long i = 1l; i < count; i++) {
            long time = startTime + section * i;
            Date date = new Date();
            date.setTime(time);
            list.add(date);
        }

        list.add(end);

        return list;
    }

    /**
     * 返回两个日期之间隔了多少天，包含开始、结束时间
     *
     * @param start
     * @param end
     * @return
     */
    public static List<String> getDaySpaceDate(Date start, Date end) {
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(start);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(end);
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);

        List<String> dateList = new LinkedList<String>();

        long dayCount = (toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24);
        if (dayCount < 0) {
            return dateList;
        }

        dateList.add(format(fromCalendar.getTime(), DEFAULT_DATE_REGEX));

        for (int i = 0; i < dayCount; i++) {
            fromCalendar.add(Calendar.DATE, 1);// 增加一天
            dateList.add(format(fromCalendar.getTime(), DEFAULT_DATE_REGEX));
        }

        return dateList;
    }

    /**
     * 获取开始时间
     *
     * @param start
     * @param end
     * @return
     */
    public static Date getDateByDay(Date start, int end) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        calendar.add(Calendar.DATE, end);// 明天1，昨天-1
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date date = calendar.getTime();
        return date;
    }

    /**
     * 获取结束时间
     *
     * @param start
     * @return
     */
    public static Date endDateByDay(Date start) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        Date date = calendar.getTime();
        return date;
    }

    /**
     * 获取开始时间
     *
     * @param start
     * @param end
     * @return
     */
    public static Date startDateByHour(Date start, int end) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        calendar.set(Calendar.MINUTE, end);
        Date date = calendar.getTime();
        return date;
    }

    /**
     * 获取结束时间
     *
     * @param end
     * @return
     */
    public static Date endDateByHour(Date end) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(end);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        Date date = calendar.getTime();
        return date;
    }

    /**
     * 根据年份和周得到周的开始和结束日期
     *
     * @param year
     * @param week
     * @return
     */
    public static Map<String, Date> getStartEndDateByWeek(int year, int week) {
        Calendar weekCalendar = new GregorianCalendar();
        weekCalendar.set(Calendar.YEAR, year);
        weekCalendar.set(Calendar.WEEK_OF_YEAR, week);
        weekCalendar.set(Calendar.DAY_OF_WEEK, weekCalendar.getFirstDayOfWeek());

        // 得到周的开始日期
        Date startDate = weekCalendar.getTime();

        weekCalendar.roll(Calendar.DAY_OF_WEEK, 6);
        // 得到周的结束日期
        Date endDate = weekCalendar.getTime();

        // 开始日期往前推一天
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(startDate);
        // 明天1，昨天-1
        startCalendar.add(Calendar.DATE, 1);
        startCalendar.set(Calendar.HOUR_OF_DAY, 0);
        startCalendar.set(Calendar.MINUTE, 0);
        startCalendar.set(Calendar.SECOND, 0);
        startCalendar.set(Calendar.MILLISECOND, 0);
        startDate = startCalendar.getTime();

        // 结束日期往前推一天
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDate);
        // 明天1，昨天-1
        endCalendar.add(Calendar.DATE, 1);
        endCalendar.set(Calendar.HOUR_OF_DAY, 23);
        endCalendar.set(Calendar.MINUTE, 59);
        endCalendar.set(Calendar.SECOND, 59);
        endCalendar.set(Calendar.MILLISECOND, 999);
        endDate = endCalendar.getTime();

        Map<String, Date> map = new HashMap<>();
        map.put("start", startDate);
        map.put("end", endDate);
        return map;
    }

    /**
     * 根据日期月份，获取月份的开始和结束日期
     *
     * @param date
     * @return
     */
    public static Map<String, Date> getMonthDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        // 得到前一个月的第一天
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        Date start = calendar.getTime();

        // 得到前一个月的最后一天
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date end = calendar.getTime();

        Map<String, Date> map = new HashMap<>();
        map.put("start", start);
        map.put("end", end);
        return map;
    }

    /**
     * 获取 sec 秒之后的系统时间
     *
     * @param sec
     * @return
     */
    public static String getNowAfterSecTime(int sec) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, +sec);
        Date time = cal.getTime();
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String ctime = formatter.format(time);

        return ctime;
    }

    /**
     * 判断 dateTime  是否在开始/结束时间之间
     *
     * @param dateTime  目标时间
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param regex     时间格式
     * @return true-两者之间，false-不是
     */
    public static boolean isTween(String dateTime, String startTime, String endTime, String regex) {
        DateFormat df = null;
        if (StringUtil.isEmpty(regex))
            df = new SimpleDateFormat(DEFAULT_DATE_TIME_RFGFX);
        else
            df = new SimpleDateFormat(regex);
        try {

            Date dateDt = df.parse(dateTime);
            Date startDt = df.parse(startTime);
            Date endDt = df.parse(endTime);

            if (dateDt.after(startDt) && dateDt.before(endDt))
                return true;

            return false;
        } catch (ParseException e) {
            logger.error("DateUtil.isTween.error:{}", e);
            return false;
        }
    }

    /**
     * 两个时间比较  string, string  "yyyy-MM-dd HH:mm:ss"
     *
     * @param date1 时间1
     * @param date2 时间2
     * @return 0-表示2个时间相等；小于0-表示d1在d2之前；大于0表示d1在d2之后
     */
    public static int dateCompare(String date1, String date2) {
        SimpleDateFormat df = new SimpleDateFormat(DEFAULT_DATE_TIME_RFGFX);

        try {
            Date d1 = df.parse(date1);
            Date d2 = df.parse(date2);
            return d1.compareTo(d2);
        } catch (Exception e) {
            logger.error("DateUtil.dateCompare.error:{}", e);
            return Integer.MAX_VALUE;
        }
    }

    /**
     * 计算2个时间差   --  (date1 - date2)
     *
     * @param date1 时间1
     * @param date2 时间2
     * @param type  差值类型 Days-天，Hours-小时，Minutes-分钟，Seconds-秒
     * @return long值
     */
    public static long getBetween(String date1, String date2, String type) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date one = df.parse(date1);
            Date two = df.parse(date2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff;
            //time1 = "2017-02-24 00:00:01" time2 ="2017-03-02 00:00:01"  相减为负
            diff = time1 - time2;
//            if (diff < 0)
//                return 0;

            if ("Days".equals(type)) {
                //天
                return diff / (1000 * 60 * 60 * 24);
            }
            if ("Hours".equals(type)) {
                //小时
                return diff / (1000 * 60 * 60);
            }
            if ("Minutes".equals(type)) {
                //分
                return diff / (1000 * 60);
            }
            if ("Seconds".equals(type)) {
                //秒
                return diff / 1000;
            }
        } catch (ParseException e) {
            logger.error("DateUtil.getBetween.error:{}", e);
        }

        return -1;
    }

    /**
     * 获取指定时间
     *
     * @param date      标准日期
     * @param type      差值类型
     * @param diffValue 差值  负数：标准时间之前；正数：标准时间之后
     * @return 指定时间
     */
    public static String getTime(String date, int type, int diffValue) throws ParseException {
        //含时分秒
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_TIME_RFGFX);
        Date date1 = sdf.parse(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);

        switch (type) {
            case Calendar.HOUR:
                calendar.add(Calendar.HOUR, diffValue);
                return sdf.format(calendar.getTime());
            case Calendar.MINUTE:
                calendar.add(Calendar.MINUTE, diffValue);
                return sdf.format(calendar.getTime());
            case Calendar.SECOND:
                calendar.add(Calendar.SECOND, diffValue);
                return sdf.format(calendar.getTime());
        }

        return "";
    }

    /**
     * 获取年，月，日
     *
     * @param date 时间字符串
     * @return Map  year,month,day
     */
    public static Map<String, String> getDateMap(String date) {
        Map<String, String> map = new HashMap<>();
        map.put("year", date.substring(0, 4));
        map.put("month", date.substring(5, 7));
        map.put("day", date.substring(8, 10));
        return map;
    }

    /**
     * 获取过去/将来时间
     *
     * @param type  year/month/week
     * @param value 差值，负数拿过去时间，正数拿将来时间
     * @return String
     */
    public static String getDate(String type, int value) {
        Calendar curr = Calendar.getInstance();
        return getChoose(curr, type, value);
    }


    /**
     * 获取过去/将来时间
     *
     * @param type  year/month/week
     * @param value 差值，负数拿过去时间，正数拿将来时间
     * @return String
     */
    public static String getDate(String type, String date, int value, String format) {
        Calendar curr = Calendar.getInstance();
        curr.setTime(getDate(date, format));
        return getChoose(curr, type, value);
    }

    public static String getChoose(Calendar curr, String type, int value) {
        if ("week".equals(type)) {
            curr.set(Calendar.DAY_OF_MONTH, curr.get(Calendar.DAY_OF_MONTH) + 7 * value);
            return format(curr.getTime(), DateUtil.DEFAULT_DATE_TIME_RFGFX);
        } else if ("month".equals(type)) {
            curr.set(Calendar.MONTH, curr.get(Calendar.MONTH) + value);
            return format(curr.getTime(), DateUtil.DEFAULT_DATE_TIME_RFGFX);
        } else if ("year".equals(type)) {
            curr.set(Calendar.YEAR, curr.get(Calendar.YEAR) + value);
            return format(curr.getTime(), DateUtil.DEFAULT_DATE_TIME_RFGFX);
        }
        return "未知类型!";
    }

    /**
     * 获取日期时间
     *
     * @param time   时间
     * @param format 格式化标准
     * @return Date
     */
    public static Date getDate(String time, String format) {
        return format(time, format);
    }

    /**
     * 根据指定日期月份，获取月份的开始和结束日期
     *
     * @param month 月份 Demo:2017-03
     * @return Map<String,String>
     */
    public static Map<String, String> getMonthDate(String month) {
        //2017-03-15 12:00:00
        month = month + "-15 12:00:00";
        Date d = DateUtil.getDate(month, DateUtil.DEFAULT_DATE_TIME_RFGFX);
        Map<String, Date> map = DateUtil.getMonthDate(d);

        Map<String, String> rtnMap = new HashedMap();
        rtnMap.put("start", DateUtil.format(map.get("start"), DateUtil.DEFAULT_DATE_TIME_RFGFX));
        rtnMap.put("end", DateUtil.format(map.get("end"), DateUtil.DEFAULT_DATE_TIME_RFGFX));

        return rtnMap;
    }

    public static List<String> getAllMonthDate(String date, int monthCount) {

        Calendar calendar = Calendar.getInstance();
        String year = date.substring(0, 4);
        String month = date.substring(5, 7);
        String currMonth = year + "-" + month;
        calendar.set(Calendar.YEAR, Integer.valueOf(year));
        calendar.set(Calendar.MONTH, Integer.valueOf(month) - 1);
        int count = Math.abs(monthCount);
        List<String> list = new ArrayList<String>(count + 1);
        list.add(0, currMonth);
        for (int i = 0; i < count; i++) {
            if (monthCount > 0) {
                calendar.add(Calendar.MONTH, 1);
            } else {
                calendar.add(Calendar.MONTH, -1);
            }
            String temp = DateUtil.format(calendar.getTime(), "yyyy-MM");
            list.add(i + 1, temp);
        }
        return list;

    }

    /**
     * 根据时间戳生成订单号,可以自定义前缀
     *
     * @param prefix
     * @return eg:2017
     */
    public static String getOrderNo(final String prefix) {
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmm");
        String MI = "";
        String last = "";
        Date date = null;
        synchronized (df) {
            date = new Date();
            Calendar CD = Calendar.getInstance();
            MI = CD.get(Calendar.MILLISECOND) + "";
            if (MI.length() < 3) {
                Random random = new Random();
                MI = (random.nextInt(999 - 100 + 1) + 100) + "";
            }
            /*Random random = new Random();
            last = (random.nextInt(999 - 100 + 1) + 100) + "";*/
        }
        return prefix + df.format(date) + MI + last;
    }

    /**
     * 获取日期的前一天的开始日期与结束日期
     *
     * @param time
     * @return
     */
    public static Map<String, String> getTheDayBeforeDate(String time) {
        Map<String, String> result = new HashMap<>(2);

        Date date = toDate(time);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date startDate = calendar.getTime();

        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 59);
        Date endDate = calendar.getTime();

        result.put("start", toStr(startDate, DEFAULT_DATE_TIME_RFGFX));
        result.put("end", toStr(endDate, DEFAULT_DATE_TIME_RFGFX));

        return result;
    }

    public static void main(String[] args) {
        String date = DateUtil.getNow();
        List<String> list = getAllMonthDate(date, -5);
        System.out.println(list.toString());

    }
}

class EPNDateFormat {
    private SimpleDateFormat instance;

    EPNDateFormat() {
        instance = new SimpleDateFormat(DateUtil.DEFAULT_DATE_REGEX);
        instance.setLenient(false);
    }

    EPNDateFormat(String regex) {
        instance = new SimpleDateFormat(regex);
        instance.setLenient(false);
    }

    synchronized String format(Date date) {
        if (date == null) {
            return "";
        }
        return instance.format(date);
    }

    synchronized Date parse(String source) throws ParseException {
        return instance.parse(source);
    }
}