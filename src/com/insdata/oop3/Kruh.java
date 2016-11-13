package com.insdata.oop3;

/**
 * Created by key on 12.11.2016.
 */
public class Kruh extends Tvar {

    public static final String TVAR = "KRUH";

    public Kruh(){
        super(TVAR);
    }

    @Override
    public String kresliObvod() {
        return "Kreslim obvod kruhu";
    }

    @Override
    public String kresliVypln() {
        return "Kreslim vypln kruhu";
    }
}
