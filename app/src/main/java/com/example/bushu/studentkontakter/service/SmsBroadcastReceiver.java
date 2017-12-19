package com.example.bushu.studentkontakter.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.Calendar;


public class SmsBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //Toast.makeText(context, "I SmsBroadcastReceiver", Toast.LENGTH_SHORT).show();
        boolean meldingerEnabled = context.getSharedPreferences("STUDENTKONTAKTERPREF", context.MODE_PRIVATE).getBoolean("SmsMeldinger",false);

        if(meldingerEnabled){
            Calendar calendar = Calendar.getInstance();
            Intent i = new Intent(context, SmsService.class);
            PendingIntent pintent = PendingIntent.getService(context, 999, i, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), (15*60*1000), pintent);
        }
    }
}
