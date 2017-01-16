package com.insdata.enums;

/**
 * Created by key on 16.1.2017.
 */
public class EnumDefinovanyVTriede {
    //moze byt private, protected, default, public
    enum EnumVTriede {
        PONDELOK,
        UTOROK,
        STREDA//mozeme pouzit na konci bud ';' alebo ',' Niesu vsak povinne
    }//; tiez je nepovinne

    public static void main(String[] args) {
        System.out.println(EnumVTriede.PONDELOK);
        System.out.println(EnumVTriede.UTOROK);
        System.out.println(EnumVTriede.STREDA);
        //enum{ONE,TWO,THREE} => !!!nemoze byt definovany uprostred metody
    }
}
