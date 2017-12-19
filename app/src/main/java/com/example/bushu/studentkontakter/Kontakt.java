package com.example.bushu.studentkontakter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Kontakt {
    long _ID;
    String fornavn;
    String etternavn;
    String telefon;


    public Kontakt() {

    }

    public Kontakt(String navn,String etternavn, String telefon) {
        this.fornavn = navn;
        this.etternavn = etternavn;
        this.telefon = telefon;
    }

    public Kontakt(long _ID, String navn, String etternavn,String telefon) {
        this._ID = _ID;
        this.fornavn = navn;
        this.etternavn = etternavn;
        this.telefon = telefon;
    }


    public String getFornavn() {
        return fornavn;
    }
    public String getEtternavn() {
        return etternavn;
    }

    public String getTelefon() {
        return telefon;
    }

    public long get_ID() {
        return _ID;
    }

    public void set_ID(long _ID) {
        this._ID = _ID;
    }

    public void setFornavn(String fornavn) {
        this.fornavn = fornavn;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }
    public void setEtternavn(String etternavn) {
        this.etternavn = etternavn;
    }
}

