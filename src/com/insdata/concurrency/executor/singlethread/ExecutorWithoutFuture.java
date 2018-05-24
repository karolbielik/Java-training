package com.insdata.concurrency.executor.singlethread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorWithoutFuture {
    public static void main(String[] args) {
        //poradie vykonavania singlethreadexecutor je garantovane tak, ako sa posiela do service vlakna
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
    }
}
