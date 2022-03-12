package com.myplanner.myplanner.helper;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.myplanner.myplanner.adapter.AlarmReceiver;
import com.myplanner.myplanner.model.Tache;

public class Alarm {
    AlarmManager alarmManager;
    Context context;
    Intent intent;
    PendingIntent pendingIntent;
    Tache tache;

    public Alarm(Context context, Tache tache) {
        this.context = context;
        this.tache = tache;
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("titre", tache.getTitreTache());
        intent.putExtra("description", tache.getDescriptionTache());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            pendingIntent = PendingIntent.getBroadcast(context, tache.getId(), intent, PendingIntent.FLAG_IMMUTABLE);
        }
    }

    public void setAlarm(long time) {
        Log.d("alarm:titre", tache.getTitreTache());
        Log.d("alarm:desc", tache.getDescriptionTache());

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, pendingIntent);
    }

    public void supprimerAlarm() {
        alarmManager.cancel(pendingIntent);
    }


    public static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "myplannerAlarmChannel";
            String description = "Channel des alarmes de MyPlanner";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("myplanner", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}