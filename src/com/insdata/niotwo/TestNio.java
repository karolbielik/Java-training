package com.insdata.niotwo;

import com.insdata.primitives.Chars;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

/**
 * Created by karol.bielik on 12.9.2017.
 */
public class TestNio {

    public static void main(String[] args) {
        String directoryPath = System.getProperty("user.dir")
                +File.separator
                +"resources"
                +File.separator
                +"nio2"
                +File.separator;
        //legacy kod
        File file = new File(directoryPath + "test.txt");

//        //nove NIO.2
        Path path = file.toPath();
//        //je moznost aj z NIO2 na NIO
        file = path.toFile();
        path.toUri();

        //pod Linuxom absolutna cesta /home/xy
        //pod windowsom absolutna cesta c:\\home\xy
        //relativna cesta home\xy

        //vytvorenim cez get a pouzitim relativnej cesty sa pouzije cesta akoby sme napisali
        //Pahts.get(System.getProperty("user.dir")+File.separator+"resources"+File.separator+"nio2");
        Path path1 = Paths.get("resources","nio2");
        //da sa napisat aj zlozitejsie
        path1 = FileSystems.getDefault().getPath("resources", "nio2");
        //absolutnu cestu by som rovnako vytvoril:
        //pod windows
//        Paths.get("C:", "projekt", "resources", "nio2");
        //pod linux
//        Paths.get("/", "projekt", "resources", "nio2");

        //pre adresovanie resourcu cez uri (file://, http://, https://)
//        Paths.get("file:///c:/miesto/na/sieti");
        //alebo
//        try {Paths.get(new URI("file:///c:/miesto/na/sieti"));} catch (URISyntaxException e) {}

        Path path2 = Paths.get("resources","nio2");
        try {
            //Path a Paths operuje nad tymto objektom aj bez toho, ze by file/folder existovali
            //ale ak pouzijem toRealPath a file/folder neexistuje tak vyhody NoSuchFileException
            path2.toRealPath(LinkOption.NOFOLLOW_LINKS);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //-------------------------------------------------------------------------
        //LinkOptions sa pouzivaju ako vstupne parametre do operacii nad file/folder
        //NOFOLLOW_LINKS: pri testovani ci exituje subor, citani dat zo subora, kopirovani, presuvani suboru
        //FOLLOW_LINKS: pri traversovani stromovej struktury adresara
        //COPY_ATTRIBUTES: pri kopirovani suboru
        //REPLACE_EXISTING:pri kopirovani, presuvani suboru
        //ATOMIC_MOVE:pri presune suboru, ak to file system nepodporuje tak vyhodi AtomicMoveNotSupportedException
                        // => ak je presun atomicky, tak proces monitorujuci presuvanie subor nikdy nespozoruje
                        // ciastocne nedokonceny, presunuty subor
        //-------------------------------------------------------------------------
        //---------------------Operacie nad Path objektom--------------------------
        Path fileTest = Paths.get("resources","nio2","test.txt");
        for(int i = 0;i<fileTest.getNameCount();i++){
            //getName(int) vracia string ale java vola pri konverzii na string toString() na Path
            System.out.println(fileTest.getName(i));//getName je indexovana od 0 ,system root cesta nieje zahrnuta
        }
        //---------------getFileName----------------------------------------
        System.out.println("meno suboru, resp. najdalejsi element od system root:"+fileTest.getFileName());
        //--------------------getParent--------------------------------------
        System.out.println("adresarova cesta bez system root a bez posledneho elementu cesty:"+fileTest.getParent());
        //------------------getRoot()-----------------------------------------
        System.out.println("Ak je cesta relativna tak vrati null, ak absolutna tak vrati system root cestu:"+fileTest.getRoot());
        Path fileTest1 = Paths.get("C:/java_training/testFile.txt");
        System.out.println("fileTest1 :: Ak je cesta relativna tak vrati null, ak absolutna tak vrati system root:"+fileTest1.getRoot());
        System.out.println("fileTest1 :: predposledny element cesty aj s rootom:"+fileTest1.getParent());
        System.out.println("fileTest1 :: posledny element cesty:"+fileTest1.getFileName());

        //----------------------isAbsolute(), toAbsolutePath()------------------------------------------
        Path path3 = Paths.get("niekde\\na\\disku");
        System.out.println("Je absolutna cesta:"+path3.getFileName()+":"+path3.isAbsolute());
        Path path4 = path3.toAbsolutePath();
        System.out.println("Je absolutna cesta path3 po toAbsolutePath():"+path3.getFileName()+":"+path3.isAbsolute());
        System.out.println("Root path3:"+path3.getParent());
        System.out.println("Je absolutna cesta path4:"+path4.getFileName()+":"+path4.isAbsolute());
        System.out.println("Root path4:"+path4.getParent());

        //--------------------------------subpath(int,int)----------------------------------------------
        //sluzi na vytvorenie, resp. pouzitie nejakej casti existujuce cesty na vytvorenie danej podcesty v ramci inej cesty
        //prvy a druhy parameter nemozu byt tie iste cisla a max hodnota parametra moze byt max n, ak by bol n+1 tak vyhodi IllegalArgumentException
        //root nieje zahrnuty do indexu, cize v nasom pripade iindex=0 => jedna
        Path path5 = Paths.get("/jedna/dva/tri/styri");
        System.out.println("path5 subpath:"+path5.subpath(0/*included*/, 3/*excluded*/));

        //----------------relativize(Path)----------------------------------------------
        //relativizovanie relativnych ciest
        Path path6 = Paths.get("kuchyna\\sporak.txt");
        Path path7 = Paths.get("obyvacka\\gauc.xml");
        System.out.println(path6.relativize(path7));
        System.out.println(path7.relativize(path6));
        //relativizovanie absolutnych ciest
        Path path8 = Paths.get("C:\\shareFolder\\sporak.txt");
        Path path9 = Paths.get("C:\\shareFolder\\obyvacka\\gauc.txt");
        System.out.println(path8.relativize(path9));
        System.out.println(path9.relativize(path8));
        //ak by  sme chceli zrelativizovat cestu medzi absolutnou a relativnou cestou tak vyhodi exception napr.
        try{
            System.out.println(path6.relativize(path8));
        }catch (IllegalArgumentException iaex){
            String s = iaex.getMessage();
        }
        //--------------------------------------resolve()--------------------------------------------
        //sluzi na vytvorenie novej cesty tym ze pripoji k this cestu z argumentu
        System.out.println(path6.resolve(path7));
        //ak do parametru dame absolutnu cestu a this je ralativna, tak sa this ignoruje
        System.out.println(path6.resolve(path8));
        //--------------------------------normalize()------------------------------------------------
        Path path10 = Paths.get("C:\\cesta\\do\\..\\zeme\\nezeme");
        Path path11 = Paths.get("pekna\\rit");
        Path path12;
        System.out.println(path12 = path10.resolve(path11));
        System.out.println(path12.normalize());

        //-----------------------------------------java.nio.file.Files------------------------------------
        //---------------------------------------------exists()-------------------------------------------
        System.out.println("Subor existuje:"+Files.exists(path10));
        //---------------------------------------------isSameFile()-------------------------------------------
        try {
            //ak subory/adresare(nasleduje aj symbolic links) existuju tak zisti ci su dva objekty equal()
            //neporovnava obsah suborov, ak su dva identicke subory(obsah a atributy) kazdy na inej lokacii
            //tak to vyhodnoti ako ine subory.
            System.out.println("Je to ten isty subor:"+Files.isSameFile(path10, Paths.get("C:\\cesta\\do\\zeme\\nezeme")));
        } catch (NoSuchFileException e) {
            e.printStackTrace();
        } catch (IOException ioex){}

        try {
            //drevo ak by bol sym link tak vrati true
//            System.out.println(Files.isSameFile(Paths.get("/user/home/dubina"), Paths.get("/user/home/drevo")));

            //vrati true, lebo v pozadi sa cesta normalizuje
            System.out.println("/abs/cesta/:"+Files.isSameFile(Paths.get("/abs/cesta/../daleko"), Paths.get("/abs/cesta")));
            System.out.println(Files.isSameFile(Paths.get("/leaves/./giraffe.exe"), Paths.get("/leaves/giraffe.exe")));
            System.out.println(Files.isSameFile(Paths.get("/flamingo/tail.data"), Paths.get("/cardinal/tail.data")));
        }catch (IOException ioex){
            ioex.printStackTrace();
        }

        //-----------------------------EXAMPLES-----------------------------------
        //zakladne operacie nad adresarmi
        //--------------createDirectory(), createDirectories()------------------------------
        try {
//            Files.createDirectory(Paths.get("C:\\rodic\\dieta"));//vyhodi kontrolovanu IOException, ked rodicovsky adresar neexistuje
            Files.createDirectories(Paths.get("C:\\rodic\\dieta"));//vytvori neexistujece rodicovske adresare
        } catch (NoSuchFileException nfe) {
            nfe.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        //----------------zakladne operacie nad subormi --------------------------
        //---------------------------vytvorenie suboru-----------------------------------
        //-------------------------------newBufferedWriter()---------------------------------
        Path novySubor = Paths.get("resources","nio2", "vytvoreny.subor.txt");
        //je treba sa uistit ze dana cesta existuje ak nie tak musime vytvorit adresar
        try {
            Files.createDirectories(novySubor.getParent());
        } catch (IOException e) {e.printStackTrace();}

        try (BufferedWriter bw = getBufferedWriter(novySubor)){
            bw.write("test : ľščťžýáíéäň");
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //-------------------------citanie zo suboru------------------------------------------------
        //-------------------------------newBufferedReader()---------------------------------
        try(BufferedReader br = Files.newBufferedReader(novySubor,Charset.forName("UTF-8"))){
            String line;
            System.out.println("---------------Vypis ulozeneho suboru-------------------");
            while ((line = br.readLine())!=null){
                System.out.println(line);
            }
            System.out.println("-----------Koniec vypisu ulozeneho suboru--------------");
        }catch (IOException ioex){
            ioex.printStackTrace();
        }
        //------------------------vypis suboru pomocou readAllLines()-----------------------------------
        try {
            System.out.println("-------------------vypis suboru pomocou readAllLines-------------------");
            //pri
            Files.readAllLines(novySubor, Charset.forName("UTF-8")).forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //link k suboru
        //----------------------------prekopirovanie suboru---------------------------------------
        //-----------------------------------copy()-------------------------------------------------
        Path kopirovanySubor = Paths.get(novySubor.getParent().toString()+"\\kopia.noveho.suboru.txt");
        try {//TODO:este dokoncit ked vytvorim subor, tak jeho kopirovanie
            //kopirovanie nefunguje rekurzivne(vnorene), prekopuruje len dany adresar(nie obsah) a subor s obsahom
            //Ak subor uz exituje tak vyhodi FileAlreadyExistsException
            Files.copy(novySubor, kopirovanySubor );
//            Files.copy(Paths.get(""), new FileOutputStream(""));
//            Files.copy(new FileInputStream(""), Paths.get(""));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //----------------------------------presun suboru------------------------------------------------
        //-----------------------------------move()-----------------------------------------------------
        Path presunutySubor = Paths.get(kopirovanySubor.getParent().toString()+"\\historia\\presunuty.kopirovany.subor.txt");
        try {
            //ak adresar do ktoreho sa presuva neexistuje, tak vyhodi NoSuchFileException
            if(!Files.exists(presunutySubor.getParent())){
                Files.createDirectories(presunutySubor.getParent());
            }
            Files.move(kopirovanySubor, presunutySubor, StandardCopyOption.ATOMIC_MOVE);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //-------------------------------vymazanie suboru atomicke a neatomicke------------------------
        //---------------------------------delete()----------------------------------------------------
        try {
            //ak mazem adresar a nieje prazdny, tak vyhodi exception
            Files.delete(presunutySubor);
            //ak vymazavam adresar a nieje prazdny vrati false
//            Files.deleteIfExists(presunutySubor);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //----------------------------------Atributy suborov/adresarov------------------------------------
        //----------isDiretory(), isRegularFile(), isSymbolicLink()---------------------------------------
        System.out.println("subor/adresar:"+novySubor.getFileName()+":je adresar:"+Files.isDirectory(novySubor));
        System.out.println("subor/adresar:"+novySubor.getFileName()+":je subor:"+Files.isRegularFile(novySubor));
        System.out.println("subor/adresar:"+novySubor.getFileName()+":je symLink:"+Files.isSymbolicLink(novySubor));
        //-----------------------------------------------isHidden()-----------------------------------------
        //nastavenie suboru na hidden, pozor tato operacia je specificka pre kazdy operacny system
        //dane riesenie je pre windows
        try {
            System.out.println("kopirovany subor pre hidden:"+Files.isHidden(novySubor));
            Files.setAttribute(novySubor, "dos:hidden", true, LinkOption.NOFOLLOW_LINKS);
            System.out.println("kopirovany subor po hidden:"+Files.isHidden(novySubor));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //------------------------------isReadable(), isExecutable()------------------------------------------
        System.out.println("novy subor isReadable:"+ Files.isReadable(novySubor));
        System.out.println("novy subor isExecutable:"+ Files.isExecutable(novySubor));
        //-----------------------------size()--------------------------------------------------
        //velkost suboru v bajtoch
        try {
            System.out.println("velkost noveho suboru:"+Files.size(novySubor));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //----------------getLastModified(), setLastModified()----------------------------------
        try {
            System.out.println("povodny timestamp suboru:"+Files.getLastModifiedTime(novySubor));
            //zmena casovej znacky na subore
            Files.setLastModifiedTime(novySubor, FileTime.fromMillis(System.currentTimeMillis()) );
            System.out.println("novy timestamp suboru:"+Files.getLastModifiedTime(novySubor));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //---------------------------------setOwner(), getOwner()----------------------------------
        try {
            System.out.println("Owner noveho suboru je:"+Files.getOwner(novySubor));
            //dve moznosti ako ziskat usera zo systemu, moze vyhodit UserPrincipalNotFoundException
            UserPrincipal up =  path.getFileSystem().getUserPrincipalLookupService().lookupPrincipalByName("INSDATA\\karol.bielik");
            UserPrincipal up1 = FileSystems.getDefault().getUserPrincipalLookupService().lookupPrincipalByName("INSDATA\\karol.bielik");
            Files.setOwner(novySubor, up);
            System.out.println("Owner noveho suboru po zmente je:"+Files.getOwner(novySubor));
        } catch (IOException e) {
            e.printStackTrace();
        }

//        Files.readAttributes()
        //-----------------read file attributes------------------------------------------------------
        try {
            //BasicFileAttributes => atributy spolocne pre vsetky OS
//            DosFileAttributes =>  atributy pre dos
//            PosixFileAttributes => atributy pre posix = linux, mac, unix
            BasicFileAttributes bfa = Files.readAttributes(novySubor, BasicFileAttributes.class);
            System.out.println("BasicFileAttrs:");
            System.out.println("creationTime():"+bfa.creationTime().toString());
            System.out.println("size():"+bfa.size());
            System.out.println("lastModified():"+bfa.lastModifiedTime().toString());
            DosFileAttributes dfa = Files.readAttributes(novySubor, DosFileAttributes.class);
            System.out.println("DosFileAttributes:");
            System.out.println("is hidden:"+dfa.isHidden());
            System.out.println("creation time:"+dfa.creationTime());
            System.out.println("lastModified:"+dfa.lastModifiedTime());

        } catch (IOException e) {
            e.printStackTrace();
        }

        //BasicFileAttributeView
        //UserDerfinedFileAttributeView
        //FileOwnerAttributeView
        try {
            BasicFileAttributeView bfaw = Files.getFileAttributeView(
                    novySubor,
                    BasicFileAttributeView.class//,
            );
            System.out.println("last Modiefied:" + bfaw.readAttributes().lastModifiedTime() + ", last Access:" + bfaw.readAttributes().lastAccessTime() + ", create Time:"+bfaw.readAttributes().creationTime());
            TimeUnit.SECONDS.sleep(5);
            bfaw.setTimes(FileTime.fromMillis(System.currentTimeMillis()), FileTime.fromMillis(System.currentTimeMillis()), FileTime.fromMillis(System.currentTimeMillis()));
            System.out.println("last Modiefied:" + bfaw.readAttributes().lastModifiedTime() + ", last Access:" + bfaw.readAttributes().lastAccessTime() + ", create Time:"+bfaw.readAttributes().creationTime());
        }catch (IOException ioex){

        }catch (InterruptedException inex){

        }

        UserDefinedFileAttributeView udfav = Files.getFileAttributeView(
                novySubor,
                UserDefinedFileAttributeView.class//,
        );

        FileOwnerAttributeView foav = Files.getFileAttributeView(
                novySubor,
                FileOwnerAttributeView.class//,
        );
    }

    private static BufferedWriter getBufferedWriter(Path novySubor) throws IOException {
        if(Files.exists(novySubor)){
            return Files.newBufferedWriter(novySubor, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
        }
        return Files.newBufferedWriter(novySubor, Charset.forName("UTF-8"));
    }


}
