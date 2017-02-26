package com.insdata.generika;


import java.util.ArrayList;

/**
 * Created by key on 26.2.2017.
 */
public class Kolekcia {
    public static void main(String[] args) {
        //od java 7 <Integer> nieje povinny
        ArrayList<Integer> genericky = new ArrayList<Integer>();
        ArrayList<Integer> genericky1 = new ArrayList<>();
        ArrayList<Integer> genericky2 = new ArrayList();
        genericky.add(new Integer(1));
        genericky.add(2);

        //pri definicii generik neplatia pravidla polymorfizmu
        //je to kvoli tomu, lebo typ arraylistu je znami len v kompilacnom case
        //a nie v runtime case
//        ArrayList<Object> genericky3 = new ArrayList<Integer>();//NOK
//        ArrayList<Number> genericky4 = new ArrayList<Integer>();//NOK
//        ArrayList<Rodic> genericky5 = new ArrayList<Dieta>();//NOK
        ArrayList<Rodic> genericky6 = new ArrayList<Rodic>();//OK

        //ale pole moze obsahovat kompatibilne prvky
        genericky6.add(new Dieta());
        genericky6.add(new Rodic());

        //zakladna vyhoda je ze nemusim castovat
        //Ako cielovy typ moze byt typ kompatibilny s typom generika v deklaracii pola
        //Object je kompatibilny ale Dieta nieje kompatibilne
        for(Rodic /*Object =>OK , Dieta=>NOK*/ r : genericky6){

        }
    }

}

