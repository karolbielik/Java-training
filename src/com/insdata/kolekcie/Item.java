package com.insdata.kolekcie;

/**
 * Created by key on 18.2.2017.
 */
public class Item implements Comparable<Item>{

    Integer firstElement;
    String secondElement;

    public Item(Integer firstElement, String secondElement) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (!firstElement.equals(item.firstElement)) return false;
        return secondElement.equals(item.secondElement);

    }

    @Override
    public int hashCode() {
        int result = firstElement != null ? firstElement.hashCode() : 0;
        result = 31 * result + (secondElement != null ? secondElement.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Item{" +
                "firstElement=" + firstElement +
                ", secondElement='" + secondElement + '\'' +
                '}';
    }

    @Override
    public int compareTo(Item o) {
        return this.firstElement.compareTo(o.firstElement);
//        return this.firstElement - o.firstElement;
    }
}
