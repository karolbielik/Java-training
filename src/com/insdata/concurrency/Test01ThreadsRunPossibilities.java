package com.insdata.concurrency;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by karol.bielik on 31.7.2017.
 */
public class Test01ThreadsRunPossibilities {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //----------------------------jeden sposob ako spustit thread -viac pouzivany-------------------------------
        Thread thread1 = new Thread(new RunTask());
//        deamon = false by default a mozne nastavit len pred nastartovanim threadu
//        thread1.setDaemon(true);
        thread1.start();
        //-----------------druhy sposob ako spustit thread - ak vytvaram svoj iny priority based thread-------------
        CustomThread runThreadTask = new CustomThread();
        runThreadTask.start();
        //---------------------------treti sposob ako spustit thread------------------------------------------------
        //-----------pomocou executora-----------------
        ExecutorService service = Executors.newSingleThreadExecutor();
        try {
            service.execute(() -> System.out.println("Ja bezim vzdy prvy, lebo sa spustam prvy"));
            service.execute(() -> {
                System.out.println("Ja bezim vzdy druhy lebo sa spustam druhy");
                for (int i = 0; i < 3; i++) {
                    System.out.println("Hodnota i:" + i);
                }
            });
            System.out.println("Koniec vypisu i-cka");
        }finally {
            if(service!=null)service.shutdown();
        }
    }
}

class RunTask implements Runnable{
    @Override
    public void run() {
        System.out.println("Ja som thread:"+Thread.currentThread().getId());
    }
}

class CustomThread extends Thread{

    public CustomThread() {
        this.setPriority(Thread.MAX_PRIORITY);
        this.setName(this.getClass().getSimpleName());
        this.setDaemon(true);
    }

    @Override
    public void run(){
        System.out.println("Ja som thread:"+Thread.currentThread().getId());
    }
}