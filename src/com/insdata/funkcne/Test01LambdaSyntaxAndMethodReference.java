package com.insdata.funkcne;

import java.util.function.*;

public class Test01LambdaSyntaxAndMethodReference {
    public static void main(String[] args) {
        //-----------------------------porovnanie implementacie pomocou anonymnej tredy a lambda----------------------
        Function<String, String> preScriptum1 = new Function<String, String >(){
            @Override
            public String apply(String s) {
                return s + " startum";
            }
        };//.andThen(a->a+" Two").andThen(a -> a+" Three");

        //casting potrebujem v pripade, ze by som lambdu nepriradoval lokalnej premennej preScriptum2
        //((Function<String,String>)s->s+" startum").apply("One");
        Function<String, String> preScriptum2 = ((Function<String,String>)s->s+" startum");
//                .andThen(s -> s+" Two").andThen(s -> s+" Three");

        System.out.println(preScriptum1.apply("One"));
        System.out.println(preScriptum2.apply("One"));

        //-------------------------------moznosti zapisu lambda vyrazu-----------------------------
        //bez parametru
        Supplier<String> defValue = ()->"default";
        //s jednym parametrom
        //moze byt aj bez zatvorky
        Consumer<String> setValue = s -> System.out.println("def hodnota:"+s);
        //Consumer<String> setValue = (s) -> System.out.println("def hodnota:"+s);
        //Consumer<String> setValue = (String s) -> System.out.println("def hodnota:"+s);
        //s n-parametrami
        BiFunction<String, Integer, Long> morphosis = (s, i) -> Long.valueOf(s.length() + i);

        //lambda so zlozitejsou implementaciou
        BiFunction<String, Integer, Long> morphosis2 = (s, i) ->
                                                            { //zatvorky su povinne
                                                                long ret = 0;
                                                                for(int j = 0; j< s.length();j++){
                                                                    ret += s.charAt(i);
                                                                }
                                                                //return je povinny ak funkcne interface ma navratovu hodnotu
                                                                return ret + i;
                                                            };
        //lambda definovana pomocou method reference
        /*mozeme pouzit:
         * staticku metodu
         * instancnu metodu
         * instancnu metodu o ktorej sa rozhodne v runtime
         * konstruktor
         * */
        //staticka metoda
        Function<String, String> initiadedString1 = String::valueOf;
        //instancna metoda
//        Predicate<String> evaluateString = s -> s.isEmpty(); => mozem zapisat nasledovne
        Predicate<String> evaluateString = String::isEmpty;
        //konstruktor
        Supplier<String> emptyString = String::new;
        Function<String, String> initiatedString = String::new;
    }
}
