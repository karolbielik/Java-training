package com.insdata.streams;

import java.io.IOException;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class Test03PrimitiveStreams {

    public static void main(String[] args) {
        //java streams podporuju int, long a double
        /*------------------------------vytvorenie Int,Long,DoubleStream------------------------------------------*/
        //------------------vytvorenie stream primitivov pre int, obdobne aj pre long--------
        IntStream intStream = IntStream.empty();
        intStream.forEach(System.out::println);
        IntStream intStream1 = IntStream.range(1,5);
        intStream1.forEach(System.out::print);
        System.out.println();
        IntStream intStream2 = IntStream.rangeClosed(1,5);
        intStream2.forEach(System.out::print);
        //-------------------------vytvorenie nekonecny int stream----------------------------
        System.out.println();
        IntStream intStream3 = IntStream.iterate(1, x->x*2);
        intStream3.limit(3).forEach(System.out::print);
        System.out.println();

        //--------------------------vytvorenie stream pre Double------------------------------
        DoubleStream doubleStream = DoubleStream.empty();
        doubleStream.forEach(System.out::println);
        DoubleStream doubleStream1 = DoubleStream.of(1.11,2.22,3.33,4.44,5,6,7);
        doubleStream1.forEach(d->System.out.print(d+", "));
        System.out.println();
        //----------------------------vytvorenie nekonecny double stream----------------------
        DoubleStream doubleStream3 = DoubleStream.iterate(.5, d->d/2);
        doubleStream3.limit(3).forEach(d->System.out.print(d+", "));
        System.out.println();
        DoubleStream doubleStream2 = DoubleStream.generate(Math::random);
        doubleStream2.limit(3).forEach(d->System.out.print(d+", "));
        System.out.println();
        //--------------------------vytvorenie stream mapovanim z jedneho typu na druhy----------
        //---------------------z double na long----------------------------------
        Supplier<DoubleStream> doubleStreamSupplier = () -> DoubleStream.of(1.11,2.50,3.49,4.44,5.55,6,7);
        LongStream longStream = doubleStreamSupplier.get().mapToLong(d-> (long)d);
        longStream.forEach(d->System.out.print(d+", "));
        System.out.println();
        //alebo s prihliadnutim na zaokruhlovanie
        longStream = doubleStreamSupplier.get().mapToLong(d->Math.round(d));
        longStream.forEach(d->System.out.print(d+", "));
        System.out.println();
        //--------------------------------zo Stream<String> na IntStream pomocou mapToXXX------------------------------------
        Stream<String> names = Stream.of("Peter", "Jan", "Martin", "Fedor");
        IntStream namesLength = names.mapToInt(s->s.length());
        namesLength.forEach(s->System.out.print(s+", "));
        System.out.println();
        //------------------------------z Listu na intStream pomocou flatMapToXXX---------------------------------
        List<String> lstNames = Arrays.asList("Peter", "Richard", "Valentin", "Jan");
        lstNames.stream().flatMapToInt(s -> IntStream.of(s.length()));
        lstNames.forEach(s->System.out.print(s+", "));
        //---------------------------z primitivneho typu streamu na object stream-----------------
        //IntStream nema collect(Collector) metodu, preto ho treba premapovat na Stream, ktory takuto metodu ma.
        System.out.println("range(1,5):"+ intStream1.mapToObj(value -> value).collect(Collectors.toList()) );


        //--------------------------terminalne operacie pre primitivy---------------------------------------------------
        //-------------------------sum,avg,max(min),orElse,orElseGet,getAsXxx---------------------------
        //-------------------------sum----------------------------
        LongStream longStream1 = LongStream.of(444444,555555,666666);
        long longSum = longStream1.sum();
        System.out.println("Long sum :"+longSum);
        //-------------------------average, orElse(Get)----------------------------
        Supplier<IntStream> intStreamSupplier = () -> IntStream.generate(()->(new Random()).nextInt());
        IntStream intStream5 = intStreamSupplier.get();
        OptionalDouble avgResult = intStream5.limit(10)
                .peek((a)->System.out.println("peek hodnota:"+a))
                .average();
        System.out.print("average value:");
        System.out.printf("%1$.2f", avgResult.orElse(0));
        System.out.println();
        //-------------------------max,min----------------------------
        System.out.println("max of values:"+intStreamSupplier.get().limit(5)
                                                                .peek((a)->System.out.println("peek hodnota:"+a))
                                                                .max()
//                                                                 .min());
                                                                .getAsInt());
        //----------------------------------summary statistics-----------------------------------------------------
        //v pripade ze potrebujeme na jednom streame pouzit hodnoty vysledkov z viacerych terminalnych
        //operacii, mame k dispozicii summary statistics. Teda napr. deltu z min a max hodnot mozeme vypocitat nasledovne
        IntStream randomIntegers = intStreamSupplier.get().limit(10);
        IntSummaryStatistics sumStatistics = randomIntegers.summaryStatistics();
        System.out.println("delta min a max hodnot:"+(sumStatistics.getMax()-sumStatistics.getMin()));

        //-----------------------------------------------funkcne interface pre primitivne typy-------------------------
        //DoubleSupplier, IntSupplier, LongSupplier
        //DoubleConsumer, IntConsumer, LongConsumer
        //DoublePredicate, IntPredicate, LongPredicate
        //DoubleFunction<R>, IntFunction<R> ,LongFunction<R>
        //DoubleUnaryOperator, IntUnaryOperator, LongUnaryOperator
        //DoubleBinaryOperator, IntBinaryOperator, LongBinaryOperator
        //-------------------------------------specificke interface len pre primitivne typy-----------------------------
        //ToDoubleFunction<T>, ToIntFunction<T>, ToLongFunction<T>
        ToIntFunction<String> lengthFunction = (s)->s.length();
        System.out.println(lengthFunction.applyAsInt("My test input"));
        //ToDoubleBiFunction<T, U>, ToIntBiFunction<T, U>, ToLongBiFunction<T, U>
        //DoubleToIntFunction, DoubleToLongFunction, IntToDoubleFunction
        //IntToLongFunction, LongToDoubleFunction, LongToIntFunction
        IntToLongFunction addBigNumber = (i)->i+999999999999999000L;
        System.out.println(addBigNumber.applyAsLong(444));
        //ObjDoubleConsumer<T>, ObjIntConsumer<T>, ObjLongConsumer<T>
        System.out.println(
        intStreamSupplier.get().limit(10)
                                .collect(
                                        ()->new ArrayList<Integer>()
                                        ,(list, elem)->list.add(elem)//ObjectIntConsumer
                                        ,(list1, list2)->list1.addAll(list2)
                                )
        );

        //------------------------------------vztah streamu a dat v nom-------------------------------------------------
        ArrayList<String> mena = new ArrayList<>(Arrays.asList("Jano", "Dezo", "Fero"));
        //vytvorim stream z listu, toto je ale lazy, teda v skutocnosti nieje vytvoreny
        Stream<String> streamMena = mena.stream();
        //pridam este do listu dalsie meno, po tom co som vytvoril stream
        mena.add("Izidor");
        //zbehnem na streame count, co je terminalna operacia a vlastne az v tejto chvily
        //zbehne pipeline nad streamom, teda vysledok nebude 3 ale 4
        System.out.println(streamMena.count());

        /*---------------------------------------Optionals chaning-----------------------------------------------------*/
        Optional<String> value = Optional.of("2345");
        value.map(n->"_"+n+"_").filter(n->n.length()>3)
                .ifPresent((x)->System.out.println("Optional after change:"+x));

        /*Toto vrati int, ale povedzme, ze by sme chceli vratit Optional<Integer>.*/
        //value.map(String::length);
        /*Potom potrebujeme naimplementovat metodu, ktora nam Optional vrati, ale potom nemozeme
          pouzit map() metodu, ktora vracia int, napr.
          Optional<Integer> ret = value.map(PrimitiveStreamTest::lentgthCalculator);
          Takze potrebujeme pouzit miesto toho flatMap, ktora Optional vracia.
          FlatMap je uzitocne, ked potrebujeme premapovat Optional jedneho typu na iny Optional typ*/
        Optional<Integer> ret = value.flatMap(Test03PrimitiveStreams::lentgthCalculator);

        /*-------------------------------problem s funkcnym interface--------------------------------------------------*/
        /*funkcne interface nedeklaruje checked exceptions a preto moze byt problem s pouzitim referencie metody ktora
         * takuto exception definuje*/
        //Nekompiluje toto kvoli unhandled exception of type IOException
//        PrimitiveStreamTest.create("1","2","3").forEach(System.out::println);
        //Musim spravit wrapper a je to ok
        Test03PrimitiveStreams.createSafe("1","2","3");

    }

    private static Stream<String> create(String ... s) throws IOException {
        return Stream.of(s);
    }

    private static Stream<String> createSafe(String ... s){
        try {
            return Test03PrimitiveStreams.create(s);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Optional<Integer> lentgthCalculator(String string){
        return Optional.of(string.length());
    }
}
