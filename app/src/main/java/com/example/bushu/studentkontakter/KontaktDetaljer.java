package com.example.bushu.studentkontakter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class KontaktDetaljer extends AppCompatActivity {
    DBHandler db;
    long id;
    Kontakt kontakt = new Kontakt();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kontakt_detaljer);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.studlist_toolbar);
        setSupportActionBar(myToolbar);

        Intent i = getIntent();
        String resultat = i.getExtras().getString("selectedId");
        id = Integer.parseInt(resultat);
        db = new DBHandler(this);
        hentfraDbogOppdater();

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
            case R.id.kontakter_ikon: // noe bug som kommer ikke tilbake fra preferanser til studentliste, men tilbake hit
                /*Toast.makeText(this, "Hei!", Toast.LENGTH_SHORT).show();*/
                Intent is = new Intent(this,Studenter.class);
                startActivity(is);
                break;
            case R.id.melding_ikon:
                /*Toast.makeText(this, "Hei!", Toast.LENGTH_SHORT).show();*/
                Intent im = new Intent(this,Meldinger.class);
                startActivity(im);
                break;
            case R.id.settings_icon:
                /*Toast.makeText(this, "Hopp!", Toast.LENGTH_SHORT).show();*/
                Intent ip = new Intent(this,Preferanser.class);
                startActivity(ip);
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void hentfraDbogOppdater() {
        Kontakt kontakt = db.finnKontakt(id);

        TextView fornavn = (TextView) findViewById(R.id.fornavn_input);
        fornavn.setText(kontakt.getFornavn());
        TextView etternavn = (TextView) findViewById(R.id.etternavn_input);
        etternavn.setText(kontakt.getEtternavn());
        TextView mobil = (TextView) findViewById(R.id.mobil);
        mobil.setText(kontakt.getTelefon());
    }

    public void sletteKontakt(View v){

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(KontaktDetaljer.this, Studenter.class);
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        db.slettKontakt(id);
                        setResult(RESULT_OK, intent);
                        Toast.makeText(getApplicationContext(), R.string.kontakt_slettet, Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Vil du slette kontakt?").setPositiveButton("Ja", dialogClickListener)
                .setNegativeButton("Nei", dialogClickListener).show();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode== RESULT_OK) {
            hentfraDbogOppdater();
        }
    }
    public void oppdaterDetaljer(View v){
        Intent intent = new Intent(KontaktDetaljer.this, LeggTilStudent.class);
        intent.putExtra("studId", id);
        startActivityForResult(intent,30);
    }
    public void avbrytKontaktDetaljer(View v) {
        Intent intent = new Intent(this, Studenter.class);
        setResult(RESULT_CANCELED, intent);
        finish();
    }
}
