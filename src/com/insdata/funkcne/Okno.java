package com.insdata.funkcne;

import com.insdata.funkcne.interfeis.OknoKontrolovatelne;

import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Created by karol.bielik on 20.4.2017.
 */
public class Okno {
    private boolean zavrete;

    public boolean isZavrete() {
        return zavrete;
    }

    boolean zavriOkno(OknoKontrolovatelne oknoKontrolovatelne){
        if(oknoKontrolovatelne.skontroluj()){
            zavrete = true;
            System.out.println("Zatvaram okno");
        }else{
            System.out.println("Okno nezatvorim");
        }
        return oknoKontrolovatelne.skontroluj();
    }

    boolean otvorOkno(OknoKontrolovatelne oknoKontrolovatelne){
        if(oknoKontrolovatelne.skontroluj()){
            System.out.println("Otvaram okno");
        }else{
            System.out.println("Okno neotvorim");
        }
        return oknoKontrolovatelne.skontroluj();
    }

    boolean zavriOknoStandart(Supplier<Boolean> oknoKontrolovatelne){
        if(oknoKontrolovatelne.get()){
            zavrete = true;
            System.out.println("Zatvaram okno standardne");
        }else{
            System.out.println("Okno nezatvorim standardne");
        }
        return oknoKontrolovatelne.get();
    }

    boolean zavriOknoStandardTest(Boolean zavriOkno){
        if(zavriOkno){
            zavrete = true;
            System.out.println("Zatvaram okno standardny test");
        }else{
            System.out.println("Okno nezatvorim standardny test");
        }
        return zavriOkno;
    }
}

