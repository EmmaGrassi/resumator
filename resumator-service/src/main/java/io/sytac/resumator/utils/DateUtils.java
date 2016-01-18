package io.sytac.resumator.utils;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by user on 15-Jan-16.
 */
public class DateUtils {

    private static final String ISO_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.sss'Z'";

    public static Long convert(String date) {
        DateTimeFormatter fmt = DateTimeFormat.forPattern(ISO_DATE_FORMAT);
        return Long.valueOf(fmt.parseDateTime(date).getMillis());
    }
}
