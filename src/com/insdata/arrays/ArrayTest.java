package com.insdata.arrays;

import java.util.Arrays;

/**
 * Created by key on 8.1.2017.
 */
public class ArrayTest {

    public static void main(String[] args) {
        //DEKLARACIA pola
        int[] pole1; //tento zapis sa pouziva obycajne, je lepsie citatelny
        String pole2[];//menej citatelne ale povolene

        //KONSTRUKCIA pola
        pole1 = new int[3];//mozne napisat aj v jednom riadku int[]pole1 = new int[3];
        pole2 = new String[3];

        //pole1 obsahuje hodnoty primitivov nainicializovanych jvm defaultne na 0
        for (int i = 0; i < pole1.length; i++) {
            System.out.println("pole1 " + i + ". hodnota:" + pole1[i]);
        }

        //pole2 obsahuje hodnoty prazdnych referencii cize null
        for (int i = 0; i < pole2.length; i++) {
            System.out.println("pole2 " + i + ". hodnota:" + pole2[i]);
        }

        System.out.println();

        //INICIALIZACIA pola
        pole1[1] = 1;
        pole2[1] = new String("som druhy");

        for (int i = 0; i < pole1.length; i++) {
            System.out.println("pole1 " + i + ". hodnota:" + pole1[i]);
        }

        for (int i = 0; i < pole2.length; i++) {
            System.out.println("pole2 " + i + ". hodnota:" + pole2[i]);
        }

        //DEKLARACIA, KONSTRUKCIA, INICIALIZACIA na jednom riadku
        System.out.println();
        int[] pole3 = {0, 1, 2};
        String druhy = "som druhy";
        String[] pole4 = {"som prvy", druhy,};

        for (int i = 0; i < pole3.length; i++) {
            System.out.println("pole3 " + i + ". hodnota:" + pole3[i]);
        }

        for (int i = 0; i < pole4.length; i++) {
            System.out.println("pole4 " + i + ". hodnota:" + pole4[i]);
        }

        //ANONYMNA INICIALIZACIA
        //pole ktore nemusim priradit do ziadnej premennej a mozem ho napr pouzit v runtime ako vstupny parameter do metody
        dajMiPole(new int[]{11, 12, 13, 14});

        //VIAC(DVOJ)ROZMERNE POLE
        int[][] pole1_1 = new int[1][];//jvm potrebuje vediet iba velkost objektu prideleneho k pole1_1, ale polia niesu v pripade primitivu nainicializovane
        pole1_1[0] = new int[2];//tento riadok musim pridat v pripade, ze som v riadku hore do druheho rozmeru nezadal hodnotu napr. int[3][2]
        pole1_1[0][0] = 1;//pozor toto nemozem ale urobit ak riadok o jedno vyssie je zakomentovany, musim vytvorit referenciu na pole

        String[][] pole1_2 = new String[3][];
        int[][] pole1_3 = {{0, 1}, {0, 1, 2}};

        //Vypis vicrozmerneho pola v cykle
        System.out.println("VYpis viacrozmer. pola v cykle");
        for (int i = 0; i < pole1_1.length; i++) {
            int[] ref = pole1_1[i];
            for (int j = 0; j < ref.length; j++) {
                System.out.println(ref[j]);
            }
        }
        //zjednoduseny zapis
        for (int[] ref : pole1_1) {
            for (int j: ref) {
                System.out.println(j);
            }
        }

        //Obj referencie a polia
        Car[] cars = {new Car(), new Car()};
        Honda[] hondas = {new Honda(), new Honda()};
        cars = hondas;//dovolene kompatibilne typy vo vnutry pola
        //hondas = cars;//nedovolene nekompatibilne typy v poly

        //Pomocna trieda Arrays

        Arrays.asList(new String[]{"a", "b", "c"});
        String[] objArr = {"a", "b", "c"};
        System.out.println("Binarysearch:" + Arrays.binarySearch(objArr, "b"));
        String[] objCopy = Arrays.copyOf(objArr, 5);//vrati kopiu pola s pozadovanou dlzkou ci uz s odrezanou alebo pridanou(null) init hodnotou
        boolean isDeepEqual = Arrays.deepEquals(objArr, objCopy);
        boolean isEqual = Arrays.equals(objArr, objCopy);
        String[] objArr1 = new String[3];
        Arrays.fill(objArr1, "a");
        int[] arrCalc = {1, 3, 2};
        Arrays.sort(arrCalc);
        //od java 8 moznost sortovat a modifikovat polia paralne =java robi operaciu vo viacerych vlaknach => meno funkcie zacina parallel
//        arrCalc = new int[] {1,2,3};
        Arrays.parallelPrefix(arrCalc, (a, b) -> a + b);//upravujem vysledok na predchadzajucom vysledku 1+0, 2+1, 3+3
        arrCalc = new int[]{2, 1, 3};
        Arrays.parallelSort(arrCalc);
        //Arrays.spliterator(arrCalc); //v java 8 pre paralelne spracovanie dat mozne pouzit v java 7 fork/join framework
        //Arrays.stream(arrCalc);//v java 8 mozno vyuzit pracu so stream-ami
        System.out.println(Arrays.toString(arrCalc));

        //ine pomocne implementacie pre pracu s poliami => nieje sucastou SDK => samostudium
        //https://commons.apache.org/proper/commons-lang/apidocs/org/apache/commons/lang3/ArrayUtils.html
    }

    private static void dajMiPole(int[] pole) {
        System.out.println();
        System.out.println("dajMiPole vypis:");
        for (int i = 0; i < pole.length; i++) {
            System.out.println("pole4 " + i + ". hodnota:" + pole[i]);
        }
        System.out.println();
    }
}
class Car{}
class Honda extends Car {}
