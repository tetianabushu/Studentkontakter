package com.example.bushu.studentkontakter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bushu.studentkontakter.service.SmsService;

import java.util.Calendar;
import java.util.Date;


public class SendMelding extends AppCompatActivity {
    Melding melding;
    DBHandler db;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_ny_melding);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.studlist_toolbar);
        setSupportActionBar(myToolbar);

        db = new DBHandler(this);

    }

    public void avbrytSendeMelding(View v){
        Intent intent = new Intent(this, Meldinger.class);// New activity
        setResult(RESULT_CANCELED, intent);
        finish();

    }
    public void sendMelding(View v) {
        try {

            EditText meldingSende = (EditText) findViewById(R.id.melding);
            if(meldingSende.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), R.string.tommelding_validation, Toast.LENGTH_SHORT).show();
                return;
            }

            melding = new Melding();
            melding.setMelding(meldingSende.getText().toString());
            Date timeSaved = Calendar.getInstance().getTime();
            melding.setErSendt(0);
            db.leggTilMelding(melding);
    }
        catch (Exception ex){
            Log.e("Send melding Error", ex.getMessage());
        }
/*
        Intent intent = new Intent(this, SmsService.class);
        boolean alarmUp = (PendingIntent.getService(this, 999, intent, PendingIntent.FLAG_NO_CREATE) != null);

        if (alarmUp)
        {
            Toast.makeText(getApplicationContext(), "Sms Service is already Running", Toast.LENGTH_SHORT).show();
            Log.d("myTag", "Alarm is already active");
        }
        else {
            Calendar calendar = Calendar.getInstance();
            PendingIntent pintent = PendingIntent.getService(this, 999, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), (5*60*1000), pintent);
            Toast.makeText(getApplicationContext(), "Starting Sms Service", Toast.LENGTH_SHORT).show();
        }
*/
        Intent mintent = new Intent(this, Meldinger.class);
        setResult(RESULT_OK, mintent);
        finish();
    }
}
