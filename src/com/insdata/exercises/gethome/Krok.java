package com.insdata.exercises.gethome;

/**
 * Created by key on 10.2.2017.
 */
class Krok {
    protected Smer smer;

    public Krok(Smer smer) {
        this.smer = smer;
    }

    public boolean spravKrok(Cesta cesta){
        return cesta.krokMozny(this.smer);
    }

    @Override
    public String toString() {
        return smer.toString();
    }
}
