package com.insdata.niotwo;

import java.io.File;
import java.nio.file.Path;

/**
 * Created by karol.bielik on 12.9.2017.
 */
public class TestNio {

    public static void main(String[] args) {
        //legacy kod
        File file = new File("C:/work/Trainings/InsData/Java/test.txt");

        //nove NIO.2
        Path path = file.toPath();

        //je moznost aj z NIO2 na NIO
        file = path.toFile();


    }



}
