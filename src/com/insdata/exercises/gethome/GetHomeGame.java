package com.insdata.exercises.gethome;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by key on 10.2.2017.
 */
public class GetHomeGame {

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("-------------------Get HOME nova hra--------------------------");
        System.out.println("Zadaj dlzku cesty:");
        boolean cestaOK = false;

        Cesta cesta = new Cesta();
        Chodec chodec = new Chodec(cesta);

        while(!cestaOK){
            //cesta.generujMapu(); => vyhodi runtime exc, lebo je trieda pouzita nepovolene
            try {
                cesta.nastavDlzkuCesty(InterpreterPrikazov.dekodujDlzkuCesty(br.readLine()))
                .generujMapu();
                cestaOK = true;
            } catch (NeznamyPrikazException | DlzkaCestyException e) {
                System.out.println(e.getMessage());
                System.out.print("Zadaj dlzku cesty znova:");
            }
        }


        System.out.println("Zadaj krok(r,l,f):");
        while (!cesta.jeKoniecCesty()) {
            try {
                Krok k = InterpreterPrikazov.dekodujKrok(br.readLine());
                if(chodec.robimKrok(k)){
                    System.out.println("Krok "+k.toString()+" uspesne spraveny.");
                    if(!cesta.jeKoniecCesty()){
                        System.out.println("Zadaj dalsi krok:");
                    }
                }else{
                    System.out.println("Krok "+k.toString()+" nebol mozny.");
                    System.out.println("Zadaj krok(r,l,f):");
                }
            }catch (NeznamyPrikazException nzpex){
                System.out.println(nzpex.getMessage());
            }
        }

        System.out.println("Prisli ste na koniec cesty");
        System.out.println("Celkovy pocet pokusov:"+chodec.getPocetPokusov());
        System.out.println("Vygenerovana cesta:"+cesta.getMapa().toString());
    }
}
