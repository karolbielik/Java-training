package com.insdata.generika.example.pokladna;

import com.insdata.generika.example.pokladna.bezgenerika.EURPokladna;
import com.insdata.generika.example.pokladna.bezgenerika.SKKPokladna;
import com.insdata.generika.example.pokladna.generika.Pokladna;
import com.insdata.generika.example.pokladna.mena.EUR;
import com.insdata.generika.example.pokladna.mena.SKK;

/**
 * Created by karol.bielik on 13. 3. 2017.
 */
public class TestPokladna {


    public static void main(String[] args) {
        EURPokladna eurPokladna = new EURPokladna();
        SKKPokladna skkPokladna = new SKKPokladna();
        Pokladna<SKK> gSkkPokladna = new Pokladna<>();

        eurPokladna.prijmiDoPokladne(new EUR(new Double(12.33)));
        eurPokladna.prijmiDoPokladne(new EUR(new Double(13.33)));
        eurPokladna.prijmiDoPokladne(new EUR(new Double(14.33)));
        System.out.println(eurPokladna.vypisPeniaze());

        gSkkPokladna.prijmiDoPokladne(new SKK(new Double(112.33)));
        gSkkPokladna.prijmiDoPokladne(new SKK(new Double(113.33)));
        gSkkPokladna.prijmiDoPokladne(new SKK(new Double(114.33)));
        System.out.println(gSkkPokladna.vypisPeniaze());

    }

}
