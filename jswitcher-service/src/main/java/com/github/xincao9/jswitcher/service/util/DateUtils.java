/*
 * Copyright 2019 xingyunzhi.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.xincao9.jswitcher.service.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author xincao9@gmail.com
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
