package com.example.bushu.studentkontakter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.bushu.studentkontakter.service.SmsService;
import com.example.bushu.studentkontakter.service.UkentligService;

import java.util.Arrays;


public class Preferanser extends AppCompatActivity {
    DBHandler db;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preferanser);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.studlist_toolbar);
        setSupportActionBar(myToolbar);

        db = new DBHandler(this);
/*      Switch simpleSwitch = (Switch) findViewById(R.id.sending_std_mel_on_off);
        Boolean switchState = simpleSwitch.isChecked();
        */

        datoTidSetup();

        EditText melding = (EditText) findViewById(R.id.standardmelding);
        melding.setText(getSharedPreferences("STUDENTKONTAKTERPREF",MODE_PRIVATE).getString("Melding",""));

        Boolean ukentlig = getSharedPreferences("STUDENTKONTAKTERPREF",MODE_PRIVATE).getBoolean("UkentligMelding",false);
        Switch ukentligSwitch = (Switch) findViewById(R.id.ukentligsending_on_off);
        ukentligSwitch.setChecked(ukentlig);

        Boolean sms_meldinger = getSharedPreferences("STUDENTKONTAKTERPREF",MODE_PRIVATE).getBoolean("SmsMeldinger",false);
        Switch smsMeldingSwitch = (Switch) findViewById(R.id.sending_std_mel_on_off);
        smsMeldingSwitch.setChecked(sms_meldinger);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.stud_listmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        lagreISharedPreferences();
        switch (item.getItemId()) {
            case R.id.melding_ikon:
                finish();
                Intent im = new Intent(this,Meldinger.class);
                startActivity(im);
                break;
            case R.id.kontakter_ikon:
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    public void ukentligMeldingToggle(View v){
        Switch simpleSwitch = (Switch)  v;
        Boolean switchState = simpleSwitch.isChecked();
        if(switchState == true){

            getSharedPreferences("STUDENTKONTAKTERPREF",MODE_PRIVATE).edit().putBoolean("UkentligMelding", true).apply();
            lagreISharedPreferences();

            Intent intent = new Intent();
            intent.setAction("com.example.ukentligbroadcast");
            sendBroadcast(intent);
        }
        else{
            getSharedPreferences("STUDENTKONTAKTERPREF",MODE_PRIVATE).edit().putBoolean("UkentligMelding", false).apply();
            Intent i = new Intent(this, UkentligService.class);
            PendingIntent pintent = PendingIntent.getService(this, 0, i, 0);
            AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            if (alarm!= null) {
                alarm.cancel(pintent);
            }
        }
    }

    public void enkeltMeldingToggle(View v){
        Switch simpleSwitch = (Switch)  v;
        Boolean switchState = simpleSwitch.isChecked();
        if(switchState == true){
            getSharedPreferences("STUDENTKONTAKTERPREF",MODE_PRIVATE).edit().putBoolean("SmsMeldinger", true).apply();
            Intent intent = new Intent();
            intent.setAction("com.example.SmsBroadcast");
            sendBroadcast(intent);
        }
        else{
            getSharedPreferences("STUDENTKONTAKTERPREF",MODE_PRIVATE).edit().putBoolean("SmsMeldinger", false).apply();
            Intent i = new Intent(this, SmsService.class);
            PendingIntent pintent = PendingIntent.getService(this, 999, i, 0);
            AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            if (alarm!= null) {
                alarm.cancel(pintent);
            }
        }
    }

    private void lagreISharedPreferences() {
        EditText ukentligMelding = (EditText) findViewById(R.id.standardmelding);
        String melding =  ukentligMelding.getText().toString();

        Spinner spinnerDager = (Spinner) findViewById(R.id.spinnerdager);
        String dag =  spinnerDager.getSelectedItem().toString();

        Spinner spinnerTimer = (Spinner) findViewById(R.id.spinnertimer);
        String timeString = spinnerTimer.getSelectedItem().toString();

        Spinner spinnerMin = (Spinner) findViewById(R.id.spinnerminutter);
        String minutt =  spinnerMin.getSelectedItem().toString();

        getSharedPreferences("STUDENTKONTAKTERPREF",MODE_PRIVATE)
                .edit()
                .putString("Melding",melding)
                .putString("Dag", dag)
                .putString("Time", timeString)
                .putString("Minutt", minutt)
                .apply();
    }

    private void datoTidSetup() {
        Spinner spinner = (Spinner) findViewById(R.id.spinnerdager);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.dager, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        String dag = getSharedPreferences("STUDENTKONTAKTERPREF",MODE_PRIVATE).getString("Dag", null);
        spinner.setSelection(adapter.getPosition(dag));

        Spinner spinnerTime = (Spinner) findViewById(R.id.spinnertimer);
        ArrayAdapter<CharSequence> adapterTimr = ArrayAdapter.createFromResource(this, R.array.timer, android.R.layout.simple_spinner_item);
        adapterTimr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTime.setAdapter(adapterTimr);
        String time = getSharedPreferences("STUDENTKONTAKTERPREF",MODE_PRIVATE).getString("Time", null);
        spinnerTime.setSelection(adapterTimr.getPosition(time));

        Spinner spinnerMin = (Spinner) findViewById(R.id.spinnerminutter);
        ArrayAdapter<CharSequence> adapterMin = ArrayAdapter.createFromResource(this, R.array.minutter, android.R.layout.simple_spinner_item);
        adapterMin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMin.setAdapter(adapterMin);
        String min = getSharedPreferences("STUDENTKONTAKTERPREF",MODE_PRIVATE).getString("Minutt", null);
        spinnerMin.setSelection(adapterMin.getPosition(min));
    }
}
