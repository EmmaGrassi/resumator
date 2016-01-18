package io.sytac.resumator.utils;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * Created by user on 15-Jan-16.
 */
public class DateUtils {

    private static final String ISO_DATE_FORMAT = "yyyy-MM-dd";

    public static Date convert(String date) {
        return DateTimeFormat.forPattern(ISO_DATE_FORMAT).parseDateTime(date).toDate();
    }
}
