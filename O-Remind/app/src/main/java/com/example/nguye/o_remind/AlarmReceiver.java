package com.example.nguye.o_remind;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by nguye on 14-Aug-17.
 */

public class AlarmReceiver extends BroadcastReceiver {
    int notificationid;
    @Override
    public void onReceive(Context context, Intent intent) {

            CreateNotification(context);
            Intent intentService = new Intent(context,NotificationService.class);
            intentService.putExtra("data","hihi");
        context.startService(intentService);
    }
    private void CreateNotification(Context context) {
        Intent reintent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, reintent, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri uri = Uri.parse("android.resource://" + context.getPackageName() + "/" +R.raw.rington);
        NotificationCompat.Builder mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.iconapp)
                .setContentTitle(context.getResources().getString(R.string.app_name))
                .setContentText(context.getResources().getString(R.string.contentNotifi))
                .addAction(R.drawable.ic_pause_black_24dp,context.getResources().getString(R.string.Pause),pendingIntent)
                .addAction(R.drawable.ic_skip_next_black_24dp,context.getResources().getString(R.string.Skip),pendingIntent)
                .addAction(R.drawable.ic_stop_black_24dp,context.getResources().getString(R.string.Stop),pendingIntent)
                .setSound(uri)
                .setDeleteIntent(pendingIntent)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);
        notificationid = 113;
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        mNotificationManager.notify(notificationid,mBuilder.build());
    }

}
