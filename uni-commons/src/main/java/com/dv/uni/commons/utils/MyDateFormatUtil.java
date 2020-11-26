package com.dv.uni.commons.utils;

import lombok.Getter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/11/9 0009
 */
public final class MyDateFormatUtil {

    /**
     * 缓存SimpleDateFormat
     */
    private static Map<String, ThreadLocal<SimpleDateFormat>> cachedMap = new HashMap<String, ThreadLocal<SimpleDateFormat>>() {{
        this.put(Format.YYYY_MM_DD.getFormat(), new ThreadLocal<SimpleDateFormat>() {
            @Override
            protected SimpleDateFormat initialValue() {
                return new SimpleDateFormat(Format.YYYY_MM_DD.getFormat());
            }
        });
        this.put(Format.YYYY_MM_DD_HH_MM_SS.getFormat(), new ThreadLocal<SimpleDateFormat>() {
            @Override
            protected SimpleDateFormat initialValue() {
                return new SimpleDateFormat(Format.YYYY_MM_DD_HH_MM_SS.getFormat());
            }
        });
        this.put(Format.CRON.getFormat(), new ThreadLocal<SimpleDateFormat>() {
            @Override
            protected SimpleDateFormat initialValue() {
                return new SimpleDateFormat(Format.CRON.getFormat());
            }
        });
    }};

    /**
     * 获取SimpleDateFormat
     *
     * @param format
     * @return
     */
    public static SimpleDateFormat getDateFormat(String format) {
        ThreadLocal<SimpleDateFormat> simpleDateFormatThreadLocal = cachedMap.get(format);
        if (simpleDateFormatThreadLocal == null) {
            synchronized (format) {
                if (simpleDateFormatThreadLocal == null) {
                    simpleDateFormatThreadLocal = new ThreadLocal<SimpleDateFormat>() {
                        @Override
                        protected SimpleDateFormat initialValue() {
                            return new SimpleDateFormat(format);
                        }
                    };
                    cachedMap.put(format, simpleDateFormatThreadLocal);
                }
            }
        }
        return simpleDateFormatThreadLocal.get();
    }

    /**
     * 获取SimpleDateFormat
     *
     * @param format
     * @return
     */
    public static SimpleDateFormat getDateFormat(Format format) {
        return getDateFormat(format.getFormat());
    }

    /**
     * 获取SimpleDateFormat
     *
     * @return
     */
    public static SimpleDateFormat getDateFormat() {
        return getDateFormat(Format.YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 格式化日期
     *
     * @param format
     * @param target
     * @return
     */
    public static String format(String format, Date target) {
        return getDateFormat(format).format(target);
    }

    /**
     * 格式化日期
     *
     * @param format
     * @param target
     * @return
     */
    public static String format(Format format, Date target) {
        return format(format.getFormat(), target);
    }

    /**
     * 格式化日期
     *
     * @param target
     * @return
     */
    public static String format(Date target) {
        return format(Format.YYYY_MM_DD_HH_MM_SS, target);
    }

    /**
     * 解析日期
     *
     * @param format
     * @param target
     * @return
     */
    public static Date parse(String format, String target) {
        try {
            return getDateFormat(format).parse(target);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 解析日期
     *
     * @param format
     * @param target
     * @return
     */
    public static Date parse(Format format, String target) {
        return parse(format.getFormat(), target);
    }

    /**
     * 解析日期
     *
     * @param target
     * @return
     */
    public static Date parse(String target) {
        return parse(Format.YYYY_MM_DD_HH_MM_SS, target);
    }

    public enum Format {
        YYYY_MM_DD("yyyy-MM-dd"),
        YYYY_MM_DD_HH_MM_SS("yyyy-MM-dd HH:mm:ss"),
        CRON("ss mm HH dd MM ? yyyy");
        @Getter
        private String format;

        Format(String format) {
            this.format = format;
        }
    }
}
