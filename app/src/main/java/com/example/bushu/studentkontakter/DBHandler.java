package com.example.bushu.studentkontakter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import java.util.List;

/**
 * Created by bushu on 18.09.2017.
 */

public class DBHandler extends SQLiteOpenHelper {
    static String TABLE_KONTAKTER = "Kontakter";
    static String KEY_ID = "_ID";
    static String KEY_NAME = "Fornavn";
    static String KEY_SURNAME = "Etternavn";
    static String KEY_PH_NO = "Telefon";
    static int DATABASE_VERSION = 1;
    static String DATABASE_NAME = "Telefonkontakter";

    static String TABLE_MELDINGER = "Meldinger";
    static String MELDING_ID = "M_ID";
    static String MELDINGTEXT_COLUMNNAVN = "Meldingtekst";
    static String DATE = "Dato";
    static String DATESENT = "DatoSendt";
    static String ER_SENDT= "ErSendt";


    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String lagTabell = "CREATE TABLE " + TABLE_KONTAKTER + "(" + KEY_ID +
                " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT," + KEY_SURNAME + " TEXT," + KEY_PH_NO + " INTEGER" + ")";
        Log.d("SQL", lagTabell);
        db.execSQL(lagTabell);

        String meldingTabell = "CREATE TABLE " + TABLE_MELDINGER + "(" + MELDING_ID +
                " INTEGER PRIMARY KEY," + MELDINGTEXT_COLUMNNAVN + " TEXT," + DATE + " TEXT," +DATESENT + " TEXT,"+ ER_SENDT + " INTEGER" + ")";
        Log.d("SQL", meldingTabell);
        db.execSQL(meldingTabell);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KONTAKTER);
        onCreate(db);
    }
    public void leggTilKontakt(Kontakt kontakt) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, kontakt.getFornavn());
        values.put(KEY_SURNAME, kontakt.getEtternavn());
        values.put(KEY_PH_NO, kontakt.getTelefon());
        db.insert(TABLE_KONTAKTER, null, values);
        db.close();
    }

    public List<Kontakt> finnAlleKontakter() {
        List<Kontakt> kontaktListe = new ArrayList<Kontakt>();
        String selectQuery = "SELECT * FROM " + TABLE_KONTAKTER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Kontakt kontakt = new Kontakt();
                kontakt.set_ID(cursor.getLong(0));
                kontakt.setFornavn(cursor.getString(1));
                kontakt.setEtternavn(cursor.getString(2));
                kontakt.setTelefon(cursor.getString(3));
                kontaktListe.add(kontakt);
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return kontaktListe;
    }

    public List<Melding> finnAlleMeldinger() {
        List<Melding> meldingListe = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_MELDINGER + " order by "+DATESENT+" IS NOT NULL, " + DATESENT + " desc";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Melding melding = new Melding(cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4));

                meldingListe.add(melding);

            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return meldingListe;
    }

    public List<Melding> finnAlleIkkeSendtMeldinger() {
        List<Melding> meldingListe = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_MELDINGER + " WHERE "+ ER_SENDT + " = 0";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Melding melding = new Melding(cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4));

                meldingListe.add(melding);

            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return meldingListe;
    }

    public void leggTilMelding(Melding melding) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MELDINGTEXT_COLUMNNAVN, melding.getMelding());
        values.put(DATESENT, melding.getDatoSendt());
        values.put(ER_SENDT, melding.getErSendt());
        db.insert(TABLE_MELDINGER, null, values);
        db.close();
    }

    public int oppdaterMelding(Melding melding) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ER_SENDT, melding.getErSendt());
        values.put(DATESENT, melding.getDatoSendt());
        values.put(MELDINGTEXT_COLUMNNAVN, melding.getMelding());
        int endret = db.update(TABLE_MELDINGER, values, MELDING_ID + "= ?",
                new String[]{String.valueOf(melding.getId())});
        db.close();
        return endret;
    }

    public void slettKontakt(Long inn_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_KONTAKTER, KEY_ID + " =? ",
                new String[]{Long.toString(inn_id)});
        db.close();
    }

    public void slettMelding(Long meldingId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MELDINGER, MELDING_ID + " =? ",
                new String[]{Long.toString(meldingId)});
        db.close();
    }

    public int oppdaterKontakt(Kontakt kontakt) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, kontakt.getFornavn());
        values.put(KEY_SURNAME, kontakt.getEtternavn());
        values.put(KEY_PH_NO, kontakt.getTelefon());
        int endret = db.update(TABLE_KONTAKTER, values, KEY_ID + "= ?",
                new String[]{String.valueOf(kontakt.get_ID())});
        db.close();
        return endret;
    }


    public Kontakt finnKontakt(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_KONTAKTER, new String[]{
                        KEY_ID, KEY_NAME,KEY_SURNAME, KEY_PH_NO}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) cursor.moveToFirst();
        Kontakt kontakt = new
                Kontakt(Long.parseLong(cursor.getString(0)),cursor.getString(1),
                cursor.getString(2), cursor.getString(3));
        cursor.close();
        db.close();
        return kontakt;
    }

    public Melding finnMelding(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_MELDINGER, new String[]{
                        MELDING_ID, MELDINGTEXT_COLUMNNAVN,DATESENT, ER_SENDT}, MELDING_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) cursor.moveToFirst();
        Melding melding = new
                Melding(Long.parseLong(cursor.getString(0)),cursor.getString(1),
                cursor.getString(2), cursor.getInt(3));
        cursor.close();
        db.close();
        return melding;
    }
}
