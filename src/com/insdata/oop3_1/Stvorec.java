package com.insdata.oop3_1;

/**
 * Created by key on 12.11.2016.
 */
public class Stvorec extends Tvar {

    public static final String TVAR = "STVOREC";

    public Stvorec(){
        super(TVAR);
    }

    @Override
    public String kresliObvod() {
        return "Kreslim obvod stvorca";
    }

    @Override
    public String kresliVypln() {
        return "Kreslim vypln stvorca";
    }
}
