package com.insdata.enums;

/**
 * Created by key on 16.1.2017.
 */
public class TestEnums {
    public static void main(String[] args) {
        System.out.println(EnumDefinovanieAkoTrieda.PONDELOK);
        System.out.println();

        System.out.println(EnumDefinovanyVTriede.EnumVTriede.UTOROK);
        System.out.println();

        System.out.println(EnumEquivalentnaTrieda.STREDA);
        System.out.println();

        //vypis enum
        for(EnumDefinovanieAkoTrieda value : EnumDefinovanieAkoTrieda.values()){
            System.out.println(value);
        }
        System.out.println();

        System.out.println(EnumDefinovanieAkoTrieda.valueOf("PONDELOK"));

    }
}
