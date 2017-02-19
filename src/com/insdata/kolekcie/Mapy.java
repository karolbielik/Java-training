package com.insdata.kolekcie;

import java.time.DayOfWeek;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by key on 18.2.2017.
 */
public class Mapy {
    //jedna sa o kluc/hodnota par
    //kluc aj hodnota su typu Object a musi override equals a hashCode funkcie
    //na zaklade kluca sa vrati hodnota
    //o tom ci dva kluce su rovnake rozhoduje metoda equals
    //Mapa neimplementuje Collection ale Map interface


    public static void main(String[] args) {

        //--------------------HashMap------------------
        //nezoradena, nesortovana
        //ked mi nezalezi na poradi klucov tak toto je naj volba
        //povoluje null kluc a viacnasobnych null hodnot
        HashMap hashMap = new HashMap();
        //kluc moze byt aj Enum, vyhoda je ze enum defaultne implementuje equal a hashCode
        hashMap.put(DayOfWeek.MONDAY, "PONDELOK");
        hashMap.put(DayOfWeek.TUESDAY, "UTOROK");
        hashMap.put(DayOfWeek.WEDNESDAY, "STREDA");
        hashMap.put(DayOfWeek.THURSDAY, "STVRTOK");
        hashMap.put(DayOfWeek.FRIDAY, "PIATOK");
        System.out.println("Vypis FRIDAY:"+hashMap.get(DayOfWeek.FRIDAY));

        //kluc implementujuci equals a hashCode
        HashMap hashMap1 = new HashMap();
        hashMap1.put(new Item(1, "1"), "jedna");
        hashMap1.put(new Item(2, "2"), "dva");
        hashMap1.put(new Item(3, "3"), "tri");
        hashMap1.put(new Item(4, "4"), "styri");
        System.out.println("Vypis hashMap1 tri:"+hashMap1.get(new Item(3,"3")));

        //kluc neimplementujuci equals a hashCode
        HashMap hashMap2 = new HashMap();
        hashMap2.put(new ItemHoreBez(1, "1"), "jedna");
        hashMap2.put(new ItemHoreBez(2, "2"), "dva");
        hashMap2.put(new ItemHoreBez(3, "3"), "tri");
        hashMap2.put(new ItemHoreBez(4, "4"), "styri");
        System.out.println("Vypis hashMap2 tri:"+hashMap2.get(new ItemHoreBez(3,"3")));

        //--------------------Hashtable------------------
        //ide o staru java metodu
        //je synchronizovana, metody kluca su synchronizovane
        //nedovoluje mat akekolvek null ani kluca ani hodnoty
        Hashtable hashtable = new Hashtable();

        //--------------------LinkedHashMap------------------
        //je zoradena -> kluce sa ponechavaju v poradi ako boli vkladane
        //pomalsia ako HashMapa co sa tyka vkladania a vymazavania
        //vhodna pre rychlu iteraciu
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put(1, "jedna");
        linkedHashMap.put(2, "dva");
        linkedHashMap.put(3, "tri");
        linkedHashMap.put(4, "styri");

        System.out.println("Vypis kluce z mapy:"+linkedHashMap.keySet().toString());
        System.out.println("Vypis hodnoty z mapy"+linkedHashMap.values().toString());
        //iteracia nad mapov
        for(Object entry : linkedHashMap.entrySet()){
            Map.Entry entry1 = (Map.Entry)entry;
            System.out.println("linkedHashMap key:"+entry1.getKey()+" - value:"+entry1.getValue());
        }


        //--------------------TreeMap------------------
        //sortovana implementuje SortedMap
        TreeMap treeMap = new TreeMap();
        treeMap.put(1, "jedna");
        treeMap.put(2, "dva");
        treeMap.put(3, "tri");
        treeMap.put(4, "styri");

//        treeMap.higherEntry();
//        treeMap.higherKey();
//        treeMap.ceilingEntry();
//        treeMap.ceilingKey();
//        treeMap.lowerEntry();
//        treeMap.lowerKey();

//        treeMap.firstEntry();
//        treeMap.firstKey();

//        treeMap.pollFirstEntry();
//        treeMap.pollLastEntry();

        //backed kolekcia
        //plati to iste ako pre SortedSet
//        SortedMap subMap1 = treeMap.headMap();
//        SortedMap subMap2 = treeMap.subMap();
//        SortedMap subMap3 = treeMap.tailMap();

        //mapy specialneho urcenia

        //------------------EnumMap-------------------
        //------------------WeakHashMap---------------
        //------------------IdentityHashMap-----------

        //concurentne mapy
        //------------------ConcurrentHashMap---------
    }
}
