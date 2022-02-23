package com.niklasarndt.watermill.notification;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.niklasarndt.watermill.storage.MetadataStorage;
import com.niklasarndt.watermill.storage.SettingsStorage;

public class NotificationPublisher extends BroadcastReceiver {

    public static final String NOTIFICATION_ID = "wat-not-id";
    public static final String NOTIFICATION_OBJ = "wat-not-obj";


    @Override
    public void onReceive(Context context, Intent intent) {
        long last = MetadataStorage.getLastNotification(context);

        if (!SettingsStorage.hasRemindersEnabled(context) || System.currentTimeMillis() - last < 60_000) {
            Log.i("watermill-reminder", "Not because disabled or too recent");
            return;
        }

        MetadataStorage.updateLastNotification(context);
        Notification notification = intent.getParcelableExtra(NOTIFICATION_OBJ);
        int id = intent.getIntExtra(NOTIFICATION_ID, 0);
        NotificationManager.display(context, id, notification);
    }
}
