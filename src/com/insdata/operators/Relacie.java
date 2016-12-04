package com.insdata.operators;

/**
 * Created by key on 4.12.2016.
 */
public class Relacie {

    //logicke operatory vracaju ako vysledok boolean
    //pouzivame ich s ciselnimi premennymi a char
    //== pouzivame s cislami, char, boolean, Object(porovnava referenciu)
    //< , <=, >, >=, ==, !=
    public static void main(String[] args) {
        System.out.println("2,2 rovna sa:"+rovnaSa(2,2));
        System.out.println("2,3 rovna sa:"+rovnaSa(2,3));
        System.out.println("2,3 nerovna sa:"+nerovnaSa(2,3));
        System.out.println("2 ja vacsie ako 3:"+jeVacsie(2,3));

        Object a = new Kruh_1();
        Object b = new Kruh_1();
        System.out.println("objekt a == b:"+porovnajObjekty(a,b));
        b = a;
        System.out.println("objekt a == b po priradeni:"+porovnajObjekty(a,b));
    }

    private static boolean rovnaSa(int a, int b){
        return a == b;
    }

    private static boolean nerovnaSa(int a, int b){
        return a!=b;
    }

    private static boolean jeVacsie(int a, int b){
        return a>b;
    }

    private static boolean porovnajObjekty(Object a, Object b){
        return a == b;
    }
}
