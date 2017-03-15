package com.insdata.innertriedy;

/**
 * Created by karol.bielik on 15. 3. 2017.
 */
public class StatickaInnerTriedaTest {
    public static void main(String[] args) {
        VonkajsiaTrieda.VnutornaTrieda vnTr = new VonkajsiaTrieda.VnutornaTrieda();
        vnTr.vypisClenVonkajsejTriedy();
    }
}

class VonkajsiaTrieda{
    //vnutorna trieda je oznacena slovom static
    //vyjadruje to ze sa jej instancia moze vytvorit bez toho aby som mal instanciu vonkajsej tiedy
    //tak ako je to pri statickych clenoch.
    private int clenVonkajsejTriedy;
    private static int statClenVonkajsejTriedy;

    static class VnutornaTrieda{
        public void vypisClenVonkajsejTriedy(){
            //kedze nemam instanciu vonkajsej triedy tak instancneho clena vonkajsej triedy nevidim
//            System.out.println(clenVonkajsejTriedy);

            //Staticke cleny vonkajsej triedy vidim
            System.out.println("statClenVonkajsejTriedy:"+statClenVonkajsejTriedy);
        }
    }
}