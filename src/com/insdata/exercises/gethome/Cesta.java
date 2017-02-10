package com.insdata.exercises.gethome;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by key on 10.2.2017.
 */
public class Cesta {

    private int aktualnaPozicia = -1;
    private Integer dlzkaCesty;
    private Smer[] mapa;

    public int getDlzkaCesty() {
        return dlzkaCesty;
    }

    public Cesta nastavDlzkuCesty(int dlzkaCesty) throws DlzkaCestyException{
        if(dlzkaCesty<1 || dlzkaCesty>5){
            throw new DlzkaCestyException(1,5);
        }
        this.dlzkaCesty = dlzkaCesty;
        return this;
    }

    public String getMapa(){
        return Arrays.toString(mapa);
    }

    public int getAktualnaPozicia() {
        return aktualnaPozicia;
    }

    public void setAktualnaPozicia(int aktualnaPozicia) {
        this.aktualnaPozicia = aktualnaPozicia;
    }

    public boolean jeKoniecCesty(){
        return aktualnaPozicia == dlzkaCesty-1;
    }

    public void generujMapu(){
        if(dlzkaCesty == null) {
            throw new IllegalStateException("Dlzka cesty musi byt nastavena pred generovanim mapy!");
        }

        mapa = new Smer[dlzkaCesty];
        Random random = new Random();
        for(int i = 0; i< dlzkaCesty; i++){
            int randomNr = vratRandomCislo(0, 2, random);
            mapa[i] = Smer.smerPodlaOrdinal(randomNr);
        }

    }

    private int vratRandomCislo(int zaciatok, int koniec, Random random) {
        int rozsah = (koniec - zaciatok) + 1;
        return (int)(rozsah * random.nextDouble() + zaciatok);
    }

    public boolean krokMozny(Smer smer) {
        int novaPozicia = aktualnaPozicia+1;
        if(mapa.length>novaPozicia && mapa[novaPozicia] == smer){
            aktualnaPozicia++;
            return true;
        }
        return false;
    }
}
