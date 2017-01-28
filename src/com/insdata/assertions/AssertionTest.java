package com.insdata.assertions;

/**
 * Created by key on 28.1.2017.
 */

//assertion je by default desablovane, enabluje sa prepinacom -ea (-enableassertions), disabluje -da (-disableassertions)
public class AssertionTest {
    //v assertion vzdy predpokladam ze nieco plati ak to neplati tak sa vyhodi AssertionError
    public static void main(String[] args) {
        zadajCislaOdNajmensiehoPoNajvacsie(4,3);
        spocitajPozitivneCisla(-1, 3);
    }

    private static void zadajCislaOdNajmensiehoPoNajvacsie(int x, int y){
        //ak vyraz (x<y) je vyhodnoteny ako false tak sa vyhodi AssertionError
        //AssertionError sa nikdy nehandluje try/catch blokom
        //try {
            assert x < y : "x=" + x + "je vacsie ako y=" + y;
//        }catch (Throwable assertionError){
//            System.out.println(assertionError.getMessage());
//        }
    }

    public static long spocitajPozitivneCisla(int a, int b){
        //ak chcem kontrolovat hodnoty parametrov ci su spravne, nepouzivam na to assertion kedze tato moze byt disablovana
        //negarantuje to spravnost funkcie, co by hlavne pri public metode sa garantovat malo
        assert a>0 && b>0;
        return a+b;
    }
}
