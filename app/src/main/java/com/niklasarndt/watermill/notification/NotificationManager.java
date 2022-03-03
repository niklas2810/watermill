package com.niklasarndt.watermill.notification;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.niklasarndt.watermill.MainActivity;
import com.niklasarndt.watermill.R;

import java.util.Calendar;

public class NotificationManager {

    private static final String DEFAULT_CHANNEL_ID = "default-watermill";

    private static void register(Context context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(DEFAULT_CHANNEL_ID, "Notifications", android.app.NotificationManager.IMPORTANCE_DEFAULT);

            android.app.NotificationManager notificationManager = context.getSystemService(android.app.NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public static void scheduleNotification(Context context, String content, long delayInMs) {
        scheduleNotification(context, build(context, content), delayInMs);
    }

    public static void scheduleNotification(Context context, Notification not, long delayInMs) {
        Intent notIntent = new Intent(context, NotificationPublisher.class);
        notIntent.putExtra(NotificationPublisher.NOTIFICATION_OBJ, not);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, notIntent, 0);

        AlarmManager alarmManager = context.getSystemService(AlarmManager.class);
        assert alarmManager != null;
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + delayInMs, pendingIntent);
    }

    public static Notification build(Context context, String content) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        return new NotificationCompat.Builder(context, DEFAULT_CHANNEL_ID)
                .setContentTitle("Watermill")
                .setContentText(content)
                .setSmallIcon(R.drawable.ic_notfication_icon)
                .setAutoCancel(true).setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .build();
    }

    public static void display(Context context, int id, Notification notification) {
        register(context);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.cancel(id);
        notificationManager.notify(id, notification);
    }

    public static void scheduleReminder(Context context) {
        Notification build = build(context, "Let's drink some water!");

        //Reminders at 8AM, 12AM, 3PM, 6PM, 9PM
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        if (hour < 8) {
            calendar.set(Calendar.HOUR_OF_DAY, 8);
        } else if (hour < 12) {
            calendar.set(Calendar.HOUR_OF_DAY, 12);
        } else if (hour < 15) {
            calendar.set(Calendar.HOUR_OF_DAY, 15);
        } else if (hour < 18) {
            calendar.set(Calendar.HOUR_OF_DAY, 18);
        } else if (hour < 21) {
            calendar.set(Calendar.HOUR_OF_DAY, 21);
        } else {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            calendar.set(Calendar.HOUR_OF_DAY, 8);
        }

        long delay = calendar.getTimeInMillis() - System.currentTimeMillis();
        Log.i("watermill-reminder", String.format("Reminder in %d minutes", delay / 1000 / 60));

        if (delay > 0)
            scheduleNotification(context, build, delay);
    }
}
