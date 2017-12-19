package com.example.bushu.studentkontakter;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;


public class Meldinger extends AppCompatActivity{
    DBHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DBHandler(this);

        setContentView(R.layout.meldinger);
        GetFromDbAndUpdateMeldingList();
        Toolbar toolbar = (Toolbar) findViewById(R.id.studlist_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton addMeldingbutton = (FloatingActionButton) findViewById(R.id.addmeldingbutn);
        addMeldingbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i  = new Intent(Meldinger.this, SendMelding.class);
                startActivityForResult(i, 10);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.stud_listmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.kontakter_ikon:
                finish();
                break;
            case R.id.settings_icon:
                finish();
                Intent is = new Intent(this,Preferanser.class);
                startActivity(is);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public void GetFromDbAndUpdateMeldingList(){
        List<Melding> meldinger = db.finnAlleMeldinger();
        String format = getString(R.string.datetime_format);
        final DateFormat simpleDateFormat = new SimpleDateFormat(format);

        ArrayAdapter<Melding> adapter = new MeldingAdapter(this,meldinger);
        ListView meldinglistview = (ListView)findViewById(R.id.meldinger_list);
        meldinglistview.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode== RESULT_OK) {
            GetFromDbAndUpdateMeldingList();
        }
    }

    public void visMelding(View v){
        Intent intent = new Intent(this, VisMelding.class);
        Object itemId = v.getTag();
        intent.putExtra("meldingId",itemId.toString());
        startActivityForResult(intent, 22);
    }
}
