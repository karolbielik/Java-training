package com.insdata.primitives;

import java.math.BigDecimal;

/**
 * Created by karol.bielik on 26. 11. 2016.
 */
public class Numbers {
    int defaultInt;

    public static void main(String[] args) {
        System.out.println((new Numbers()).defaultInt );
        //celociselne literaly
        byte b = 127;
        short s = 32767;
        int i = 2147483647;
        long l = 9223372036854775807L;

        //pripad pretecenia
        System.out.println(b);
        s = 127+1;
        b=(byte)s;
        System.out.println("byte = 127 + 1:"+b);
        System.out.println();

        int res = Math.addExact(2147483647, 1);

        float f = 14.23f;//mozna strata precission(desatin)
        double d = 14.23;
//      float ff = 999999999999999999999999999999999999999.999f;//floating point number too large
        float ff = 99999999999999999999999999999999999999.999f;
        double dd = 999999999999999999999999999999999999999.999d;//d nieje povinne

        //hexadecimal
        int hex = 0xDeadCafe;
        int hex1 = 0XDeadCafe;
        System.out.println("Hexadecimal:");
        System.out.println("0XDeadCafe:"+hex);

        //octal literal
        int six = 06; //decimal 6
        int eight = 010; //decimal 8
        System.out.println("Octal:");
        System.out.println("06:"+six);
        System.out.println("010:"+eight);

        //binary
        int binaryInt = 0b10000000;
//        byte binaryByte1 = 0b10000000;
        byte binaryByte = 0B0111111;
        System.out.println("Binary:");
        System.out.println("0b10000000:"+binaryInt);
    }
}
