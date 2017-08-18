package com.insdata.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by karol.bielik on 16.8.2017.
 */
public class TestSynchronization {

    private Integer pocet = new Integer(0);

    private AtomicInteger atomicPocet = new AtomicInteger(0);

    public /*synchronized*/ void pridajASpocitaj(){
//        System.out.print((++pocet)+" ");
        //++ operator nieje atomicky, java concurency api ponuka moznost pouzit synchronizovane
        //primitivne typy, ale toto nam problem nevyriesi, lebo hoci inkrementacia je atomicka ale vypis nie.
//        System.out.print(atomicPocet.incrementAndGet()+" ");
        //preto potrebujeme zatomizovat cely blok
        synchronized (this){
            System.out.print((++pocet)+" ");
        }
    }

    //je mozne synchronizovat aj staticke metody alebo bloky
    private static Integer staticPocet = new Integer(0);
    public static /*synchronized*/ void staticPridajASpocitaj(){
        //preto potrebujeme zatomizovat cely blok, mozem blokovat aj na objekt trieda
        synchronized (TestSynchronization.class){
            System.out.print((++staticPocet)+" ");
        }
    }

    public static void main(String[] args) {
        TestSynchronization ts = new TestSynchronization();
        ExecutorService service = Executors.newCachedThreadPool();
        try {
            for(int i = 0;i<10;i++)
                service.submit(() -> ts.pridajASpocitaj());
        }finally {
            service.shutdown();
        }
    }
}
