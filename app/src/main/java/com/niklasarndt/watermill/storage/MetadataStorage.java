package com.niklasarndt.watermill.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.niklasarndt.watermill.utils.NamingConstants;

public class MetadataStorage {

    private static final String META_PREFIX = "metadata";
    private static final String ADD_SCROLL_INDEX = META_PREFIX + "add_scroll_index";
    private static final String LAST_NOTICATION = META_PREFIX + "last_notification";

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(NamingConstants.META_PREFS, Context.MODE_PRIVATE);
    }

    public static int getAddScrollIndex(Context context) {
        return getPrefs(context).getInt(ADD_SCROLL_INDEX, 0);
    }

    public static boolean setAddScrollIndex(Context context, int index) {
        SharedPreferences.Editor edit = getPrefs(context).edit();

        if (index < 0)
            edit.remove(ADD_SCROLL_INDEX);
        else
            edit.putInt(ADD_SCROLL_INDEX, index);

        return edit.commit();
    }

    public static long getLastNotification(Context context) {
        return getPrefs(context).getLong(LAST_NOTICATION, 0);
    }

    public static boolean updateLastNotification(Context context) {
        return getPrefs(context).edit().putLong(LAST_NOTICATION, System.currentTimeMillis()).commit();
    }
}
