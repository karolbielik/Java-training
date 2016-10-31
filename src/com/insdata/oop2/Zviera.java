package com.insdata.oop2;

/**
 * Created by karol.bielik on 27. 10. 2016.
 */
import java.lang.String;

//Triery(Objekty) Sa Pisu Velkym Zaciatocnym Pismenom  A Z Pravidla Su Podstatne Meno
/*public*/ class Zviera {

    //konstruktor triedy
    Zviera(){
        //zradlo = "zviera zerie zradlo";
    }

    {
        zradlo = "zviera zerie zradlo";
    }

    //dobrym zvykom je drzat premonne triedy ako privat a zpristupnit ich cez motodu kvoli enkapsulacii
    //nieje to vsak povinnost
    String zradlo;

    //funkcieSaPisuMalymZaciatocnymPismenomADalejCamelCase
    String zerie(){
        return zradlo;
    }
}
