package com.insdata.primitives;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class NumberFormatting {
    public static void main(String[] args) throws ParseException {
        //---------------------------------formatting, parsing-----------------------------

        //ako preve potrebujeme vytvorit NumberFormat
        //formater vseobecneho urcenia
        NumberFormat generalFormat = NumberFormat.getInstance();
        NumberFormat generalFormatDefLocale = NumberFormat.getInstance(Locale.getDefault());
        //formater rovnaky ako getInstance()
        NumberFormat.getNumberInstance();
        NumberFormat.getNumberInstance(Locale.getDefault());
        //formater  pre monetarne ciastky
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        NumberFormat.getCurrencyInstance(Locale.getDefault());
        //formater pre percenta
        NumberFormat.getPercentInstance();
        NumberFormat.getPercentInstance(Locale.getDefault());
        //formater ktory zaokruhluje decimali pred vyobrazenim
        NumberFormat.getIntegerInstance();
        NumberFormat.getIntegerInstance(Locale.getDefault());

        //NumberFormat nieje thread-safe, neukladat do instancnych a statickych premennych

        //ked mame numberformat, potom mozme zavolat format() alebo parse()
        //-------------------general purpose------------------------------------
        double doubleNr = 3/2;
        System.out.println(doubleNr);
        System.out.println(generalFormat.format(doubleNr));
        System.out.println(generalFormatDefLocale.format(doubleNr));

        String strNr = "342xxx";
        Number nr = generalFormat.parse(strNr);
        System.out.println(nr);
        String excNr = "x342";
        try {
            nr = generalFormat.parse(excNr);
            System.out.println(nr);
        }catch (ParseException pex){
            System.out.println(pex.getMessage());
        }

        //--------------------------------currency----------------------------------
        double price = 55;
        System.out.println(currencyFormat.format(price));//java doda EUR znak a desatinne cisla

        //TODO: dorobit aj ostatne typy formaterov

    }
}
