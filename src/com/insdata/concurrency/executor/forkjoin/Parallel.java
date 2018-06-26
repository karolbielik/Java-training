package com.insdata.concurrency.executor.forkjoin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Parallel {
    public static void main(String[] args) {

        long timeNow = System.currentTimeMillis();

        Parallel parallel = new Parallel();
        System.out.println(parallel.getFolderSize1(Paths.get("C:\\work")));
        System.out.println("duration:"+(System.currentTimeMillis()-timeNow));

//        System.out.println(parallel.getFolderSize1(Paths.get("C:\\work")));
    }

    public Optional<Long> getFolderSize1(Path folder){

        List<Path> subFolders = new ArrayList<>();
        getSubFoldersDeep(folder, subFolders);

        ArrayList<Long> sizes =
                subFolders.stream().parallel().collect(()->new ArrayList<Long>(),
                        (res, path)->{
                            try {
                                Files.list(path)
                                        .filter(Files::isRegularFile)
                                        .forEach((path1) -> {
                                            try {
                                                res.add(Files.size(path1));
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        });
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        },
                        (res1, res2)->res1.addAll(res2));
        return sizes.stream().parallel().reduce((size1, size2)->size1+size2);

    }

    private void getSubFoldersDeep(Path folder, List<Path> subFolders) {
        if(Files.isDirectory(folder)){
            try {
                Files.list(folder).filter(Files::isDirectory).forEach(fld ->{
                        getSubFoldersDeep(fld, subFolders);
                        subFolders.add(fld);
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private List<Path> getSubFoldersDeep(Path folder) {
        List<Path> subFolders = new ArrayList<>();
        if(Files.isDirectory(folder)){
            try {
                Files.list(folder).filter(Files::isDirectory).forEach(fld ->{
                    subFolders.addAll(getSubFoldersDeep(fld));
                    subFolders.add(fld);
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return subFolders;
    }

    public Optional<Long> getFolderSize2(Path folder){
//        Stream<Path> pathStream = Stream.iterate(folder, )
        return Optional.empty();
    }

}
