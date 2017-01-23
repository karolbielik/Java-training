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


        //porovnavanie enumov
        EnumDefinovanieAkoTrieda pondelok1 = EnumDefinovanieAkoTrieda.PONDELOK;
        EnumDefinovanieAkoTrieda pondelok2 = EnumDefinovanieAkoTrieda.PONDELOK;
        EnumDefinovanieAkoTrieda utorok1 = EnumDefinovanieAkoTrieda.UTOROK;
        EnumDefinovanyVTriede.EnumVTriede utorok2 = EnumDefinovanyVTriede.EnumVTriede.UTOROK;

        //compareTo interne porovnava ordinalne hodnoty enumu
        System.out.println("pondelok1 compare pondelok2:"+(pondelok1.compareTo(pondelok2)==0?"TRUE":"FALSE"));
        //interne porovnava referencie pondelok1 a pondelok2,
        // kedze enum su konstanty tak ide o jednu a tu istu referenciu.
        // Moze vyhodit ale NullPointerExc ak pondelok1 je null
        System.out.println("pondelok1 equals pondelok2:"+pondelok1.equals(pondelok2));
        //vyhodnejsie je pouzit nasledovne, lebo sa vyhneme null pointer exception,
        // a "==" kontroluje typovost enumov,
        // equals nekontroluje typ parametru, lebo je definovany ako equals(Object o)
        System.out.println("pondelok1 == pondelok2:"+(pondelok1==pondelok2));

        //mozem porovnavat len enum toho isteho typu
        //nasledovne nieje dovolene
        //System.out.println((utorok2 == utorok1));
        //System.out.println("pondelok1 compare pondelok2:"+(utorok1.compareTo(utorok2)==0?"TRUE":"FALSE"));
        //ale
        System.out.println("utorok1 equals utorok2:"+utorok1.equals(utorok2));


        //ordinalna hodnota enum
        System.out.println("poradova hodnota enumu:"+pondelok1.ordinal());

        //pristup k enum konstante pomocou indexu
        EnumDefinovanieAkoTrieda e = EnumDefinovanieAkoTrieda.values()[0];

        //vracia metadata enum triedy
        System.out.println("getDeclaringClass:"+utorok2.getDeclaringClass().getSimpleName());


    }
}
