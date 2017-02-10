package com.insdata.exercises.gethome;

/**
 * Created by key on 10.2.2017.
 */
public enum Smer {
    RIGHT('r'),
    LEFT('l'),
    FORWARD('f');

    char smer;
    Smer(char smer){
        this.smer = smer;
    }

    char value(){
        return smer;
    }

     public static Smer smerPodlaOrdinal(int ord){
        return ord==0?RIGHT:ord==1?LEFT:ord==2?FORWARD:RIGHT;
    }
}
