package com.insdata.generika.example.pokladna.mena;

/**
 * Created by karol.bielik on 13. 3. 2017.
 */
public abstract class Mena {

    Double ciastka;

    public Mena(Double ciastka){
        this.ciastka = ciastka;
    }

    public Double getCiastka() {
        return ciastka;
    }
}
