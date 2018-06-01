package com.insdata.kolekcie.lambdas;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

public class TestCollectionsWithLambdas {

    public static void main(String[] args) {
        //---------------------method referencie-------------------
//        Collections.sort(); => Consumer
        //-----------------------------List---------------------------------------------------------
        //----------------------removeIf()------------------------------
        //mozeme vymazat prvky z kolekcie na zaklade implementacie bloku kodu
        List<Integer> integerArrayList = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9,10));
        System.out.println("Pred removeIf");
        System.out.println(integerArrayList);
        System.out.println("Po removeIf");
        integerArrayList.removeIf(integer -> (integer % 2) == 0);
        System.out.println(integerArrayList);
        //----------------------replaceAll()-------------------------------
        integerArrayList.replaceAll((item) -> item +1);
        System.out.println("po replaceAll");
        System.out.println(integerArrayList);
        //----------------------forEach()-------------------------------
        integerArrayList.forEach(integer -> System.out.print(integer+","));

        //-----------------------------Map---------------------------------------------------------
        Map<String, String> oblubenePolozky = new HashMap<>();
        oblubenePolozky.put("Jano", "tenis");
        oblubenePolozky.put("Peter", "volejbal");
        oblubenePolozky.put("Michal", null);
        System.out.println(oblubenePolozky);
        //---------------------------------------putIfAbsent---------------------------
        oblubenePolozky.putIfAbsent("Jano", "hadzana");
        oblubenePolozky.putIfAbsent("Peter", "volejbal");
        oblubenePolozky.putIfAbsent("Michal", "korculovanie");
        oblubenePolozky.putIfAbsent("Fero", "lyzovanie");
        System.out.println(oblubenePolozky);
        //----------------------merge()----------------------------------------
        BiFunction<String, String, String> merger = (value1, value2)->value2.charAt(0)>value1.charAt(0)?value2:value1;
        oblubenePolozky.merge("Jano", "vybijana",merger);
        oblubenePolozky.merge("Fero", "byciklovanie", merger);
        System.out.println("po merge");
        System.out.println(oblubenePolozky);
        //ked je hodnota null tak, merge sa nevola a nastavi sa nova hodnota
        oblubenePolozky.put("Dominik", null);
        System.out.println("s null hodnotou pred merge");
        System.out.println(oblubenePolozky);
        oblubenePolozky.merge("Dominik", "samba", merger);
        System.out.println("s null hodnotou po merge");
        System.out.println(oblubenePolozky);
        //ked BiFunciton vracia null, tak kluc sa vyhodi z mapy
        System.out.println("merger vracia null po merge:");
        oblubenePolozky.merge("Dominik", "nicota", (a,b)->null);
        System.out.println(oblubenePolozky);
        //----------------------computeIfPresent()-------------------------------
        //zavola BiFunction ked sa kluc nachadza v mape
        BiFunction computeBiFunct = (a,b)->{
            System.out.println("compute zavolane pre "+a);
            return "compute if present";
        };

        Function computeFunct = (a)->{
            System.out.println("compute zavolane pre "+a);
            return "compute if absent";
        };

        System.out.println("pred computeIfPresent chybajuci kluc");
        System.out.println(oblubenePolozky);
        oblubenePolozky.computeIfPresent("Dominik", computeBiFunct);
        System.out.println("po computeIfPresent");
        System.out.println(oblubenePolozky);

        System.out.println("pred computeIfPresent nechybajuci kluc");
        System.out.println(oblubenePolozky);
        oblubenePolozky.computeIfPresent("Michal", computeBiFunct);
        System.out.println("po computeIfPresent");
        System.out.println(oblubenePolozky);
        //----------------------computeIfAbsent()-------------------------------
        //zavola funkciu v pripade, ze kluc neexistuje
        System.out.println("pred computeIfAbsent chybajuci kluc");
        System.out.println(oblubenePolozky);
        oblubenePolozky.computeIfAbsent("Dominik", computeFunct);
        System.out.println("po computeIfAbsent");
        System.out.println(oblubenePolozky);

    }
}
