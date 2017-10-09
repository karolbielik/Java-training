package com.insdata.nio;

import java.io.*;
import java.nio.CharBuffer;

/**
 * Created by karol.bielik on 18.9.2017.
 */
public class TestNio {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String filePath = System.getProperty("user.dir")
                +File.separator
                +"resources"
                +File.separator
                +"testFileStream.txt";
        File file = new File(filePath);
        //---------------------implementujuce interface InputStream a OutputStream--------------------------------------
        //File(Input/Output)Stream je trieda nizkeho pristupu k byte datam suboru, low-level
        //Buffered(Input/Output)Stream treba pouzivat ako efektivny pristup k suborom, kedy su data ulozene na disku sekvencne, high-level
        //Object(Input/Output)Stream je trieda ktora sluzi na de/serializaciu, high-level
        //PrintStream => zapisuje formatovanu reprezentaciu java objektov do binarneho streamu, high-level

         //-------------------FileInputStream, FileOutputStream
        //zapisuje/cita data ako bajty
        copyFileWithFileStream(file, new File(filePath+".copy"));

        //---------------------------------- BufferedInputStream, BufferedOutputStream
        //cita/zapisuje data po skupinach bajtov cim zvysuje performance
        copyFileWithBufferedStream(file, new File(filePath+".copy"));

        // markSupported(), mark(int), reset()

        InputStream is = new BufferedInputStream( new FileInputStream(file));
        System.out.println("Velkost suboru:"+is.available());
        //mark sluzi na to aby sa oznacilo miesto v streame a potom sa vedelo pokracovat od toho miesta
        if(is.markSupported()){
            System.out.print((char) is.read());
            System.out.print((char) is.read());
            is.mark(1);
            System.out.print((char) is.read());
            System.out.print((char) is.read());

            is.reset();
        }
        System.out.println();
        System.out.println("Po resete:");
        System.out.print((char) is.read());
        System.out.print((char) is.read());
        System.out.print((char) is.read());
        System.out.print((char) is.read());

        //skip
        is = new BufferedInputStream( new FileInputStream(file));
        is.skip(2);
        System.out.println();
        System.out.println("Po skipe:");
        System.out.print((char) is.read());
        System.out.print((char) is.read());
        System.out.print((char) is.read());
        System.out.print((char) is.read());

        //----------------------ObjectInputStream, ObjectOutputStream
        //mozem zapisat aky kolvek java objekt na subor v urcitom poradi a v tom isto m ho spat nacitat
        String serializedPath =      filePath+"serialized.serializujma.tst";
        ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(serializedPath)));
        SerializujMa serializujMa = new TestNio.SerializujMa("serializacny test", 1L,2);
        System.out.println("serializujMa pred ulozenim do suboru:"+serializujMa.toString());
        oos.writeUTF("zaciatok serializacie");
        oos.writeObject(serializujMa);  //zaserializuje objekt tak ako je v pamati do bajtov
        oos.writeUTF("koniec serializacie");
        oos.flush();
        oos.close();
        ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(serializedPath)));
        System.out.println("nacitany serializovany objekt");
        System.out.println(ois.readUTF());
        System.out.println(((SerializujMa)ois.readObject()).toString());
        System.out.println(ois.readUTF());
        ois.close();

        //----------------------------------------PrintStream
        //mozem vypisovat nim aj na systemovy vystup, ktory je tiez Stream, alebo do suboru
        PrintStream ps = new PrintStream(System.out/*new FileOutputStream(serializedPath)*/);
        ps.println("zaciatok print stream serializacie");
        ps.println(serializujMa); //zaserializuje objekt ako citatelny string//defaultne enkodovanie je pouzite
        ps.println("koniec print stream serializacie");
        ps.flush();
        //nevyhadzuje IOException ako ine outputStream ale nastavi flag, ktory mozeme kontrolovat
        System.out.println(ps.checkError());
        //format a printf sa spravaju uplne rovnako
        ps.format("Ja som krasny %s retazec %s","formatovany", "znakov");
        ps.println();
        ps.printf("Ja som krasny %s retazec %s","formatovany", "znakov");  //printf vola vo vnutry format()
        ps.close();

        //---------------------------implementujuce interface Reader a Writer ------------------------------------------
        //Na pracu so String alebo charaktermi pouzivame Read/Writer triedy
//        FileWriter, FileReader => zapisuje/cita data ako charakters (low level)
//        BufferedReader, BufferedWriter => cita/zapisuje data z existujuceho Reader/Writer tak ze ich
//        bufferuje(nacita naraz viac), tym optimalizuje na performance
//        InputStreamReader, OutputStreamWriter => cita/zapisuje data z/do InputStream
//        PrintWriter => zapisuje formatevanu reprezentaciu java objektov do textoveho output streamu

        //---------------------------------FileWriter, FileReader
        //najpouzivanejsie triedy pre pre zapisovanie a citanie textovych dat
        String fileReaderPath = filePath+"filereader.txt";
        FileWriter fw = new FileWriter(new File(fileReaderPath));
        FileReader fr = new FileReader(new File(fileReaderPath));
        FileWriter fwcopy = new FileWriter(new File(fileReaderPath+".copy"));
        fw.write("Tak toto by som necakal, ze sa to skopííííruje.");
        fw.flush();
        fw.close();

        int ch=-1;
        while((ch = fr.read())>0){
                fwcopy.append((char)ch);
        }
        fr.close();
        fwcopy.flush();
        fwcopy.close();

        //---------------------------------BufferedReader, BufferedWriter
        //buffrovany pristup, mozne ho parovat s FileWriter,FileReader
        //taky isty priklad ako pre FileReader/Writer s pouzitim wrapper triedy
        String bufferedReaderPath = filePath+"bufferedreader.txt";
        BufferedWriter bfWr = new BufferedWriter(new FileWriter(bufferedReaderPath));
        bfWr.write("Tak toto by som necakal, ze sa to tak rýchlo skopííííruje.");
        bfWr.newLine();
        bfWr.write("Tak toto by som necakal, ze sa to tak rýchlo skopííííruje.");
        bfWr.flush();
        bfWr.close();
        BufferedReader bfRe = new BufferedReader(new FileReader(bufferedReaderPath));
        BufferedWriter bfWr1 = new BufferedWriter(new FileWriter(bufferedReaderPath+".copy"));
        String readData = null;
//        while ((readData = bfRe.readLine())!=null){
//            bfWr1.write(readData);
//        }
        //alebo cez lambdu
        bfRe.lines().forEach(line->{
                                      try{bfWr1.write(line);
                                    }catch (IOException ioex){}
        });
        bfWr1.flush();
        bfWr1.close();
        
        //---------------------------------InputStreamReader, OutputStreamWriter
        //---------------------------------PrintWriter


    }

    private static void copyFileWithFileStream(File source, File destination) throws IOException {
        FileInputStream bis = new FileInputStream(source);
        FileOutputStream bos = new FileOutputStream(destination);

        int c;
        try {
            while ((c = bis.read()) != -1) {
                bos.write(c);
            }
        }finally {
            bis.close();
            bos.close();
        }
    }

    private static void copyFileWithBufferedStream(File source, File destination) throws IOException {
        int dlzkaPrecitanych;
        //dlzka precitanych dat sa odporuca byt mocnina 2 kvoli hardverovej architekture suboroveho bloku a velkosti cache
        byte[] precitaneData = new byte[1024];
        try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(source))) {
            try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(destination))) {
                while ((dlzkaPrecitanych = bis.read(precitaneData)) > 0) {
                    bos.write(precitaneData, 0, dlzkaPrecitanych);
                    bos.flush();
                }
            }
            //blok finaly nieje potrebny lebo InputStream implementuje Closable interface a teda resource sa zavrie
            //automaticky po opusteni bloku
            //finally {
//            bis.close();
//            bos.close();
//        }
        }
    }


    private static class SerializujMa implements Serializable{
        String serializujString;
        Long serializujLong;
        Integer serializujInteger;

        public SerializujMa(String serializujString, Long serializujLong, Integer serializujInteger) {
            this.serializujString = serializujString;
            this.serializujLong = serializujLong;
            this.serializujInteger = serializujInteger;
        }

        public String getSerializujString() {
            return serializujString;
        }

        public void setSerializujString(String serializujString) {
            this.serializujString = serializujString;
        }

        public Long getSerializujLong() {
            return serializujLong;
        }

        public void setSerializujLong(Long serializujLong) {
            this.serializujLong = serializujLong;
        }

        public Integer getSerializujInteger() {
            return serializujInteger;
        }

        public void setSerializujInteger(Integer serializujInteger) {
            this.serializujInteger = serializujInteger;
        }

        @Override
        public String toString() {
            return "SerializujMa{" +
                    "serializujString='" + serializujString + '\'' +
                    ", serializujLong=" + serializujLong +
                    ", serializujInteger=" + serializujInteger +
                    '}';
        }
    }
}
