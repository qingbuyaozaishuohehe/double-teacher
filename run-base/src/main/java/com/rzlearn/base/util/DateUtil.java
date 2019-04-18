package com.rzlearn.base.util;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

/**
 * <p>ClassName:DateUtil</p>
 * <p>Description:日期操作辅助类</p>
 *
 * @author JiPeigong
 * @date 2018年4月11日
 * @see
 * @since
 */
public final class DateUtil {

    /**
     * Number of milliseconds in a standard second.
     */
    public static final long MILLIS_PER_SECOND = 1000;
    /**
     * Number of milliseconds in a standard minute.
     */
    public static final long MILLIS_PER_MINUTE = 60 * MILLIS_PER_SECOND;
    /**
     * Number of milliseconds in a standard hour.
     */
    public static final long MILLIS_PER_HOUR = 60 * MILLIS_PER_MINUTE;
    /**
     * Number of milliseconds in a standard day.
     */
    public static final long MILLIS_PER_DAY = 24 * MILLIS_PER_HOUR;

    private static final int[] MONTH_LENGTH = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    public static final String HOUR_NAME = "小时";
    public static final String DAY_NAME = "天";

    private DateUtil() {
    }

    /**
     * 日期格式
     */
    public interface DatePattern {
        /**
         * The constant HHMMSS.
         */
        String HHMMSS = "HHmmss";
        /**
         * The constant HH_MM_SS.
         */
        String HH_MM_SS = "HH:mm:ss";
        /**
         * The constant YYYYMMDD.
         */
        String YYYYMMDD = "yyyyMMdd";
        /**
         * The constant YYYY_MM_DD.
         */
        String YYYY_MM_DD = "yyyy-MM-dd";
        /**
         * The constant YYYY/MM/DD.
         */
        String CENGINE_DATE_FORMAT = "yyyy/MM/dd";

        /**
         * The constant YYYY/MM.
         */
        String CENGINE_MONTH_FORMAT = "yyyy/MM";

        /**
         * The constant YYYYMMDDHHMMSS.
         */
        String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
        /**
         * The constant YYYYMMDDHHMMSSSSS.
         */
        String YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";
        /**
         * The constant YYYY_MM_DD_HH_MM_SS.
         */
        String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

        /**
         * The constant YYYYMMDD_HH_MM_SS.
         */
        String YYYYMMDD_HH_MM_SS = "yyyy/MM/dd HH:mm:ss";

        /**
         * The constant YYYY_MM_DD_HH_MM_SS_SSS.
         */
        String YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";
        /**
         * The constant MM_DD_CN.
         */
        String MM_DD_CN = "MM月dd日";
        /**
         * The constant YYYY_MM_DD_CN.
         */
        String YYYY_MM_DD_CN = "yyyy年MM月dd日";

        /**
         * The constant YYYY_MM.
         */
        String YYYY_MM = "yyyy-MM";
    }

    /**
     * 格式化日期
     *
     * @param date the date
     * @return string string
     */
    public static final String format(Object date) {
        return format(date, DatePattern.YYYY_MM_DD);
    }

    /**
     * 格式化日期
     *
     * @param date    the date
     * @param pattern the pattern
     * @return string string
     */
    public static final String format(Object date, String pattern) {
        if (date == null) {
            return null;
        }
        if (pattern == null) {
            return format(date);
        }
        return new SimpleDateFormat(pattern).format(date);
    }

    /**
     * 获取日期
     *
     * @return date date
     */
    public static final String getDate() {
        return format(new Date());
    }

    /**
     * 获取日期时间
     *
     * @return date time
     */
    public static final String getDateTime() {
        return format(new Date(), DatePattern.YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 获取日期
     *
     * @param pattern the pattern
     * @return date time
     */
    public static final String getDateTime(String pattern) {
        return format(new Date(), pattern);
    }

    /**
     * 日期计算
     *
     * @param date   the date
     * @param field  the field
     * @param amount the amount
     * @return date date
     */
    public static final Date addDate(Date date, int field, int amount) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(field, amount);
        return calendar.getTime();
    }

    /**
     * 上months个月的日期.
     *
     * @param months the months
     * @return the last month date
     */
    public static final Date getMinusMonthsDate(int months) {
        LocalDate date = LocalDate.now();
        date = date.minusMonths(months);
        ZonedDateTime zdt = date.atStartOfDay(ZoneId.systemDefault());
        return Date.from(zdt.toInstant());
    }

    /**
     * Gets plus months date.
     *
     * @param months the months
     * @return the plus months date
     */
    public static final Date getPlusMonthsDate(int months) {
        LocalDate date = LocalDate.now();
        date = date.plusMonths(months);
        ZonedDateTime zdt = date.atStartOfDay(ZoneId.systemDefault());
        return Date.from(zdt.toInstant());
    }

    /**
     * 下days个日的日期.
     *
     * @param days the days
     * @return the minus day date
     */
    public static final Date getMinusDaysDate(int days) {
        LocalDate date = LocalDate.now();
        date = date.minusDays(days);
        ZonedDateTime zdt = date.atStartOfDay(ZoneId.systemDefault());
        return Date.from(zdt.toInstant());
    }

    /**
     * Gets minus days date.
     *
     * @param date the date
     * @param days the days
     * @return the minus days date
     */
    public static final Date getMinusDaysDate(Date date, int days) {
        Instant instant = date.toInstant();
        LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        localDate = localDate.minusDays(days);
        ZonedDateTime zdt = localDate.atStartOfDay(ZoneId.systemDefault());
        return Date.from(zdt.toInstant());
    }

    /**
     * Gets minus day date.
     *
     * @param days the days
     * @return the minus day date
     */
    public static final Date getPlusDaysDate(int days) {
        LocalDate date = LocalDate.now();
        date = date.plusDays(days);
        ZonedDateTime zdt = date.atStartOfDay(ZoneId.systemDefault());
        return Date.from(zdt.toInstant());
    }

    /**
     * 字符串转换为日期:不支持yyM[M]d[d]格式
     *
     * @param date the date
     * @return date date
     */
    public static final Date stringToDate(String date) {
        if (date == null) {
            return null;
        }
        String separator = String.valueOf(date.charAt(4));
        String pattern = "yyyyMMdd";
        if (!separator.matches("\\d*")) {
            pattern = "yyyy" + separator + "MM" + separator + "dd";
            if (date.length() < 10) {
                pattern = "yyyy" + separator + "M" + separator + "d";
            }
            pattern += " HH:mm:ss.SSS";
        } else if (date.length() < 8) {
            pattern = "yyyyMM";
        } else {
            pattern += "HHmmss.SSS";
        }
        pattern = pattern.substring(0, Math.min(pattern.length(), date.length()));
        try {
            return new SimpleDateFormat(pattern).parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 间隔天数
     *
     * @param startDate the start date
     * @param endDate   the end date
     * @return day between
     */
    public static final Integer getDayBetween(Date startDate, Date endDate) {
        Calendar start = Calendar.getInstance();
        start.setTime(startDate);
        start.set(Calendar.HOUR_OF_DAY, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        start.set(Calendar.MILLISECOND, 0);
        Calendar end = Calendar.getInstance();
        end.setTime(endDate);
        end.set(Calendar.HOUR_OF_DAY, 0);
        end.set(Calendar.MINUTE, 0);
        end.set(Calendar.SECOND, 0);
        end.set(Calendar.MILLISECOND, 0);

        long n = end.getTimeInMillis() - start.getTimeInMillis();
        return (int) (n / (60 * 60 * 24 * 1000L));
    }

    /**
     * Gets minute between.
     *
     * @param startDate the start date
     * @param endDate   the end date
     * @return the minute between
     */
    public static final Long getMinuteBetween(Date startDate, Date endDate) {
        Instant startInstant = startDate.toInstant();
        Instant endInstant = endDate.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime startDateTime = startInstant.atZone(zoneId).toLocalDateTime();
        LocalDateTime endDateTime = endInstant.atZone(zoneId).toLocalDateTime();
        return ChronoUnit.MINUTES.between(startDateTime, endDateTime);
    }

    /**
     * 间隔月
     *
     * @param startDate the start date
     * @param endDate   the end date
     * @return month between
     */
    public static final Integer getMonthBetween(Date startDate, Date endDate) {
        if (startDate == null || endDate == null || !startDate.before(endDate)) {
            return null;
        }
        Calendar start = Calendar.getInstance();
        start.setTime(startDate);
        Calendar end = Calendar.getInstance();
        end.setTime(endDate);
        int year1 = start.get(Calendar.YEAR);
        int year2 = end.get(Calendar.YEAR);
        int month1 = start.get(Calendar.MONTH);
        int month2 = end.get(Calendar.MONTH);
        int n = (year2 - year1) * 12;
        n = n + month2 - month1;
        return n;
    }

    /**
     * 间隔月，多一天就多算一个月
     *
     * @param startDate the start date
     * @param endDate   the end date
     * @return month between with day
     */
    public static final Integer getMonthBetweenWithDay(Date startDate, Date endDate) {
        if (startDate == null || endDate == null || !startDate.before(endDate)) {
            return null;
        }
        Calendar start = Calendar.getInstance();
        start.setTime(startDate);
        Calendar end = Calendar.getInstance();
        end.setTime(endDate);
        int year1 = start.get(Calendar.YEAR);
        int year2 = end.get(Calendar.YEAR);
        int month1 = start.get(Calendar.MONTH);
        int month2 = end.get(Calendar.MONTH);
        int n = (year2 - year1) * 12;
        n = n + month2 - month1;
        int day1 = start.get(Calendar.DAY_OF_MONTH);
        int day2 = end.get(Calendar.DAY_OF_MONTH);
        if (day1 <= day2) {
            n++;
        }
        return n;
    }

    //////// 日期比较 ///////////

    /**
     * 是否同一天.
     *
     * @param date1 the date 1
     * @param date2 the date 2
     * @return the boolean
     * @see DateUtils#isSameDay(Date, Date) DateUtils#isSameDay(Date, Date)
     */
    public static boolean isSameDay(final Date date1, final Date date2) {
        return DateUtils.isSameDay(date1, date2);
    }

    /**
     * 是否同一时刻.
     *
     * @param date1 the date 1
     * @param date2 the date 2
     * @return the boolean
     */
    public static boolean isSameTime(final Date date1, final Date date2) {
        // date.getMillisOf() 比date.getTime()快
        return date1.compareTo(date2) == 0;
    }

    /**
     * 判断日期是否在范围内，包含相等的日期
     *
     * @param date  the date
     * @param start the start
     * @param end   the end
     * @return the boolean
     */
    public static boolean isBetween(final Date date, final Date start, final Date end) {
        if (date == null || start == null || end == null || start.after(end)) {
            throw new IllegalArgumentException("some date parameters is null or dateBein after dateEnd");
        }
        return !date.before(start) && !date.after(end);
    }

    //////////// 往前往后滚动时间//////////////

    /**
     * 加一月
     *
     * @param date   the date
     * @param amount the amount
     * @return the date
     */
    public static Date addMonths(final Date date, int amount) {
        return DateUtils.addMonths(date, amount);
    }

    /**
     * 减一月
     *
     * @param date   the date
     * @param amount the amount
     * @return the date
     */
    public static Date subMonths(final Date date, int amount) {
        return DateUtils.addMonths(date, -amount);
    }

    /**
     * 加一周
     *
     * @param date   the date
     * @param amount the amount
     * @return the date
     */
    public static Date addWeeks(final Date date, int amount) {
        return DateUtils.addWeeks(date, amount);
    }

    /**
     * 减一周
     *
     * @param date   the date
     * @param amount the amount
     * @return the date
     */
    public static Date subWeeks(final Date date, int amount) {
        return DateUtils.addWeeks(date, -amount);
    }

    /**
     * 加一天
     *
     * @param date   the date
     * @param amount the amount
     * @return the date
     */
    public static Date addDays(final Date date, final int amount) {
        return DateUtils.addDays(date, amount);
    }

    /**
     * 减一天
     *
     * @param date   the date
     * @param amount the amount
     * @return the date
     */
    public static Date subDays(final Date date, int amount) {
        return DateUtils.addDays(date, -amount);
    }

    /**
     * 加一小时
     *
     * @param date   the date
     * @param amount the amount
     * @return the date
     */
    public static Date addHours(final Date date, int amount) {
        return DateUtils.addHours(date, amount);
    }

    /**
     * 减一小时
     *
     * @param date   the date
     * @param amount the amount
     * @return the date
     */
    public static Date subHours(final Date date, int amount) {
        return DateUtils.addHours(date, -amount);
    }

    /**
     * 加一分钟
     *
     * @param date   the date
     * @param amount the amount
     * @return the date
     */
    public static Date addMinutes(final Date date, int amount) {
        return DateUtils.addMinutes(date, amount);
    }

    /**
     * 减一分钟
     *
     * @param date   the date
     * @param amount the amount
     * @return the date
     */
    public static Date subMinutes(final Date date, int amount) {
        return DateUtils.addMinutes(date, -amount);
    }

    /**
     * 终于到了，续一秒.
     *
     * @param date   the date
     * @param amount the amount
     * @return the date
     */
    public static Date addSeconds(final Date date, int amount) {
        return DateUtils.addSeconds(date, amount);
    }

    /**
     * 减一秒.
     *
     * @param date   the date
     * @param amount the amount
     * @return the date
     */
    public static Date subSeconds(final Date date, int amount) {
        return DateUtils.addSeconds(date, -amount);
    }

    //////////// 直接设置时间//////////////

    /**
     * 设置年份, 公元纪年.
     *
     * @param date   the date
     * @param amount the amount
     * @return the years
     */
    public static Date setYears(final Date date, int amount) {
        return DateUtils.setYears(date, amount);
    }

    /**
     * 设置月份, 0-11.
     *
     * @param date   the date
     * @param amount the amount
     * @return the months
     */
    public static Date setMonths(final Date date, int amount) {
        return DateUtils.setMonths(date, amount);
    }

    /**
     * 设置日期, 1-31.
     *
     * @param date   the date
     * @param amount the amount
     * @return the days
     */
    public static Date setDays(final Date date, int amount) {
        return DateUtils.setDays(date, amount);
    }

    /**
     * 设置小时, 0-23.
     *
     * @param date   the date
     * @param amount the amount
     * @return the hours
     */
    public static Date setHours(final Date date, int amount) {
        return DateUtils.setHours(date, amount);
    }

    /**
     * 设置分钟, 0-59.
     *
     * @param date   the date
     * @param amount the amount
     * @return the minutes
     */
    public static Date setMinutes(final Date date, int amount) {
        return DateUtils.setMinutes(date, amount);
    }

    /**
     * 设置秒, 0-59.
     *
     * @param date   the date
     * @param amount the amount
     * @return the seconds
     */
    public static Date setSeconds(final Date date, int amount) {
        return DateUtils.setSeconds(date, amount);
    }

    /**
     * 设置毫秒.
     *
     * @param date   the date
     * @param amount the amount
     * @return the milliseconds
     */
    public static Date setMilliseconds(final Date date, int amount) {
        return DateUtils.setMilliseconds(date, amount);
    }

    ///// 获取日期的位置//////

    /**
     * 获得日期是一周的第几天. 已改为中国习惯，1 是Monday，而不是Sundays.
     *
     * @param date the date
     * @return the day of week
     */
    public static int getDayOfWeek(final Date date) {
        int result = getWithMondayFirst(date, Calendar.DAY_OF_WEEK);
        return result == 1 ? 7 : result - 1;
    }

    /**
     * 获得日期是一年的第几天，返回值从1开始
     *
     * @param date the date
     * @return the day of year
     */
    public static int getDayOfYear(final Date date) {
        return get(date, Calendar.DAY_OF_YEAR);
    }

    /**
     * 获得日期是一月的第几周，返回值从1开始.
     * <p>
     * 开始的一周，只要有一天在那个月里都算. 已改为中国习惯，1 是Monday，而不是Sunday
     *
     * @param date the date
     * @return the week of month
     */
    public static int getWeekOfMonth(final Date date) {
        return getWithMondayFirst(date, Calendar.WEEK_OF_MONTH);
    }

    /**
     * 获得日期是一年的第几周，返回值从1开始.
     * <p>
     * 开始的一周，只要有一天在那一年里都算.已改为中国习惯，1 是Monday，而不是Sunday
     *
     * @param date the date
     * @return the week of year
     */
    public static int getWeekOfYear(final Date date) {
        return getWithMondayFirst(date, Calendar.WEEK_OF_YEAR);
    }

    private static int get(final Date date, int field) {
        Validate.notNull(date, "The date must not be null");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        return cal.get(field);
    }

    private static int getWithMondayFirst(final Date date, int field) {
        Validate.notNull(date, "The date must not be null");
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.setTime(date);
        return cal.get(field);
    }

    ///// 获得往前往后的日期//////

    /**
     * 2016-11-10 07:33:23, 则返回2016-1-1 00:00:00
     *
     * @param date the date
     * @return the date
     */
    public static Date beginOfYear(final Date date) {
        return DateUtils.truncate(date, Calendar.YEAR);
    }

    /**
     * 2016-11-10 07:33:23, 则返回2016-12-31 23:59:59.999
     *
     * @param date the date
     * @return the date
     */
    public static Date endOfYear(final Date date) {
        return new Date(nextYear(date).getTime() - 1);
    }

    /**
     * 2016-11-10 07:33:23, 则返回2017-1-1 00:00:00
     *
     * @param date the date
     * @return the date
     */
    public static Date nextYear(final Date date) {
        return DateUtils.ceiling(date, Calendar.YEAR);
    }

    /**
     * 2016-11-10 07:33:23, 则返回2016-11-1 00:00:00
     *
     * @param date the date
     * @return the date
     */
    public static Date beginOfMonth(final Date date) {
        return DateUtils.truncate(date, Calendar.MONTH);
    }

    /**
     * 2016-11-10 07:33:23, 则返回2016-11-30 23:59:59.999
     *
     * @param date the date
     * @return the date
     */
    public static Date endOfMonth(final Date date) {
        return new Date(nextMonth(date).getTime() - 1);
    }

    /**
     * 2016-11-10 07:33:23, 则返回2016-12-1 00:00:00
     *
     * @param date the date
     * @return the date
     */
    public static Date nextMonth(final Date date) {
        return DateUtils.ceiling(date, Calendar.MONTH);
    }

    /**
     * 2017-1-20 07:33:23, 则返回2017-1-16 00:00:00
     *
     * @param date the date
     * @return the date
     */
    public static Date beginOfWeek(final Date date) {
        return DateUtils.truncate(DateUtil.subDays(date, DateUtil.getDayOfWeek(date) - 1), Calendar.DATE);
    }

    /**
     * 2017-1-20 07:33:23, 则返回2017-1-22 23:59:59.999
     *
     * @param date the date
     * @return the date
     */
    public static Date endOfWeek(final Date date) {
        return new Date(nextWeek(date).getTime() - 1);
    }

    /**
     * 2017-1-23 07:33:23, 则返回2017-1-22 00:00:00
     *
     * @param date the date
     * @return the date
     */
    public static Date nextWeek(final Date date) {
        return DateUtils.truncate(DateUtil.addDays(date, 8 - DateUtil.getDayOfWeek(date)), Calendar.DATE);
    }

    /**
     * 2016-11-10 07:33:23, 则返回2016-11-10 00:00:00
     *
     * @param date the date
     * @return the date
     */
    public static Date beginOfDate(final Date date) {
        return DateUtils.truncate(date, Calendar.DATE);
    }

    /**
     * 2017-1-23 07:33:23, 则返回2017-1-23 23:59:59.999
     *
     * @param date the date
     * @return the date
     */
    public static Date endOfDate(final Date date) {
        return new Date(nextDate(date).getTime() - 1);
    }

    /**
     * 2016-11-10 07:33:23, 则返回2016-11-11 00:00:00
     *
     * @param date the date
     * @return the date
     */
    public static Date nextDate(final Date date) {
        return DateUtils.ceiling(date, Calendar.DATE);
    }

    /**
     * 2016-12-10 07:33:23, 则返回2016-12-10 07:00:00
     *
     * @param date the date
     * @return the date
     */
    public static Date beginOfHour(final Date date) {
        return DateUtils.truncate(date, Calendar.HOUR_OF_DAY);
    }

    /**
     * 2017-1-23 07:33:23, 则返回2017-1-23 07:59:59.999
     *
     * @param date the date
     * @return the date
     */
    public static Date endOfHour(final Date date) {
        return new Date(nextHour(date).getTime() - 1);
    }

    /**
     * 2016-12-10 07:33:23, 则返回2016-12-10 08:00:00
     *
     * @param date the date
     * @return the date
     */
    public static Date nextHour(final Date date) {
        return DateUtils.ceiling(date, Calendar.HOUR_OF_DAY);
    }

    /**
     * 2016-12-10 07:33:23, 则返回2016-12-10 07:33:00
     *
     * @param date the date
     * @return the date
     */
    public static Date beginOfMinute(final Date date) {
        return DateUtils.truncate(date, Calendar.MINUTE);
    }

    /**
     * 2017-1-23 07:33:23, 则返回2017-1-23 07:33:59.999
     *
     * @param date the date
     * @return the date
     */
    public static Date endOfMinute(final Date date) {
        return new Date(nextMinute(date).getTime() - 1);
    }

    /**
     * 2016-12-10 07:33:23, 则返回2016-12-10 07:34:00
     *
     * @param date the date
     * @return the date
     */
    public static Date nextMinute(final Date date) {
        return DateUtils.ceiling(date, Calendar.MINUTE);
    }

    ////// 闰年及每月天数///////

    /**
     * 是否闰年.
     *
     * @param date the date
     * @return the boolean
     */
    public static boolean isLeapYear(final Date date) {
        return isLeapYear(get(date, Calendar.YEAR));
    }

    /**
     * 是否闰年，copy from Jodd Core的TimeUtil
     * <p>
     * 参数是公元计数, 如2016
     *
     * @param y the y
     * @return the boolean
     */
    public static boolean isLeapYear(int y) {
        boolean result = false;

        if (((y % 4) == 0) && // must be divisible by 4...
                ((y < 1582) || // and either before reform year...
                        ((y % 100) != 0) || // or not a century...
                        ((y % 400) == 0))) { // or a multiple of 400...
            result = true; // for leap year.
        }
        return result;
    }

    /**
     * 获取某个月有多少天, 考虑闰年等因数, 移植Jodd Core的TimeUtil
     *
     * @param date the date
     * @return the month length
     */
    public static int getMonthLength(final Date date) {
        int year = get(date, Calendar.YEAR);
        int month = get(date, Calendar.MONTH);
        return getMonthLength(year, month);
    }

    /**
     * 获取某个月有多少天, 考虑闰年等因数, 移植Jodd Core的TimeUtil
     *
     * @param year  the year
     * @param month the month
     * @return the month length
     */
    public static int getMonthLength(int year, int month) {

        if ((month < 1) || (month > 12)) {
            throw new IllegalArgumentException("Invalid month: " + month);
        }
        if (month == 2) {
            return isLeapYear(year) ? 29 : 28;
        }

        return MONTH_LENGTH[month];
    }

    public static final String getBetween(String format, Date date) {
        String result;
        Instant nowInstant = Instant.now();
        Instant afterInstant = date.toInstant();

        long hoursDiff = Duration.between(nowInstant, afterInstant).toHours();
        long days = hoursDiff / 24;
        long hours = hoursDiff % 24;
        if (days > 0) {
            result =  format + days + DAY_NAME + hours + HOUR_NAME;
        } else {
            result =  format + hours + HOUR_NAME;
        }
        return result;
    }
}
