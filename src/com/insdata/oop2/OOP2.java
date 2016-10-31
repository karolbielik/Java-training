package com.insdata.oop2;

/**
 * Created by karol.bielik on 27. 10. 2016.
 */
public class OOP2 {
    public static void main(String[] args) {
        //operator new vytvara novy objekt na heap
        Zviera z = new Zviera();
        Pes p = new Pes();
        Vtak v = new Vtak();
        Zviera zv = new Vtak();
        System.out.print(z.zerie());
        System.out.print(p.zerie());
        System.out.print(v.zerie());
    }
}
