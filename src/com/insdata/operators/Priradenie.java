package com.insdata.operators;

/**
 * Created by key on 4.12.2016.
 */
public class Priradenie {

    //=, +=, -=, *=, /=, %=, <<=, >>=, &=, |=, ^=
    //pouzivame s java ciselnymi primitivmi premennymi(aj s wrappermi) aj s char
    //+= pouzivame aj so String
    public static void main(String[] args) {

        int a = 2;
        a = a + 3;
        System.out.println(a);

        //to iste ako vyraz hore
        a = 2;
        a += 3;
        System.out.println(a);

        int result = 2 * 3 + 4;//vyhodnocuje sa podla priority
        System.out.println("2 * 3 + 4:"+result);

        int result2 = 2;
        result2 *= 3 + 4; //prvy sa vyhodnocuje vyraz za operatorom => 2 * (3 + 4)
        System.out.println("result2 *= 3 + 4:"+result2);


        byte aa = 60;//0b00111100
        byte bb = aa >>= 2; // bb = 15; 0b00001111
        System.out.println(bb);
        byte ret = addExact((byte)127,(byte)1);

        char c = 'c';
        System.out.println("c += 2:"+ (c += 2));


    }

    public static byte addExact(byte var0, byte var1) {
        byte var2 = (byte)(var0 + var1);
        if(((var0 ^ var2) & (var1 ^ var2)) < 0) {
            throw new ArithmeticException("integer overflow");
        } else {
            return var2;
        }
        /*
        * prvy bit je signifikantny 0 = kladne, 1 = zaporne cislo
        *
        * C1    C2      Vysl
        * 1(zap)1       0       =>  1 je zaporne
        * 0(kl) 0       1       =>  1 je zaporne
        * 1     0       0       =>  0 nieje zaporne
        * 0     1       0       =>  0 nieje zaporne
        * */
    }

}
