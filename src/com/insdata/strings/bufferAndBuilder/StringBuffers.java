package com.insdata.strings.bufferAndBuilder;

/**
 * Created by key on 5.2.2017.
 */
public class StringBuffers {
    //pouzivame v pripade ak potrebujeme na stringu robit vecsie mnozstvo modifikacii
    //a nechceme skoncit pri velkom mnozstve nereferencovanych String objektov

    //StringBuffer nieje synchronizovany teda je rychlejsi ako StringBuilder

    public static void main(String[] args) {
        StringBuffer sb = new StringBuffer("abc");
        //sekvencia volania metod jednu za druhou nazyvame "chained methods"-> zretazene metody
        sb.append("def")/*abcdef*/.delete(2,3)/*abdef*/.insert(2, "c")/*abcdef*/.reverse()/*fedcba*/;
        System.out.println(sb.append("def").delete(2,3).insert(2, "c").reverse());

        //vzdy sa robia operacie nad tym istym objektom
        System.out.println(System.identityHashCode( sb.reverse()));
        System.out.println(System.identityHashCode( sb.insert(3,"xx")) );
    }

}
