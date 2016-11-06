package com.insdata.oop1;

/**
 * Created by karol.bielik on 31. 10. 2016.
 */

//Triery(Objekty) Sa Pisu Velkym Zaciatocnym Pismenom  A Z Pravidla Su Podstatne Meno
/*public*/ class Lampa {

    //konstruktor triedy
    Lampa(){
        svieti = false;
    }

    //stav
    boolean svieti;

    //funkcieSaPisuMalymZaciatocnymPismenomADalejCamelCase
    //spravanie mozem zapnut alebo vypnut
    public void zapniLampu(){
        svieti = true;
    }

    public void vypniLampu(){
        svieti = false;
    }
}
