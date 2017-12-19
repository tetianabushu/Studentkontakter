package com.example.bushu.studentkontakter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class VisMelding extends AppCompatActivity {
    DBHandler db;
    long meldingId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vis_melding);

        Toolbar toolbar = (Toolbar) findViewById(R.id.studlist_toolbar);
        setSupportActionBar(toolbar);
        Intent i = getIntent();
        String resultat = i.getExtras().getString("meldingId");
        meldingId = Integer.parseInt(resultat);
        db = new DBHandler(this);
        hentFraDbOgVisMelding(meldingId);

    }

    public void hentFraDbOgVisMelding(long id){
        Melding melding = db.finnMelding(id);
        TextView meldingTekst = (TextView) findViewById(R.id.vis_hele_melding);
        meldingTekst.setText(melding.getMelding());
        TextView tiddatosendt = (TextView) findViewById(R.id.tid_datosendt);
        if(melding.getErSendt() == 1){
            tiddatosendt.setText(melding.getDatoSendt());
        }else{
            tiddatosendt.setText("Melding er ikke sendt enda.");
        }

    }

    public void slettMelding(View v) {
        Intent intent = new Intent(this, Meldinger.class);
        db.slettMelding(meldingId);
        setResult(RESULT_OK, intent);
        Toast.makeText(getApplicationContext(), R.string.melding_slettet, Toast.LENGTH_SHORT).show();
        finish();
    }

    public void avbrytVisMelding(View v) {
        Intent intent = new Intent(this, Meldinger.class);
        setResult(RESULT_CANCELED, intent);
        finish();
    }
}
