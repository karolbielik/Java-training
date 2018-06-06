package com.insdata.io.nio;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;
import java.util.concurrent.Future;

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

//        readIOSposob(fileName);
//        readNIOSposob(fileName);
        readNIOAsynchronous(fileName);
        System.out.println("files processing finished");

        //mapovanie casti suboru na buffer
//        mappedBufferExample(fileName);
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
                //dalsi sposob ako je mozne vytvorit channel
                //FileChannel channel1 = FileChannel.open(Paths.get(directoryPath + fileName),StandardOpenOption.READ, StandardOpenOption.WRITE);
                //v pripade, ze pracujem s velkymi subormi, tak alokujem konstantnu velkost,
                // aby som nesportreboval pamat(OutOfMemoryException), potom uprednostnim
                //alokaciu mensej casti pamate, ktoru spracovavam postupne v cykloch
                //napr. alokujem 4MB =>  ByteBuffer buffer = ByteBuffer.allocate(4*1024*1024);
                //velkost buffer alokovana musi byt parne cislo, aby nam fungoval tento priklad
                ByteBuffer buffer = ByteBuffer.allocate(4);

                long startTime = System.currentTimeMillis();

                //------------------Buffer terminologia--------------------------------
                //Kapacita - je fixna velkost bufra. Ak je kapacita bufera naplnena,
                // je potrebne ho vycistit(clear)
                //Pozicia - je zavisla na read/write mode. Je to pozicia z ktorej sa cita/zapisuje.
                //Po kazdom buffer.get()/citanie alebo put()/zapis sa pozicia posunie o jednu.
                //Moze byt max rovna kapacite -1.
                //Limit - je limit, ktory urcuje kolko dat mozeme z bufra citat.
                // Je zavisly na read/write mode. V pripade zapisu sa Limit=Kapacita
                //Ak flip-nem zo zapisovanieho modu do citacieho, limit je nastaveny na write
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
                    //pripravim buffer na dalsie citanie dat
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

    private static void readNIOAsynchronous(String fileName){
        try(AsynchronousFileChannel channel = AsynchronousFileChannel.open(Paths.get(directoryPath, fileName), StandardOpenOption.READ, StandardOpenOption.WRITE)) {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            Future<Integer> operation = channel.read(buffer,0);
            System.out.println("start waiting:"+System.currentTimeMillis());
            while(!operation.isDone());
            System.out.println("end waiting:"+System.currentTimeMillis());
            buffer.flip();
            while(buffer.hasRemaining()){
                System.out.print((char)buffer.get());
            }
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
//            raf.close();
            long endTime = (new Date()).getTime();
            System.out.println("Duration time blocking:"+(endTime-startTime));
//            startTime = endTime;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //zapise niekam do vnutra suboru "VOVNUTRI"
    public static void mappedBufferExample(String fileName)
    {
//        int length = 0xC00000; // 12 MB
        String dataPreZapis = "VOVNUTRI";
        int length = dataPreZapis.length();
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
