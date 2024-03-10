package com.teachmeskills.finalAssignment.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static String getCurrentDate() {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
        return dateFormat.format(currentDate);
    }
}
