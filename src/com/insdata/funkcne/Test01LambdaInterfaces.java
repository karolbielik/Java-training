package com.insdata.funkcne;


/*
Dolezite naucit sa nasledovnu tabulku

Functional Interfaces | # Parameters | Return Type | Single Abstract Method
Supplier<T>             0                T              get
Consumer<T>             1               (T) void        accept
BiConsumer<T, U>        2               (T, U) void     accept
Predicate<T>            1               (T) boolean     test
BiPredicate<T, U>       2               (T, U) boolean  test
Function<T, R>          1               (T) R           apply
BiFunction<T, U, R>     2               (T, U) R        apply
UnaryOperator<T>        1               (T) T           apply
BinaryOperator<T>       2               (T, T) T        apply
* */


import java.util.Comparator;
import java.util.function.*;

public class Test01LambdaInterfaces {
    public static void main(String[] args) {
        //---------------------------------Supplier------------------------------------------------
        Supplier<Long> lng = () -> new Long(11198754321L);
        Supplier<String>  empty = ()->new String();
        Supplier<String> hello = ()-> String.valueOf("hello");
        System.out.println("Looooong number:"+lng.get());
        System.out.println("Empty string:"+("".equals(empty.get())?"is empty":"is not empty"));


        //----------------------------------Consumer, BiConsumer-----------------------------------
        Consumer<String> takeString =  s ->  System.out.println(s);
        takeString.accept("Print me!");
        BiConsumer<String, Long> stringToLongConsumer = (s, l) -> System.out.println(s + " " + l);
        stringToLongConsumer.accept("And the winner in the number", 7L);
        //------------------------default funkcie-------------------------
        //------------------------------andThen---------------------------
        ((Consumer<String>)s->System.out.println("first line "+s)).andThen(s -> System.out.println("second line "+s))
        .accept("TEST");


        //----------------------------------Predicate, BiPredicate---------------------------------
        Predicate<String> containsX = (s)->s.contains("X") || s.contains("x");
        System.out.println("Ja neobsahujem ziadne iks. Obsahuje X/x:"+ containsX.test("Ja neobsahujem ziadne iks."));
        BiPredicate<String, String > checkString = (s1, s2)->s1.contains(s2);
        System.out.println("Obsahujem x:"+checkString.test("Obsahujem x", "x"));
        //------------------------default funkcie-------------------------
        Predicate<String> green = s->s.contains("green");
        Predicate<String> apple = s -> s.contains("apple");
        //------------------------------and------------------------------
        //------------------------------or------------------------------
        //------------------------------equal------------------------------
        //------------------------------negate------------------------------
        boolean isGreen = green.test("green skirt");
        boolean isAppleGreenApple = green.and(apple).test("green apple");
        boolean isNotApple = apple.negate().test("yellow apple");


        //----------------------------------Function, BiFunkcion-----------------------------------
        Function<String, Long> stringToLong = (s -> Long.parseLong(s));
//        Function<String, Long> stringToLong = Long::parseLong;
        Long lng1 = stringToLong.apply("342424");
        System.out.println("Long value:"+lng1);
        BiFunction<String, String, String> concatWithSpace = (s1, s2) -> s1.concat(" "+s2);
        System.out.println("Concatenated string:"+concatWithSpace.apply("Prva cast", "druha cast"));
        //------------------------default funkcie-------------------------
        Function<String , Integer> stringToInteger = s -> s.length();
        Function<String, String> transformString = s -> s.replace("x", "");
        //------------------------------andThen---------------------------
        System.out.println("String Hellxo xWorlxd po andThen transformacii:"
                +transformString.andThen(stringToInteger).apply("Hellxo xWorlxd"));
        //------------------------------compose---------------------------
        System.out.println("String Hellxo xWorlxd po compose transformacii:"
                + stringToInteger.compose(transformString).apply("Hellxo xWorlxd"));


        //----------------------------------UnaryOperator------------------------------------------
        //unarna operacia nad stringom
        UnaryOperator<String> sToUpperCase = s -> s.toUpperCase();
        System.out.println("String uppercase unari operation:"+sToUpperCase.apply("monkey business"));
        //unarna operacia nad cislom
        UnaryOperator<Integer> intIncrement = integer -> integer + 1;//integer++
        System.out.println("Integer unari operation:"+intIncrement.apply(6));


        //----------------------------------BinaryOperator-----------------------------------------
        //binarna operacia nad stringom
        BinaryOperator<String> sConcat = (s1, s2) -> s1.concat(s2);
        System.out.println("String uppercase unari operation:"+sConcat.apply("monkey", "business"));

        //binarna operacia nad cislom
        BinaryOperator<Long> addLongNumbers = (n1, n2) -> n1 + n2;//1+1
        System.out.println("add numbers:"+addLongNumbers.apply(3L, 4L));

        //-------------------staticke metody v BinaryOperator---------------------------
        //--------------------------------minBy, maxBy----------------------------------
        Comparator<String> minStringComparator = (a,b)-> a.compareTo(b);
        BinaryOperator<String> minString = BinaryOperator.minBy(minStringComparator);
        System.out.println("Mensi je:"+minString.apply("Hello Dedo", "Hello World"));
    }
}

