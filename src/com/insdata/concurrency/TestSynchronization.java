package com.insdata.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by karol.bielik on 16.8.2017.
 */
public class TestSynchronization {

    Integer pocet = new Integer(0);

    private final AtomicInteger atomicPocet = new AtomicInteger(0);

    public /*synchronized*/ void pridajASpocitaj(){
//        System.out.print((++pocet)+" ");
        //++ operator nieje atomicky, java concurency api ponuka moznost pouzit synchronizovane
        //primitivne typy, ale toto nam problem nevyriesi, lebo hoci inkrementacia je atomicka ale vypis nie.
        //preto potrebujeme zatomizovat cely blok
        synchronized (this){
            System.out.println("[ThreadId]"+Thread.currentThread().getId()+" -> "+ (++pocet)+" ");
        }
    }

    ReentrantLock reentrantLock = new ReentrantLock();
    public void pridajASpocitajReentrantLock(){
        //zalokuje if clock(ak uspesne tak vrati true) a po uspesnej inkrementacii sa blok odlokuje
        //existuje aj variant timeout, kedy sa caka na uvolnenie loku po nastavenu dobu
//        reentrantLock.tryLock(1, TimeUnit.SECONDS); => caka na uvolnenie loku max 1 sekundu
        if(reentrantLock.tryLock()){
            System.out.println("[ThreadId]"+Thread.currentThread().getId()+" -> Successful update "+ (++pocet)+" ");
            reentrantLock.unlock();
        }else {
            System.out.println("[ThreadId]"+Thread.currentThread().getId()+" -> Unsuccessful update ");
        }
    }

    public void pridajASpocitajSAtomicInteger(){
        int currentValue = atomicPocet.get();
        int newValue = currentValue + 1;//++currentValue; nefunguje dobre
        //v tomto pripade sa hodnota updatuje v pripade, ze nieje blokovana inym vlaknom a vrati true
        //ak blokovana je, tak vrati false.
        //oproti synchronizacii ma hlavny rozdiel v tom, ze sa ostatne concurrentne vlakna neblokuju
        //teda implementujem pre taky usecase, kde nemusim zabezpecit nevyhnutne, ze kazde vlakno updatuje hodnotu
        boolean ret = atomicPocet.compareAndSet(currentValue, newValue);
        //pomocou getAndAdd mozem zabezpecit opakovanie operacie compareAndSet zakial nebude incrementacia uspesna
        atomicPocet.getAndAdd(3);
        //kontrolujem ci bola atomic (CAS-compare and swap) operacia uspesna
        if(ret){
            System.out.println("[ThreadId]"+Thread.currentThread().getId()+" -> Successfully set value:"+ (newValue)+" ");
        }else{
            System.out.println("[ThreadId]"+Thread.currentThread().getId()+" -> Unsuccessfully set value:"+ (newValue)+" ");
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
        //---------------------------synchronized block-----------------------------------
        //uplne zosynchronizovana premenna
        try {
            for(int i = 0;i<10;i++)
                service.submit(() -> ts.pridajASpocitaj());
        }finally {
            service.shutdown();
        }

        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //------------------------------atomic integer---------------------------------
        //nieje uplne zosynchronizovana, obcas sa moze stat ze update atomicInteger
        //nebude vzdy uspesny, ale je to efektivnejsie ako reentrant lock, lebo sa jedna
        //o menej narocnu operaciu lokovania ako v pripade reentrant lock. Vacsina threadov
        //sa preto dostane k neblokovanej premennej
        try {
            service = Executors.newCachedThreadPool();
            for(int i = 0;i<10;i++)
                service.submit(() -> ts.pridajASpocitajSAtomicInteger());
        }finally {
            service.shutdown();
        }
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //------------------------------reentrant lock---------------------------------
        //najnakladnejsia operacia lokovania, preto sa pri rychlom spracovani thready nedostanu
        //k zalokovanemu bloku
        try {
            service = Executors.newCachedThreadPool();
            ts.pocet = 0;
            for(int i = 0;i<10;i++)
                service.submit(() -> ts.pridajASpocitajReentrantLock());
        }finally {
            service.shutdown();
        }
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
