package com.insdata.io.nio;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
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
        readNonBlocking(fileName);

        //mapovanie casti suboru na buffer
        mappedBufferExample();

    }

    //NIO presunulo operacie ako naplnanie buffera na uroven operacneho systemu, co zrychlilo pristup k suborom
    private static void readNonBlocking(String fileName) {
        try (RandomAccessFile raf = new RandomAccessFile(directoryPath + fileName, "r")) {
            //v java 4 bolo tomuto pridana metoda getChannel
            FileChannel channel = raf.getChannel();
//            ByteBuffer buffer = ByteBuffer.allocate(536870912);
            //v pripade, ze pracujem s velkymi subormi tak alokujem konstantnu velkost aby som nesportreboval pamat
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            //v pripade malych suborov mozem nacita data naraz => POZOR ak by som natrafil na velky subor tak, nasledujuci
            //riadok moze vyhodit OutOfMemoryException
//            ByteBuffer buffer = ByteBuffer.allocate(channel.size()>Integer.MAX_VALUE?Integer.MAX_VALUE:(int)channel.size());

            long startTime = System.currentTimeMillis();

            //------------------Buffer terminologia--------------------------------
            //Kapacita - je fixna velkost bufra. Ak je kapacita bufera naplneny,
            // je potrebne ho vycistit(clear)
            //Pozicia - je zavisla na read/write mode. Je to pozicia z ktorej sa cita/zapisuje.
            //Po kazdom buffer.get()/citanie alebo put()/zapis sa pozicia posunie o jednu.
            //Moze byt max rovna kapacite -1.
            //Limit - je limit, ktory urcuje kolko dat mozeme z bufra cita.
            // Je zavisly na read/write mode. V pripade zapisu sa Limit=Kapacita
            //Ak flip(nem) zo zapisovanieho modu do citacieho, limit je nastaveny na write
            //poziciu write modu. Teda mozeme citat, maximalne tolko dat kolko bolo zapisanych.

            //------------------operacie nad Buffer--------------------------------
            //------------------rewind-----------------------
            //The position is set to zero and the mark is discarded.
//            buffer.rewind();
            //------------------flip-------------------------
            //The limit is set to the current position and then
            //the position is set to zero.  If the mark is defined then it is discarded.
//            buffer.flip();
            //------------------clear------------------------
            //The position is set to zero, the limit is set to
            //the capacity, and the mark is discarded.
//            buffer.clear();
            //------------------mark-------------------------
            //Sets this buffer's mark at its position.
//            buffer.mark();
            //------------------reset------------------------
            //Resets this buffer's position to the previously-marked position.
//            buffer.reset();
            //------------------limit------------------------
            while (channel.read(buffer)>0) {
                //spracovanie dataRead nasleduje TU:
                //po nacitani dat do buffer musim flipnut na read mode
//                buffer.flip();
//                for(int i = 0; i<buffer.limit();i++){
//                    System.out.print((char)buffer.get());
//                }
//                System.out.println(Charset.defaultCharset().decode(ByteBuffer.wrap(buffer.array())));
                //vycistim buffer kvoli nacitaniu dalsich dat
                buffer.clear();
            }
            long endTime = (new Date()).getTime();
            System.out.println("Duration time non-blocking:"+(endTime-startTime));
            channel.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readBlocking(String fileName) {
        try (RandomAccessFile raf = new RandomAccessFile(directoryPath + fileName, "r")) {
//            byte[] dataRead = new byte[536870912];
            byte[] dataRead = new byte[1024];

            long startTime = System.currentTimeMillis();
//            System.out.println("Start time blocking:"+startTime);

            //read alebo write operacio I/O je blokujuca toto vlakno zakial sa data zo suboru nenacitaju
            while (raf.read(dataRead) != -1) {
                //spracovanie dataRead nasleduje TU:
//                System.out.println(Charset.defaultCharset().decode(ByteBuffer.wrap(dataRead)));
            }
            long endTime = (new Date()).getTime();
            System.out.println("Duration time blocking:"+(endTime-startTime));
//            startTime = endTime;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void mappedBufferExample()
    {
        int length = 0xC00000; // 12 MB
        MappedByteBuffer out = null;
        try {
            FileChannel channel = new RandomAccessFile(directoryPath +"mappedFile.txt", "rw")
                    .getChannel();
            out = channel.map(FileChannel.MapMode.READ_WRITE, 0, length);

            for (int i = 0; i < length; i++)
            {
                //prepise prvych 12 MB suboru na pismeno x
                out.put((byte) 'x');
            }
            channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
