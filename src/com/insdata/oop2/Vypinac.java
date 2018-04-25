package com.insdata.oop2;

import com.insdata.oop1.Lampa;

/**
 * Created by karol.bielik on 27. 10. 2016.
 */
public class Vypinac {
    public static void main(String[] args) {
        //operator new vytvara novy objekt na heap
        Lampa lampa = new Lampa();
        DiscoGula discoGula = new DiscoGula();
        Majak majak = new Majak();

        System.out.println("------------- pracuj s lampou ------------------");
        lampa.zapniLampu();
        lampa.vypniLampu();
        System.out.println("------------- pracuj s disco gulou ------------------");
        discoGula.zapniLampu();
        discoGula.vypniLampu();
        System.out.println("------------- pracuj s majakom ------------------");
        majak.zapniLampu();
        majak.vypniLampu();

        System.out.println("------------- ukazovatel cez rodica ------------------");
        Lampa lampa1 = new DiscoGula();
        Lampa lampa2 = new Majak();
        System.out.println("------------- pracuj s lampou cez rodica ------------------");
        lampa1.zapniLampu();
        lampa1.vypniLampu();
        System.out.println("------------- pracuj s disco gulou cez rodica ------------------");
        lampa2.zapniLampu();
        lampa2.vypniLampu();

        //nekompatibilne typy, su z jednej hierarchyckej urovne dedenia, obaja su priamy potomkovia Lampa
//        DiscoGula dg = new Lampa();
//        DiscoGula discoGula1 = new Majak();

//        System.out.println(lampa1.x);
//        System.out.println(lampa2.x);

        //prekryvanie atributov triedy
        DiscoGula dg = new DiscoGula();
        dg.svieti = false;
        Lampa dgLampa = dg;
        //nastavi svieti na Lampa na true
        dgLampa.zapniLampu();
        System.out.println("DiscoGula.svieti:"+dg.svieti);
        System.out.print("DiscoGula->Lampa svieti:"+dgLampa.isSvieti());


    }
}
