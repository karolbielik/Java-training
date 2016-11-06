package com.insdata.oop1;

/**
 * Created by karol.bielik on 31. 10. 2016.
 */
public class Vypinac {
    public static void main(String[] args) {
        Lampa lampa;
        lampa = new Lampa();
        System.out.println("svieti:"+lampa.svieti);
        lampa.zapniLampu();
        System.out.println("zapni");
        System.out.println("svieti:"+lampa.svieti);
        lampa.vypniLampu();
        System.out.println("vypni");
        System.out.println("svieti:"+lampa.svieti);
    }
}
