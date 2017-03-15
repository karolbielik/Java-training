package com.insdata.innertriedy;

/**
 * Created by karol.bielik on 15. 3. 2017.
 */
public class InnerTriedaVMetodeTest {

    public static void main(String[] args) {
        InnerTriedaVMetodeTest test = new InnerTriedaVMetodeTest();
        Lopticka lopticka1 = new Lopticka();
        Lopticka lopticka2 = test.metodkaSInnerTriedou(lopticka1);

        System.out.println("Co robi lopticka1:"+lopticka1.coRobim());
        System.out.println("Co robi lopticka2:"+lopticka2.coRobim());
    }

    Lopticka metodkaSInnerTriedou(Lopticka lopticka){
        String hrajLopticka = new String("skacem a pritom hram");

        //Musim instancovat az za deklaraciou triedy
        //Lopticka novaLopticka = new LoptickaSvetojanska();

        class LoptickaSvetojanska extends Lopticka{
            private String svietLopticka = "skacem a pritom hram a svietim";
            @Override
            String coRobim() {
                return hrajLopticka;
//                return svietLopticka;
            }
        }

        //Mozem instancovat az tu za deklaraciou
        Lopticka novaLopticka = new LoptickaSvetojanska();
        return novaLopticka;
    }
}


class Lopticka{
    String coRobim(){
        return "skacem";
    }
}