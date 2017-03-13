package com.insdata.generika.example.pokladna.generika;

import com.insdata.generika.example.pokladna.mena.Mena;
import com.insdata.generika.example.pokladna.mena.SKK;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by karol.bielik on 13. 3. 2017.
 */
public class Pokladna<T extends Mena> {
    List<T> suplik = new ArrayList<>();

    public String vypisPeniaze(){
        String ret = "";
        for(T mena : suplik){
            ret += mena.getCiastka().toString()+" / ";
        }
        return ret;
    }

    public void prijmiDoPokladne(T mena){
        suplik.add(mena);
    }
}
