package com.insdata.flow_control;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by key on 21.1.2017.
 */
public class WhileDoForLoopStatement {
    public static void main(String[] args) {
        //WHILE cyklus
        int i = 4;
        //premenna musi byt deklarovana pred tym ako je vyraz vyhodnoteny
        //while(boolean b = true){} nieje povolene
        while(i>0){
            System.out.println("vypisujem while:"+i--);
        }

        //DO WHILE cyklus
        i = 4;
        //prikaz vo vnutri vyrazu sa vykona najmenej 1 krat, az potom sa vyhodnocuje
        do{
            System.out.println("vypisujem do:"+i--);
        }while(i>0);

        //FOR cyklus
        //pouziva sa v pripade ze viem kolko krat sa ma cyklus zopakovat

        for(/*inicializacia*/int j = 0;/*podmienka*/j<4;/*iteracia*/j++){
            //j je viditelna len vo vnutri bloku
            System.out.println("vypisujem for:"+j);
        }
        //viditelnost j konci v bloku for
        //j=5  => mimo scope

        //INICIALIZACIA FOR
        //mozem inicializovat aj viac premmenych toho isteho typu
        //inicializacia prebieha len raz pred zacatim cyklu
        for(int i1=0, j1=0;i1<4;i1++){
            System.out.println("vypisujem i1 for:"+i1);
            System.out.println("vypisujem j1 for:"+j1++);
        }

        //PODMIENKA
        //moze byt len jedna aj ked nieje problem aby bola komplexna
        //prebieha pred vykonanim tela cyklu
        for(int i1=0, j1=4;i1<4 && j1>0;i1++){
            System.out.println("vypisujem i1 complex podmienka for:"+i1);
            System.out.println("vypisujem j1 complex podmienka for:"+j1--);
        }

        //ITERACIA
        //prebieha po vykonani tela cyklu
        for(int i1=0, j1=4;i1<4 && j1>0;i1++, j1--){
            System.out.println("vypisujem i1 iteracia for:"+i1);
            System.out.println("vypisujem j1 iteracia for:"+j1);
        }

        for(int i1 = 4;i1>0;System.out.println("vypisujem z iteracie:"+i1)){
            i1--;
        }

        //vynutene prerusenie cyklu sposobuje, ze sa iteracia nevykona
        //prerusene pomocou break, return, System.exit(), alebo vyhodena vynimka
        int i2;
        for(i2=4;i2>0;i2--){
            System.out.println("vypis a vypadni!");
            break;
        }
        System.out.println("i2 hodnota po preruseni cyklu:"+i2);

        //nekonecne cykly
//        for(;;){
//            System.out.println("som nekonecny cyklus 1");
//        }

//        for(;true;){
//            System.out.println("som nekonecny cyklus 2");
//        }

        //ekvivalent while
        int i3 = 4;
        for(;i3>0;){
            System.out.println("vypis z ekvivalent while:"+i3--);
        }

        //FOR EACH
        //mozme robit nad poliami alebo listami
        Integer[] arr = {1,2,3,4,5,6,7,8,9,10};
        for(Integer cislo : arr){
            if(cislo%2==0){
                System.out.println("parne cislo z pola:"+cislo);
            }
        }

        List<Integer> arrayList = Arrays.asList(arr);
        for(Integer cislo : arrayList){
            if(cislo%2==0){
                System.out.println("parne cislo z listu:"+cislo);
            }
        }

        //break a continue v cykle
        //break => prerusi najvnutornejsi cyklus a pokracuje kodom bezprostredne za jeho telom;
        for(int i1=0;i1<4;i1++){
            for(int j1=0;j1<4;j1++){
                System.out.println("Vypisem sa 4 krat a nie (4x4 krat) lebo je vo mne break!");
                break;
            }
            System.out.println("Vypisem sa 4 krat, tak ako mam.");
        }

        //continue prerusi danu iteraciu cyklu a pokracuje v dalsej iteracii toho isteho cyklu
        for(int i1=0;i1<4;i1++){
            for(int j1=0;j1<4;j1++){
                if(j1%2==0)
                System.out.println("Vypisem iba parne cisla!:"+j1);
            }
            System.out.println("Vypisem sa 4 krat.");
        }

        System.out.println();
        //cykly s navestim
        //navestie musi splnit pravidla pre pomenovanie premennych
        //nemoze sa zacinat cislom, specialnym znakom ...
        vonkajsi:
        for(int i1=0;i1<4;i1++){
            System.out.println("Vypisem sa 4 krat.:"+i1);
            for(int j1=0;j1<4;j1++){
                if(j1 > 1) {
                    continue vonkajsi;
                }
                System.out.println("Vypisem sa dvakrat!:" + j1);
            }
            System.out.println("Nevypisem sa!.");
        }

    }
}
