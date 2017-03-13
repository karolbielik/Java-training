package com.insdata.generika;

import java.util.ArrayList;

/**
 * Created by key on 26.2.2017.
 */
public class Metoda {
    public static void main(String[] args) {
        Metoda m = new Metoda();

        ArrayList<Dcera> list = new ArrayList<>();
        list.add(new Dcera());

//        m.vypisList1(list);//NOK
        m.vypisList2(list);//OK

        ArrayList<Rodic> rodicia = new ArrayList<>();
        m.vypisList3(rodicia);

        Dcera dcera =  m.spracujData(new Dcera());
        Syn syn =  m.spracujData(new Syn());
        Rodic rodic = m.spracujData(new Rodic());
    }

    //parameter nemoze prijat nic ine ako ArrayList<Rodic>
    private void vypisList1(ArrayList<Rodic> list){
        for(Rodic r : list){
            System.out.println(r);
        }
        //ak by parameter metody prijal aj ArrayLIst<Dcera>, tak potom by sme mohli urobit nasledovne
        //teda pridat new Syn(), ktory ale nieje kompatibilny s Dcera triedou,
        //to by znamenalo runtime chybu, ale kedze typ generika je znamy len v kompilacnom case
        //tak to java nedovoluje
        list.add(new Syn());
    }

    //zapisom ? extends Rodic "slubujeme", ze nevlozime do kolekcie nic v ramci tejto metody
    //lebo raz mi tam moze vstupovat ArrayList s generikom Rodic, raz Dcera, Dieta, alebo Syn
    private void vypisList2(ArrayList<? extends Rodic> list){
        for(Rodic r : list){
            System.out.println(r);
        }

//        list.add(new Rodic());//NOK
//        list.add(new Dcera());//NOK
    }

    //v tomto pripade mozeme do kolekcie vkladat iba Dcera triedu
    //je to preto lebo zo stromu dedicnosti je Dcera najkonkretnejsia trieda
    //teda ak mi tam pride ArrayList<Rodic> tak Dcera je kompatibilna
    private void vypisList3(ArrayList<? super Dcera> list){
        list.add(new Dcera());//OK
//        list.add(new Rodic());//NOK
    }

    private <T extends Rodic> T spracujData(T data){
        System.out.println(data);
        return data;
    }

}
