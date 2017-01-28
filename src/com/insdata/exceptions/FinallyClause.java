package com.insdata.exceptions;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by key on 28.1.2017.
 */
public class FinallyClause {

    public static void main(String[] args) throws MojaEksepsna{
        FinallyClause fc = new FinallyClause();
        try {
            System.out.println("----------------------handlujAVyhodEksepsnu----------------");
            fc.handlujAVyhodEksepsnu();
        }catch (MojaEksepsna mex){
            //vynimka je pohltena bez toho aby som videl nejaku chybu
        }

        System.out.println("----------------------vykonajKodAPropagujVynimkuDalej----------------");
//        fc.vykonajKodAPropagujVynimkuDalej();

        try {
            System.out.println("----------------------vyhodSpecifickuVynimku----------------");
            fc.vyhodSpecifickuVynimku(2);
        }catch (MojaEksepsna mex) {//ak mame viac catch tak ideme od specifickejsej vynimke k viac vseobecnej
            mex.printStackTrace();
        }catch (MojaEksepsnaDva mexDva) {
            mexDva.printStackTrace();
        }catch (Exception ex) {//neodporuca sa handlovat(zachytavat vsetky vynimky)
            //v tomto bloku sa handluju vsetky vynimky vyhodene metodou vyhodSpecifickuVynimku okrem uz hore hendlovanych
            // MojaEksepsna a MojaEksepsnaDva
            ex.printStackTrace();
        }

        try {
            System.out.println("----------------------vyhodSpecifickuVynimku Java7----------------");
            fc.vyhodSpecifickuVynimku(2);
        }catch (MojaEksepsna | MojaEksepsnaDva mex) {
            mex.printStackTrace();
        }

        System.out.println("----------------------Java7 resource s exception ----------------");
        //ak moja trieda pracuje s nejakymi resoursami a potrebujem ich uvolnit
        //v pripade exception, tak to mozem docielit implementaciou Closable interface
        //a potom pouzit nasledovny zapis
        try(MojaCloasableClass closableCls = new MojaCloasableClass()) {
            closableCls.vyhodVynimku();
        }catch (IOException | MojaEksepsna mex){
            System.out.println(mex.getMessage());
        }
    }

    private void handlujAVyhodEksepsnu() throws MojaEksepsna{
        //try nemoze stat osamote bez catch alebo finally
        try {
            vyhodEksepsnu();
            System.out.println("ja sa nikdy nevypisem");
        }catch (MojaEksepsna mex){ //catch musi is vzdy za try, ale moze absentovat
            System.out.println("handlujem moju eksepsnu");
            System.out.println(mex.getMessage());

            throw mex; //propaguj vynimku vyssie
        }finally {//musi ist vzdy za catch alebo try blokom, alebo moze absentovat
            System.out.println("ja sa vypisem vzdy, ci je vynimka alebo nie");
        }
    }

    private void vykonajKodAPropagujVynimkuDalej() throws MojaEksepsna{
        try {
            vyhodEksepsnu();
            System.out.println("ja sa nikdy nevypisem");
        }finally {
            System.out.println("ja sa vypisem vzdy, ci je vynimka alebo nie");
        }
    }

    private void vyhodSpecifickuVynimku(int cisloVynimky) throws MojaEksepsna, MojaEksepsnaDva {
        if(cisloVynimky < 5){
            throw new MojaEksepsna();
        }else{
            throw new MojaEksepsnaDva();
        }
    }

    private void vyhodEksepsnu() throws MojaEksepsna{
        throw new MojaEksepsna();
    }

}

class MojaEksepsnaDva extends Exception{
    @Override
    public String getMessage() {
        return "moja druha eksepsna";
    }
}

class MojaCloasableClass implements Closeable{
    @Override
    public void close() throws IOException {
        System.out.println(".......uvolujem vsetky zdroje mojej closable triedy");
    }

    public void vyhodVynimku()throws MojaEksepsna{
        throw new MojaEksepsna();
    }
}
