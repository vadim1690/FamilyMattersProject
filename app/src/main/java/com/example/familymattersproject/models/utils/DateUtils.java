package com.example.familymattersproject.models.utils;



import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Locale;

public class DateUtils {
    private static final String DAY_MONTH_PATTERN = "dd/MM";
    private static final String DAY_MONTH_YEAR_PATTERN = "dd/MM/yyyy";

    public static String formatDateToDayAndMonth(Date date){
        return new SimpleDateFormat(DAY_MONTH_PATTERN, Locale.US).format(date);
    }
    public static String formatDateToDayMonthYear(Date date){
        return new SimpleDateFormat(DAY_MONTH_YEAR_PATTERN, Locale.US).format(date);
    }


    public static Date getDateFromLong(long date) {
        return new Date(date);
    }
}
