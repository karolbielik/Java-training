package com.insdata.defaultmethod;

import java.util.Objects;

public class DefaultMethod {
    public static void main(String[] args) {
        Objects.requireNonNull(null);

    }
}

interface TestDefault{
    String mojeMenoJe();
    //default keyword bolo do java uvedene kvoli spetnej kompatibilite
    //napr. v interfac Iterable bola do java 8 uvedena metoda forEach
    //kedze Collection rozsiruje Iterable, tak by vsetky implementacie
    //Collection museli implementovat forEach. Bez toho aby sa narusila
    //implementacia vsetkych Collection interface, tak sa musela uviest do interface
    //default metoda. Tuto potom implementacie tohto interface implementovat nemusia.
    default String menoZeme(){
        return "Zem";
    }
}