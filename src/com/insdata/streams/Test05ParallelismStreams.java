package com.insdata.streams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test05ParallelismStreams {
    public static void main(String[] args) {
        Supplier<Stream<Integer>> streamIntegerSupplier = ()-> Stream.of(1,2,3,4,5,6,7,8,9);
        /*----------------------------------Paralelny pristup -------------------------------------------------------*/
        Arrays.asList(1,2,3,4,5,6,7,8).stream().forEach(s -> System.out.print(s+" "));
        System.out.println();
        //mozne pracovat s paralelnymi streamamy na Collection interface cez parallelStream metodu
        Arrays.asList(1,2,3,4,5,6,7,8).parallelStream().forEach(s -> System.out.print(s+" "));
        //alebo priamo na Stream pracovat z parallel streamom
        Stream<Integer> intStream = streamIntegerSupplier.get();
        System.out.println();
        //kedze toto je spracovavane paralelne, tak vystup bude vzdy nepredvidatelny
        intStream.parallel().forEach(s -> System.out.print(s+" "));
        System.out.println();
        //s nejakou reziou navyse zarucime, ze vypis bude v poradi
        intStream = streamIntegerSupplier.get();
        intStream.parallel().forEachOrdered(s -> System.out.print(s+" "));

        intStream = streamIntegerSupplier.get();
        //vysledok je vzdy nepredpokladatelny, kedze sa jedna o paralelne spracovanie
        System.out.println(intStream.parallel().findAny().get());
        //findFirst, limit, skip pracuju pomalsie v paralelnom prostredi, pretoze paralelny task musi v tomto
        //pripade koordinovat thready synchronizovanym sposobom.
        intStream = streamIntegerSupplier.get();
        //potom vrati vzdy konsistentny vysledok aj na seriovom aj paralelnom streame
        System.out.println(intStream.parallel().skip(5).limit(2).findFirst());

        intStream = streamIntegerSupplier.get();
        //unordered stream povie JVM, ze ak by mal uplatnit ordered-based operaciu, tak poradie moze ignorovat
        //napr. skip(5) nepreskoci prvych 5 prvkov, ale 5 nejakych prvkov
        //tymto sposobom sa moze zrychlit paralelne spracovanie
        intStream.unordered().parallel().skip(3).forEach(s->System.out.print(s+" "));

        //----------------------------------------reduce----------------------------------------------------------------
        //paralelne streamy v pripade reduce funkcie sa spravaju rovnako ako synchronne v pripade, ze
        //dodrzime nasledovne pravidla parametrov funkcie reduce
        /*
        1/prvy parameter identity combiner.apply(identity,u == u)
        2/druhy param. accumulator musi byt asociativny (a op b) op c == a op (b op c). op je operator.
        3/treti param. combiner musi by asociativny combiner.apply(u,accumulator.apply(identity,t)) == accumulator.apply(u,t)
        */
        intStream = Stream.of(1,2,3,4,5,6,7,8);
        intStream.parallel().reduce(0, (a,b)-> a+b);//bude vracat rovnaky vysledok ako synchronny stream

        intStream = streamIntegerSupplier.get();
        System.out.println("Paralelne odcitanie:"+
                //tu je problem lebo odcitanie nieje asociativny operator
                intStream.parallel().reduce(0, (a,b)-> {System.out.println("[a:]"+a+"[b:]"+b);System.out.println(); return a-b;})
        );
        intStream = streamIntegerSupplier.get();
        System.out.println("Synchronne odcitanie:"+
                intStream.reduce(0, (a,b)-> a-b)
        );

        intStream = Stream.of(1,2,3,4,5,6,7,8);
        //tu je takisto problem lebo sme nedodrzali pravidlo pre prvy parameter, 0 je OK
        System.out.println("Paralelne scitanie s init=1:"+
                intStream.parallel().reduce(1, (a,b)-> a+b)
        );
        //--------------------------------------collect--------------------------------------------------------------
        //pre paraleleny pristup sluzi troj parametrovy collect
        intStream = streamIntegerSupplier.get();
        //v pripade paralelneho spracovania s a vykona aj treti parameter(combiner)
        ArrayList<Double> res = intStream.parallel().collect(ArrayList<Double>::new,
                (list, elem)->list.add(Math.pow(elem,2)),
                (list1, list2)->list1.addAll(list2)/*ArrayList::addAll*/ );
        System.out.println(res);
        //Nato aby sme dosiahli paralelnu redukciu(v cielovej kolekcii su data v rovnakom poradi ako v streame)
        // je potrebne splnit nasledovne
        /*
        1/stream musi byt paralelny
        2/parameter colect operacie ma charakteristiku Collector.Characteristics.CONCURENT
        3/bud stream musi byt unordered alebo kolector ma charakteristiku Collector.Characteristics.UNORDERED
         */
        //v tomto pripade je hoci stream paralel ale charakteristika toSet je sice unordered ale nieje concurent,
        //nieje preto paralelnou redukciou
        intStream = streamIntegerSupplier.get();
        System.out.println(intStream.parallel().collect(Collectors.mapping(i->Math.pow(i, 2), Collectors.toSet())));

        //exituju 2 groupy v Collectors, ktore poskytuju concurent aj unordered toConcurentMap() a groupingByConcurent()
        //------------------------------toConccurentMap()----------------------------------------
        intStream = streamIntegerSupplier.get();
        //kedze sa pouzije cocurrent mapa, ktora ma charakteristiku concurent, tak sa jedna o paralelnu redukciu,
        //teda prvky su v poradi v akom su v streame
        ConcurrentMap concurrentMapResult = intStream.parallel().collect(Collectors.toConcurrentMap(Function.identity(), (v)->Math.pow(v, 2)));
        System.out.println(concurrentMapResult);
        //------------------------------groupingByConcurent()------------------------------------
        //groupingBy je mozne pomocou groupingByConcurent prepisat na paralelny stream a paralelnu redukciu
        Supplier<Stream<String>> animalsSupplier = () -> Stream.of("lev", "panda", "kareta", "tiger", "medved", "sob");
        Stream<String> animals = animalsSupplier.get();
        Map<Integer, List<String>> map = animals.collect(
                Collectors.groupingBy(String::length));
        System.out.println(map);
        //paralelne spracovanie je v celku lepsie a odporucane
        animals = animalsSupplier.get().parallel();
        ConcurrentMap<Integer, List<String>> mapConcurent = animals.collect(
                Collectors.groupingByConcurrent(String::length));
        System.out.println(mapConcurent);

    }
}
