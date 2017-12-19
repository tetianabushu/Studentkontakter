package com.example.bushu.studentkontakter;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bushu.studentkontakter.service.SettPeriodiskService;
import com.example.bushu.studentkontakter.service.SmsService;

import java.util.Calendar;
import java.util.List;

public class Studenter extends AppCompatActivity {

    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DBHandler(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.studenter);


        GetFromDbAndUpdateStudentList();

        Toolbar toolbar = (Toolbar) findViewById(R.id.studlist_toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton addstudentbutn = (FloatingActionButton) findViewById(R.id.addstudentbutn);
        addstudentbutn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i  = new Intent(Studenter.this, LeggTilStudent.class);
                startActivityForResult(i, 10);
            }
        });

        int perm = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        if (perm != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 0);
            }
        }

     }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.stud_listmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.melding_ikon:
                /*Toast.makeText(this, "Hei!", Toast.LENGTH_SHORT).show();*/
                Intent im = new Intent(this,Meldinger.class);
                startActivity(im);
                break;
            case R.id.settings_icon:
                /*Toast.makeText(this, "Hopp!", Toast.LENGTH_SHORT).show();*/
                Intent is = new Intent(this,Preferanser.class);
                startActivity(is);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }


    private void GetFromDbAndUpdateStudentList() {
        List<Kontakt> kontakter = db.finnAlleKontakter();
        ArrayAdapter<Kontakt> adapter = new KontaktAdapter(this, kontakter);
        ListView studentListView = (ListView) findViewById(R.id.student_list);
        studentListView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode== RESULT_OK) {
            GetFromDbAndUpdateStudentList();
        }
    }

    public void viseDetaljer(View v){
        Intent intent = new Intent(this, KontaktDetaljer.class);// New activity
        Object itemId = v.getTag();
        intent.putExtra("selectedId",itemId.toString());
        startActivityForResult(intent, 20);
    }
}
