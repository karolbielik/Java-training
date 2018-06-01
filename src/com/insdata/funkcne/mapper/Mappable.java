package com.insdata.funkcne.mapper;


import java.util.function.Function;

@FunctionalInterface
public interface Mappable<T> {

    //Funkcne interface musi mat zadefinovanu len a len jednu abstraktnu metodu
    //Ak triedu neoznacim @FunctionalInterface, tak potom nemusim maz zadefinovanu ani jednu abstract
    //metodu a vsetko mozu byt default metody, potom sa ale nejedna o funkcne interface.
    //Ak mam takychto interface viac, tak potom mozem hovorit o moznosti viacnasobneho dedenia.
    //Ak jedna trieda implementuje viac interfacov, ktore maju len default metody,
    //potom moze nastat unbeguity problem, v pripade ze, niektore dve interface implementuju
    //default metodu s rovnakou signaturou.
    T getMappableObject();

    default <R> Mappable<R> map(Function<T, R> mapFuntion){
        return ()->{
            T object = Mappable.this.getMappableObject();
            return object == null ? null : mapFuntion.apply(object);
        };
    }
}
