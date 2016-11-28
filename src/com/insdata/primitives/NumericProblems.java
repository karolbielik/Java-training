package com.insdata.primitives;

import java.math.BigDecimal;

/**
 * Created by karol.bielik on 28. 11. 2016.
 */
public class NumericProblems {
    public static void main(String[] args) {
        float f1 = (float) (0.1 * 0.1);
        float f2 = 0.1f * 0.1f;
        double d1 = 0.1 * 0.1 * 0.1;
        System.out.println("double result to float:"+f1);
        System.out.println("floats result:"+f2);
        System.out.println("double result:"+d1);
        System.out.println("compare float results:"+(f1 == f2));

        System.out.println();

        //problematicke pripady
        double ddd = 3 * 0.1;
        System.out.println(ddd);
        //ak potrebujem mat presnost v desatinach, teda v aplikacii pocitam s peniazmi
        System.out.println( (new BigDecimal(3000000000000000000L)).multiply(new BigDecimal(0.1)) );
        //treba pouzivat konstruktor so String parametrom pre spravnu reprezentaciu desatinneho cisla
        System.out.println( (new BigDecimal(3000000000000000000L)).multiply(new BigDecimal("0.10")) );//0.1 je BigDecimalom reprezentovana ako 1*10^-1

//        Math
    }
}
