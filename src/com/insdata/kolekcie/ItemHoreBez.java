package com.insdata.kolekcie;

/**
 * Created by key on 18.2.2017.
 */
public class ItemHoreBez {

    Integer firstElement;
    String secondElement;

    public ItemHoreBez(Integer firstElement, String secondElement) {
        this.firstElement = firstElement;
        this.secondElement = secondElement;
    }

    public Integer getFirstElement() {
        return firstElement;
    }

    public void setFirstElement(Integer firstElement) {
        this.firstElement = firstElement;
    }

    public String getSecondElement() {
        return secondElement;
    }

    public void setSecondElement(String secondElement) {
        this.secondElement = secondElement;
    }
}
