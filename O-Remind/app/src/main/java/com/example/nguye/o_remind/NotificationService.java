package com.example.nguye.o_remind;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
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
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String nhankey = intent.getExtras().getString("data");
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        if (nhankey.equals("hihi")) {
            //CreateNotification();

        }
        return START_NOT_STICKY;
    }

}
