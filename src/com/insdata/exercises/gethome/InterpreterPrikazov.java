package com.insdata.exercises.gethome;

/**
 * Created by key on 10.2.2017.
 */
public class InterpreterPrikazov {

    public static Krok dekodujKrok(String prikaz) throws NeznamyPrikazException{
        switch (prikaz){
            case "r":
                return new KrokDoprava();
            case "l":
                return new KrokDolava();
            case "f":
                return new KrokRovno();
            default:
                throw new NeznamyPrikazException("Neviem vykonat krok");
        }
    }

    public static int dekodujDlzkuCesty(String prikaz) throws NeznamyPrikazException{
        try{
           return new Integer(prikaz);
        }catch (NumberFormatException nfe){
            throw new NeznamyPrikazException("Neplatna dlzka cesty");
        }
    }
}
