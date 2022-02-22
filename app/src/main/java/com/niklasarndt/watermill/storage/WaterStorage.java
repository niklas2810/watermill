package com.niklasarndt.watermill.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.niklasarndt.watermill.utils.DateUtils;
import com.niklasarndt.watermill.utils.NamingConstants;

import java.time.LocalDate;

public class WaterStorage {

    public static int getWaterForDay(Context context, LocalDate date) {
        return getWaterForDay(context, DateUtils.getIdForDate(date));
    }

    public static int getWaterForDay(Context context, String dateId) {
        SharedPreferences prefs = getPrefs(context);
        return prefs.getInt(dateId, -1);
    }

    public static boolean setWaterForDay(Context context, LocalDate date, int milliliter) {
        return setWaterForDay(context, DateUtils.getIdForDate(date), milliliter);
    }

    /**
     * @param context    The current application context. Used for accessing shared preferences.
     * @param dateId     The id of the specified date.
     * @param milliliter The amount in milliliters.
     * @return Returns true if the new values were successfully written to persistent storage.
     * @see DateUtils#getIdForDate(LocalDate)
     */
    public static boolean setWaterForDay(Context context, String dateId, int milliliter) {
        SharedPreferences.Editor edit = getPrefs(context).edit();
        if (milliliter > 0)
            edit.putInt(dateId, milliliter);
        else
            edit.remove(dateId);
        return edit.commit();
    }

    public static boolean addWaterForDay(Context context, LocalDate date, int milliliter) {
        return addWaterForDay(context, DateUtils.getIdForDate(date), milliliter);
    }

    public static boolean addWaterForDay(Context context, String dateId, int milliliter) {
        if (milliliter == 0)
            return false;
        SharedPreferences prefs = getPrefs(context);
        int current = prefs.getInt(dateId, 0);

        SharedPreferences.Editor editor = prefs.edit();

        if (current + milliliter > 0)
            editor.putInt(dateId, current + milliliter);
        else
            editor.remove(dateId);

        return editor.commit();
    }

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(NamingConstants.WATER_PREFS, Context.MODE_PRIVATE);
    }
}
