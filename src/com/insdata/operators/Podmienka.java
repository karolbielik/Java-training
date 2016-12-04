package com.insdata.operators;

/**
 * Created by key on 4.12.2016.
 */
public class Podmienka {
    //(boolean vyraz) ? (navratova hodnota ak je vyraz true) : (navratova hodnota ak je vyraz false)

    public static void main(String[] args) {
        int i = dajVyssieCislo(2, 3);
        System.out.println(i);
    }

    private static int dajVyssieCislo(int a, int b) {
        return a>b?a:b;
    }
}
