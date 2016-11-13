package com.insdata.oop3;

/**
 * Created by key on 12.11.2016.
 */
public abstract class Tvar {

    private String meno;

    protected Tvar(String meno){
        this.meno = meno;
    }

    public abstract String kresliObvod();

    public abstract String kresliVypln();

    public String getMeno() {
        return meno;
    }

    public void setMeno(String meno) {
        this.meno = meno;
    }
}
