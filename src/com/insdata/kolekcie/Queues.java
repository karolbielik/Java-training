package com.insdata.kolekcie;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Created by key on 18.2.2017.
 */
public class Queues {
    //elementy su zoradene na zaklade priority
    public static void main(String[] args) {
        PriorityQueue priorityQueue = new PriorityQueue();
        int[] prvkyArr = new int[]{5,4,3,1,2,6,7,9,8};

        //iterator negarantuje zoradenie
        for(Integer prvok : prvkyArr){
            priorityQueue.offer(prvok);
        }
        System.out.println("Vypis priorityQueue"+priorityQueue.toString());

        PriorityQueue priorityQueue1 = new PriorityQueue(new Comparator() {
            @Override
            public int compare(Object o, Object t1) {
                return ((Integer)t1).compareTo(((Integer)o));
//                return ((Integer)t1) - ((Integer)o);
            }
        });

        //iterator negarantuje zoradenie
        for(Integer prvok : prvkyArr){
            priorityQueue1.offer(prvok);
        }
        System.out.println("Vypis priorityQueue1"+priorityQueue1.toString());

        //poll a peek garantuju ze vrati prvok s najvissiou prioritou
        //podla zoradenia
        System.out.print("Vypis priorityQueue pomocou poll:");
        while (priorityQueue.size()>0){
            System.out.print(priorityQueue.poll()+" ");
        }

        System.out.println();

        System.out.print("Vypis priorityQueue1 pomocou poll:");
        while (priorityQueue1.size()>0){
            System.out.print(priorityQueue1.poll()+" ");
        }
        System.out.println();
    }
}
