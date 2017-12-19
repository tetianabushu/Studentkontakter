package com.example.bushu.studentkontakter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LeggTilStudent extends AppCompatActivity {
    Kontakt kontakt;

    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.legg_til_student);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.studlist_toolbar);
        setSupportActionBar(myToolbar);

        EditText fornavnInn = (EditText) findViewById(R.id.fornavn);
        EditText etternavnInn = (EditText) findViewById(R.id.etternavn);
        EditText telefonInn = (EditText) findViewById(R.id.telefonnr);

        db = new DBHandler(this);
        Intent i = getIntent();
        if (i.hasExtra("studId")) {
            long id = i.getExtras().getLong("studId");
            kontakt = db.finnKontakt(id);

            fornavnInn.setText(kontakt.getFornavn());
            etternavnInn.setText(kontakt.getEtternavn());
            telefonInn.setText(kontakt.getTelefon());
        } else {
            kontakt = null;
        }
    }
    public void avbrytbtn(View v){
        Intent intent = new Intent(this, Studenter.class);// New activity
        setResult(RESULT_CANCELED, intent);
        finish();

    }
    public void leggtil(View v) {
        EditText fornavnInn = (EditText) findViewById(R.id.fornavn);
        EditText etternavnInn = (EditText) findViewById(R.id.etternavn);
        EditText telefonInn = (EditText) findViewById(R.id.telefonnr);

        if(kontakt == null)
            kontakt = new Kontakt();

        kontakt.setFornavn(fornavnInn.getText().toString());
        kontakt.setEtternavn(etternavnInn.getText().toString());
        kontakt.setTelefon(telefonInn.getText().toString());

        if(kontakt.fornavn.isEmpty() || kontakt.etternavn.isEmpty() || kontakt.telefon.isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.leggtill_validation, Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent;
        if (kontakt.get_ID() == 0) {
            intent = new Intent(this, Studenter.class);
            db.leggTilKontakt(kontakt);
        } else {
            intent = new Intent(this, KontaktDetaljer.class);
            db.oppdaterKontakt(kontakt);
        }

        Log.d("Legg inn: ", "legger til kontakter");
        setResult(RESULT_OK, intent);
        finish(); // Call once you redirect to another activity
    }

}
