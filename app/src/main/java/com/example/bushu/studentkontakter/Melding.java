package com.example.bushu.studentkontakter;

/**
 * Created by bushu on 19.10.2017.
 */

public class Melding {

    long Id;
    String Melding;
    String dato;
    String datoSendt;
    int erSendt;


        public Melding(long id, String melding, String dato, String datoSendt, int erSendt) {
            Id = id;
            Melding = melding;
            this.dato = dato;
            this.datoSendt = datoSendt;
            this.erSendt = erSendt;
        }

        public Melding() {

          }

        public Melding(long id, String melding, String datoSendt, int erSendt) {
        this.Id = id;
        this.Melding = melding;
        this.datoSendt = datoSendt;
        this.erSendt = erSendt;
    }

        public long getId() {
            return Id;
        }

        public String getMelding() {
            return Melding;
        }

        public String getDato() {
            return dato;
        }

        public String getDatoSendt() {
            return datoSendt;
        }

        public int getErSendt() {
            return erSendt;
        }
        public void setId(long id) {
            Id = id;
        }

        public void setMelding(String melding) {
            Melding = melding;
        }

        public void setDato(String dato) {
            this.dato = dato;
        }

        public void setDatoSendt(String datoSendt) {
            this.datoSendt = datoSendt;
        }

        public void setErSendt(int erSendt) {
            this.erSendt = erSendt;
        }

}
