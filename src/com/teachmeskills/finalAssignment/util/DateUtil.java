package com.teachmeskills.finalAssignment.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The DateUtil class provides utility methods for working with dates.
 */
public class DateUtil {

    /**
     * Provides the current date and time in the specified format.
     *
     * @return A string representing the current date and time.
     */
    public static String getCurrentDate() {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
        return dateFormat.format(currentDate);
    }
}
