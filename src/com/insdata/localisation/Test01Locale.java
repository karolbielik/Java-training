package com.insdata.localisation;

import java.util.Locale;

public class Test01Locale {
    public static void main(String[] args) {
        //Internationalization(i28n) - dizajn programu tak, ze moze byt adaptovany pre viac geografickych regionov.
        //Nemusi podporovat viac jazykov alebo krajin. Znamena to ze moze podporovat.
        //Localization(l10n) - podporuje viacero lokacii. Je to jazyk_KRAJINA par., napr. en_US, en_EN, alebo len jazyk
        //napr. en. Vsetky ostatne su neplatne.

        //defaultna locale
        Locale defLocale = Locale.getDefault();
        System.out.println(defLocale);
        //zmena defaultnej locale, len pre tento beziaci program, nezmeni systemove nastavenia
        Locale.setDefault(new Locale(("fr")));
        System.out.println(Locale.getDefault());

        //existuju 3 sposoby ako vytvorit locale
        //1/pomocou konstanty preddefinovanej javou
        System.out.println(Locale.GERMAN);//de
        System.out.println(Locale.GERMANY);//de_DE
        //2 pomocou konstruktora a string parametrov
        System.out.println(new Locale("fr"));
        System.out.println(new Locale("hi", "IN"));
        //3 pomocou builder pattern
        System.out.println(
                new Locale.Builder()
                .setLanguage("en")
                .setRegion("US")
                .build()
        );

    }
}
