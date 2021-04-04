package com.olist.plp.application.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static final String DEFAULT_DATE_FORMAT = "dd-MM-yyyy";
    public static final String DEFAULT_DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm:ss";

    private DateUtil() {
        // only static methods
    }
    
    public static String format(Date date) {
        var simpleDateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        return simpleDateFormat.format(date);
    }

    public static String format(Date date, String format) {
        var simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }
}
