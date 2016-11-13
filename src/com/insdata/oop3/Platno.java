package com.insdata.oop3;

/**
 * Created by key on 12.11.2016.
 */
public class Platno {
    public static void main(String[] args) {

        Ceruzka ceruzka = new CeruzkaKruh();
        ceruzka.kresliTvar();
        ceruzka = new CeruzkaStvorec();
        ceruzka.kresliTvar();
    }
}
