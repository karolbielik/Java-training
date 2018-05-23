package com.insdata.io.nio;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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
        String fileName = "random.access.file.txt";

        readIOSposob(fileName);
        readNIOSposob(fileName);
        System.out.println("files processing finished");

        //mapovanie casti suboru na buffer
        mappedBufferExample(fileName);
    }

    //tato funkcia v resoruces/nio/random.access.file.txt zapise byte=4(dec)=>(char='-') do kazdeho 1-heho(neparneho) byte-u
    //NIO presunulo operacie ako naplnanie buffera na uroven operacneho systemu, co zrychlilo pristup k suborom
    //velkost buffer alokovana musi byt parne cislo
    private static void readNIOSposob(String fileName) {
        try (RandomAccessFile raf = new RandomAccessFile(directoryPath + fileName, "rw")) {
            //v java 4 bolo tomuto pridana metoda getChannel
            /*
            Channel oproti stream:
            - cita aj zapisuje zaroven, do streamu je mozne iba zapisovat, alebo iba citat
            - je mozne do channel citat/zapisovat asynchronne
            - channel vzdy zapisuje do alebo cita z buffer-a
             */
            try(FileChannel channel = raf.getChannel()) {
                //FileChannel channel1 = FileChannel.open(Paths.get(directoryPath + fileName),StandardOpenOption.READ, StandardOpenOption.WRITE);
                //ByteBuffer buffer = ByteBuffer.allocate(536870912);
                //v pripade, ze pracujem s velkymi subormi tak alokujem konstantnu velkost aby som nesportreboval pamat
//            ByteBuffer buffer = ByteBuffer.allocate(1024);
                //allocate 4MB
//            ByteBuffer buffer = ByteBuffer.allocate(4*1024*1024);
                //velkost buffer alokovana musi byt parne cislo
                ByteBuffer buffer = ByteBuffer.allocate(4);
                //v pripade malych suborov mozem nacita data naraz => POZOR ak by som natrafil na velky subor tak, nasledujuci
                //riadok moze vyhodit OutOfMemoryException
//            ByteBuffer buffer = ByteBuffer.allocate(channel.size()>Integer.MAX_VALUE?Integer.MAX_VALUE:(int)channel.size());

                long startTime = System.currentTimeMillis();

                //------------------Buffer terminologia--------------------------------
                //Kapacita - je fixna velkost bufra. Ak je kapacita bufera naplnena,
                // je potrebne ho vycistit(clear)
                //Pozicia - je zavisla na read/write mode. Je to pozicia z ktorej sa cita/zapisuje.
                //Po kazdom buffer.get()/citanie alebo put()/zapis sa pozicia posunie o jednu.
                //Moze byt max rovna kapacite -1.
                //Limit - je limit, ktory urcuje kolko dat mozeme z bufra citat.
                // Je zavisly na read/write mode. V pripade zapisu sa Limit=Kapacita
                //Ak flip(nem) zo zapisovanieho modu do citacieho, limit je nastaveny na write
                //poziciu write modu.

            /*
            Write Mode
            --------------------|-------------|
                            pozicia         limit,kapacita
            Read Mode
            |-------------------|-------------|
            pozicia         limit           kapacita
            * */

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

                int readDataCount = 0;
                while ((readDataCount = channel.read(buffer)) > 0) {
                    //spracovanie dataRead nasleduje TU:
                    //po nacitani dat do buffer musim flipnut na read mode
                    buffer.flip();
                    while (buffer.hasRemaining()) {
                        buffer.put((byte) 45);
                        int newBufferPosition = buffer.position() + 1;
                        if (buffer.hasRemaining()) {
                            buffer.position(newBufferPosition);
                        }
                    }
                    //znova musim buffer nastavit na zapis
                    buffer.flip();
                    channel.position(channel.position()-readDataCount);
                    channel.write(buffer);
                    //vycistim buffer kvoli nacitaniu dalsich dat
                    buffer.clear();
                }
                long endTime = (new Date()).getTime();
                System.out.println("Duration time non-blocking:" + (endTime - startTime));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //v resoruces/nio/random.access.file.txt zapise byte=43(dec)=>(char='+') do kazdeho 2-heho(parneho) byte-u
    //velkost alokovaneho pola dataRead musi byt parne cislo
    private static void readIOSposob(String fileName) {
        try (RandomAccessFile raf = new RandomAccessFile(directoryPath + fileName, "rw")) {
//            byte[] dataRead = new byte[536870912];
            //velkost alokovaneho pola dataRead musi byt parne cislo
            byte[] dataRead = new byte[4];

            long startTime = System.currentTimeMillis();
//            System.out.println("Start time blocking:"+startTime);

            //read alebo write operacio I/O je blokujuca toto vlakno zakial sa data zo suboru nenacitaju
            int dataReadCount = 0;
            while ( (dataReadCount = raf.read(dataRead)) > 0) {
                long startPosition = raf.getFilePointer()-dataReadCount;
                for(long i = 1;i<=(dataReadCount/2);i++){
//                    System.out.println(dataRead[i]);
                    //nastavi file-pointer poziciu na 10
                    raf.seek((startPosition + i*2)-1);
                    raf.writeByte(43);//43 = '+'
                    //file pointer position je vlastene == seek nastaveniu pozicii
                    //avsak po writeByte operacii sa pointer posunie o jedno miesto vyssie
//                    System.out.println("file pointer:"+raf.getFilePointer());
                }
            }
            raf.close();
            long endTime = (new Date()).getTime();
            System.out.println("Duration time blocking:"+(endTime-startTime));
//            startTime = endTime;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //zapise niekam do vnutra suboru "KOKOTI"
    public static void mappedBufferExample(String fileName)
    {
//        int length = 0xC00000; // 12 MB
        String dataPreZapis = "KOKOTI";
        int length = dataPreZapis.length();//6 byte-ov
        MappedByteBuffer mappedBuffer = null;
        try(FileChannel channel = new RandomAccessFile(directoryPath +fileName, "rw").getChannel()) {
            mappedBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 5, length);

            byte[] dataPreZapisByteArr = new byte[length];
            String[] dataPreZapisStringArr = dataPreZapis.split("");
            for(int i = 0; i<length; i++){
                dataPreZapisByteArr[i] =  (byte)(dataPreZapisStringArr[i].charAt(0));
            }
            mappedBuffer.put(dataPreZapisByteArr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
