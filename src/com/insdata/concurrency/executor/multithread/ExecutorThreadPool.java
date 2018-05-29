package com.insdata.concurrency.executor.multithread;

import java.sql.Time;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ExecutorThreadPool {

    public static void main(String[] args) {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Thread Pool je skupina pred vytvorenych vlakien, ktore je mozne znova pouzit pre vykonanie taskov.
        //---------------------------------cached thread pool -----------------------------------------
        //"neobmedzeny" pocet threadov, pouzivam ak mam k spracovaniu vela taskov s kratkou zivotnostou
        ExecutorService service = Executors.newCachedThreadPool();
        //---------------------------------fixed thread pool -----------------------------------------
        //parameter predstavuje velkost pool teda threadov vytvorenych
//        ExecutorService service = Executors.newFixedThreadPool(2);

        //Mnozstvo threadov urcujeme podla mnozstva CPU a podla povahy tasku.
        //Ak mam task, ktory zatazuje procesor viac, tak vytvaram menej threadov a ak mam task, ktory je viac zavisly
        //na externych resoursoch(DB, internet)tak mozem pouzit viac threadov

        //1 core(fyzicky procesor) moze vykonavat simultanne dva procesy, teda ma 2 logicke procesory.
        //Toto sa dosiahne pomocou Hyper Threading technologie(od Pentium 4 rok 2002)
        //=> Duplikaciou registovej casti procesora(virtualny procesor). Potom sa aplikacii javi, akoby boli 2 procesory.
        //Druhy virtualny procesor moze vyuzivat iba tie prostriedky skutocneho procesora,
        // ktore nevyuziva prvy virtualny procesor.
        //Kolko logickych procesorov(nie core processor) je k dispozicii mozme zistit nasledovne
        System.out.println("Pocet dostupnych logickych procesorov:"+ Runtime.getRuntime().availableProcessors());
        try {
            Future<String> future1 = service.submit(
                    () -> {
                        simulateProcessing(5);
                        System.out.println("{ThreadName:"+Thread.currentThread().getName()+"}{1}"); return "po jedna";
                    }
            );
            Future<String> future2 = service.submit(
                    () -> {
                        simulateProcessing(5);
                    System.out.println("{ThreadName:"+Thread.currentThread().getName()+"}{2}"); return "po dva";
                    }
            );
            Future<String> future3 = service.submit(
                    () -> {
                        simulateProcessing(5);
                        System.out.println("{ThreadName:"+Thread.currentThread().getName()+"}{3}"); return "po tri";
                    }
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
                //blokuje po dobu zadaneho casu(5), alebo ked vsetky thready skoncia skor ako tento cas
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

    private static void simulateProcessing(int factor) {
        int toRun = 0;
        for(int i = 0;i<Integer.MAX_VALUE;i++){
            if(i==Integer.MAX_VALUE-1){
                if(factor==toRun){
                    return;
                }
                i = 0;
                toRun++;
            }
        }
    }
}
