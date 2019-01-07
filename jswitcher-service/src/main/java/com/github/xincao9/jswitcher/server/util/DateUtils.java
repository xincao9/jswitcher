package com.github.xincao9.jswitcher.server.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author xin.cao@asiainnovations.com
 */
public class DateUtils {
    
    public static final String DATETIME_YYYY_MM_DD_HH_MM_SS_FORMAT = "yyyy-MM-dd hh:mm:ss";
    public static final String DATETIME_YYYYMMDDHHMMSS_FORMAT = "yyyyMMddhhmmss";
    public static final String DATETIME_YYYYMMDDHHMMSSS_FORMAT = "yyyyMMddhhmmssS";
    public static final String DATETIME_YYYY_MM_DD_FORMAT = "yyyy-MM-dd";
    public static final String DATETIME_YYYYMMDD_FORMAT = "yyyyMMdd";

    public static String format (Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public static Date parser (String date, String pattern) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.parse(date);
    }
}
