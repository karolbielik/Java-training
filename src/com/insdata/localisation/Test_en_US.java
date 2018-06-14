package com.insdata.localisation;

import java.util.ListResourceBundle;

public class Test_en_US extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"greeting", "Hello"},
                {"intro", "My name is internationalizationing program."}
        };
    }
}
