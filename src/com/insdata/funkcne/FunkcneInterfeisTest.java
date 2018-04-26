package com.insdata.funkcne;

import com.insdata.funkcne.interfeis.OknoKontrolovatelne;
//import org.apache.commons.lang3.StringUtils;

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

public class FunkcneInterfeisTest {

    public static void main(String[] args) {
        Okno okno = new Okno();
        boolean vonkuFucka = true;

        //parameter je funkcne interface OknoKontrolovatelne
        //mozem priamo zadat lambda vyraz
        okno.zavriOkno(()->vonkuFucka);
        //alebo mozem zadat funkciu ktora vracia implementovane funkcne interface
        okno.otvorOkno((new KontrolorOkna()).mozemOtvoritKontrolovatelneOkno(vonkuFucka));

        KontrolorOkna kontrolorOkna = new KontrolorOkna();
        //lambda vyraz mozem priradit funkcnemu interface, ktore zodpoveda signature metody
        Supplier<Boolean> skusCiMamOtvoritOkno = ()->kontrolorOkna.mozemOtvoritOkno(vonkuFucka);
        //parameter je Supplier funkcne interface ktore je definovany v java 8
        okno.otvorOknoStandart(skusCiMamOtvoritOkno);

        //implementuje funkcne interface lambda vyrazom
        Predicate<Boolean> kontrolaZavretiaOkna = (t) -> KontrolorOkna.mozemZavrietOkno(t);
        //na danej implementacii zavolam implementovanu metodu
        okno.zavriOknoStandardTest(kontrolaZavretiaOkna.test(vonkuFucka));

        //implementujem funkcne interface referenciou na metodu,
        // ktorej signatura v parametrovej casti metody zodpoveda signature metody
        //vo funkcnom interface
        kontrolaZavretiaOkna = KontrolorOkna::mozemZavrietOkno;
        //tymto sposobom predam parameter do implementacie interface
        okno.zavriOknoStandardTest(kontrolaZavretiaOkna.test(vonkuFucka));

    }
}

class KontrolorOkna{

    public boolean mozemOtvoritOkno(Boolean vonkuFuka){
        return !vonkuFuka;
    }

    public static Boolean mozemZavrietOkno(Boolean vonkuFuka){
        return vonkuFuka;
    }

    public OknoKontrolovatelne mozemOtvoritKontrolovatelneOkno(Boolean vonkuFucka){
        return ()-> !vonkuFucka;
    }
}
