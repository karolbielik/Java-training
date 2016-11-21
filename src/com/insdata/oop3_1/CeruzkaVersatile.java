package com.insdata.oop3_1;

/**
 * Created by karol.bielik on 21. 11. 2016.
 */
public class CeruzkaVersatile extends CeruzkaImpl {

    protected CeruzkaVersatile(Tvar tvar) {
        super(tvar);
    }

    //overloading
    public void kresliTvar(Tvar tvar){
        System.out.println(tvar.kresliObvod());
        System.out.println(tvar.kresliVypln());
    }

}
