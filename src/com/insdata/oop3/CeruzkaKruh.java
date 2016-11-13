package com.insdata.oop3;

/**
 * Created by key on 13.11.2016.
 */
public class CeruzkaKruh implements Ceruzka {

    private Kruh kruh = new Kruh();

    @Override
    public void kresliTvar() {
        System.out.println(kruh.kresliObvod());
        System.out.println(kruh.kresliVypln());
    }
}
