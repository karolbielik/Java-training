package com.insdata.oop1;

/**
 * Created by karol.bielik on 31. 10. 2016.
 */
public class Main {
    public static void main(String[] args) {
        Lampa lampa = new Lampa();
        System.out.println("svieti:"+lampa.svieti);
        lampa.zapni();
        System.out.println("zapni");
        System.out.println("svieti:"+lampa.svieti);
        lampa.vypni();
        System.out.println("vypni");
        System.out.println("svieti:"+lampa.svieti);
    }
}
