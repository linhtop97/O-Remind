package com.example.nguye.o_remind;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by nguye on 15-Aug-17.
 */

public class ReceiverStop extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            int x = intent.getExtras().getInt("abc");
            if(x==1){
                Intent it = new Intent(context,MainActivity.class);
                it.putExtra("ccc",1);
                Toast.makeText(context, "gg", Toast.LENGTH_SHORT).show();
            }

        }catch (Exception e){
            Toast.makeText(context, "đéo vào", Toast.LENGTH_SHORT).show();
        }

    }
}

