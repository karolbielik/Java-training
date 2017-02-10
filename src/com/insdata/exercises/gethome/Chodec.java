package com.insdata.exercises.gethome;

/**
 * Created by key on 10.2.2017.
 */
public class Chodec {

    private Cesta cesta;
    private int pocetPokusov;

    public Chodec(Cesta cesta) {
        this.cesta = cesta;
    }

    public boolean robimKrok(Krok krok){
        pocetPokusov++;
        return krok.spravKrok(cesta);
    }

    public int getPocetPokusov() {
        return pocetPokusov;
    }
}
