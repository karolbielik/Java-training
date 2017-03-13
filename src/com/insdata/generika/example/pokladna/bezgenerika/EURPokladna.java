package com.insdata.generika.example.pokladna.bezgenerika;

import com.insdata.generika.example.pokladna.mena.EUR;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by karol.bielik on 13. 3. 2017.
 */
public class EURPokladna {

    List<EUR> suplik = new ArrayList<>();

    public String vypisPeniaze(){
        String ret = "";
        for(EUR eur : suplik){
            ret += eur.getCiastka().toString()+" / ";
        }
        return ret;
    }

    public void prijmiDoPokladne(EUR eur){
        suplik.add(eur);
    }
}
