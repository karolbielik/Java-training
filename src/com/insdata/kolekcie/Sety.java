package com.insdata.kolekcie;

import java.time.DayOfWeek;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by key on 18.2.2017.
 */
public class Sety {
    //Set nedovoli duplicity elementov a equals metoda rozhoduje o tom ci
    //dva elementy su roznake
    //hodnota elementu moze byt null


    public static void main(String[] args) {
        //----------------HashSet-------------------
        //nesortovany, nezoradeny => pri iteracii sa negarantuje poradie
        //na umiestnovanie prvkov pouziva metodu hashCode() elementu
        System.out.println();
        HashSet hashSet = new HashSet();
        hashSet.add(new Item(1,"jedna"));
        hashSet.add(new Item(2,"dva"));
        hashSet.add(new Item(1,"jedna"));
        System.out.println("Vypis hashSet:"+hashSet.toString());


        //-------------------LinkedHashSet-------------------
        //je zoradena verzia HashSetu, nesortovany => pri iteracii sa garantuje poradie
        System.out.println();
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        linkedHashSet.add(new Item(1,"jedna"));
        linkedHashSet.add(new Item(2,"dva"));
        linkedHashSet.add(new Item(1,"jedna"));
        System.out.println("Vypis linkedHashSet:"+hashSet.toString());

        //-------------------TreeSet-------------------
        //sortovany => implementuje SortedSet
        //sortovany defaultne vzostupne
        System.out.println();
        TreeSet treeSet = new TreeSet();
        treeSet.add(new Integer(3));
        treeSet.add(new Integer(2));
        treeSet.add(new Integer(1));
        System.out.println("Vypis treeSet:"+treeSet.toString());
        //NavigableSet interface implementacia
        //vrati element mensi ako parameter
        System.out.println("lower treeSet:"+treeSet.lower(3));
        //vrati element mensi rovny ako parameter
        System.out.println("floor treeSet:"+treeSet.floor(3));
        //vrati element vacsi ako parameter
        System.out.println("higher treeSet:"+treeSet.higher(2));
        //vrati element vacxi rovny ako parameter
        System.out.println("ceiling treeSet:"+treeSet.ceiling(2));

        System.out.println("first treeSet:"+treeSet.first());
        System.out.println("last treeSet:"+treeSet.last());

        //vrati a vymaze prvy element z kolekcie
        System.out.println("pollFirst treeSet:"+treeSet.pollFirst());
        System.out.println("treeSet after pollFirst:"+treeSet.toString());
        //vrati a vymaze posledny element z kolekcie
        System.out.println("pollLast treeSet:"+treeSet.pollLast());
        System.out.println("treeSet after pollLast:"+treeSet.toString());

        //SortedSet interface implementacia
        treeSet.clear();
        treeSet.add(new Integer(5));
        treeSet.add(new Integer(4));
        treeSet.add(new Integer(3));
        treeSet.add(new Integer(2));
        treeSet.add(new Integer(1));
        //backed(tienove) kolekcie
        //vranci subsetu mozem operovat len nad prvkami vramci ktore patria do jeho rozsahu
        //inak vyhodi exception
        //zmeny na subkolekcii sa prejavia aj na hlavnej kolekcii treeSet
        SortedSet subSet1 = treeSet.headSet(2);//vyberie prvky 1
        System.out.println("subSet1:"+subSet1.toString());
        SortedSet subSet2 = treeSet.subSet(2, 4);//vyberie 2,3
        System.out.println("subSet2:"+subSet2.toString());
        SortedSet subSet3 = treeSet.tailSet(4);//vyberie 4,5
        System.out.println("subSet3:"+subSet3.toString());


        //defaultne spravanie asc zoradenia mozem ovplivnit konstruktorom ktory ma vstup. param.
        //Comparator(funkcne interface), ktory je v nasom pripade anonymnou(triedou) implementaciou interface
        Comparator comp = new Comparator() {
            @Override
            public int compare(Object o, Object t1) {
                return ((Integer)t1).compareTo((Integer)o) ;
            }
        };
        TreeSet treeSetDesc = new TreeSet(comp);
        treeSetDesc.add(new Integer(1));
        treeSetDesc.add(new Integer(2));
        treeSetDesc.add(new Integer(3));
        System.out.println("Vypis treeSetDesc:"+treeSetDesc.toString());

        //to iste viem dosiahnut aj pomocou
        NavigableSet treeSetAsc = treeSetDesc.descendingSet();
        System.out.println("Vypis treeSetAsc:"+treeSetAsc.toString());

        //Specialne implementacie Set
        //-------------------CopyOnWriteArraySet-------------------
        //zarucuje ze sa konkarentne vlakna nad tymto objektom nedostanu do concurency exception stavu
        //vhodna pre sety ktore su casto iterovane ale malokedy modifikovane
        //pri kazdom volani add,set,remove sa vnutorne vytvara nova kopia array
        CopyOnWriteArraySet copyOnWriteArraySet = new CopyOnWriteArraySet();

        //-------------------EnumSet-------------------
        //vysoko vykonny set pre enum
        System.out.println();
        System.out.println("Vypis enumSet range:"+EnumSet.range(DayOfWeek.MONDAY, DayOfWeek.FRIDAY).toString());
        System.out.println("Vypis enumSet:"+EnumSet.allOf(DayOfWeek.class).toString());
        System.out.println("Vypis enumSet:"+EnumSet.of(DayOfWeek.SATURDAY,DayOfWeek.SUNDAY).toString());

        //-------------------ConcurrentSkipListSet----------------------------
        //je konkarentny naprotivok sortovaneho TreeSet
        NavigableSet concurrentNavigableSet = new ConcurrentSkipListSet();

        //-------------------ConcurrentSkipListMap---------------------------------------
        //je konkarentna verzia sortovaneho naprotivejsku TreeMap
        SortedMap<Integer, String> concCisla = new ConcurrentSkipListMap<>();

        //--------------------------------------------Concurrency API -------------------------------------
        Set<Integer> synchronizedSet = Collections.synchronizedSet(treeSet);
        //POZOR na rozdiel od ConcurrentHashMap tato ma synchronizovane len get a set
        //pri iteracii je ju treba explicitne synchronizovat


        //iterator nieje sinchronizovany preto treba pri iteracii synchronizovat
        synchronized (synchronizedSet) {
            for (Integer key : synchronizedSet) {
                //!!!!!!!!!!!!! vyhodi concurrency exception na rozdiel od ConcurrentHashMap
                synchronizedSet.remove(key);
            }
        }
    }
}
