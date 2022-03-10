package com.myplanner.myplanner.adapter;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.myplanner.myplanner.R;
import com.myplanner.myplanner.controllers.AlarmActivity;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String titre = intent.getStringExtra("titre");
        String description = intent.getStringExtra("description");
        Intent in = new Intent(context, AlarmActivity.class);
        in.putExtra("titre", titre);
        in.putExtra("description", description);
        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, in, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "myplanner")
                .setSmallIcon(R.drawable.ic_baseline_notification_important_24)
                .setContentTitle(context.getString(R.string.nom_application))
                .setContentText(titre)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(123, builder.build());
    }
}
