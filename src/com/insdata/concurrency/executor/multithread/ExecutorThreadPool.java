package com.insdata.concurrency.executor.multithread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ExecutorThreadPool {

    public static void main(String[] args) {
        //mozem pouzit aj multi-threaded executor
        //---------------------------------cached thread pool -----------------------------------------
        ExecutorService service = Executors.newCachedThreadPool();
        //---------------------------------fixed thread pool -----------------------------------------
        //alebo kde parameter predstavuje velkost pool teda threadov vytvorenych
        //service = Executors.newFixedThreadPool(10);
        //mnozstvo threadov urcujeme podla mnozstva CPU a podla povahy tasku
        //ak mam task, ktory zatazuje procesor viac, tak vytvaram menej threadov a ak mam task ktory je viac zavisly
        //na externych resoursoch(DB, internet)tak mozem pouzit viac threadov
        System.out.println("Pocet dostupnych procesorov:"+ Runtime.getRuntime().availableProcessors());
        try {
            Future<String> future1 = service.submit(
                    () -> { Thread.currentThread().sleep(5000); System.out.println("ThreadId:"+Thread.currentThread().getId()); return "po jedna";}
            );
            Future<String> future2 = service.submit(
                    () -> { Thread.currentThread().sleep(5000); System.out.println("ThreadId:"+Thread.currentThread().getId()); return "po dva";}
            );
            Future<String> future3 = service.submit(
                    () -> { Thread.currentThread().sleep(5000); System.out.println("ThreadId:"+Thread.currentThread().getId()); return "po tri";}
            );

            //ak nepotrebujem mat vysledok z Future pre ziadny task,
            //namiesto toho, aby som cakal na kazdy vysledok v cykle,
            //mozem pouzit na service awaitTermination vid. dole.
            //Musim zavolat najskor service.shutdown() a potom pouzit awaitTermination.
//            for (Future<String> future : futures) {
//                System.out.println(future.get());
//            }
        }
        finally {
            if(service!=null)service.shutdown();
        }
        if(service != null){
            try {
                //pockam po dobu predpokladaneho casu ukoncenia
                service.awaitTermination(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //skontrolujem ci su vsetky tasky ukoncene
            if(service.isTerminated()){
                System.out.println("Vsetky tasky su ukoncene");
            }else{
                System.out.println("Minimalne jeden task este bezi");
            }
        }
    }
}
