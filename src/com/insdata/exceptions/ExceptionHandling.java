package com.insdata.exceptions;

/**
 * Created by key on 28.1.2017.
 */
public class ExceptionHandling {
    //error => dedi z Error(technicky nieje vynimka lebo nededi z Exception), predstavuje specialnu situaciu, ktora nieje sposobena chybou v programe. Aplikacia obycajne sa nevie zotavit z Error, preto ju neosetrujeme. napr. OutOfMemoryError, StackOverflowError
    //runtime exception => dedi z RuntimeException => je to vynimka ktora je spojena napr. s chybou v kode. napr. NullPointerException. Nemusi byt handlovana, ale propaguje sa az do hlavnej metody a program terminuje
    //user exception => dedi z Exception => je nutne ju vzdy handlovat a je spojena napr. s vynimkou v busines logike aplikacii.

    public static void main(String[] args) {
        ExceptionHandling eksHand = new ExceptionHandling();
        try{
            //tento riadok vyhodi exception, ktora musi byt handlovana
            eksHand.vyhodMojuEksepsnu(Boolean.TRUE);
            //tento sa kvoli tomu preskoci a ide do catch bloku
            eksHand.vyhodRuntimeEksepsnu(5);
        }catch (MojaEksepsna meks){
            //po odchyteni exception sa exception dalej nepropaguje a kod pokracuje dalej
            System.out.println(meks.getMessage());
        }
        //toto sa vykona a vyhodi runtime exception, ktora nemusi byt handlovana
        eksHand.vyhodRuntimeEksepsnu(4);

        eksHand.vyhodError();
    }

    private void vyhodMojuEksepsnu(boolean dosloKChybe) throws MojaEksepsna {
        if(dosloKChybe){
            throw new MojaEksepsna();
        }
    }

    private void vyhodRuntimeEksepsnu(int parameter) /*throws IllegalArgumentException niejo povinne uviest a handlovat lebo je to runtime exception*/{
        if(parameter>5){
            throw new IllegalArgumentException();
        }else{
            throw new MojaRuntimeEksepsna();
        }
    }

    private void vyhodError(){
        throw new MojaKonfiguracnaError();
    }
}

class MojaEksepsna extends Exception{
    @Override
    public String getMessage() {
        return "Toto je moja eksepsna, kdo je vac!";
    }
}

class MojaRuntimeEksepsna extends RuntimeException{
    @Override
    public String getMessage() {
        return "Toto je moja rantajm eksepsna, kdo je vac!";
    }
}

class MojaKonfiguracnaError extends Error{
    @Override
    public String getMessage() {
        return "Mea culpa mea maxima culpa.";
    }
}