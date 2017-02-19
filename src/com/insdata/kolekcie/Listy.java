package com.insdata.kolekcie;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by key on 18.2.2017.
 */

/*
Kolekcie obsahuju zoznam prvkov ktore su
zoradene, sortovane
zoradene, nesortovane
nezoradene, nesortovane
nikdy nie nezoradene, sortovane
 */

public class Listy {
    //vsetky implementacie Listu akceptuju null ako hodnotu elementu

    public static void main(String[] args) {

        //----------------ArrayList----------------
        //pouziva sa v pripade ked potrebujem robit rychle iterovanie, alebo rundom pristup k prvkom
        //zoznam je zoradeny(pomocou indexu, ma get(index) metodu), nesortovany
        ArrayList arrayList = new ArrayList();
        arrayList.add(new Item(1,"jedna"));
        arrayList.add(new Item(3, "tri"));
        arrayList.add(new Item(4, "styri"));
        arrayList.add(1, new Item(2, "dva"));
        for(Object item : arrayList){
            Item itm = (Item)item;
            System.out.print("nr:"+itm.getFirstElement()+",text:"+itm.getSecondElement());
            System.out.println();
        }
        //kontrola ci prvok je v liste
        //pouziva sa na zistenie ci je prvok rovnaky implementacia metody equal na vlozenych prvkoch
        System.out.println("obsahuje dvua:"+ arrayList.contains(new Item(2, "dvua")));
        //vytiahnutie prvku na zaklade indexu => throws ArrayIndexOutOfBoundsException
        System.out.println(arrayList.get(arrayList.indexOf(new Item(2, "dva"))));

        //ConcurrentModificationException
        //iteracia pomocou iteratora
        ArrayList arrayList1 = new ArrayList(arrayList);
        Iterator it = arrayList1.iterator();
        int i = 0;
        while(it.hasNext()){
            Item item = (Item)it.next();
            if(i==1 || i == 3){
                //toto vyhodi ConcurrentModificationException
//                arrayList.remove(item);
                //mieste toho pouzije na iteratore metodu remove alebo CopyOnWriteArrayList
                it.remove();
            }
            i++;
        }

        //----------------CopyOnWriteArrayList----------------
        //List specialneho urcenia
        //implementovany pomocou copy-on-write array
        //garantovane ze nikdy nevyhodi ConcurrentModificationException
        CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList(arrayList);
        i = 0;
        Iterator it2 = copyOnWriteArrayList.iterator();
        while(it2.hasNext()){
            Item item = (Item)it2.next();
            if(i==1 || i == 3){
                //toto vyhodi ConcurrentModificationException
                copyOnWriteArrayList.remove(item);
            }
            i++;
        }

        System.out.println("Velkost listu arrayList1:"+arrayList1.size());
        System.out.println("arrayList1 je prazdny"+arrayList1.isEmpty());

        //pretoze nepouzivame generics tak musime castovat
        Object[] itemsArray = arrayList1.toArray();
        System.out.print("Vypis pola:");
        System.out.print(Arrays.toString(itemsArray));


        //----------------Vector----------------
        //najstarsi z impl. listu, rovnaky ako ArrayList ale je synchronizovany
        //nepouzivat kvoli overhead synchronizacii
        Vector vector = new Vector();

        //----------------LinkedList---------------------
        //pouziva sa ked potrebujem robit velke mnozstvo insertov a deletov
        //prvky su zoradene index poziciou, prvok je zlinkovany s prvkom pred a za
        //vhodny pre implementaciu stack alebo queue
        System.out.println();
        LinkedList linkedList = new LinkedList();
        //prida nakoniec
        linkedList.add(new Item(2, "dva"));
        //prida nakoniec => vnutorne vola add funkciu
        linkedList.offer(new Item(1, "jedna"));
        //prida nakoniec
        linkedList.offerLast(new Item(3, "tri"));
        //prida nazaciatok
        linkedList.offerFirst(new Item(0, "nula"));
        System.out.println("Vypis liked listu:"+linkedList.toString());
        //vrati prvy element(ak neexistuje tak null) ale nevymaze ho
        linkedList.peek();
//        linkedList.peekFirst();
//        linkedList.peekLast();
        //to iste ako peek ale vyhodi NoSuchElementException ak element neexistuje
        linkedList.element();
        //vrati prvy element a vymaze ho
        linkedList.poll();
        //vlozi na prve miesto v liste
        linkedList.push(new Item(4, "styri"));
        //vrati prvy element a vymaze ho
        linkedList.pop();

        linkedList.listIterator();
        Iterator descIt = linkedList.descendingIterator();
        while (descIt.hasNext()){
            System.out.print(descIt.next().toString()+",");
        }

    }
}
