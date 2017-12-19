package com.example.bushu.studentkontakter.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;



public class UkentligBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //Toast.makeText(context, "I UkentligBroadcastReceiver", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(context, SettPeriodiskService.class);
        context.startService(i);
    }
}