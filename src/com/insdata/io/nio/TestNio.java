package com.insdata.io.nio;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Date;

/**
 * Created by karol.bielik on 18.9.2017.
 *
 * Java SE 4+ pridali NIO co znamena non-blocking IO
 *  ktore predstavilo koncept buffer a channel namiesto java.io.streams.
 *  Data sa loaduju z file channel do docasneho bufferu, na rozdiel od file streamu,
 *  data mozu byt citane vpred a vzad bez blokovania resource-u.
 *
 *  Dalej sa pridala do javy Charset trieda pre de/enkodovanie bytov na/z stringy.
 *
 */
public class TestNio {

    static String directoryPath = System.getProperty("user.dir")
            + File.separator
            +"resources"
            + File.separator
            +"nio"
            +File.separator;

    public static void main(String[] args) {
        String fileName = "testFile.txt";

        readBlocking(fileName);

        //FileChannel
        readNonBlocking(fileName);

    }

    private static void readNonBlocking(String fileName) {
        try (RandomAccessFile raf = new RandomAccessFile(directoryPath + fileName, "rw")) {
            //v java 4 bolo tomuto pridana metoda getChannel
            FileChannel channel = raf.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(536870912);
            long startTime = System.currentTimeMillis();
            channel.read(buffer);
            long endTime = (new Date()).getTime();
            System.out.println("Duration time non-blocking:"+(endTime-startTime));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readBlocking(String fileName) {
        try (RandomAccessFile raf = new RandomAccessFile(directoryPath + fileName, "rw")) {
            byte[] dataRead = new byte[536870912];
            //read alebo write operacio I/O je blokujuca toto vlakno zakial sa data zo suboru nenacitaju
            long startTime = System.currentTimeMillis();
//            System.out.println("Start time blocking:"+startTime);

            while (raf.read(dataRead) != -1) {
                long endTime = (new Date()).getTime();
                System.out.println("Duration time blocking:"+(endTime-startTime));
                startTime = endTime;
//                System.out.println(Charset.defaultCharset().decode(ByteBuffer.wrap(dataRead)));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
