package com.insdata.funkcne;

import com.insdata.funkcne.interfeis.OknoKontrolovatelne;
//import org.apache.commons.lang3.StringUtils;

import java.util.function.BooleanSupplier;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Created by karol.bielik on 28.3.2017.
 */

/*
Dolezite naucit sa nasledovnu tabulku

Functional Interfaces | # Parameters | Return Type | Single Abstract Method
Supplier<T>             0                T              get
Consumer<T>             1               (T) void        accept
BiConsumer<T, U>        2               (T, U) void     accept
Predicate<T>            1               (T) boolean     test
BiPredicate<T, U>       2               (T, U) boolean  test
Function<T, R>          1               (T) R           apply
BiFunction<T, U, R>     2               (T, U) R        apply
UnaryOperator<T>        1               (T) T           apply
BinaryOperator<T>       2               (T, T) T        apply
* */

public class FunkcneInterfiesTest {

    public static void main(String[] args) {
        Okno okno = new Okno();
        boolean vonkuFucka = true;

        okno.zavriOkno(()->vonkuFucka);
        OknoKontrolovatelne skusCiMozemOtvoritOkno = ()->!vonkuFucka;
        okno.otvorOkno(skusCiMozemOtvoritOkno);

        //parameter je Supplier ktory je definovany v java 8
        KontrolorOkna kontrolorOkna = new KontrolorOkna();
        okno.zavriOknoStandart(()-> vonkuFucka);
        Supplier<Boolean> skusCiMamZavrietOkno = ()->vonkuFucka;

        Predicate<Boolean> kontrolaZavretiaOkna = t->KontrolorOkna.musimZavrietOkno(t);
        okno.zavriOknoStandardTest(kontrolaZavretiaOkna.test(vonkuFucka));

        kontrolaZavretiaOkna = KontrolorOkna::musimZavrietOkno;
        okno.zavriOknoStandardTest(kontrolaZavretiaOkna.test(vonkuFucka));

    }
}

class KontrolorOkna{

    public boolean mozemOtvoritOkno(Boolean vonkuFuka){
        return !vonkuFuka;
    }

    public static Boolean musimZavrietOkno(Boolean vonkuFuka){
        return vonkuFuka;
    }
}
