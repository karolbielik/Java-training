package com.insdata.oop2;

import com.insdata.oop1.Lampa;

/**
 * Created by karol.bielik on 6. 11. 2016.
 */
public class DiscoGula extends Lampa{/*musi byt public class aj konstruktor lebo je v inom package*/

//    public int x = 2;
    boolean tociSa;//implicitne je false

    DiscoGula(){
        //super();
        tociSa = false;
    }

    @Override
    public void zapniLampu() {
        roztocGulu();
        super.zapniLampu();
    }

    @Override
    public void vypniLampu() {
        zastavGulu();
        super.vypniLampu();
    }

    private void roztocGulu(){
        tociSa = true;
        System.out.println("gula sa toci:"+tociSa);
    }

    private void zastavGulu(){
        tociSa = false;
        System.out.println("gula sa toci:"+tociSa);
    }
}
