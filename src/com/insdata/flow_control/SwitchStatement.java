package com.insdata.flow_control;

import com.insdata.enums.EnumDefinovanieAkoTrieda;

/**
 * Created by key on 21.1.2017.
 */
public class SwitchStatement {

    public static void main(String[] args) {
        //vyraz vo vnutri swich moze byt char, byte, short, int
        //od java 6 ,enum
        //od java 7 ,String

        int i = 1;
        System.out.println("1 je spravne cislo?:"+jeSpravneCislo(i));
        System.out.println("3 je spravne cislo?:"+jeSpravneCislo(3));

        EnumDefinovanieAkoTrieda enumDefinovanieAkoTrieda = EnumDefinovanieAkoTrieda.NEDELA;
        System.out.println("Co robit? "+coRobit(EnumDefinovanieAkoTrieda.PONDELOK));
        System.out.println("Co robit? "+coRobit(EnumDefinovanieAkoTrieda.SOBOTA));
        System.out.println("Co robit? "+coRobit(EnumDefinovanieAkoTrieda.STREDA));


        System.out.println("Co robitopti? "+coRobitOpti(EnumDefinovanieAkoTrieda.PONDELOK));
        System.out.println("Co robitopti? "+coRobitOpti(EnumDefinovanieAkoTrieda.SOBOTA));
        System.out.println("Co robitopti? "+coRobitOpti(EnumDefinovanieAkoTrieda.STREDA));

        //switch so String
        System.out.println( odpovedz("Slovencina"));
        System.out.println( odpovedz("Madarcina"));
        System.out.println( odpovedz("Nemcina"));
    }

    private static String odpovedz(String otestuj) {
        String vysledok;
        switch (otestuj){
            default:
                vysledok = "Nerozumiem reci tvojho kmena";
                break;
            //v pripade ze za case nieje break tak sa dalej vykonavaju vsetky prikazi v nasledujucich caseoch
            //za po najblizsi break, alebo po koniec switch
            case "Nemcina":
                vysledok = "Rozumiem po nemecky";
            case "Slovencina":
                vysledok = "Rozumiem po slovensky";
                break;
        }
        return vysledok;
    }

    private static String coRobitOpti(EnumDefinovanieAkoTrieda enumDefinovanieAkoTrieda) {
        String vysledok;
        switch (enumDefinovanieAkoTrieda){
            case PONDELOK:
            case UTOROK:
            case STREDA:
            case STVRTOK:
            case PIATOK:
                vysledok = "Je tyzden, treba ist do roboty.";
                break;
            case SOBOTA:
            case NEDELA:
                vysledok = "Je vikend, zostan doma.";
                break;
            default:
                vysledok = "Neviem co sa deje, asi si chory.";
        }
        return vysledok;
    }

    private static String coRobit(EnumDefinovanieAkoTrieda enumDefinovanieAkoTrieda) {
        String vysledok;
        //switch s enum

        switch (enumDefinovanieAkoTrieda){
            case PONDELOK:
                vysledok = "Je tyzden, treba ist do roboty.";
                break;
            case UTOROK:
                vysledok = "Je tyzden, treba ist do roboty.";
                break;
            case SOBOTA:
                vysledok = "Je vikend, zostan doma.";
                break;
            default:
                vysledok = "Neviem co sa deje, asi si chory.";
        }
        return vysledok;
    }

    private static boolean jeSpravneCislo(int i) {
        boolean vysledok = false;
        final int c1 = 1;
//        final int c2; //musi byt konstanta nestaci ked bude final
        switch (i){
            case c1:
                vysledok = true;
                break;
//            case c2:
//                break;
            case 2:
                vysledok = true;
                break;
            default:
                vysledok = false;
        }
        return vysledok;
    }
}
