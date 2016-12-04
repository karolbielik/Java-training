package com.insdata.operators;

import com.insdata.oop3_1.Kruh;
import com.insdata.oop3_1.Stvorec;
import com.insdata.oop3_1.Tvar;

/**
 * Created by key on 4.12.2016.
 */
public class Instancia {

    //instanceof operator - zistujeme nim v design time, ci dana instancia je kompatibilna s typom
    public static void main(String[] args) {
        Tvar kruh_1 = new Kruh_1();
        System.out.println("kruh_1 instanceof Kruh:"+(kruh_1 instanceof Kruh) );
        System.out.println("kruh_1 instanceof Stvorec:"+(kruh_1 instanceof Stvorec) );

    }
}
