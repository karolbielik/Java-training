package com.insdata.concurrency;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by karol.bielik on 31.7.2017.
 */
public class TestThreads {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //----------------------------jeden sposob ako spustit thread -viac pouzivany-------------------------------
        Thread thread1 = new Thread(new RunTask());
        thread1.start();
        //-----------------druhy sposob ako spustit thread - ak vytvaram svoj iny priority based thread-------------
        RunThreadTask runThreadTask = new RunThreadTask();
        runThreadTask.start();
        //---------------------------treti sposob ako spustit thread------------------------------------------------
        //poradie vykonavania ce singlethreadexecutor je garantovane tak ako sa posiela do service vlakna
        ExecutorService service = Executors.newSingleThreadExecutor();
        try {
            //najprv sa dokonci toto
            service.execute(() -> System.out.println("Vypis i-cko"));
            //a potom sa zacne vykonavat toto
            service.execute(() -> {
                for (int i = 0; i < 3; i++) {
                    System.out.println("Hodnota i:" + i);
                }
            });
            //obidve service.execute mozu byt prerusene main vlaknom
            //toto moze bezat skor ako dobehne service vlakno nad tymto textom, lebo sa toto vypisuje v main vlakne
            //a nie v service vlakne
            System.out.println("Koniec vypisu i-cka");
        }finally {
            //je nevyhnutne service zastavit, lebo sa vytvara non-deamon thread a
            // preto nasa aplikacia je blokovana a nezastavi.
            //ak este bezia nejaky z taskov hore tak isShutdown = true a isTerminated = false
            //shutdown ale casa na tasky ktore boli poslane do executora, aby sa dokoncili
            if(service!=null)service.shutdown();
            //ak chcem tasky ukoncit hned a nechcem aby shutdown caka tak pouzijem
            //tento sa pokusi zastavit vsetky beziace tasky a tie ktore ma ale nebezia tak diskartuje
            //vracia List<Runnable> tasky ktore boli poslane do executora ale neboli nikdy nastartovane
//            service.shutdownNow();
        }
        //kedze execute() nam nevracia ziadny vysledok o spracovani
        //tak ExecutorService implementuje aj submit ktory vracia Future
        service = Executors.newSingleThreadExecutor();
        try {
            Future<String> res = service.submit(() -> {
                //zabrzdime task o pol sekundy
                Thread.currentThread().sleep(500);
                for (int i = 0; i < 5; i++) {
                    System.out.println("po pici i-cko:" + i);
                }
                return "po pici to dobehlo";
            });
            //ocakavame vysledok do  0,1 sekundy cim dostaneme TimeOutException
            System.out.println(res.get(100, TimeUnit.MILLISECONDS));
        }catch (TimeoutException toex){
            System.out.println(toex.toString());
        }
        finally {
            service.shutdown();
        }

        Collection<Callable<String>> tasks = Arrays.asList(
                () -> "po jedna",
                () -> "po dva",
                () -> "po tri",
                () -> "po styri"
        );

        service = Executors.newSingleThreadExecutor();
        try {

            //spusti sa synchronne, tasky sa spustia asynchronne
            //caka na vsetky tasky zakial sa vykonaju a vrati vysledok
            List<Future<String>> futures = service.invokeAll(tasks);
            for (Future<String> future : futures) {
                System.out.println(future.get());
            }

            //ak prvy task nejaky skoncil tak ostatne terminuje a vrati vysledok
            String resAny = service.invokeAny(tasks);
            System.out.println("Vysledok resAny"+resAny);
        }
        finally {
            if(service!=null)service.shutdown();
        }


        service = Executors.newSingleThreadExecutor();
        //mozem pouzit aj multy-threaded executor
//        service = Executors.newCachedThreadPool();
        //alebo kde parameter predstavuje velkost pool teda threadov vytvorenych
//        service = Executors.newFixedThreadPool(10);
        //velkost threadov urcujeme podla mnozstva CPU a podla povahy tasku
        //ak mam task ktory zatazuje procesor viac, tak vytvaram menej threadov a ak mam task ktory je viac zavisli
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

            //ak nepotrebujem mat vysledok zo servicu pre kazdy Future
            // namiesto toho aby som cakal na kazdy vysledok v cykle tak
            //mozem pouzit na service awaitTermination vid. dole
//            for (Future<String> future : futures) {
//                System.out.println(future.get());
//            }
        }
        finally {
            if(service!=null)service.shutdown();
        }
        if(service != null){
            service.awaitTermination(5, TimeUnit.SECONDS);
            if(service.isTerminated()){
                System.out.println("Vsetky tasky su ukoncene");
            }else{
                System.out.println("Minimalne jeden task este bezi");
            }
        }

    }
}

class RunTask implements Runnable{
    @Override
    public void run() {
        System.out.println("Ja som thread:"+Thread.currentThread().getId());
    }
}

class RunThreadTask extends Thread{
    @Override
    public void run(){
        System.out.println("Ja som thread:"+Thread.currentThread().getId());
    }
}