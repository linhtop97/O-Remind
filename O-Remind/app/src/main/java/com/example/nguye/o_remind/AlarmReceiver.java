package com.example.nguye.o_remind;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by nguye on 14-Aug-17.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String chuoi_String = intent.getExtras().getString("extra");
        if(chuoi_String.equals("on")){
            Intent intentService = new Intent(context,NotificationService.class);
         //   intentService.putS("Context",context);
        }
    }

}
