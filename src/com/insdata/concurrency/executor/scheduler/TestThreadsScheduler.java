package com.insdata.concurrency.executor.scheduler;

import java.util.concurrent.*;

/**
 * Created by karol.bielik on 15.8.2017.
 */
public class TestThreadsScheduler {
    static int i;
    public static void main(String[] args) {
        /*---------------------------------------single-threaded service---------------------------------------------*/
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        //obdobne mozem pouzit multi-threaded executor
        //rozdiel je v tom ze pre kazdy task sa priradi dalsi thread z pool
//        service = Executors.newScheduledThreadPool(5);
        Object res = null;
        String res1 = null;
        try {
            ScheduledFuture sf = service.schedule(() -> System.out.println("Vypisem sa po 5 sekundach cez Runnable"), 5, TimeUnit.SECONDS);

            ScheduledFuture<String> sf1 = service.schedule(() -> {System.out.println("Vypisem sa po 5 sekundach cez Callable"); return "OK";}, 5, TimeUnit.SECONDS);

            //get caka zakial sa nevykona naschedulovany task
            //get v tomto pripade vrati null pretoze pouzivame Runnable interface, ktore ma void navratovu hodnotu
            res = sf.get();
            //get vracia vysledok z naschedulovaneho tasku, teda v nasom pripade hodnotu "OK"
            res1 = sf1.get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println(e.getMessage());
        } finally {
            //aj pri schedulovanom service je dolezite zavolat shutdown, inak nam program neskonci
            service.shutdown();
        }

        service = Executors.newSingleThreadScheduledExecutor();
//        service = Executors.newScheduledThreadPool(5);
        ScheduledFuture sf=null;
        try {
            //tasky sa posielaju do executora kazdych 5 sec, bez ohladu nato ci uz predchadzajuci dobehol alebo nie.
            //ak chcem posielat tasky do executora az potom ako sa predchadzajuci task dokonci tak pouzijem
//            service.scheduleWithFixedDelay(()-> System.out.println("fixed delay"),2, 5, TimeUnit.SECONDS);
            sf = service.scheduleAtFixedRate(() -> {
                i += 1;
                        try {
                            Thread.currentThread().sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Vypisujem sa az kym ma niekto nezastavi:"+i);
            },
            2, 5, TimeUnit.SECONDS);
        }finally {
            //ak zbehne service shutdown tak sa zastavi cely service scheduled
            boolean run = true;
            while(run){
                try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
                if(i>=5){
                    //ak pouzijem cancel tak zrusim task ale service bude stale bezat takze hlavny program mi nezastavi
//                    sf.cancel(true);
                    //aby sa zastavil hlavny program tak musim service.shutdown()
                    //tasky ktore uz bezia sa neukoncia, pocka sa nane zakial dobehnu,
                    // az potom sa zastavi service
                    //ak chcem forsnut zastavenie aj prebiehajucich taskov v service tak pouzijem
                    //ScheduledFuture.cancel(true);
                    sf.cancel(true);
                    service.shutdown();
                    run = false;
                }
            }
        }
    }
}
