package com.example.nguye.o_remind;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;

/**
 * Created by nguye on 15-Aug-17.
 */

public class NotificationService extends Service {
    int notificationid;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
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
