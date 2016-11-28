package com.insdata.primitives;

/**
 * Created by karol.bielik on 28. 11. 2016.
 */
public class Booleans {
    boolean defaultBoolean;

    public static void main(String[] args) {
        System.out.println((new Booleans()).defaultBoolean );

        boolean b1 = true;//false
        //boolean b2 = 0;//compilation error

        //stack variables need to be initialized
        boolean notInitBool;
        if(false)notInitBool=true;
//        b1=notInitBool;

    }
}
