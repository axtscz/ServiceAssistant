package com.collusion.serviceassistant;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class onAlarmReceive extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("ALARM", "BroadcastReceiver, in onReceive:");
        Toast t = new Toast(context);


    }
}