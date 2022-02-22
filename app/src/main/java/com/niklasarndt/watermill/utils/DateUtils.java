package com.niklasarndt.watermill.utils;

import java.time.LocalDate;
import java.util.Locale;

public class DateUtils {

    public static String getIdForDate(LocalDate date) {
        return String.format(Locale.ENGLISH, "%04d_%02d_%02d", date.getYear(), date.getMonthValue(), date.getDayOfMonth());
    }
}
