package com.insdata.primitives;

/**
 * Created by karol.bielik on 28. 11. 2016.
 */
public class Chars {
    char defaultChar;

    public static void main(String[] args) {
        System.out.println((new Chars()).defaultChar );
        char h = 'h';
        char e = 'e';
        char l = 'l';
        char l1 = 'l'; //108;//0x6c;
        //unicode o \u006F => http://unicode-table.com/en/
        char o = '\u006F';
        //escape sequence => https://docs.oracle.com/javase/tutorial/java/data/characters.html
        char enter = '\n';//new line
        char hearth = '\u2764';
        char football = '\u26BD';
        System.out.print(h);
        System.out.print(e);
        System.out.print(l);
        System.out.print(l1);
        System.out.print(enter);
        System.out.print(o);

        System.out.println();
        System.out.print(hearth);
        System.out.print(football);


        int hInt = h;
        System.out.println();
        System.out.println("char in int:"+hInt);
        char charFromInt = (char)hInt;
        System.out.println("char from int:"+charFromInt);

        //http://www.asciitable.com/
        for(int i = 65;i<91;i++)System.out.print((char)i);


    }
}
