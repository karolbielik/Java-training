package com.insdata.streams;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test04Collectors {
    public static void main(String[] args) {

        //Priklad ako spravit concat pomocou collect metody s parametrami (supplier, accumulator, combiner)
        //a pomocou collect metody s parametrom (collector)
        List listOfAnimals = Arrays.asList("monkey", "donkey", "dog", "cat");
        Stream<String> streamOfAnimals = listOfAnimals.parallelStream();
        System.out.println(streamOfAnimals.collect(
                ()->new StringBuilder()
                ,(sb, str)->sb.append(str)
                ,(sb1, sb2)->sb1.append(", ").append(sb2)
        ));
        //podobne ako toto nad vieme dosiahnut nasledovne pomocou Collectors
        System.out.println(listOfAnimals.parallelStream().collect(Collectors.joining(", ")));

        Supplier<Stream<String>> supplyAnimals = () -> Stream.of("medved", "tava", "somar", "kobyla");
        //java aimplementuje rozne taketo collectory pre metodu collect
        //------------------------------------averagingDouble,averagingInt,averagingLong---------------
        //vypocita priemernu hodnotu z primitivnych typov v streame
        Stream<String> animals = supplyAnimals.get();
        //priemerna dlzka slov
        Double priemDlzka = animals.collect(Collectors.averagingInt(String::length));
        System.out.println("priemerna dlzka slov:"+priemDlzka);
        //------------------------------------------counting-------------------------------------------
        //spocita pocet elementov
        animals = supplyAnimals.get();
        Map<Integer, Long> animalGroupCount = animals.collect(Collectors.groupingBy(String::length, Collectors.counting()));
        System.out.println("Vysledok counting:"+animalGroupCount);
        //----------------------------------------groupingBy-------------------------------------------
        //vytvori mapu grup na zaklade specifikovanej funkcii
        animals = supplyAnimals.get();
        //kolektor organizuje mena zvierat do List grup na zaklade funkcie(String::length)tak,
        // ze kluc mapy je dlzka stringu
        //a hodnota je list obsahujuci vsetky zvierata, zodpovedajuce tejto dlzke
        Map<Integer, List<String>> groupedAnimals = animals.collect(Collectors.groupingBy(
                                                                                    String::length
                                                                                    //,TreeMap::new//ak chcem kontrolovat aka bude navratova mapa
                                                                                    //,Collectors.toSet()//a zaroven ak chcem ma v hodnotach Set a nie List
                                                                                ));
        System.out.println("Grouped animals:"+groupedAnimals);


        //-----------------------------------------joining---------------------------------------------
        //vid. prvy priklad hore
        //-----------------------------------------maxBy, minBy----------------------------------------
        //najde najvacsie/najmensie Optional<T> elementy
        //-----------------------------------------mapping---------------------------------------------
        //Prida dalsi level kolektorov
        Supplier<Stream<String>> supplyZmeska = ()->Stream.of("medved", "medaky", "tava", "tula", "somar", "sonar", "kobyla", "kibana");
        //chcem zgrupit dane stringy na zaklade dlzdy a potom vytiahnut prve zaciatocne pismena
        animals = supplyZmeska.get();
        Map<Integer, List<Character>> mappingAnimalsList = animals.collect(Collectors.groupingBy(String::length
                                            ,Collectors.mapping(
                                                s->s.charAt(0),
                                                Collectors.toList()
                                            )
                                    ));
        System.out.println("Vysledok mapping:"+mappingAnimalsList);
        //chcem zgrupit dane stringy na zaklade dlzdy a potom vytiahnut prve zaciatocne pismena bez opakovania
        animals = supplyZmeska.get();
        Map<Integer, Set<Character>> mappingAnimalsSet = animals.collect(Collectors.groupingBy(String::length
                ,Collectors.mapping(
                        s->s.charAt(0),
                        Collectors.toSet()
                )
        ));
        System.out.println("Vysledok mapping:"+mappingAnimalsSet);

        //----------------------------------------partitioningBy---------------------------------------
        //vytvori mapu grup na zaklade specifikovaneho predikatu
        //je podobne ako grouping, ale zgrupy elementy streamu do dvoch skupin na zakladen predicate funkcie
        //na grupu true a false
        animals = supplyAnimals.get();
        Map<Boolean, List<String>> partinionedAnimals = animals.collect(Collectors.partitioningBy(
                                                                                                    s->s.length()<=4
                                                                                                    //,Collectors.toSet()//ak chcem kontrolovat navratovy typ groupy
                                                                                                ));
        System.out.println("Vysledok partitioningBy:"+partinionedAnimals);

        //----------------------------------summarizingDouble, summarizingInt, summarizingLong---------
        //vypocita priemer, max, min ...atd.
        //ak chceme v collect pouzit sumarizaciu Integer,Double,Long(nieje mutable) musime pouzit Collectors
        //streamOfNumbers = listOfNumbers.parallelStream();
        //System.out.println(streamOfNumbers.collect(Collectors.summarizingInt((i)-> i )));
        Stream<String> streamOfStringNumbers = Arrays.asList("2","3","4","5","6").parallelStream();
        System.out.println(streamOfStringNumbers.collect(Collectors.summarizingInt((i)-> new Integer(i) )));

        //-----------------------------------summingDouble, summingInt, summingLong--------------------
        //vypocita sumu pre primitivov v streame
        animals = supplyAnimals.get();
        //spocitam dlzku vsetkych slov v streame
        Integer countLength = animals.collect(Collectors.summingInt(s->s.length()));

        //-------------------------------------------toList--------------------------------------------
        animals = supplyAnimals.get();
        List<String> animalsList = animals.filter(s -> s.startsWith("m"))
                .collect(Collectors.toList());
        System.out.println("[Vysledok funkcie toList]:"+animalsList+":[typ vrateneho listu]:"+animalsList.getClass().getName());
        //------------------------------------------toSet----------------------------------------------
        animals = supplyAnimals.get();
        Set<String> animalsSet = animals.filter(s -> s.startsWith("m"))
                .collect(Collectors.toSet());
        System.out.println("Vysledok funkcie toSet:"+animalsSet+":[typ vrateneho setu]:"+animalsSet.getClass().getName());
        //----------------------------------------toCollection-----------------------------------------
        //vytvori kolekciu specifikovaneho typu napr. TreeSet
        animals = supplyAnimals.get();
        TreeSet<String> animalsTree = animals.filter(s -> s.startsWith("m"))
                                             .collect(Collectors.toCollection(TreeSet::new));
        System.out.println("Vysledok funkcie toCollection:"+animalsTree+":[typ vratenej kolekcie]:"+animalsTree.getClass().getName());
        //--------------------------------------------toMap--------------------------------------------
        //vytvori mapu za pouzitia funkcii potrebnych k namapovaniu kluca a hodnoty
        animals = supplyAnimals.get();
        //prvy parameter je funkcia, ktora hovori o tom ako vytvorit kluc a druha ako vytvorit hodnotu
        //Function.identity() == s -> s
        Map<String, Integer> animalsMap = animals.collect(Collectors.toMap(Function.identity()/*s->s*/, String::length));
        try {
            //dva stringy maju rovnaku dlzku, co vedie k duplicate key exception
            animals = supplyAnimals.get();
            Map<Integer, String> animalsMapDuplKeyExc = animals.collect(Collectors.toMap(String::length, Function.identity()));
        }catch (IllegalStateException illstex){
            illstex.printStackTrace();
        }
        animals = supplyAnimals.get();
        //v tejto mape je klucom int = dlazka mena zvierata, co moze viest k chybe DuplicateKeyException
        //Preto potrebujeme v tretom porametry toMap zadat co sa ma robit v pripade kolizie klucov
        TreeMap<Integer, String> animalsTreeMap = animals.collect(Collectors.toMap(
                String::length
                ,Function.identity()
                ,(a,b)->a+","+b
                ,TreeMap::new//ak chcem kontrolovat akeho typu bude vratena mapa
        ));
    }
}
