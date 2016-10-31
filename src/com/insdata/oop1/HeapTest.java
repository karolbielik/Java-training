package com.insdata.oop1;

import java.util.ArrayList;

/**
 * Created by karol.bielik on 31. 10. 2016.
 */

    //na command line:
    //cd C:\Program Files\Java\jdk1.8.0_73\bin
    //javac C:\work\Trainings\Java-training\Java-training\src\com\insdata\oop1\*
    //java -cp C:\work\Trainings\Java-training\Java-training\src com.insdata.oop1.HeapTest

    //v druhom okne spustim jconsole => C:\Program Files\Java\jdk1.8.0_73\bin\jconsole

public class HeapTest {
    public static void main(String[] args) {
        ArrayList<Lampa> arr = new ArrayList<>();
        while(true){
            arr.add(new Lampa());
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
