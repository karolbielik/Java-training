package com.insdata.concurrency.executor.forkjoin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ForkJoin {

    /*
    fork join framework bol predstaveny prvy krat v java 7. Poskituje tooly ktore pomahaju zrychlit
    paralelne spracovanie, tym ze sa vyuzije max mozne mnozstvo dostupnych procesorov. Toto je dosiahnute
    devide & conquer pristupom.
    Task je vlastne rozbity do samostatne (asynchronne) spracovatelnych subtaskov. Potom vysledky tychto
    subtaskov su rekurzivne (joined) spojene do jedneho vysledku. Framework na efektivnu pracu pouziva
    ForkJoinPool => implementacia ExecutorService, kt. managuje worker thready.
    */

    public static void main(String[] args) throws InterruptedException {
        TimeUnit.SECONDS.sleep(8);

        //------------------------Recursive Action------------------------------
        //rozdeli string na zadanu dlzku a vykona tieto subtasky bez navratovej hodnoty
        CustomRecursiveAction forkJoinAction = new ForkJoin.CustomRecursiveAction("Ahojte ja som stary dobry processor. Kdo je vac, ked nie ja.");
        System.out.println("data:"+forkJoinAction.getData());

        ForkJoinPool.commonPool().execute(forkJoinAction);
        forkJoinAction.join();

        //------------------------Recursive Task------------------------------
        //pocitanie velkosti obsahu adresara
//
//        CustomRecursiveTask forkRecursiveTask = new ForkJoin.CustomRecursiveTask(Paths.get("C:\\work"));
//        forkRecursiveTask.invoke();
//        System.out.println("Totalna velkost adresara v bytoch:"+forkRecursiveTask.join());

        TimeUnit.SECONDS.sleep(3);
    }

    private static class CustomRecursiveAction extends RecursiveAction{

        //urcuje max dlzku retazca vramci jedneho subtasku
        private static final int limitDlzky = 6;

        private String data;

        public CustomRecursiveAction(String data) {
            this.data = data;
        }

        public String getData(){
            return data;
        }

        @Override
        protected void compute() {
            if(data.length()>limitDlzky){
                processDataParallel(data);
            }else{
                processData(data);
            }
        }

        private void processDataParallel(final String data) {
            List<CustomRecursiveAction> actionList = new ArrayList<>();
            if(data.length()<limitDlzky){
                actionList.add(new CustomRecursiveAction(data));
//                (new CustomRecursiveAction(data)).fork();
            }else{
                int subtasksCount = data.length()/limitDlzky;
                subtasksCount += (data.length()|limitDlzky)>0?1:0;
                //rozdelim data na zaklade limitu dlzky pre jeden task
                for(int i =1; i<= subtasksCount; i++){
                    int beginIndex = (i-1)*limitDlzky;
                    int endIndex = (i*limitDlzky)>data.length()?data.length():i*limitDlzky;
                    actionList.add(new CustomRecursiveAction(data.substring(beginIndex, endIndex)));
//                    (new CustomRecursiveAction(data.substring(beginIndex, endIndex))).fork();
                }
            }
            //paralelne spracujem dane tasky
            ForkJoinTask.invokeAll(actionList);
        }

        private void processData(String data){
            System.out.println("[ThreadId]:{"+Thread.currentThread().getId()+"}"+"Processing data:"+data.toUpperCase());
        }
    }

    private static class CustomRecursiveTask extends RecursiveTask<Long>{

        private final Path path;

        public CustomRecursiveTask(Path folderPath) {
            this.path = folderPath;
        }

        @Override
        protected Long compute() {
            long filesSize = 0;
            ArrayList<CustomRecursiveTask> subTasks = new ArrayList<>();
            try {
                if(Files.isDirectory(path)){
                    Files.list(path)
                            .filter(Files::isDirectory)
                            .forEach(path1 -> {
                                CustomRecursiveTask crt = new CustomRecursiveTask(path1);
                                crt.fork();
                                subTasks.add(crt);
                            });

                    filesSize += subTasks.stream().mapToLong(a-> a.join()).reduce((a,b)-> a + b).orElse(0);

                    filesSize += Files.list(path)
                            .filter(Files::isRegularFile)
                            .mapToLong(file ->{
                                long fileSz = 0;
                                try {
                                    fileSz = Files.size(file);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                return fileSz;
                            })
                            .reduce(Long::sum).orElse(0);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return filesSize;
        }
    }

}
