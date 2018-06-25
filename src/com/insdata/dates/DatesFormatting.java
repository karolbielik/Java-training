package com.insdata.dates;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.Date;
import java.util.Locale;

public class DatesFormatting {
    public static void main(String[] args) {
        /*---------------------------Formatovanie datumov--------------------------------------------------------------*/
        //preddefinovane formatery
        //------------------formatovanie pomocou preddefinovanych formaterov----------------------
        //ISO je standard pre datumi
        LocalDate date = LocalDate.of(2020, Month.JANUARY, 20);
        LocalTime time = LocalTime.of(11, 12, 34);
        LocalDateTime dateTime = LocalDateTime.of(date, time);
        System.out.println(date.format(DateTimeFormatter.ISO_LOCAL_DATE));
        System.out.println(time.format(DateTimeFormatter.ISO_LOCAL_TIME));
        System.out.println(dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        //-----------------formatovanie pouzitim DateTimeFormatter--------------------------------
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
        System.out.println(dateTimeFormatter.format(date));
        System.out.println(dateTimeFormatter.format(dateTime));
        try {
            //pre tento styl formatu(FormatStyle.SHORT) time neobsahuje data o datume
            System.out.println(dateTimeFormatter.format(time));
        }catch (UnsupportedTemporalTypeException uttex){
            System.out.println(uttex.getMessage());
        }
        //----------------formatovanie priamo cez LocalDate,LocalTime,LocalDateTime a ich funkciu format---------
        System.out.println(date.format(dateTimeFormatter));
        System.out.println(dateTime.format(dateTimeFormatter));
        try{
        System.out.println(time.format(dateTimeFormatter));
        }catch (UnsupportedTemporalTypeException uttex){
            System.out.println(uttex.getMessage());
        }

        //----------------------preddefinovane typy formaterov SHORT, MEDIUM, LONG, FULL-------------------------
        DateTimeFormatter shortF = DateTimeFormatter
                .ofLocalizedDateTime(FormatStyle.SHORT);
        DateTimeFormatter mediumF = DateTimeFormatter
                .ofLocalizedDateTime(FormatStyle.MEDIUM);
        DateTimeFormatter fullF = DateTimeFormatter
                .ofLocalizedDateTime(FormatStyle.FULL);
        DateTimeFormatter longF = DateTimeFormatter
                .ofLocalizedDateTime(FormatStyle.LONG);
        System.out.println(shortF.format(dateTime));
        System.out.println(mediumF.format(dateTime));
        //pre full a long musim robit nad zoned date time inak vyhodi DateTimeException
        System.out.println(fullF.format(dateTime.atZone(ZoneId.systemDefault())));
        System.out.println(longF.format(dateTime.atZone(ZoneId.systemDefault())));
        //------------------------------ofPattern--------------------------------------
        //mozeme si vytvorit vlastny formater
        /*
        MMMM - mesiac napr. januar => M=1, MM=01, MMM=jan, MMMM=januar
        dd - den
        , - vypise ciarku
        yyyy - rok napr. 2021 yy=21, yyyy=2021
        hh - hodina napr. 2 h=2, hh=02
        : - vypise dvojbodku
        mm - minuta napr. 5 m=5, mm=05
        */
        DateTimeFormatter customDTF = DateTimeFormatter.ofPattern("hh:mm , dd.MM.yyyy");
        System.out.println(customDTF.format(dateTime));
        //------------------------------parse-------------------------------------------
        DateTimeFormatter f = DateTimeFormatter.ofPattern("MM dd yyyy");
        date = LocalDate.parse("01 02 2015", f);
        time = LocalTime.parse("11:22");
        System.out.println(date); // 2015–01–02
        System.out.println(time);

        //------------------------------lokalizovany formater------------------------------------------
        //-----------------------------------ofLocalizedDateTime()---------------------------------
        DateTimeFormatter usFormatt = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL).withLocale( Locale.US);
        DateTimeFormatter frFormatt = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL).withLocale( Locale.FRANCE);
        System.out.println(usFormatt.format(dateTime.atZone(ZoneId.of("US/Eastern"))));
        System.out.println(frFormatt.format(dateTime.atZone(ZoneId.of("Europe/Paris"))));
        //-----------------------------------porovnanie stary sposob formatovania a novy-------------------------------
        //stary
        SimpleDateFormat sf = new SimpleDateFormat("hh:mm");
        //pracuje nad Date
        System.out.println(sf.format(new Date()));
        //novy
        //pracuje nad LocalDate, LocalTime, LocalDateTime
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("hh:mm");
        System.out.println(dtf.format(time));
        //alebo pomocou lokalizovaneho casu
        dtf = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(Locale.FRANCE);
        System.out.println(dtf.format(time));


    }
}
