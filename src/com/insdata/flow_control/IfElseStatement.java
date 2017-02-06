package com.insdata.flow_control;

/**
 * Created by key on 21.1.2017.
 */
public class IfElseStatement {
    public static void main(String[] args) {
        int i = 1;
        //if moze byt pouzite bez else, nie naopak
        if(i<4){
            System.out.println("i<4");
        }

        //else moze by pouzite len po if alebo (else if)
        if(i<4){
            System.out.println("i<4");
        }else {
            System.out.println("i>=4");
        }///else { nieje dovolene
        //}

        //zatvorky nemusim pisat ak je za vyrazom jeden prikaz, ktory sa ma vykonat
        //pre lepsiu citatelnost sa odporuca zatvorky pouzivat
        if(i>4)
            System.out.println("i>4");
        else
            System.out.println("i<=4");

        //pocet else if statementov nieje obmedzeny, musia vsak nasledovat po if, alebo po inom else if
        i=1;
        if(i==4){
            System.out.println("i==4");
        }else if(i==3) {
            System.out.println("i==3");
        }else if(i==2){
            System.out.println("i==2");
        }else {
            System.out.println("i<>2,3,4");
        }

        //hadanka
        //ku ktoremu if statmentu patri else statement
        i=3;
        if(i<4)
        if(i>2)
            System.out.println("i==3");
        else
            System.out.println("?");

        //ak je prvy z vyrazu vo vztahu && false, tak druhy sa uz nevyhodnocuje a cely vyraz je false
        //ak i<4 je vyhodnotene ako false tak i>2 sa uz nevyhodnocuje
        if(i<4 && i>2){
            System.out.println("i<4 && i>2 => i==3");
        }
        //ak je prvy z vyrazu vo vztahu || true, tak druhy sa uz nevyhodnocuje a cely vyraz je true
        if(i<4 || i>2){
            System.out.println("i<4 || i>2 => i=="+i);
        }

        //vyraz vo vnutri if statmentu moze byt len boolean typu, resp. vyhodnoteny ako boolean typ
//        if(i=5) => nieje dovolene lebo to nieje boolean vyraz
        boolean b = false;
        if(b=true){//vyraz sa najprv vykona a potom sa vyhodnoti
            System.out.println("b=true => "+b);
        }
        //vsetky operatory vo vnutri if statmentu su vyhodnocovane podla priority
        //dany vyraz je vyhodnoteny nasledovne (i<4 && i>2) || i>4
        i = 5;
        if( i<4 && i>2 || i>4){
            System.out.println("i<4 && i>2 || i>4 => i=="+i);
        }
        i=1;//i musi byt iba 1, inak je cele vyhodnotene ako false
        //dany vyraz je vyhodnoteny nasledovne i<4 && (i>2 | i==1)
        if(i<4 && i>2 | i==1){
            System.out.println("i<4 && i>2 | i==1 => i=="+i);
        }
    }
}
