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


import java.util.function.Function;

public class Test01Lambda {
    public static void main(String[] args) {
        Function<String, String> fstring = a ->a+" startum";
        Function<String, String> postScriptum = fstring.compose(a -> a+" Two").compose(a -> a+" Three");

        System.out.println("Lambda:"+fstring.apply("One"));

        new Function<String, String >(){
            @Override
            public String apply(String s) {
                return s + " startum";
            }
        }.andThen(a->a+" Two").andThen(a -> a+" Three").apply("One");

        Function<String, String> preScriptum = fstring.andThen(a -> a+" Two").andThen(a -> a+" Three");

        System.out.println(fstring.apply("One"));
        System.out.println(postScriptum.apply("One"));
        System.out.println(preScriptum.apply("One"));
    }
}

