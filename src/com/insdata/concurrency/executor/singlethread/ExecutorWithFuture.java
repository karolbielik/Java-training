package com.insdata.concurrency.executor.singlethread;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

public class ExecutorWithFuture {
    public static void main(String[] args) {
        //kedze execute() nam nevracia ziadny vysledok o spracovani
        //tak ExecutorService implementuje aj submit ktory vracia Future
        ExecutorService service = Executors.newSingleThreadExecutor();
        Future<String> res=null;
        try {
            //-------------------------------submit---------------------------------------------------
            res = service.submit(() -> {
                //zabrzdime task, aby sme simulovali
                //dlhsie spracovavanie
//                int count = 0;
//                for(int i=0;i<Integer.MAX_VALUE;i++){
//                    if(i==Integer.MAX_VALUE-1){
//                        i=0;
//                        count++;
//                    }
//                    if(count==5)break;
//                }
//  /*1,3*/               Thread.currentThread().sleep(200);
//  /*2*/             Thread.currentThread().sleep(5000);
                for (int i = 0; i < 5; i++) {
                    System.out.println("submit i-cko:" + i);
                }
                //toto sa vypise ak budeme dostatocne dlho cakat na tento thread
                return "Ked nie je timeOutException, tak dobehnem";
            });
             //Hlavny thread pozastavim, aby som dal cas submit(hore) threadu, aby dobehol.
            //Ak je sleep zakomentovane potom sa stihne submit thread hore cancelovat a vyhodi
            //CancellationException
//  /*1*/        Thread.currentThread().sleep(5000);
            //ak submit thread uz dobehol a spravime na nom cancel, tak to nebude mat ziadny efekt
            //aj tak sa bude javit ako canceled=false;
//  /*2*/        res.cancel(true);
            //aj ked je future.isDone=true, mohol task skoncit s exception
            //done je true, ked thread dokonci ci OK alebo s Exception
            System.out.println("{isDone:"+res.isDone()+"}{isCanceled:"+res.isCancelled()+"}");
/*3*/       //ocakavame vysledok do  0,01 sekundy cim dostaneme TimeOutException
            System.out.println(res.get(100, TimeUnit.MILLISECONDS));
        }catch (TimeoutException toex){
            System.out.println(toex.toString());
            System.out.println("isDone:"+res.isDone());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (CancellationException canex){
            canex.printStackTrace();
        } finally {
            service.shutdown();
        }

        Collection<Callable<String>> tasks = Arrays.asList(
                () -> "po jedna",
                () -> "po dva",
                () -> "po tri",
                () -> "po styri"
        );

        //-------------------------------invokeAll---------------------------------------------------
        service = Executors.newSingleThreadExecutor();
        try {
            //spusti sa synchronne, tasky sa spustia asynchronne
            //caka na vsetky tasky zakial sa vykonaju a vrati vysledok
            List<Future<String>> futures = service.invokeAll(tasks);
            for (Future<String> future : futures) {
                try {
                    System.out.println(future.get());
                }catch (ExecutionException exex){
                    exex.printStackTrace();
                }
                catch (RuntimeException rtex){
                    rtex.printStackTrace();
                }
            }

            //-------------------------------invokeAny---------------------------------------------------
            //ak skoncil nejaky z taskov ako prvy, tak ostatne sa terminuju a vrati vysledok
            String resAny = service.invokeAny(tasks);
            System.out.println("Vysledok resAny:"+resAny);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            if(service!=null)service.shutdown();
        }
    }
}
