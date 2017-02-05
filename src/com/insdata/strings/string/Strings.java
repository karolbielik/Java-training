package com.insdata.strings.string;

/**
 * Created by key on 5.2.2017.
 */
public class Strings {

    public static void main(String[] args) {
        //String je final trieda, teda neda sa rozsirovat
        //String je immutable, to znamena, ze ked raz nastavym na objekte string hodnotu, neviem ju menit

        String s = "abc";
        System.out.println("s identity:"+System.identityHashCode(s));
        //s1 bude referovat na tu istu hodnotu na heap, lebo String trieda interne implementuje String constant pool
        String s1 = "abc";
        System.out.println("s1 identity:"+System.identityHashCode(s1));

        //neplati to v pripade ze vytvorime explicitne novu instanciu na heap
        String s2 = new String("abc");
        System.out.println("s2 identity:"+System.identityHashCode(s2));

        //operacie nad stringom
        //kedze String je immutable, kazda operacia nad stringom vytvara novy objekt, teda nemodificuje existujuci objekt na heap
        //s2 nieje modifikovany ale concat vrati novy objekt ktory obsahuje vysledok danej funkcie
        System.out.println();
        String s3 = s2.concat("def");
        System.out.println("s2 concat:"+s2);
        System.out.println("s3 concat:"+s3);
        System.out.println();

        //len pre ilustraciu nejake metody
        //charAt => vrati znak na konkretnom indexe, vyhodi StringIndexOutOfBoundsException ak je index mimo stringu
        System.out.println("s3.charAt(4):"+s3.charAt(4));

        //spoji stirng v parametri na koniec stringu, da sa pouzit aj + operator
        System.out.println("s3.concat(\"ghi\"):"+s3.concat("ghi"));

        //porovna ci dva stringy su rovnake pricom ignoruje velke, male pismena
        System.out.println("s3.equalsIgnoreCase(\"AbCdeF\"):"+s3.equalsIgnoreCase("AbCdeF"));

        //dlzka stringu = pocet chars v stringu
        System.out.println("s3.length():"+s3.length());

        //nahradi podskupinu charov za chary v druhom parametry
        System.out.println("s3.replace(\"ab\", \"\"):"+s3.replace("cd", "xxx"));

        //vrati cast stringu !pre parameter 0 vrati tenisty objekt
        System.out.println("s3.substring():"+s3.substring(2));
        System.out.println("s3 identityHashCode:"+System.identityHashCode(s3));
        System.out.println("s3.substring() identityHashCode:"+System.identityHashCode(s3.substring(0)));

        //prevedie chars na velke chars
        System.out.println("s3.toUpperCase():"+s3.toUpperCase());

        //prevedie chars na male chars
        System.out.println("s3.toLowerCase():"+s3.toLowerCase());

        //zoberie zo zaciatku a konca prazdne znaky
        System.out.println("\"  abcdef  \".trim():"+"  abcdef  ".trim());

        //rozdeli string na zaklade delimitera
        String[] sArr = s3.split("");
        for(String item : sArr){
            System.out.print(item+",");
        }
    }
}
