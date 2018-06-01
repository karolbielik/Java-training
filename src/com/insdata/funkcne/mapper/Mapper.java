package com.insdata.funkcne.mapper;

public class Mapper<T> implements Mappable<T> {

    T mappableObject;

    private Mapper(T object) {
        this.mappableObject = object;
    }

    public static <R> Mapper<R> of(R object){
        return new Mapper(object);
    }

    @Override
    public T getMappableObject() {
        return this.mappableObject;
    }


}
