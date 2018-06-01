package com.insdata.concurrency.executor.forkjoin;

import java.util.ArrayList;
import java.util.concurrent.*;

public class ExecutorWorkStealingPool {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        TimeUnit.SECONDS.sleep(3);

        //pridany do Executors od java 8
        //pouzivam ked chcem sparalelizovat viacmenej nezavysle tasky
        ExecutorService service = Executors.newWorkStealingPool(/*2*/);
        try {
            String dataToProcess = "Toto je text, ktory sa spracuje paralelne a zvacsi vsetky pismena.";
            int lenthToProcess = 6;
            int offset = 0;
            ArrayList<CustomCallable> subTasks = new ArrayList<>();
            while(offset < dataToProcess.length()){
                subTasks.add(new CustomCallable(dataToProcess.substring(offset,
                                                                        offset+lenthToProcess>dataToProcess.length()?
                                                                                dataToProcess.length()
                                                                                :offset+lenthToProcess )));
                offset += lenthToProcess;
            }

            for(Future<String> result : service.invokeAll(subTasks)){
                System.out.println(result.get());
            }
        }catch(InterruptedException ie){
            ie.printStackTrace();
        }finally {
            service.shutdown();
        }
    }

    private static class CustomCallable implements Callable<String>{
        String data;

        public CustomCallable(String data) {
            this.data = data;
        }

        @Override
        public String call() throws Exception {
            return "[ThreadId]:{"+Thread.currentThread().getId()+"}"+"Processing data:"+data.toUpperCase();
        }
    }
}
