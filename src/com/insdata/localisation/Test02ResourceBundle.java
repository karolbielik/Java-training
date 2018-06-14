package com.insdata.localisation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

/*
poradie vyberania bundle => od naspecifickejsieho po najvseobecnejsi
pre:
default locale = en_US
ResourceBundle.getBundle("Test", "sk_SK")
potom:
Test_sk_SK.java, Test_sk_SK.properties, Test_sk.java, Test_sk.properties, Test_en_US.java,
Test_en_US.properties, Test_en.java, Test_en.properties, Test.java, Test.properties

pre:
default locale = en
ResourceBundle.getBundle("Test", "sk")
potom:
Test_sk.java, Test_sk.properties, Test_en.java, Test_en.properties, Test.java, Test.properties

java nieje obmedzena vyberanim hodnoty pre kluce z jedneho bundle. Moze vybrat hodnotu pre kluc z rodicovskeho bundle.
Rodicovsky = menej specificky napr. Test.properties(ten je posledny v hierarchii vid. hore)
*/

public class Test02ResourceBundle {
    public static void main(String[] args) throws IOException {
        //resource bundle - obsahuje pre lokaciu specificke objekty pouzivane programom.
        //Je to ako mapa s klucami a hodnotami.
        //Moze to byt property file alebo resource bundle java class
        /*-----------------------------property file------------------------------------------------------------------*/
        Locale skLocale = new Locale("sk", "SK");
        Locale enLocale =  new Locale("en", "US");
        printGreeting(skLocale);
        printGreeting(enLocale);

        //mozeme aj v cykle prejst po vsetkych hodnotach klucov v bundle
        ResourceBundle rb = ResourceBundle.getBundle("Test", skLocale);
        Set<String> keys = rb.keySet();
        keys.stream().map(key->key+"="+rb.getString(key)).forEach(System.out::println);

        //pouzitie Properties triedy
        //----------------------------naplnenie v loop---------------------------
        Properties props = new Properties();
        keys.stream().forEach(key->props.put(key, rb.getString(key)));
        System.out.println(props.getProperty("greeting"));
        System.out.println(props.get("intro"));
        System.out.println(props.getProperty("fake", "default"));

        //-------------------------naplnenie pomocou load-------------------------
        Properties props1 = new Properties();
        props1.load(Files.newInputStream(Paths.get("bundles", "Test_en.properties"), StandardOpenOption.READ));
        System.out.println(props1.getProperty("greeting"));
        System.out.println(props1.get("intro"));
        System.out.println(props1.getProperty("fake", "default"));

        /*----------------------------------------------bundle class--------------------------------------------------*/
        //kluce su vzdy stringy
        //hodnota kluca je hocijaky java objekt
        //Oproti properties, mozme do hodnoty priradit akykolvek java objekt a nie len string a hodnoty mozeme
        //tvorit v runtime
        ResourceBundle rb1 = ResourceBundle.getBundle("com.insdata.localisation.Test", Locale.US);
        Set<String> keys1 = rb1.keySet();
        keys1.stream().map((key)->key+"="+rb1.getObject(key)).forEach(System.out::println);

        //nacita bundle s default locale
        Locale.setDefault(new Locale("sk", "SK"));
        ResourceBundle rb2 = ResourceBundle.getBundle("com.insdata.localisation.Test");
        Set<String> keys2 = rb1.keySet();
        keys2.stream().map((key)->key+"="+rb2.getObject(key)).forEach(System.out::println);
    }

    private static void printGreeting(Locale locale){
        ResourceBundle rb = ResourceBundle.getBundle("Test", locale);
        System.out.println(rb.getString("greeting"));
        System.out.println(rb.getString("intro"));
    }
}
