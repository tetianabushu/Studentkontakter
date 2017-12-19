package com.example.bushu.studentkontakter.service;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.NotificationCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.example.bushu.studentkontakter.DBHandler;
import com.example.bushu.studentkontakter.Kontakt;
import com.example.bushu.studentkontakter.Meldinger;
import com.example.bushu.studentkontakter.Preferanser;
import com.example.bushu.studentkontakter.R;

import java.util.List;


public class UkentligService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            //Toast.makeText(getBaseContext(), "Kjører Ukentlig Service", Toast.LENGTH_SHORT).show();

            // sjekker sms permissions for App
            int perm = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
            if (perm != PackageManager.PERMISSION_GRANTED){
                return super.onStartCommand(intent, flags, startId);
            }

            // henter fra Shared Preferences standard ukentlig Sms
            String textMessage = getSharedPreferences("STUDENTKONTAKTERPREF",MODE_PRIVATE).getString("Melding","");

            if(textMessage != "") {
                DBHandler db = new DBHandler(this);
                List<Kontakt> kontakter = db.finnAlleKontakter();
                SmsManager sms = SmsManager.getDefault();
                for (Kontakt k : kontakter) {
                    sms.sendTextMessage(k.getTelefon(), null, textMessage, null, null);
                }

                SendNotification();
            }
            else {
                Toast.makeText(getApplicationContext(), "Ukentlig melding i preferences er tom!", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception ex){
            Log.e("SmsService Error", ex.getMessage());
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void SendNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent i = new Intent(this, Preferanser.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 20, i, 0);
        Notification notifikasjon = new NotificationCompat.Builder(this)
                .setContentTitle("UkentligService Notification")
                .setContentText("Ukentlig melding er sendt.")
                .setSmallIcon(R.mipmap.kontakter_icon)
                .setContentIntent(pIntent).build();
        notifikasjon.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(1, notifikasjon);
    }
}
