package io.sytac.resumator.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.Date;

/**
 * Created by user on 15-Jan-16.
 */
public class DateUtils {

    public static final String ISO_DATE_FORMAT = "yyyy-MM-dd";
    
    public static final String ISO_WRONG_DATE_FORMAT = "ddMMYYY";

    /**
     * Utility class, not to be instantiated.
     */
    private DateUtils() {
    }

    public static Date convert(String date) {
        return DateTimeFormat.forPattern(ISO_DATE_FORMAT).parseDateTime(date).toDate();
    }
    
    public static Date convertToWrongFormat(String date) {
        return DateTimeFormat.forPattern(ISO_WRONG_DATE_FORMAT).parseDateTime(date).toDate();
    }

    public static String convert(Date date) {
        return new DateTime(date).toString(ISO_DATE_FORMAT);
    }
}
