package com.insdata.oop3_1;

/**
 * Created by key on 13.11.2016.
 */

public class CeruzkaStvorec extends CeruzkaImpl {

//    private Stvorec stvorec = new Stvorec();

    public CeruzkaStvorec(){
        super(new Stvorec());
    }

//    @Override
//    public void kresliTvar() {
//        System.out.println(stvorec.kresliObvod());
//        System.out.println(stvorec.kresliVypln());
//
//        //refactoring => kreslitvar s parametrom Tvar na abstract triede a metode
//        //medzi Ceruzka interface a CeruzkaStvorec vlozim este abstractnu triedu napr. CeruzkaImpl majucu kresliTvar(Tvar tvar)
//    }
}
