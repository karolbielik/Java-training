package com.insdata.streams;

import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by karol.bielik on 17.7.2017.
 */
public class Test02Streams {
    public static void main(String[] args) {
        /*
            Stream je sekvencia dat. Stream pipeline su operacie beziace nad streamom veduce v vysledku.

            Source -> Intermediate operations -> Output from terminal operation
            Source - zdroj odkial je Stream
            Intermediate operations - transformuje stream na dalsi stream
            Terminal operation - produkuje vysledok
        */
        /*-----------------------------------------Praca so streamami--------------------------------------------------*/
        //---------------------------------------Moznosti vytvarania streamu--------------------
        Stream<String> stringStream = Stream.empty();
        Stream<String> stringStreamSingle = Stream.of("1");
        Stream<String> stringStreamFromArr = Stream.of("1","2","3");

        List<String> arrayList = Arrays.asList("1", "2", "3");
        Stream<String> streamFromList = arrayList.stream();
        Stream<String> streamFromListParallel = arrayList.parallelStream();

        /*-------------------------------------------NEKONECNY STREAM--------------------------------------------------*/
        /*--------------------------------------generate()--------------------------*/
        Supplier<Double> randomNumberSupplier = Math::random;
        Stream<Double> randomNumberInfiniteStream = Stream.generate(randomNumberSupplier);
        //nekonecny cyklus ktory bude vypisovat nahodne cisla az kym nezhodim program,
        // musi byt preto teza zakomentovany
//        randomNumberInfiniteStream.forEach(System.out::println);
        /*--------------------------------------iterate()---------------------------*/
        Supplier<Stream<String>> endlessStringStream = ()-> Stream.iterate("-", s -> {
                                                                int newValue = s.charAt(s.length()-1)+1;
                                                                char ch = ((char)newValue);
                                                                return String.valueOf(ch);
                                                            });
        Stream<String> endlessString = endlessStringStream.get();
                //musi byt zakomentovane inac by to blokovalo process
//        endlessString.forEach(System.out::println);

        /*-------------------------------TERMINALNE operacie na streamoch----------------------------------------------*/
        /*redukcie su specialne terminalne operacie pri ktorych obsah streamu
        je vrateny v jedne primitivnej hodnote alebo v Objekte*/

        /*--------------------------------------count()---------------------------------*/
        // - pre nekonecny stream visi, je to redukcna funkcia, vracia long
        Stream<String> streamOfAnimals = Stream.of("monkey", "donkey", "dog");
        System.out.println("streamOfAnimals pocet prvkov:"+streamOfAnimals.count());

        //ak bola na streame uz vykonana terminalna(vykonavacia) operacia tak sa musi stream
        //znova nainicializovat, pretoze inak dostaneme IllegalStateException
        List<String> listOfAnimals = Arrays.asList("monkey", "donkey", "dog");
        streamOfAnimals = listOfAnimals.stream();

        /*-------------------------------------min(),max()-------------------------------*/
        //min a max funkcie pozaduju ako vstupny param. comparator
        // - pre nekonecny stream visi, su redukcne, vracaju Optional<T>
        //na prazdnom stream sa komparator ani nespusti
        Comparator<String> stringComparator = String::compareTo;//(a,b)->a.compareTo(b);
        System.out.println("streamOfAnimals min prvok:"+/*returns Optional*/ streamOfAnimals.min(stringComparator));
        streamOfAnimals = listOfAnimals.stream();
        System.out.println("streamOfAnimals max prvok:"+/*returns Optional*/ streamOfAnimals.max(stringComparator));

        /*-------------------------------------findAny(),findFirst()---------------------*/
        //funguje aj s infinite stremom, nieje redukcna funkcia, vracia Optional<T>
        //vrati element streamu pokial stream nije prazdny, ak je prazdny, tak vrati empty optional.
        streamOfAnimals = listOfAnimals.stream();
        //Pouziva sa pri parallelnych streamoch. Vracia element, ktory prichadza ako prvy oproti tomu, ktory
        //je celkovo prvy v streame. Nebude sa mrhat cas na generovanie vsetkych elementov.
        //Vrati "reprezentativnu vzorku"
        System.out.println("Endless string findAny "+endlessStringStream.get().parallel().findAny());

        //funguje aj s infinite streamom
        streamOfAnimals = listOfAnimals.stream();
        System.out.println("streamOfAnimals findFirst prvok:"+/*returns Optional*/ streamOfAnimals.findFirst());

        /*-------------------------------------allMatch(),anyMatch(),noneMatch()------------*/
        //xxxMatch funkcie pozaduju ako vstupny param. Predicate ktory ma jeden param typu String vracia true/false
        Predicate<String> predicate = s -> s.startsWith("do");

        //ci funguju alebo nie s infinite streamom zalezi na datach ake su v streame a na podmienke v predikate
        //niesu redukcne funkcie, vracaju boolean
        streamOfAnimals = listOfAnimals.stream();
        System.out.println("streamOfAnimals allMatch vysledok:"+ streamOfAnimals.allMatch(predicate));

        streamOfAnimals = listOfAnimals.stream();
        System.out.println("streamOfAnimals anyMatch vysledok:"+ streamOfAnimals.anyMatch(predicate));

        streamOfAnimals = listOfAnimals.stream();
        System.out.println("streamOfAnimals noneMatch vysledok:"+ streamOfAnimals.noneMatch(predicate));

        /*-------------------------------------forEach()-------------------------------------*/
        //foreach pre infinete stream funguje ale sa neukonci, musime zhodit proces
        //nieje redukcna funkcia, vracia void
        streamOfAnimals = listOfAnimals.stream();
        streamOfAnimals.forEach(s->System.out.println("streamOfAnimals forEach vysledok:"+ s));
//        streamOfAnimals.forEach(System.out::println);
        /*-------------------------------------forEachOrdered()-------------------------------*/
        //pri paralelnych streamoch zabezpecuje poradie
        listOfAnimals.stream().forEachOrdered((s)->System.out.println("streamOfAnimals forEachOrdered vysledok:"+s));

        /*-------------------------------------reduce()--------------------------------------*/
        // - pre nekonecny stream visi, je redukcna funkcia, vracia Optional<T>
        streamOfAnimals = listOfAnimals.stream();
        System.out.println(streamOfAnimals.reduce((a,b)->a+b));
        //System.out.println(streamOfAnimals.reduce(String::concat));

        streamOfAnimals = listOfAnimals.stream();
        //prvy param je init hodnota, druhy je BinaryOperator, ktorym akumulujem vsetky hodnoty do jednej vyslednej
        System.out.println(streamOfAnimals.reduce("init_hodnota:",(a,b)->a+b));
//        System.out.println(streamOfAnimals.reduce("init_hodnota:",String::concat));

        streamOfAnimals = listOfAnimals.stream();
        //druhy parameter je BiFunction ktory ma rovnaku funkciu ako v predchadzajucom pripade
        //treti parameter nieje pre neparalelny stream volany
        System.out.println(streamOfAnimals.reduce("init_hodnota", String::concat, (t,u)->","));
        streamOfAnimals = listOfAnimals.stream();
        System.out.println(streamOfAnimals.parallel().reduce("init_hodnota", String::concat, (t,u)->t+","+u));

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
        System.out.println(streamOfNumbers.reduce(1,
                (t,u)-> { System.out.println("Thread Id:"+Thread.currentThread().getId()+":"+t+"*"+u+"="+t*u); return t * u; }));

        //s combinerom sa jednotlive vysledky z akumulatora kombinuju na zaklade logiky v combinery
        //nasledujuci priklad vrati: 1*2+1*3+1*4+1*5+1*6 = 20  => identity je vzdy vstupny parameter t pre accumulator,
        //jednotlive vysledky accumulatora su potom skombinovane v combinery
        listOfNumbers = Arrays.asList(2,3,4,5,6);
        streamOfNumbers = listOfNumbers.parallelStream();
        System.out.println(streamOfNumbers.reduce(1
                , /*accumulator*/(t,u)-> {System.out.println("Thread Id:"+Thread.currentThread().getId()+":"+t+"*"+u+"="+(t*u)); return t*u;}
                , /*combiner*/(p,r)-> {System.out.println("Thread Id:"+Thread.currentThread().getId()+":"+p+"+"+r+"="+(p+r)); return p+r;}));

        /*-------------------------------------collect()------------------------------------*/
        //collect funkcia je tzv. mutable redukcna funkcia. Ine typicke mutable objekty su StringBuilder, ArrayList
        //Je ucinnejsia ako reduce funkcia, pretoze pracujeme stale nad tym istym mutable objektom
        streamOfAnimals = listOfAnimals.parallelStream();
        //!nemozem pouzit String(je immutable) ako v reduce lebo robim zmeny na mutable objekte,
        // teda na obsahu samotneho objektu
        System.out.println(streamOfAnimals.collect(()-> new StringBuilder()
                                , (response,element)-> response.append(" ").append(element)//toto je volane pre kazdy element
                                , (response1, response2) -> response1.append(",").append(response2)));//toto je volane pre kazdy vysledok z predchadzajuceho riadku
        //----------------------------------Collectors-------------------------------------------------------------
        //existuju v Collectors preddefinovane kolektory, ktore nam vysledky zozbieraju do Listu alebo Setu
        //alebo typu ktory uvedieme v suppliery, nemusime potom vypisovat accumulator a combiner
        List<String> listResult = listOfAnimals.stream().collect(Collectors.toList());
        System.out.println(listResult.getClass().getName()+" : "+listResult);
        Set<String> setResult = listOfAnimals.stream().collect(Collectors.toSet());
        System.out.println(setResult.getClass().getName()+" : "+setResult);
        TreeSet<String> treeResult = listOfAnimals.stream().collect(Collectors.toCollection(TreeSet::new));
        System.out.println(treeResult.getClass().getName()+" : "+treeResult);

        /*------------------------------INTERMEDIATE operacie na streamoch---------------------------------------------*/
        /*---------------------------------------filter()---------------------------------*/
        listOfAnimals.stream().filter((s -> s.startsWith("m"))).forEach(System.out::println);
        /*---------------------------------------distinct()---------------------------------*/
        Supplier<Stream<String>> duplicateAnimals = () -> Stream.of("monkey", "donkey", "dog", "donkey", "cat", "monkey");
        duplicateAnimals.get().forEach(System.out::println);
        duplicateAnimals.get().distinct().forEach(System.out::println);
        /*------------------------------------limit(), skip()--------------------------*/
        //spravi z nekonecneho stream konecny
        Stream<String> streamOfGeneratedStrings = Stream.iterate("a", s -> { int i = s.charAt(s.length()-1)+1; char r = (char)i; return s + r; });
        streamOfGeneratedStrings/*.skip(2)*/.limit(10).forEach(System.out::println);
        /*---------------------------------------map()---------------------------------*/
        //sluzi na transformaciu dat
        streamOfAnimals = listOfAnimals.stream();
        streamOfAnimals.map(String::length).forEach(System.out::println);
        /*---------------------------------------flatMap()-----------------------------*/
        //flatMap - zoberie kazdy prvok v streame, vsetky elementy ktore stream obsahuje da do top-level urovne jedneho streamu
        //prazdne listy(s nula prvkami) ktore stream obsahuje do vysledku nezahrnie
        Stream<List<String>> animals = Stream.of(listOfAnimals, Arrays.asList(), listOfAnimals,Arrays.asList("cat", "dove", "bird"));
        animals.flatMap(l->l.stream()).forEach(System.out::println);
        /*----------------------------------------sorted()-----------------------------*/
        //sorted - funguje ako sortovanie arrays, defaultne sa pouziva prirodzene zoradenie
        streamOfAnimals = listOfAnimals.stream();
        streamOfAnimals.sorted().forEach(System.out::println);

        streamOfAnimals = listOfAnimals.stream();
        streamOfAnimals.sorted(Comparator.reverseOrder()).forEach(System.out::println);

        /*------------------------------------------peek()------------------------------*/
        //peek - vystupom je obsah streamu, sluzi na debug ucely, nijako neovplyvnuje vysledok
        streamOfAnimals = listOfAnimals.stream();
        System.out.println(streamOfAnimals
                .peek(s->System.out.println(s+","))
                .filter(s->s.startsWith("d"))
                .peek(s->System.out.println(s+","))
                .count());

        //--------------------------IllegalState Exception--------------------------------
        //na stream je mozne pouzit terminalnu operaciu len raz
        Supplier<Stream<Integer>> streamIntegerSupplier = ()-> Stream.of(1,2,3,4,5,6,7,8,9);
        Stream<Integer> streamNumbers = streamIntegerSupplier.get();
        streamNumbers.filter((n)->n%2==0).filter((n)->n>4).forEach(System.out::print);
        //toto vyhodi IllegalStateException pretoze 2x pouzivame foreach na to m istom streame
//        streamNumbers.forEach(System.out::print);
        System.out.println();
        //potom potrebujem spravit novy stream
        streamNumbers = streamIntegerSupplier.get();
        streamNumbers.forEach(System.out::print);

        //---------------------------------------spajanie intermediate operacii--------------------------------------
        Stream<Integer> postupnost = Stream.iterate(1,x->x+1);
        postupnost.skip(2)
                .limit(9)
//                .sorted()//sorted sposobi vysenie procesu, ak ho dame za limit tak potom z infinite bude finite a nebude vysiet
                .filter(x->x%2==0)
                .map(x-> x.toString())
                .map(x-> Double.valueOf(x))
                .map(x -> String.format("%1$,.2f", x))
                .forEach(System.out::println);

        /*----------------------------------Paralelny pristup -------------------------------------------------------*/
        Arrays.asList(1,2,3,4,5,6,7,8).stream().forEach(s -> System.out.print(s+" "));
        //mozne pracovat s paralelnymi streamamy na Collection interface cez parallelStream metodu
        Arrays.asList(1,2,3,4,5,6,7,8).parallelStream().forEach(s -> System.out.print(s+" "));
        //alebo priamo na Stream pracovat z parallel streamom
        Stream<Integer> intStream = streamIntegerSupplier.get();
        //kedze toto je spracovavane paralelne, tak vystup bude vzdy nepredvidatelny
        intStream.parallel().forEach(s -> System.out.print(s+" "));
        //s nejakou reziou navyse zarucime, ze vypis bude v poradi
        intStream = streamIntegerSupplier.get();
        intStream.parallel().forEachOrdered(s -> System.out.print(s+" "));

        intStream = streamIntegerSupplier.get();
        //vysledok je vzdy nepredpokladatelny, kedze sa jedna o paralelne spracovanie
        System.out.println(intStream.parallel().findAny().get());
        //findFirst, limit, skip pracuju pomalsie v paralelnom prostredi, pretoze paralelny task musi v tomto
        //pripade koordinovat thready v synchronizovanym sposobom.
        intStream = streamIntegerSupplier.get();
        //potom vrati vzdy konsistentny vysledok aj na seriovom aj paralelnom streame
        System.out.println(intStream.skip(5).limit(2).findFirst());

        intStream = streamIntegerSupplier.get();
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
