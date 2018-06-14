package com.insdata.localisation;

import java.util.ListResourceBundle;

public class Test_sk extends ListResourceBundle {

    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"greeting", "Ahoj"},
                {"intro", "Moje meno je internacionalizacny program."}
        };
    }
}
