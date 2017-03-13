package com.insdata.generika.example.pokladna.bezgenerika;

import com.insdata.generika.example.pokladna.mena.SKK;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by karol.bielik on 13. 3. 2017.
 */
public class SKKPokladna {
    List<SKK> suplik = new ArrayList<>();

    public String vypisPeniaze(){
        String ret = "";
        for(SKK skk : suplik){
            ret += skk.getCiastka().toString()+" / ";
        }
        return ret;
    }

    public void prijmiDoPokladne(SKK skk){
        suplik.add(skk);
    }
}
