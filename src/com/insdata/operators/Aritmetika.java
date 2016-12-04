package com.insdata.operators;

/**
 * Created by key on 4.12.2016.
 */
public class Aritmetika {

    //++, --, *, /, %, +, -       -> zoradene podla priority vyhodnocovania
    //pouzivame s cislami
    // + aj s char a String
    // -, ++, -- aj s char
    public static void main(String[] args) {
        int a = 2;
        int b = 5;
        System.out.println("a+b=:" +scitaj(a,b));

        System.out.println("++a+b=:"+scitaj(++a,b));
        System.out.println(a);

        a=2;
        System.out.println("a++ +b=:"+ scitaj(a++,b));

        a = 2;
        b = 5;
        System.out.println("a++ * b:"+(a++ * b));

        a = 2;
        System.out.println("++a * b:"+(++a * b));

        int i = 2;
        long l = 4;
        /*int*/long r = i + l; //vysledkom je typ s mensim obmedzenim

        double d = 2.35;
        float f = 2.65f;
        /*float*/double rf = d + f;//vysledkom je typ s mensim obmedzenim

    }

    private static int scitaj(int a, int b){
        return a + b;
    }


}
