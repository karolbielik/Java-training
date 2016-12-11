package com.insdata.wrappers;

import com.insdata.primitives.Booleans;

/**
 * Created by key on 10.12.2016.
 */
public class WrappersConstruction {

    public static void main(String[] args) {
        //ucel preco existuju wrappery
        //(A) - pouzitie v kolekciach, alebo v navratovych typoch kompatibilnych typu Object
        Object retPrim = retPrimitive();

        //(B) - wrappery poskytuju rozne funkcionality napr. prevod primitivov z/na String alebo
        //prevod do inych ciselnych sustav, konverzie na ine primitivne typy

        //vsetky konstruktory okrem Char poskytuju ako vstupny argument primitivny typ alebo String
        //String vyhadzuje NumberFormatException

        Integer i = new Integer(33);
        Integer i1 = new Integer("33");

        Float f = new Float(12.3f);
        Float f1 = new Float("12.3");//f nieje povinne

        Boolean b = new Boolean("FaLsE");
        Boolean b1 = new Boolean(true);
        Boolean b2 = new Boolean(true);

        //String parameter na primitiv/resp. wrapper
        //valueOf vracia wrapper novy objekt

        //vrati referenciu na ten isty objekt
        Boolean b3 = Boolean.valueOf(true);
        Boolean b4 = Boolean.valueOf(true);

        //pre celociselne vracia ten isty objekt(z cache) len pre byte rozsah
        Integer i2 = Integer.valueOf(-128);
        Integer i3 = Integer.valueOf(-128);

        //mozne robit prevody z cislenych sustav
        Byte by = Byte.valueOf("00111100",2);

        // parseXX vracia dany primitiv
        int parsedInt = Integer.parseInt("34");
        int parsedFromBinary = Integer.parseInt("00111100", 2);

        //konverzne utility
        Integer i4 = new Integer(55);
        byte b5 = i4.byteValue();
        int i5 = i4.intValue();
        double d = i4.doubleValue();

        //prevedenie na String
        System.out.println("i4 hodnota je:"+i4.toString());
        System.out.println("i5 hodnota je:"+Integer.toString(i5));
        System.out.println("i5 hex hodnota je:"+Integer.toHexString(i5));

        //boxing, unboxing od java 5 => tam kde je potrebne java automaticky prevadza wrapper typ na primitivny a opacne.

        //vsetky wrapre su immutable(nemenne) teda, ak bola hodnota raz nastavena uz sa neda zmenit)
        Integer i6 = Integer.valueOf(7);
        i6 = 7;
        System.out.println("i6 identity hash:"+ System.identityHashCode(i6));
        //java spravi unboxing inkrementuje primitiv a spravy boxing kde primitive zabali do objektu
        i6++;
        System.out.println("i6 identity hash:"+ System.identityHashCode(i6));
        System.out.println("identity hash i6 pomocou valueOf:"+System.identityHashCode(Integer.valueOf(i6)));

        //mozme pouzit Boolean v podmienkovych vyrazoch ale pozor na null pointer exception
        //java robi unboxing a ak je Boolean == null tak vychodi exception
        //b4=null;
        if(b4){
            System.out.println("b4 je:"+b4);
        }

        //pouzitie == / equals
        //== porovnava ci premenne ukazuju na ten isty objekt
        //equals ak su dva objekty rovnakeho typu a maju rovnaku hodnotu
        Integer ii1 = 10;
        Integer ii2 = 10;
        Long ll1 = 10L;
        System.out.println("ii1==ii2:"+(ii1==ii2));
        System.out.println("ii1==ii2:"+(ii1.equals(ii2)));
        System.out.println("ii1==ll1:"+(ii1.equals(ll1)));
        //== vyhodnoti dva objekty ako rovnake v pripade boxingu ak je Boolean, Byte, Charakter of 0 do 127, Short a Integer of -128 po 127

        //ale
        ii1 = new Integer(10);
        ii2 = new Integer(10);
        System.out.println("2/ ii1==ii2:"+(ii1==ii2));





    }

    private static Object retPrimitive(){
        byte b = 1;
        return b;
    }

}
