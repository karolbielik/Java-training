package com.insdata.io.io;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by karol.bielik on 18.9.2017.
 *
 * Java SE 1+
 *
 * I/O operacie pre pracu so subormy/streamami + serializacia
 *
 * File - sluzi na pracu s hlavickami suboru/adresara
 * InputStream(abstract) <= (FileInputStream(low-level), FilterInputStream, ObjectInputStream) <= BufferedInputStream(extends FilterInputStream)
 * Reader(abstract) <= (BufferedReader, InputStreamReader) <= FileReader(extends InputStreamReader a je low-level)
 * OutputStream(abstract) <= (FileOutputStream low-level, FilterOutputStream, ObjectOutputStream) <= BufferedOutputStream (extends FilterOutputStream, PrintStream extends FilterOutputStream)
 * Writer(abstract) <= (BufferedWriter, OutputStreamWriter, PrintWriter) <= FileWriter (extends OutputStreamWriter a je low-level)
 */
public class IOTest {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        //File - sluzi na pracu s hlavickami suboru/adresara
        String resourcesPath =
                //System.getProperty("user.dir")
                //+File.separator+
                "resources"
                +File.separator;

        //pre lepsie odlisenie Path a Absolutnej cesty, cannonical path pouzit relative path s ..
        File testPath = new File("resources/dir1/../dir1/dir2/test.txt");
        //vypise cestu tak ako je, teda relativnu
        System.out.println("Path suboru "+testPath.getName()+" je:"+testPath.getPath());
        //absolutna cesta, pripoji k abs. ceste od root relativnu cestu
        System.out.println("Absolute Path suboru "+testPath.getName()+" je:"+testPath.getAbsolutePath());
        //kanonicka cesta, ako by vyvolal getCannonicalPath a potom ju prevedie na jednoznacnu cestu,
        //spojene s tym je odstranenie . alebo .. z cesty
        System.out.println("Canonical Path suboru "+testPath.getName()+" je:"+testPath.getCanonicalPath());
        System.out.println("Parent suboru "+testPath.getName()+" je:"+testPath.getParent());
        testPath.isFile();

        File file = new File(resourcesPath+"testFileStream.txt");
        //vracia long dlzku suboru, alebo 0L ked subor neexistuje
        System.out.println("dlzka suboru testFileStream.txt:"+file.length());
        System.out.println("Total space particie je:"+file.getTotalSpace());

        if(file.isDirectory()){
            System.out.println("Zoznam suborov a adresarov v adresary:"+file.getName());
            System.out.println(file.list());
            System.out.println("Zoznam suborov v adresary:"+file.getName());
            System.out.println(file.listFiles());

        }

        //---------------------implementujuce interface InputStream a OutputStream--------------------------------------
        //File(Input/Output)Stream je trieda nizkeho pristupu k byte datam suboru, low-level
        //Buffered(Input/Output)Stream treba pouzivat ako efektivny pristup k suborom, kedy su data ulozene na disku sekvencne, high-level
        //Object(Input/Output)Stream je trieda ktora sluzi na de/serializaciu, high-level
        //PrintStream => zapisuje formatovanu reprezentaciu java objektov do binarneho streamu, high-level

        //-------------------FileInputStream, FileOutputStream
        //zapisuje/cita data ako bajty
        copyFileWithFileStream(file, new File(resourcesPath+file.getName()+".copy.txt"));

        //---------------------------------- BufferedInputStream, BufferedOutputStream
        //cita/zapisuje data po skupinach bajtov cim zvysuje performance
        copyFileWithBufferedStream(file, new File(resourcesPath+file.getName()+".copy.txt"));

        //BufferedInput/OutputStream podporuje:
        // -------------------markSupported(), mark(int), reset()
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

            System.out.println();
            System.out.println("Po resete:");
            System.out.print((char) is.read());
            System.out.print((char) is.read());
            System.out.print((char) is.read());
            System.out.print((char) is.read());
        }

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
        //mozem zapisat aky kolvek java objekt na subor v urcitom poradi a v tom istom ho spat nacitat
        String serializedPath =      resourcesPath+"serialized.serializujma.tst";
        //kvoli performance pouzivame BufferedOutputStream wrapper
        ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(serializedPath)));
        SerializujMa serializujMa = new IOTest.SerializujMa("serializacny test", 1L,2, new AjMnaSerializuj("Díki kámo za serializáciu"), 55, "skús ma zaserializovať");
        System.out.println("serializujMa pred ulozenim do suboru:"+serializujMa.toString());
        oos.writeUTF("zaciatok serializacie");
        oos.writeObject(serializujMa);  //zaserializuje objekt tak ako je v pamati do bajtov
        oos.writeUTF("koniec serializacie");
        oos.flush();
        oos.close();
        SerializujMa.mnaNeserializuj="resetuj";//vypise sa tato hodnota, lebo to bola posledna nasetovana
        //transientne a staticke hodnoty sa neserializuju a pri deserializacii sa inicializuju JVM defaultnymi hodnotami,
        // inicializacie tychto napr. v konstruktore, instancnom init bloku su ignorovane, staticky blok nieje ignorovany.
        ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(serializedPath)));
        System.out.println("nacitany serializovany objekt");
        System.out.println(ois.readUTF());
        System.out.println(((SerializujMa)ois.readObject()).toString());
        //ak som zaserializoval viac objektov zasebou toho isteho typu, tak mozem pouzit nasledovne
//        try{while(true){ois.readObject();}}catch (EOFException eofex){}
        System.out.println(ois.readUTF());
        ois.close();

        //----------------------------------------PrintStream
        //mozem vypisovat nim aj na systemovy vystup, ktory je tiez Stream, alebo do suboru
        PrintStream ps = new PrintStream(/*System.out*/new FileOutputStream(resourcesPath+"printStreamFormated.txt"));
        ps.println("zaciatok print stream serializacie");
//        ps.println(serializujMa); //zaserializuje objekt ako citatelny string//defaultne enkodovanie je pouzite
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
//        PrintWriter => zapisuje formatovanu reprezentaciu java objektov do textoveho output streamu

        //---------------------------------FileWriter, FileReader
        //najpouzivanejsie triedy pre pre zapisovanie a citanie textovych dat
        //nieje mozne zadat kodovanie, pouziva sa ak nam vyhovuje default kodovanie
        String fileReaderPath = resourcesPath+"filereader.txt";
        FileWriter fw = new FileWriter(new File(fileReaderPath));
        FileReader fr = new FileReader(new File(fileReaderPath));
        FileWriter fwcopy = new FileWriter(new File(fileReaderPath+".copy.txt"));
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
        //tiez nieje mozne zadat kodovanie, ale je moznost zadat velkost buffera
        String bufferedReaderPath = resourcesPath+"bufferedreader.txt";
        BufferedWriter bfWr = new BufferedWriter(new FileWriter(bufferedReaderPath));
        bfWr.write("Tak toto by som necakal, ze sa to tak rýchlo skopííííruje.");
        bfWr.newLine();
        bfWr.write("Tak toto by som necakal, ze sa to tak rýchlo skopííííruje.");
        bfWr.flush();
        bfWr.close();
        BufferedReader bfRe = new BufferedReader(new FileReader(bufferedReaderPath));
        BufferedWriter bfWr1 = new BufferedWriter(new FileWriter(bufferedReaderPath+".copy.txt"));
        String readData = null;
        while ((readData = bfRe.readLine())!=null){
            bfWr1.write(readData);
        }
        //alebo cez lambdu
        /*
        bfRe.lines().forEach(line->{
            try{bfWr1.write(line);
            }catch (IOException ioex){}
        });
        */
        bfWr1.flush();
        bfWr1.close();

        //---------------------------------InputStreamReader, OutputStreamWriter
        //sluzi ako premostenie medzi byte streamami a znakovymi streamami, cita bajty a dekoduje ich do znakov pouzitim
        //specifikovanej charakterovej kodovacej sady,
        // inac sa pouzivat encoding =>System.getProperty("file.encoding");

        //POZOR Charset je uz funkcionalita pridana do NIO od Java SE 4+
        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(resourcesPath+"streamReader.txt"), Charset.forName("windows-1250"));
        String lubozvucne = "ľúbozvučné ľahkovážnosti:ľščťžýáíéäúňüäö";
        osw.write(lubozvucne, 0, lubozvucne.length());
        osw.flush();
        osw.close();
        //tuna je pouzity bez Charset, teda pred verziou 1.4
        InputStreamReader isr = new InputStreamReader(new BufferedInputStream(new FileInputStream(resourcesPath+"streamReader.txt")),"windows-1250");
        System.out.println("Encoding:"+isr.getEncoding()!=null?isr.getEncoding():"null");
        int data;
        List<Character> text = new ArrayList<>();
        while((data = isr.read()) != -1){
            text.add((char)data);
        }
        isr.close();
        System.out.println("Vypis InputStreamReader nacitaneho zo suboru streamReader:");
        System.out.println(text);

        //---------------------------------PrintWriter
        PrintWriter pw = new PrintWriter(new FileOutputStream(resourcesPath+".printWriter.txt"));
        pw.print("test rôznych typov");
        pw.println();
        pw.println(1);
        pw.print(true);
        pw.println();
        pw.println("formátovaný text americký");
        /*formatovacie konverzie => https://docs.oracle.com/javase/7/docs/api/java/util/Formatter.html#syntax
        - lave zarovnanie argumentu
        + pridaj znak + al. - pre argument
        0 preznakuj argument s nulami
        , pouzi lokal separator
        ( uzatvor negativne cisla to zatvoriek
        b boolean
        c char
        d integer
        f floating pint
        s string
        t datum => nemoze byt len osamote
        * */
        pw.format(Locale.US, "This is american style date %1$tm %1$tB %1$tY and number %2$f .%n", new Date(), 3.14);
        pw.format(new Locale("sk"/*ISO 639 alpha-2 al. alpha-3*/,"SK"/*ISO 3166 alpha-2*/), "Toto je sloenský dátum %1$tm %1$tB %1$tY a číslo %2$f .%n", new Date(), 3.14);
        pw.format("Táto deska má %1$5.2f metra a váži %2$d kilov.%n", 1.2, 100);
        pw.format("Dneska je %1$tm.%1te.%1$tY, a počasie vyzerá byť %2$s, ale nedal by som zato ani %3$.2f.%n", new Date(), "slnečno", 2.5);
        pw.flush();
        pw.close();
    }

    private static void copyFileWithFileStream(File source, File destination) throws IOException {
        FileInputStream bis = new FileInputStream(source);
        FileOutputStream bos = new FileOutputStream(destination);

        //hoci je to int pracuje tu vzdy len s 1 byteom
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
        private/*moze byt akykolvek accessor*/ static final long serialVersionUID = 1L;//verzia serializacie, kvoli deserializacii
        //aby java vedela, ci je objekt kompatibilny
        //Ak by verzia nesedela, tak vyhodi InvalidClassException
        String serializujString;
        Long serializujLong;
        Integer serializujInteger;
        //transient /*=>ak dany objekt chceme vynat zo serializacie, potom sa ani neulozi do suboru*/
        AjMnaSerializuj ajMnaSerializuj;
        static String mnaNeserializuj ;//= "mnaNeser";
        transient Integer initZBloku;

        static{
            mnaNeserializuj = "mnaNeser";
        }

        {
            initZBloku = 77;
        }

        public SerializujMa(String serializujString, Long serializujLong, Integer serializujInteger, AjMnaSerializuj ajMnaSerializuj, Integer initZbloku, String... mnaNeserializuj) {
            this.serializujString = serializujString;
            this.serializujLong = serializujLong;
            this.serializujInteger = serializujInteger;
            this.ajMnaSerializuj = ajMnaSerializuj;
            this.initZBloku = initZbloku;
            if(mnaNeserializuj.length>0)
                this.mnaNeserializuj = mnaNeserializuj[0];
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

        public AjMnaSerializuj getAjMnaSerializuj() {
            return ajMnaSerializuj;
        }

        public void setAjMnaSerializuj(AjMnaSerializuj ajMnaSerializuj) {
            this.ajMnaSerializuj = ajMnaSerializuj;
        }

        @Override
        public String toString() {
            return "SerializujMa{" +
                    "serializujString='" + serializujString + '\'' +
                    ", serializujLong=" + serializujLong +
                    ", serializujInteger=" + serializujInteger +
                    ", ajMnaSerializuj=" + ajMnaSerializuj +
                    ", initZBloku=" + initZBloku +
                    ", mnaNeserializuj=" + mnaNeserializuj +
                    '}';
        }
    }

    private static class AjMnaSerializuj implements Serializable/*pozor musi byt Serializable lebo je vnoreny, inak vyhodi
                                                                  NotSerializableException*/{
        public String hoak;

        public AjMnaSerializuj(String hoak) {
            this.hoak = hoak;
        }

        public String getHoak() {
            return hoak;
        }

        public void setHoak(String hoak) {
            this.hoak = hoak;
        }

        @Override
        public String toString() {
            return "AjMnaSerializuj{" +
                    "hoak='" + hoak + '\'' +
                    '}';
        }
    }
}
