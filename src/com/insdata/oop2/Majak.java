package com.insdata.oop2;

import com.insdata.oop1.Lampa;

/**
 * Created by karol.bielik on 6. 11. 2016.
 */
public class Majak extends Lampa {

//    public int x = 3;
    boolean blika;

    @Override
    public void zapniLampu() {
        super.zapniLampu();
        spustiOscilator();
    }

    @Override
    public void vypniLampu() {
        zastavOscilator();
        super.vypniLampu();
    }

    private void zastavOscilator() {
        blika = false;
        System.out.println("majak blika:"+blika);
    }

    private void spustiOscilator() {
        blika = true;
        System.out.println("majak blika:"+blika);
    }
}
