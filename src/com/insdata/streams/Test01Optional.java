package com.insdata.streams;

import java.util.NoSuchElementException;
import java.util.Optional;

public class Test01Optional {
    public static void main(String[] args) {
        //Optional je objekt ktory moze byt vrateny z vacsiny funkcii stream
        //Sluzi vlastne nato, ze v niektorych pripadoch este vysledna hodnota nieje jasna,
        //takto mozme od java 8 vyjadrit, ze aj hodnota null je specialna hodnota.
        //Ziskavame ho z factory metod of (prvky su zname) alebo empty (prvky su nezname)
        Optional<Double> optional = Optional.ofNullable(null);//v pripade ak je parameter null, tak sa vlastne vrati Optional.of(value); pomocou metody Optional.empty()
        try {
            Optional<Double> optional1 = Optional.of(null);//vrati NullPointerException
        }catch(NullPointerException npx){

        }
        Optional<Double> optional2 = Optional.empty();//vrati new Optional<>();

        Optional<Double> doubleOptional1 = Optional.of((double)21/4);
        /*
         * Metoda                    |   Ked Optional je Empty   |   Ked Optional obsahuje hodnotu
         *
         * get()                         vyhodi RuntimeException     vrati hodnotu
         *                               NoSuchElementException
         * ifPresent(Consumer c)         nic nerobi                  vyvola c s hodnotou
         * isPresent()                   vrati false                 vrati true
         * orElse(T other)               vrati other param.          vrati hodnotu
         * orElseGet(Supplier s)         vrati vysledok volania      vrati hodnotu
         *                               Supplier
         * orElseThrow(Supplier s)       vyhodi Exception vytvorenu  vrati hodnotu
         *                               volanim Supplier
         * */
        //vyhodi NullPointerException
        try {
            //--------------------------------------isPresent-----------------------------------------
            //je potrebne kontrolovat ci nieje empty, inac moze vyhodit NoSuchElementException
            if(optional.isPresent()){
                System.out.println("1) optional value:"+optional.get());
            }
            //---------------------------------------------ifPresent------------------------------------
            //potom kod tu hore mozeme napisat bez pouzitia if statment takto:
            optional.ifPresent(d -> System.out.println("2) optional value:"+d));
        }catch (NoSuchElementException nsex){
            System.out.println("Double d = optional.get(); => skoncilo s NoSuchElementException");
        }
        System.out.println("doubleOptional1 ma hodnotu:"+doubleOptional1.get());
        Optional<Double> opt = Optional.empty();
        //-----------------------------------------orElseGet---------------------------------------------
        Double d = opt.orElseGet(Math::random);
        //-----------------------------------------orElse---------------------------------------------
        d = opt.orElse(Double.NaN);
        //-----------------------------------------orElseThrow---------------------------------------------
        opt.orElseThrow(IllegalArgumentException::new);
    }
}
