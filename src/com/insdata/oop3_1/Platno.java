package com.insdata.oop3_1;

import com.insdata.oop3_1.Ceruzka;

/**
 * Created by key on 12.11.2016.
 */
public class Platno {
    public static void main(String[] args) {

        Ceruzka ceruzka = new CeruzkaKruh();
        ceruzka.kresliTvar();
        ceruzka = new CeruzkaStvorec();
        ceruzka.kresliTvar();

//        OVERLOADING
//        CeruzkaVersatile versatile = new CeruzkaVersatile();
//        versatile.kresliTvar(new Kruh());
//        versatile.kresliTvar(new Stvorec());
    }
}
