package com.insdata.generika;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by key on 26.2.2017.
 */
public class Trieda<E,T extends Number> {

    public static void main(String[] args) {
        ArrayList<String> arr = new ArrayList<>();
        arr.add("Hello");
        Trieda<String, Integer> cls = new Trieda<>(arr, 2);

        String hello = cls.dajPrvyPrvok();
        System.out.println(hello);
        Integer cislo = cls.dajCislo();
        System.out.println(cislo);
    }

    //genericky mozebyt
    List<E> kolekcia;
    T cislo;

    public Trieda(List<E> arr, T number) {
        this.kolekcia = arr;
        this.cislo = number;
    }

    public E dajPrvyPrvok(){
        if(kolekcia!=null && kolekcia.size()>0){
            return kolekcia.get(0);
        }
        return null;
    }

    public T dajCislo(){
        return cislo;
    }
}
