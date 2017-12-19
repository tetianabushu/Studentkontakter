package com.example.bushu.studentkontakter.service;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.NotificationCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.example.bushu.studentkontakter.DBHandler;
import com.example.bushu.studentkontakter.Kontakt;
import com.example.bushu.studentkontakter.Melding;
import com.example.bushu.studentkontakter.Meldinger;
import com.example.bushu.studentkontakter.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class SmsService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {

            int perm = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
            if (perm != PackageManager.PERMISSION_GRANTED){
                return super.onStartCommand(intent, flags, startId);
            }
            // Sende SMS fra database
            DBHandler db = new DBHandler(this);
            List<Melding> meldinger = db.finnAlleIkkeSendtMeldinger();

            Calendar calendar = Calendar.getInstance();
            DateFormat df = new SimpleDateFormat(getString(R.string.datetime_format));
            Date date_time = calendar.getTime();
            String stringDate = df.format(date_time);

            List<Kontakt> kontakter = db.finnAlleKontakter();
            SmsManager sms = SmsManager.getDefault();
            for (Melding m: meldinger){
                for (Kontakt k : kontakter) {
                    sms.sendTextMessage(k.getTelefon(), null, m.getMelding(), null, null);
                }
                m.setErSendt(1);
                m.setDatoSendt(stringDate);
                db.oppdaterMelding(m);
            }

            if(meldinger.size() > 0)
                SendNotification(meldinger);

            /*Calendar calendar = Calendar.getInstance();
            PendingIntent pintent = PendingIntent.getService(this, 999, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarm.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + (5*60*1000), pintent);*/

            //Toast.makeText(getApplicationContext(), "Sms Meldinger Sendt", Toast.LENGTH_SHORT).show();

        }
        catch (Exception ex){
            Log.e("SmsService Error", ex.getMessage());
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void SendNotification(List<Melding> meldinger) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent i = new Intent(this, Meldinger.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 10, i, 0);
        Notification notifikasjon = new NotificationCompat.Builder(this)
                .setContentTitle("SmsService Notification")
                .setContentText(meldinger.size() + " meldinger er sendt")
                .setSmallIcon(R.mipmap.kontakter_icon)
                .setContentIntent(pIntent).build();
        notifikasjon.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(0, notifikasjon);
    }
}
