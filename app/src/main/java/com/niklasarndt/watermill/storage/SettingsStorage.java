package com.niklasarndt.watermill.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.niklasarndt.watermill.utils.NamingConstants;

public class SettingsStorage {

    private static final String SETTINGS_PREFIX = "settings_";
    private static final String DAILY_GOAL = SETTINGS_PREFIX + "daily_goal";

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(NamingConstants.SETTINGS_PREFS, Context.MODE_PRIVATE);
    }

    public static int getDailyGoal(Context context) {
        return getPrefs(context).getInt(DAILY_GOAL, 2000);
    }

    public static boolean setDailyGoal(Context context, int goal) {
        return getPrefs(context).edit().putInt(DAILY_GOAL, goal > 0 ? goal : 1).commit();
    }
}
