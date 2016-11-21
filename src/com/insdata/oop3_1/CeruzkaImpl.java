package com.insdata.oop3_1;

import com.insdata.oop3_1.Ceruzka;
import com.insdata.oop3_1.Kruh;
import com.insdata.oop3_1.Tvar;

/**
 * Created by karol.bielik on 14. 11. 2016.
 */
public abstract class CeruzkaImpl implements Ceruzka {

    Tvar tvar;//alebo 'Tvar tvar = new Kruh();' aby sme nedostali NullPointerException

//    protected CeruzkaImpl(){
//        this(new Kruh());// alebo 'this.tvar = new Kruh();' aby sme nedostali NullPointerException
//    }

    protected CeruzkaImpl(Tvar tvar){
        this.tvar = tvar;
    }

    @Override
    public void kresliTvar() {
        System.out.println(tvar.kresliObvod());
        System.out.println(tvar.kresliVypln());
    }
}
