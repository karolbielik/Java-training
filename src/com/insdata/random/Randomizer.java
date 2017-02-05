package com.insdata.random;

import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by key on 5.2.2017.
 */
public class Randomizer {

    public static void main(String[] args) {
        //1. moznost ako generovat pseudo nahodne cisla
        //pomocou java.lang.Math
        System.out.println("Math.random()");
        for(int i = 0;i<10;i++){
            double randomNr = Math.random(); // vrati double vacsie rovne ako 0.0 a mensie ako 1.0
            //vyber nahodne cislo od 0 po 9
            int intRandom = (int)(10 * randomNr);
            System.out.print(intRandom+",");
        }

        //2. moznost pomocou java.util.Random
        //je vlaknovo bezpecna ale pouzitie jedneho Random objektu viac vlaknami moze viest k pomalemu behu programu.
        System.out.println("java.util.Random");
        Random random = new Random();
        for(int i = 0; i< 10; i++){
            long randomNr = vratRandomCislo(5, 15, random);
            System.out.print(randomNr+",");
        }

        //cryptograficky bespecny random, ktory ma nedeterministicky vystup
        SecureRandom secureRandom = new SecureRandom();
        //pre vlaknovo bezpecny pristup sa odporuca pouzit:
        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
    }

    private static long vratRandomCislo(long zaciatok, long koniec, Random random) {
        long rozsah = (koniec - zaciatok) + 1;
        return (long)(rozsah * random.nextDouble() + zaciatok);
    }

}
