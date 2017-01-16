package com.insdata.enums;

/**
 * Created by key on 16.1.2017.
 */
public class EnumEquivalentnaTrieda {
    String name;
    Integer index;

    public EnumEquivalentnaTrieda(String name, Integer index) {
        this.name = name;
        this.index = index;
    }

    public static final EnumEquivalentnaTrieda PONDELOK = new EnumEquivalentnaTrieda("PONDELOK", 0);
    public static final EnumEquivalentnaTrieda UTOROK = new EnumEquivalentnaTrieda("UTOROK", 1);
    public static final EnumEquivalentnaTrieda STREDA = new EnumEquivalentnaTrieda("STREDA", 2);

    @Override
    public String toString() {
        return name;
    }

    public static void main(String[] args) {
        System.out.println(EnumEquivalentnaTrieda.PONDELOK);
        System.out.println(EnumEquivalentnaTrieda.UTOROK);
        System.out.println(EnumEquivalentnaTrieda.STREDA);
    }
}
