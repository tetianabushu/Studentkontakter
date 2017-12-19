package com.example.bushu.studentkontakter.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.bushu.studentkontakter.R;

import java.util.Arrays;
import java.util.Calendar;



public class SettPeriodiskService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Intent i = new Intent(this, UkentligService.class);
        PendingIntent pintent = PendingIntent.getService(this, 0, i, 0);

        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Calendar sendingCal = beregnAutoSendingTidspunkt();

        long oneWeekIntervalMilliseconds = 7 * 24 * 60 * 60 * 1000;

        //Toast.makeText(getBaseContext(), "Periodisk Service kjører", Toast.LENGTH_SHORT).show();
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, sendingCal.getTimeInMillis(), oneWeekIntervalMilliseconds, pintent);
        return super.onStartCommand(intent, flags, startId);
    }

    private Calendar beregnAutoSendingTidspunkt() {
        String dag = getSharedPreferences("STUDENTKONTAKTERPREF",MODE_PRIVATE).getString("Dag", null);
        int indexOfDag = Arrays.asList(getResources().getStringArray(R.array.dager)).indexOf(dag);
        int sendingDag = getResources().getIntArray(R.array.dager_values)[indexOfDag];

        String time = getSharedPreferences("STUDENTKONTAKTERPREF",MODE_PRIVATE).getString("Time",null);
        int sendingHour = Integer.parseInt(time);

        String min = getSharedPreferences("STUDENTKONTAKTERPREF",MODE_PRIVATE).getString("Minutt",null);
        int sendingMin = Integer.parseInt(min);

        Calendar timeOff = Calendar.getInstance();

        int days = sendingDag + (7 - timeOff.get(Calendar.DAY_OF_WEEK)); // Hvor mange dager til valgt dag, fra preferanser

        int currentHour = timeOff.get(Calendar.HOUR_OF_DAY);
        int currentMinute = timeOff.get(Calendar.MINUTE);

        if((days == 7 && sendingHour > currentHour) || days == 7 && sendingHour == currentHour && sendingMin > currentMinute ) {
            days = 0;
        }

        timeOff.add(Calendar.DATE, days);
        timeOff.set(Calendar.HOUR_OF_DAY, sendingHour);
        timeOff.set(Calendar.MINUTE, sendingMin);
        timeOff.set(Calendar.SECOND, 0);

        //Toast.makeText(getBaseContext(), days +" "+ sendingHour +":"+ sendingMin, Toast.LENGTH_SHORT).show();

        return timeOff;
    }

}
