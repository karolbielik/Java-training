package com.insdata.streams;

import com.insdata.strings.bufferAndBuilder.StringBuilders;

import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by karol.bielik on 17.7.2017.
 */
public class StreamsTest {


    public static void main(String[] args) {
        // optional je objekt ktory moze byt vrateny z vacsiny funkcii stream
        //sluzi vlastne nato, ze v niektorych pripadoch este vysledna hodnota nieje jasna
        Optional<Double> optional = Optional.ofNullable(null);//v pripade ak je parameter null, tak sa vlastne vrati new Optional<>(); pomocou metody Optional.empty()
        try {
            Optional<Double> optional1 = Optional.of(null);//vrati NullPointerException
        }catch(NullPointerException npx){

        }
        Optional<Double> optional2 = Optional.empty();//vrati new Optional<>();

        Optional<Double> doubleOptional1 = Optional.of((double)21/4);

        /*
        * Metoda                    |   Ked Optional je Empty   |   Ked Optional obsahuje hodnotu
        *
        * get()                         vyhodi RuntimeException     vrati hodnotu
        * ifPresent(Consumer c)         nic nerobi                  vyvola c s hodnotou
        * isPresent()                   vrati false                 vrati true
        * orElse(T other)               vrati other param.          vrati hodnotu
        * orElseGet(Supplier s)         vrati vysledok volania      vrati hodnotu
        *                               Supplier
        * orElseThrow(Supplier s)       vyhodi Exception vytvorenu  vrati hodnotu
        *                               volanim Supplier
        * */
        //vyhodi NullPointerException
        try {
            Double d = optional.get();
        }catch (NoSuchElementException nsex){
            System.out.println("Double d = optional.get(); => skoncilo s NoSuchElementException");
        }
            System.out.println("doubleOptional1 ma hodnotu:"+doubleOptional1.get());

        /*-----------------------------------------Praca so streamami--------------------------------------------------*/
        //Moznosti vytvarania streamu
        Stream<String> stringStream = Stream.empty();
        Stream<String> stringStreamSingle = Stream.of("1");
        Stream<String> stringStreamFromArr = Stream.of("1","2","3");

        List<String> arrayList = Arrays.asList("1", "2", "3");
        Stream<String> streamFromList = arrayList.stream();
        Stream<String> streamFromListParallel = arrayList.parallelStream();

        /*-------------------------------------------NEKONECNY STREAM--------------------------------------------------*/
        /*--------------------------------------generate()-------------------------------------------------------------*/
        Supplier<String> stringSupplier = ()->"Hello";
        Stream<String> infStream = Stream.generate(stringSupplier);
        /*--------------------------------------iterate()--------------------------------------------------------------*/
        Stream.iterate("", s -> s+(s.charAt(s.length()-1)+1) );

        /*-------------------------------TERMINALNE operacie na streamoch----------------------------------------------*/
        /*redukcie su specialne terminalne operacie pri ktorych obsah streamu je vrateny v jedne primitivnej hodnote alebo v Objekte*/

        /*--------------------------------------count()----------------------------------------------------------------*/
        //count() - pre nekonecny stream visi
        Stream<String> streamOfAnimals = Stream.of("monkey", "donkey", "dog");
        System.out.println("streamOfAnimals pocet prvkov:"+streamOfAnimals.count());

        //ak bola na streame uz vykonana terminalna(vykonavacia) operacia tak sa musi stream
        //znova nainicializovat, pretoze inak dostaneme IllegalStateException
        List<String> listOfAnnimals = Arrays.asList("monkey", "donkey", "dog");
        streamOfAnimals = listOfAnnimals.stream();

        /*-------------------------------------min(),max()-------------------------------------------------------------*/
        //min a max funkcie pozaduju ako vstupny param. comparator
        // - pre nekonecny stream visi
        Comparator<String> stringComparator = (a,b)->a.compareTo(b);
        System.out.println("streamOfAnimals min prvok:"+ streamOfAnimals.min(stringComparator));
        streamOfAnimals = listOfAnnimals.stream();
        System.out.println("streamOfAnimals max prvok:"+ streamOfAnimals.max(stringComparator));

        /*-------------------------------------findAny(),findFirst()---------------------------------------------------*/
        //funguje aj s infinite stremom
        streamOfAnimals = listOfAnnimals.stream();
        System.out.println("streamOfAnimals findAny prvok:"+ streamOfAnimals.findAny());

        //funguje aj s infinite stremom
        streamOfAnimals = listOfAnnimals.stream();
        System.out.println("streamOfAnimals findFirst prvok:"+ streamOfAnimals.findFirst());

        //xxxMatch funkcie pozaduju ako vstupny param. Predicate ktory ma jeden param typu String vracia true/false
        Predicate<String> predicate = s -> s.startsWith("do");

        /*-------------------------------------allMatch(),anyMatch(),noneMatch()---------------------------------------*/
        //ci funguju alebo nie s infinite streamom zalezi na datach ake su v streame a na podmienke v predikate
        streamOfAnimals = listOfAnnimals.stream();
        System.out.println("streamOfAnimals allMatch vysledok:"+ streamOfAnimals.allMatch(predicate));

        streamOfAnimals = listOfAnnimals.stream();
        System.out.println("streamOfAnimals anyMatch vysledok:"+ streamOfAnimals.anyMatch(predicate));

        streamOfAnimals = listOfAnnimals.stream();
        System.out.println("streamOfAnimals noneMatch vysledok:"+ streamOfAnimals.noneMatch(predicate));

        /*-------------------------------------forEach()---------------------------------------------------------------*/
        //foreach pre infinete stream funguje ale sa neukonci, musime zhodit proces
        streamOfAnimals = listOfAnnimals.stream();
        streamOfAnimals.forEach(s->System.out.println("streamOfAnimals noneMatch vysledok:"+ s));
//        streamOfAnimals.forEach(System.out::println);

        /*-------------------------------------reduce()----------------------------------------------------------------*/
        streamOfAnimals = listOfAnnimals.stream();
        System.out.println(streamOfAnimals.reduce((a,b)->a+b));

        streamOfAnimals = listOfAnnimals.stream();
        System.out.println(streamOfAnimals.reduce((a,b)->a+b));
        //System.out.println(streamOfAnimals.reduce(String::concat));

        streamOfAnimals = listOfAnnimals.stream();
        //prvy param je init hodnota, druhy je BinaryOperator, ktorym akumulujem vsetky hodnoty do jednej vyslednej
        System.out.println(streamOfAnimals.reduce("init_hodnota:",(a,b)->a+b));
//        System.out.println(streamOfAnimals.reduce("init_hodnota:",String::concat));

        streamOfAnimals = listOfAnnimals.stream();
        //druhy parameter je BiFunction ktory ma rovnaku funkciu ako v predchadzajucom pripade
        //treti parameter nieje pre neparalelny stream volany
        System.out.println(streamOfAnimals.reduce("init_hodnota", String::concat, (t,u)->","));

        //paralelne stremy vyuzivame len pre vacsie mnozstvo dat v streame
        /*The three parameters are identity, reducer, and combiner.
            - identity - identity value for the combiner function
            - reducer - function for combining two results
            - combiner - function for adding an additional element into a result.*/
        //combiner funguje len s paralelnymi streamami
        List<Integer> listOfNumbers = Arrays.asList(2,3,4,5,6);
        //s neparalelnym stream
        //Stream<Integer> streamOfNumbers = listOfNumbers.stream();
        //s paralelnym stream
        Stream<Integer> streamOfNumbers = listOfNumbers.parallelStream();
        //vypis id vlakien s vysledkom a nakoniec celkovy vysledom
        //bez combinera je vysledok = 2*3*4*5*6=720
//        System.out.println(streamOfNumbers.reduce(1, (t,u)-> { System.out.println("Thread Id:"+Thread.currentThread().getId()+":"+t+"*"+u+"="+t*u); return t * u; }));

        //s combinerom sa jednotlive vysledky z akumulatora kombinuju na zaklade logiky v combinery
        //nasledujuci priklad vrati: 1*2+1*3+1*4+1*5+1*6 = 20  => identity je vzdy vstupny parameter t pre accumulator,
        //jednotlive vysledky accumulatora su potom skombinovane v combinery
        System.out.println(streamOfNumbers.reduce(1
                , /*accumulator*/(t,u)-> {System.out.println("Thread Id:"+Thread.currentThread().getId()+":"+t+"*"+u+"="+(t*u)); return t*u;}
                , /*combiner*/(p,r)-> {System.out.println("Thread Id:"+Thread.currentThread().getId()+":"+p+"+"+r+"="+(p+r)); return p+r;}));

        /*-------------------------------------collect()---------------------------------------------------------------*/

        //collect funkcia je tzv. mutable redukcna funkcia. Ine typicke mutable objekty su StringBuilder, ArrayList
        //je ucinnejsia ako reduce funkcia, pretoze pracujeme stale nad tym istym mutable objektom
        streamOfAnimals = listOfAnnimals.parallelStream();
        //!nemozem pouzit Integer(je immutable) ako v reduce lebo robim zmeny na mutable objekte, teda na obsahu samotneho objektu
        System.out.println(streamOfAnimals.collect(()-> new StringBuilder()
                                , (response,element)-> response.append(" ").append(element)//toto je volane pre kazdy element
                                , (response1, response2) -> response1.append(",").append(response2)));//toto je volane pre kazdy vysledok z predchadzajuceho riadku
        //podobne ako toto nad vieme dosiahnut nasledovne pomocou Collectors
        streamOfAnimals = listOfAnnimals.parallelStream();
        System.out.println(streamOfAnimals.collect(Collectors.joining(", ")));


        //ak chceme v collect pouzit sumarizaciu Integer,Double,Long(nieje mutable) musime pouzit Collectors
        streamOfNumbers = listOfNumbers.parallelStream();
        System.out.println(streamOfNumbers.collect(Collectors.summarizingInt((i)-> i )));

        Stream<String> streamOfStringNumbers = Arrays.asList("2","3","4","5","6").parallelStream();
        System.out.println(streamOfStringNumbers.collect(Collectors.summarizingInt((i)-> new Integer(i) )));


        /*------------------------------INTERMEDIATE operacie na streamoch---------------------------------------------*/
        /*------------------------------------limit(), skip()----------------------------------------------------------*/
        Stream<String> streamOfGeneratedStrings = Stream.iterate("a", s -> { int i = s.charAt(s.length()-1)+1; char r = (char)i; return s + r; });
        streamOfGeneratedStrings/*.skip(2)*/.limit(10).forEach(System.out::println);
        /*---------------------------------------map()-----------------------------------------------------------------*/
        //sluzi na transformaciu dat
        streamOfAnimals = listOfAnnimals.stream();
        streamOfAnimals.map(String::length).forEach(System.out::println);
        /*---------------------------------------flatMap()-------------------------------------------------------------*/
        //flatMap - zoberie kazdy prvok v streame vsetky elementy ktore stream obsahuje da do top-level urovne jedneho streamu
        //prazdne listy(s nula prvkami) ktore stream obsahuje do vysledku nezahrnie
        Stream<List<String>> animals = Stream.of(listOfAnnimals,listOfAnnimals,Arrays.asList("cat", "dove", "bird"));
        animals.flatMap(l->l.stream()).forEach(System.out::println);
        /*----------------------------------------sorted()-------------------------------------------------------------*/
        //sorted - funguje ako sortovanie arrays, defaultne sa pouziva prirodzene zoradenie
        streamOfAnimals = listOfAnnimals.stream();
        streamOfAnimals.sorted().forEach(System.out::println);

        streamOfAnimals = listOfAnnimals.stream();
        streamOfAnimals.sorted(Comparator.reverseOrder()).forEach(System.out::println);

        /*------------------------------------------peek()-------------------------------------------------------------*/
        //peek - vystupom je obsah streamu, sluzi na debug ucely, nijako neovplyvnuje vysledok
        streamOfAnimals = listOfAnnimals.stream();
        System.out.println(streamOfAnimals.peek(s->System.out.print(s+",")).filter(s->s.startsWith("d")).count());
    }
}
