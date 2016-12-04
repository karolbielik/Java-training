package com.insdata.operators;

/**
 * Created by key on 4.12.2016.
 */
public class Logika {

    //!, &&, ||, ^(XOR)
    //pouzivame ich s premennymi typu boolean alebo vyrazmi ktore vracaju typ boolean
    public static void main(String[] args) {
        boolean ano = true;
        boolean nie = false;
        System.out.println("!ano:"+!ano);
        System.out.println("ano&&nie:"+(ano&&nie));
        System.out.println("ano||nie:"+(ano||nie));
        System.out.println(nie ^ ano);

        System.out.println("3<4 && 5<10:"+ (3<4 && 5<10));
    }
}
