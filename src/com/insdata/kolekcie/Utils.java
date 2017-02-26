package com.insdata.kolekcie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by key on 18.2.2017.
 */
public class Utils {

    public static void main(String[] args) {
        ArrayList<Item> arrayList = new ArrayList();
        arrayList.add(new Item(4, "4"));
        arrayList.add(new Item(1, "1"));
        arrayList.add(new Item(2, "2"));
        arrayList.add(new Item(5, "5"));
//        arrayList.add(new Item(3, "3"));

        Comparator<Item> comparator = new Comparator<Item>() {
            @Override
            public int compare(Item item, Item t1) {
                return item.getFirstElement().compareTo(t1.getFirstElement());
            }
        };

//        arrayList.sort(arrayList, );
        Collections.sort(arrayList, comparator);
        System.out.println("sorted arraylist:"+arrayList);

        //vracia 0 alebo pozitivnu hodnotu v pripade, ze sa element nasiel
        //negativnu hodnotu ak sa element nenasiel a znamena insertion point
        //-(insertion point)-1 => je hodnota na ktoru mozem chybajuci element vlozit
        int searchResult = Collections.binarySearch(arrayList, new Item(3,"3"), comparator);
        System.out.println("search result:"+searchResult);

        //prevod kolekcie na array
        Item[] items = new Item[arrayList.size()];
        items = arrayList.toArray(items);

    }
}
