package com.insdata.streams.primitive;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.*;

/**
 * Created by karol.bielik on 24.7.2017.
 */
public class PrimitiveStreamTest {

    public static void main(String[] args) {
        //vytvorenie stream primitivov
        IntStream intStream = IntStream.empty();
        IntStream intStream1 = IntStream.range(1,5);

        //IntStream nema collect(Collector) metodu, preto ho treba premapovat na Stream, ktory takuto metodu ma.
        System.out.println("range(1,5):"+ intStream1.mapToObj(value -> value).collect(Collectors.toList()) );

        intStream1 = IntStream.rangeClosed(1, 5);
        System.out.println("rangeClosed(1,5):"+ intStream1.mapToObj(value -> value).collect(Collectors.toList()) );

        LongStream longStream = LongStream.of(128888888888888888L);
        DoubleStream doubleStream = DoubleStream.generate(Math::random);
        DoubleStream doubleStream2 = DoubleStream.iterate(1, (a) -> a/2);

        //--------------------------------------------average,sum,range,rangeClosed------------------------------------
        Supplier<IntStream> intStreamSupplier = () -> IntStream.generate(()->(new Random()).nextInt());
        IntStream intStream2 = IntStream.iterate(1, (a) -> a+a);
        System.out.println("average value:"+ intStream2.limit(10).peek((a)->System.out.println("peek hodnota:"+a)).average().orElse(0));

        intStream2 = IntStream.iterate(1, (a) -> a+a);
        System.out.println("sum value:"+ intStream2.limit(10).sum());

        //kvoli tomu, ze streamy sa nemozu reusovat existuje XxxSumaryStatistics trieda, ktora obsahuje honoty(vysledky)
        //hore uvedenych metod
        System.out.println(intStreamSupplier.get().limit(10).summaryStatistics().toString());

        //--------------------------------------------StreamSupplier interface-----------------------------------------
        Supplier<Stream<Integer>> integerStreamSupplier = () -> Stream.of(1,2,3,4);

        //mapping
        IntStream mapIntToStream = integerStreamSupplier.get().mapToInt(value -> value);
        mapIntToStream.forEach(value -> {System.out.println("IntStream:"+value);});

        Stream<Integer> mapIntegerToStream = integerStreamSupplier.get().map(value -> value);
        mapIntegerToStream.forEach(value -> {System.out.println("Stream:"+value);});

        LongStream mapIntegerToLong = integerStreamSupplier.get().mapToLong(value -> value);
        mapIntegerToLong.forEach(value -> {System.out.println("LongStream:"+value);});

        DoubleStream mapIntegerToDouble = integerStreamSupplier.get().mapToDouble(value -> value);
        mapIntegerToDouble.forEach(value -> {System.out.println("DoubleStream:"+value);});

        //--------------------------------------------flatMapToXxx function of primitive types-------------------------
        int[][] data = {{1,2},{3,4},{5,6}};
        IntStream is1 = Arrays.stream(data).flatMapToInt(row -> Arrays.stream(row));
        System.out.println(is1.sum());
        //alebo
        IntStream is2 = Arrays.asList(1,2,3,4,5,6,7).stream().flatMapToInt(x->IntStream.of(x));
        System.out.println(is2.sum());

        /*-----------------------------------primitive functional interfaces-------------------------------------------*/

        /*------------------------------------streams and underlying data ---------------------------------------------*/
        ArrayList<String> mena = new ArrayList();
        mena.add("Fero");
        mena.add("Jozo");
        Stream<String> streamMena = mena.stream();
        mena.add("Antonko");
        System.out.println("pocet mien:"+streamMena.count());

        /*---------------------------------------Optionals chaning-----------------------------------------------------*/
        Optional<String> value = Optional.of("2345");
        value.map(n->"_"+n+"_").filter(n->n.length()>3).ifPresent((x)->System.out.println("Optional after change:"+x));

        //value.map(String::length);
        //toto sa neskompiluje lebo map vrati Optional<Optional<Integer>>
//        Optional<Integer> ret = value.map(PrimitiveStreamTest::lentgthCalculator);
        //takze potrebujeme pouzit miesto toho flatMap, flatMap dalsi optional nepridava
        //flatMap je uzitocne, ked potrebujeme premapovat Optional jedneho typu na druhu
        Optional<Integer> ret = value.flatMap(PrimitiveStreamTest::lentgthCalculator);

        /*-------------------------------problem s funkcnym interface--------------------------------------------------*/
        /*funkcne interface nedeklaruje checked exceptions a preto moze byt problem s pouzitim referencie metody ktora
        * takuto exception definuje*/
        //Nekompiluje toto
//        PrimitiveStreamTest.create("1","2","3").forEach(System.out::println);
        //Musim spravit wrapper a je to ok
        PrimitiveStreamTest.createSafe("1","2","3");

    }

    private static Stream<String> create(String ... s) throws IOException{
        return Stream.of(s);
    }

    private static Stream<String> createSafe(String ... s){
        try {
            return PrimitiveStreamTest.create(s);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Optional<Integer> lentgthCalculator(String string){
        return Optional.of(string.length());
    }
}
