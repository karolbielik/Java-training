package com.insdata.concurrency;

import org.omg.PortableServer.THREAD_POLICY_ID;

/**
 * Created by karol.bielik on 15.8.2017.
 */
public class TestThreadsOldway {
    public static void main(String[] args) {

        Thread thread1 = new Thread(new RunTask());
        thread1.start();

        RunThreadTask runThreadTask = new RunThreadTask();
        runThreadTask.start();

        Thread thread2 = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Ja som thread:" + Thread.currentThread().getId());
                    }
                }
        );
        thread2.start();

        //Thread static methods -> pracuje s prave beziacim threadom
//        Thread.sleep(1);
//        Thread.yield();
        //Thread instance's methods
//        thread2.join();
//        thread2.setPriority(2);
        //inherited from Object class
//        thread2.wait();
//        thread2.notify();
//        thread2.notifyAll();

        /*Mozne stavy a prechody: New -> Runnable -> Running -> (Sleeping,Waiting, Blocked) -> Runnable -> Running -> Dead*/

        /*----------------------------------------Sleeping stav------------------------------------------------------*/
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("Zacinam thread a pockam minimalne 1 sekundu. Teraz je " + System.currentTimeMillis());
                    //tuna prejde thread do sleeping modu a po sekunde sa moze vrati do runnable modu
                    //a thread scheduler ho moze dat do running stavu, kedy dalej pokracuje vo vykonavani kodu
                    //nieje garantovane, ze thread bude pauzovat 1 sekundu, bude to vsak minimalne 1 sekunda
                    Thread.sleep(1000);
                    System.out.println("Cakal som minimalne 1 sekundu. Teraz je " + System.currentTimeMillis());
                } catch (InterruptedException e) {
                    //spiaci thread moze by preruseny z vonka, tu sa handluje takyto pripad
                    //co sa ma robit ak mi niekto prerusil moj spiaci thread
                    e.printStackTrace();
                }
            }
        });
        thread.start();

        /*----------------------------------------Priority threadu  yield--------------------------------------------*/
        //thread priority su od 1 po 10
        //prave beziaci thread by mal mat rovnu alebo vyssiu prioritou(napr.7) ako thready cakajuce v poole, ak do poolu pride thread
        //s vyssou prioritou (8), tak prebiehajuci thread 7 sa da do stavu runnable a thread 8 do stavu running.
        //!TOTO VSAK NIEJE GARANTOVANE -> pri dizajne aplikacie sa nikdy nespolieham na thread priority

        System.out.println("Mainthread priority:" + Thread.currentThread().getPriority());
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Subthread priority:" + Thread.currentThread().getPriority() + ":ThreadId:" + Thread.currentThread().getId());
//                Thread.currentThread().setPriority(6);
//                System.out.println("Subthread priority po setPriority:"+Thread.currentThread().getPriority());
                //scheduler beziaci thread da do stavu runnable a vyberie z poolu iny thread s rovnakou prioritou
                Thread.yield();
                System.out.println("Po yield threadId:" + Thread.currentThread().getId());
            }
        });

        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Subthread priority:" + Thread.currentThread().getPriority() + ":ThreadId:" + Thread.currentThread().getId());
            }
        });
        //defaultna priority pre subthread je taka ista ako pre main thread ale mozem ju zmenit
        thread.setPriority(6);
        thread3.setPriority(6);
        thread.start();
        thread3.start();

        /*---------------------------------------------join example--------------------------------------------------*/
        Thread threadA = new Thread(new MyRunnable(), "A");
        Thread threadB = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 50; i++) {
                    System.out.println("ThreadId:" + Thread.currentThread().getName() + ":i=" + i);
                    //ked hodnota i dosiahne 25 v threde B, tak nevykonavaj thread B dotedy, zakial nebude dokonceny
                    //thread A
                    if (i == 25) {
                        try {
                            threadA.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "B");
        Thread threadC = new Thread(new MyRunnable(), "C");

        threadA.start();
        threadB.start();

        try {
            //nastartuj thread C az ked je ukonceny thread B
            //inymi slovami, thread main sa joine na koniec thredu B az ked sa tento vykona, tak pokracuje dalej,
            //co v nasom pripade znamena, ze thread C sa spusti az po ukonceni thread B
            threadB.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        threadC.start();

    /*--------------------------------interakcia medzi threadmi pomocou wait, notify, notifyAll-----------------------*/
        //tieto metody musia byt volane zo synchronizovaneho kontextu, lebo thread ktory caka na nejaky objekt beziaci v
        //inom vlakne, musi vlastnit jeho lock

        //pocitadlo bude pocitat a vzdy pri dosiahnuti isteho cisla informuje konzolu, ze dosiahlo isty pocet
        //pocitadlo a konzoly budu bezat kazdy vo vlastnom threade

        Thread pocitadlo = new Thread(new Runnable() {
            @Override
            public void run() {
                int hodnotaPocitadla = 0;
                while (true){
                    //pocitadlo notifikuje konzolu kazdy 10 nasobok
                    if(((++hodnotaPocitadla) % 10) == 0){
                        //tiez musi ziskat lock na seba
                        synchronized (Thread.currentThread()){
                            System.out.println("Dosiahlo som hodnotu:"+hodnotaPocitadla);
                            Thread.currentThread().notify();
                            //v pripade ze by sme mali viac konzol, ktore cakaju na pocitadlo
                            //a chcem aby vsetky zachitili zmenu stavu na pocitadle tak pouzijem notifyAll()
                            //ak pouzijem notify(), tak sa zavola len jedna z konzol cakajucich nieje garantovane ktora
//                            Thread.currentThread().notifyAll();
                        }
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Thread konzola1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    //hned ako sa konzola spusti, tak ziska lock na pocitadlo a nastavi si ze nan caka
                    synchronized (pocitadlo){
                        try {
                            System.out.println("Konzola 1 caka na pocitadlo");
                            pocitadlo.wait();
                        } catch (InterruptedException e) {
                            //tak ako spiaci thread, tak aj cakajuci thread moze by preruseny
                            //tu je miesto ked ohandlujeme co mame robit v pripade, ze nam niekto nas thread prerusil
                            e.printStackTrace();
                        }
                        System.out.println("Konzola 1 hlasi, ze pocitadlo dosiahlo hodnotu delitelnu 10");
                    }
                }
            }
        });
        konzola1.start();
        pocitadlo.start();
    }
}

class MyRunnable implements Runnable{

    @Override
    public void run() {
        for(int i = 0; i< 50; i++){
            System.out.println("ThreadId:"+Thread.currentThread().getName()+":i="+i);
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
