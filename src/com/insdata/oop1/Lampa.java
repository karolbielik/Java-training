package com.insdata.oop1;

/**
 * Created by karol.bielik on 31. 10. 2016.
 */

//Triery(Objekty) Sa Pisu Velkym Zaciatocnym Pismenom  A Z Pravidla Su Podstatne Meno
public class Lampa {

    //konstruktor triedy
    public Lampa(){
        svieti = false;
    }

    //stav
    boolean svieti;

//    public int x = 1;

    //funkcieSaPisuMalymZaciatocnymPismenomADalejCamelCase
    //spravanie mozem zapnut alebo vypnut
    public void zapniLampu(){
        svieti = true;
        System.out.println("lampa svieti:"+svieti);
    }

    public void vypniLampu(){
        svieti = false;
        System.out.println("lampa svieti:"+svieti);
    }

    public boolean isSvieti() {
        return svieti;
    }
}
