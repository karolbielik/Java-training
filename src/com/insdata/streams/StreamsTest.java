package com.insdata.streams;

import java.util.*;
import java.util.function.Function;
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
        System.out.println("streamOfAnimals min prvok:"+/*returns Optional*/ streamOfAnimals.min(stringComparator));
        streamOfAnimals = listOfAnnimals.stream();
        System.out.println("streamOfAnimals max prvok:"+/*returns Optional*/ streamOfAnimals.max(stringComparator));

        /*-------------------------------------findAny(),findFirst()---------------------------------------------------*/
        //funguje aj s infinite stremom
        streamOfAnimals = listOfAnnimals.stream();
        System.out.println("streamOfAnimals findAny prvok:"+/*returns Optional*/ streamOfAnimals.findAny());

        //funguje aj s infinite stremom
        streamOfAnimals = listOfAnnimals.stream();
        System.out.println("streamOfAnimals findFirst prvok:"+/*returns Optional*/ streamOfAnimals.findFirst());

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
        System.out.println(/*returns Optional*/streamOfAnimals.reduce((a,b)->a+b));

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
        //flatMap - zoberie kazdy prvok v streame, vsetky elementy ktore stream obsahuje da do top-level urovne jedneho streamu
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

        /*------------------------------------------------zozbieravanie vysledkov(Collectors)--------------------------*/
        //Basic collectors
        //collectors predavame ako parameter do funkcie collect
        Supplier<Stream<String>> animalsSupplier = ()->Stream.of("bear", "donkey", "monkey", "mouse");
        Stream<String> animalsStream = animalsSupplier.get();
        System.out.println("Average length of animal strings:"+animalsStream.collect(Collectors.averagingInt(String::length)));

        //mozeme pracovat stream sposobom a nakonci vysledok zase dat do podoby pred java8 teda Stream na List
        System.out.println("Filtered animal array list:"+ animalsSupplier.get().filter((s)->s.startsWith("m")).collect(Collectors.toList()));

        //collecting do map
        Map<Integer, String> animalsMap = animalsSupplier.get().collect(Collectors.toMap(s-> Math.abs(s.hashCode()), Function.identity()/*s->s*/));
        System.out.println("animal Map:"+animalsMap);
        //ak mame mapu s rovnakym klucom musime povedat collectoru co s tym ma robit
        //toto skonci s chybou duplicate key
//        animalsMap = animalsSupplier.get().collect(Collectors.toMap(s-> s.length(), Function.identity()/*s->s*/));
        //treti parameter nam urcuje co sa ma robit ak pride ku kolizii hodnot asociovanymi pod jednym klucom
        animalsMap = animalsSupplier.get().collect(Collectors.toMap(s-> s.length(), Function.identity()/*s->s*/,(u, u2) -> "->"+u+","+u2));
        System.out.println("animal Map s rovnakym klucom:"+animalsMap);
        //stvrty parameter urcuje aky typ sa ma vratit
        animalsMap = animalsSupplier.get().collect(Collectors.toMap(s-> s.length(), Function.identity()/*s->s*/,(u, u2) -> "->"+u+","+u2, TreeMap::new));
        System.out.println("animal TreeMap s rovnakym klucom:"+animalsMap);

        //------------------------------------------------zgrupovanie---------------------------------------------------
        Map<Integer, List<String>> zgrupnutaMapa = animalsSupplier.get().collect(Collectors.groupingBy(String::length));
        //ked chcem vysledok v mape do setu
        Map<Integer, Set<String>> zgrupnutaMapaDoSetu = animalsSupplier.get().collect(Collectors.groupingBy(String::length, Collectors.toSet()));
        //ked chcem zmenit aj mapu
        TreeMap<Integer, Set<String>> zgrupnutaTreeMapaDoSetu = animalsSupplier.get().collect(Collectors.groupingBy(String::length, TreeMap::new, Collectors.toSet()));
        System.out.println("zgrupnutaMapa:"+zgrupnutaMapa);
        //urcenie poctu clenov na zaklade pravidla
        Map<Integer, Long> countingMembers = animalsSupplier.get().collect(Collectors.groupingBy(String::length,Collectors.counting()));
        //mapping collector
//        Map<Integer, Optional<Character>> mapaZgrupnutaAZoradenaPodlaZaciatPismena = animalsSupplier.get().collect(Collectors.groupingBy(
//                 String::length,
//                 Collectors.mapping(
//                         s->s.charAt(0),
//                         Collectors.minBy(Comparator.naturalOrder()))));

        //partitioning - je rozdelenie hodnot streamu do mapy ktora ma vzdy dva kluce true/false na zaklade nejakeho pravidla
        Map<Boolean, List<String>> particiaZvierat = animalsSupplier.get().collect(Collectors.partitioningBy(s->s.length()<=5));
        System.out.println("particiaZvierat:"+particiaZvierat);


        /*----------------------------------Paralelny pristup -------------------------------------------------------*/
        Arrays.asList(1,2,3,4,5,6,7,8).stream().forEach(s -> System.out.print(s+" "));
        //mozne pracovat s paralelnymi streamamy na Collection interface cez parallelStream metodu
        Arrays.asList(1,2,3,4,5,6,7,8).parallelStream().forEach(s -> System.out.print(s+" "));
        //alebo priamo na Stream pracovat z parallel streamom
        Stream<Integer> intStream = Stream.of(1,2,3,4,5,6,7,8);
        //kedze toto je spracovavane paralelne, tak vystup bude vzdy nepredvidatelny
        intStream.parallel().forEach(s -> System.out.print(s+" "));
        //s nejakou reziou navyse zarucime, ze vypis bude v poradi
        intStream = Stream.of(1,2,3,4,5,6,7,8);
        intStream.parallel().forEachOrdered(s -> System.out.print(s+" "));

        intStream = Stream.of(1,2,3,4,5,6,7,8);
        //vysledok je vzdy nepredpokladatelny, kedze sa jedna o paralelne spracovanie
        System.out.println(intStream.parallel().findAny().get());
        //findFirst, limit, skip pracuju pomalsie v paralelnom prostredi, pretoze paralelny task musi v tomto
        //pripade koordinovat thready v synchronizovanym sposobom.
        intStream = Stream.of(1,2,3,4,5,6,7,8);
        //potom vrati vzdy konsistentny vysledok aj na seriovom aj paralelnom streame
        System.out.println(intStream.skip(5).limit(2).findFirst());

        intStream = Stream.of(1,2,3,4,5,6,7,8);
        //unordered stream povie JVM, ze ak by mal uplatnit ordered-based operaciu, tak poradie moze ignorovat
        //napr. skip(5) nepreskoci prvych 5 prvkov, ale 5 nejakych prvkov
        //tymto sposobom sa moze zrychlit paralelne spracovanie
        intStream.unordered().parallel().skip(3).forEach(s->System.out.print(s+" "));

        //paralelne streamy v pripade reduce funkcie sa spravaju rovnako ako synchronne v pripade, ze
        //dodrzime pravidla parametrov funkcie reduce
        //prvy parameter identity combiner.apply(identity,u == u)
        //druhy param. accumulator musi byt asociativny (a op b)opc == a op (b op c)
        //treti param. combiner musi by asociativny combiner.apply(u,accumulator.apply(identity,t)) == accumulator.apply(u,t)
        intStream = Stream.of(1,2,3,4,5,6,7,8);
        intStream.parallel().reduce(0, (a,b)-> a+b);//bude vracat rovnaky vysledok ako synchronny stream

        intStream = Stream.of(1,2,3,4,5,6,7,8);
        intStream.parallel().reduce(0, (a,b)-> a-b);//tu je problem lebo odcitanie nieje asociativny operator

        intStream = Stream.of(1,2,3,4,5,6,7,8);
        intStream.parallel().reduce(1, (a,b)-> a+b);//tu je takisto problem lebo sme nedodrzali pravidlo pre prvy parameter
    }
}
