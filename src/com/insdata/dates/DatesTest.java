package com.insdata.dates;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by karol.bielik on 26.7.2017.
 */
public class DatesTest {
    public static void main(String[] args) {
        /*-----------------stary pred java 8 sposob ako vytvorit datum---------------------*/
        Date d = new Date();//ako LocalDate.now() alebo LocalDateTime.now()
        //alebo dalsia moznst pomocou Calendar
        Calendar c = Calendar.getInstance();
        c.getTime();
        //alebo GregorianCalendar
        Calendar cg = GregorianCalendar.getInstance();
        //alebo
        //cg = new GregorianCalendar();
        cg.getTime();

        Calendar customTime = GregorianCalendar.getInstance();
        customTime.set(2017, Calendar.JULY, 22);
        System.out.println("customDate postarom:"+customTime.getTime());

        /*--------------------novy java 8 sposob ako vytvorit datum-----------------------*/
        //--------------------------------LocalDate, LocalTime, LocalDateTime--------------------
        //vsetky tri nenesu informaciu o casovej zone
        LocalDate ld = LocalDate.now();
        System.out.println(ld);
        LocalTime lt = LocalTime.now();
        System.out.println(lt);
        LocalDateTime ldt = LocalDateTime.now();
        System.out.println(ldt);

        LocalDate customDatePonovom = LocalDate.of(2017, Month.JULY, 22);
        System.out.println("Date of:"+customDatePonovom);
        LocalTime customLocalTime = LocalTime.of(22,22);
        System.out.println("Time of:"+customLocalTime);
        System.out.println("DateTime of:"+LocalDateTime.of(customDatePonovom, customLocalTime));

        /*---------------------ZoneId, ZonedDateTime a casove zony------------------------------------------*/
        //ZonedDateTime obsahuje datum, cas a casovu zonu
        ZonedDateTime sysZdt = ZonedDateTime.now();
        System.out.println("System time:"+sysZdt);
        //mozem vytvorit ZonedDateTime aj z LocalDateTime pridanim casovej zony
        ZonedDateTime jerusalemDateTime = ldt.atZone(ZoneId.of("Asia/Jerusalem"));
        System.out.println("Zoned datetime z localDateTime:"+jerusalemDateTime);
        //moja casova zona
        System.out.println("Moja casova zona:"+ZoneId.systemDefault());
        /*
        ZoneId(ofset od casovej zony Greenwich napr. +02:00(ZoneOffset)) identifikuje pravidla(Rules) konverzie Instant(je bod v case ratany od 1.1.1970)
        ZoneId ma dva tvary:
         a)UTC alebo GMT/Greenwich podobu(offset-based),
         b)geograficky region(region-based) na kt. sa vztahuju pravidla pre vypocet offsetu od Greenwich
         */
        //-----geograficky region-------------------
        ZoneId zoneIdOfJerusalem = ZoneId.of("Asia/Jerusalem");
        ZonedDateTime zdt = ZonedDateTime.of(LocalDateTime.now(), zoneIdOfJerusalem);
        System.out.println("Jerusalem time:"+zdt);
        //zoznam moznych region-based ZoneIds:
//      ZoneId.getAvailableZoneIds().forEach(System.out::println);
//      ZoneId.getAvailableZoneIds().stream().filter(s->s.toLowerCase().startsWith("eur")).sorted().forEach(System.out::println);
        //------------vypocet offset-u od Greenwich--------
        //UTC(Coordinate Universal Time je standard casovych zon) a GMT(Greenwich Mean Time je casova zona v Europe pouzita
        // ako nulta casova zona). Obidve UTC aj GMT pouzivaju rovnaku nulovu casovu zonu.
        System.out.println("Jerusalem time UTC offset from Greenwich:"+ZoneId.of("UTC+03:00").getId());
        System.out.println("Jerusalem time GMT offset from Greenwich:"+ZoneId.of("GMT+03:00"));
        System.out.println("Jerusalem time offset from Greenwich normalized:"+ZoneId.of("UTC+03:00").normalized().getId());

        //Na zaklade pravidiel(kt. sa mozu menit podla lubovole(vlady) daneho regionu) pre dany geo region,
        //prepocita podla nejakeho absolutneho casu offset od Greenwich
        //V Rules su napr. pravidla pre daylightsaving
        System.out.println("Jerusalem geografical region offset:"+ZoneId.of("Asia/Jerusalem").getRules().getOffset(Instant.now()));
        //-----------------------day light savings info------------------------
        System.out.println("UTC+03:00 terajsi posun casu:"+ZoneId.of("UTC+03:00").getRules()
                .getDaylightSavings(Instant.now())
                .toHours());
        //kedze 'Asia/Jerusalem' region je politicka jednotka moze byt posun casu rozny od posunu casu na 'UTC+03:00'
        //hoci Jeruzalem patri do pasma +03:00 od Greenwich
        System.out.println("Jeruzalemsky terajsi posun casu:"+ZoneId.of("Asia/Jerusalem").getRules()
                .getDaylightSavings(Instant.now())
                .toHours());
        System.out.println("Jeruzalemsky letny posun casu:"+ZoneId.of("Asia/Jerusalem").getRules()
                .getDaylightSavings(LocalDateTime.of(2018,Month.JULY,24, 12, 0).toInstant(ZoneOffset.UTC))
                .toHours());
        System.out.println("Jeruzalemsky zimny posun casu:"+ZoneId.of("Asia/Jerusalem").getRules()
                .getDaylightSavings(LocalDateTime.of(2018,Month.DECEMBER,24, 12, 0).toInstant(ZoneOffset.UTC))
                .toHours());

        /*------------------------------------------manipulacia s datumom---------------------------------------------*/
        LocalDateTime predManipulovanyDatum = LocalDateTime.now();
        System.out.println("pred manipulovanim pridavanie ponovom:"+predManipulovanyDatum);
        //--------------------pridavanie hodnoty k datumu ponovom-------------------------------
        //------------------------plusDays, plusHours, plusMinutes, plusSeconds-----------------
        //musime priradit vysledok do premennej, lebo LocalDateTime je immutable
        LocalDateTime poManipulovanyDatum = predManipulovanyDatum.plusDays(2).plusHours(3).plusMinutes(4).plusSeconds(5);
        System.out.println("po manipulacii pridavanie ponovom:"+poManipulovanyDatum);
        //--------------------pridavanie hodnoty k datumu postarom
        Calendar cal = Calendar.getInstance();
        cal.set(predManipulovanyDatum.getYear(), predManipulovanyDatum.getMonthValue()-1, predManipulovanyDatum.getDayOfMonth(),
                predManipulovanyDatum.getHour(), predManipulovanyDatum.getMinute(), predManipulovanyDatum.getSecond());
        System.out.println("pred manipulovanim pridavanie postarom:"+cal.getTime());
        cal.add(Calendar.DATE,2);
        cal.add(Calendar.HOUR, 3);
        cal.add(Calendar.MINUTE, 4);
        cal.add(Calendar.SECOND, 5);
        System.out.println("po manipulacii pridavanie postarom:"+cal.getTime());
        //--------------------uberanie hodnoty k datumu ponovom
        //---------------------------minusDays, minusHours, minusMinutes, minusSeconds-------------
        predManipulovanyDatum = LocalDateTime.now();
        System.out.println("pred manipulovanim uberanie ponovom:"+predManipulovanyDatum);
        poManipulovanyDatum = predManipulovanyDatum.minusDays(2).minusHours(3).minusMinutes(4).minusSeconds(5);
        System.out.println("po manipulacii uberanie ponovom:"+poManipulovanyDatum);
        //--------------------uberanie hodnoty k datumu postarom
        cal = Calendar.getInstance();
        cal.set(predManipulovanyDatum.getYear(), predManipulovanyDatum.getMonthValue()-1, predManipulovanyDatum.getDayOfMonth(),
                predManipulovanyDatum.getHour(), predManipulovanyDatum.getMinute(), predManipulovanyDatum.getSecond());
        System.out.println("pred manipulovanim uberanie postarom:"+cal.getTime());
        cal.add(Calendar.DATE,-2);
        cal.add(Calendar.HOUR, -3);
        cal.add(Calendar.MINUTE, -4);
        cal.add(Calendar.SECOND, -5);
        System.out.println("po manipulacii uberanie postarom:"+cal.getTime());

        //-------------------------epochSecond------------------------------------
        //je pocet sekund od 1.1.1970
        System.out.println("Od 1.1.1970 do "
                +predManipulovanyDatum.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"))
                +" ubehlo sekund:"+predManipulovanyDatum.toEpochSecond(ZoneOffset.UTC));

        /*-----------------------------------------------perioda------------------------------------------------------*/
        DateTimeFormatter datumFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        //je casovy usek rovny alebo dlhsi ako den  napr. 2 roky a 4 dni
        Period period = Period.of(2, 0, 4);
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println("before period added:"+localDateTime);
        System.out.println("after period added:"+localDateTime.plus(period));
        //dalsie moznosti vytvorenia periody
        //--------------ofDays, ofMonths, ofWeeks, ofYears------------------
        period = Period.ofDays(-2);
        System.out.println("Od datumu "+localDateTime.format(datumFormatter)
                +" odcitanie -2och dni:"+localDateTime.minus(period).format(datumFormatter));
        //Pozor na takyto zapis, ktory nieje perioda 2 roky a 4 dni, ale je to perioda 4 dni, lebo ofxxx je staticka metoda
        //Vystup je vo formate PxYxMxD kde x je cislo platne pre rok, mesiac, den
        System.out.println(Period.ofYears(2).ofMonths(3).ofDays(4));
        System.out.println(Period.of(2,3,4));

        System.out.println("Tyzdne su reprezentovane ako dni:"+Period.ofWeeks(3));

        /*-------------------------------nastavi den v mesiaci na 2-heho----------------------------------------------*/
        //-------------------------withDayOfMonth----------------------------
        LocalDate ld1 = LocalDate.now();
        //vrati kopiu datumu s dnami nastavenymi na konkretnu hodnotu
        System.out.println(ld1.withDayOfMonth(2));

        //--------------------------range-----------------------------
        //vrati napr. min a max pocet dni v danom mesiaci, podla toho aky je ChronoField.xy parameter
        ValueRange range = ld1.range(ChronoField.DAY_OF_MONTH);
        System.out.println("min-max pocet dni v mesiaci "+ld1.getMonth()+" je "+range);

        /*--------------------------------------------------duration--------------------------------------------------*/
        //Je casovy usek pre mensie casove jednotky ako je mesiac
        //Ak chcem pouzit napr. 1 rok, tak by som mohol vytvorit duration = 265 dni, ale na to by som mal uz
        //pouzit Period
        //-------------------------vytvorenie duration---------------------------------
        //Tak ako pre Period tento vyraz nieje 1h 30minut ale 30 minut
        //Format vypisu duration je PTxHxMxS kde x je platna hodnota pre dany typ
        System.out.println(Duration.ofHours(1).ofMinutes(30));
        //ak chcem zadat 1h 30min tak musim nastavit 90 minut
        System.out.println(Duration.ofMinutes(90));
        System.out.println(Duration.ofNanos(999999999999999999L));

        //druhy parameter je TemporalUnit interf., existuje jeho implementacia ChronoUnit
        Duration.of(90, ChronoUnit.MINUTES);

        //ChronoUnit je mozne pouzit aj na ziskanie velkosti rozsahu(hodin, minut) medzi dvoma casmi, datumami
        LocalTime lt1 = LocalTime.of(5, 30);
        LocalTime lt2 = LocalTime.of(6, 45);
        LocalDate ld3 = LocalDate.of(2018, Month.DECEMBER, 24);
        System.out.println("celych hodin medzi lt1 a lt2:"+ChronoUnit.HOURS.between(lt1, lt2));
        System.out.println("minut medzi lt1 a lt2:"+ChronoUnit.MINUTES.between(lt1, lt2));
        try {
            System.out.println("Rozdiel medzi time a date:" + ChronoUnit.MINUTES.between(lt1, ld3));
        }catch (DateTimeException dtex){
            System.out.println(dtex.getMessage());
        }

        //-------------------pridaj duration ----------------------
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();
        LocalDateTime localDateTime1 = LocalDateTime.now();
        Duration durationHours = Duration.ofHours(23);
        System.out.println(localDateTime1);
        //mozeme pridat hodiny premennej, ktora obsahuje cas a je to ako by sa preklopilo do dalsieho dna,
        //lebo pridavame 23 hodin
        System.out.println(localTime.plus(durationHours));
        //mozme v pohode pridat 23 hodin DateTime a preklopime sa do nasledujuceho dna
        System.out.println(localDateTime1.plus(durationHours));
        try {
            //UnsupportedTemporalException => nemozem pridavat do objektu, ktory neobsahuje cas
            System.out.println(localDate.plus(durationHours));
        }catch(UnsupportedTemporalTypeException uttex){
            System.out.println(uttex.getMessage());
        }
        //-------------------uber duration ----------------------
        //to iste plati aj pre minus funkciu co hore pre plus
        System.out.println(lt1.minus(Duration.ofHours(23)));

        //-------------------------------Period vs Duration---------------------------
        //Period a Duration niesu to iste
        Period period1 = Period.ofDays(1);
        Duration duration = Duration.ofDays(1);
        LocalDate localDate1 = LocalDate.now();
        //toto je OK
        System.out.println(localDate1.plus(period1));
        try {
            //Duration ma casove jednotky a je urcena lenpre objekty s casovymi udajmi
            System.out.println(localDate1.plus(duration));
            //To iste plati aj pre Period vo vztahu k LocalTime
            LocalTime localTime1 = LocalTime.now();
            //Tiez vyhodi UnsupportedTemporalTypeException
            localTime1.plus(period1);
        }catch(UnsupportedTemporalTypeException uttex){
            System.out.println(uttex.getMessage());
        }

        /*-----------------------------------------------Instant-------------------------------------------------------*/
        //Instant je okamih v case v ramci GMT casovych zon.
        Instant instantNow = Instant.now();
        //ZonedDateTime - je mozne z neho dostat instant
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        Instant instantFromZDT = zonedDateTime.toInstant();
        System.out.println(zonedDateTime);
        //Instant sa zbavi casovych zon a zmeni sa na casovy okamih v ramici GMT
        //Toto nevieme spravit s LocalDateTime pretoze ten neobsahuje casovu zonu a tak neviem dostat z neho
        //casovy usek v ramci GMT casovych zon.
        System.out.println(instantFromZDT);

        //da sa vsak spravit z epochy(cas od zaciatku pocitania(1970))
        Instant instantFromEpoch = Instant.ofEpochSecond(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));

        //K Instant dokazeme pridava alebo uberat hocijaku jednotku rovnu alebo mensiu ako den, ostatne su Unsupported
        //Vyhodia UnsupportedTemporalTypeException
        Instant instant = Instant.now();
        System.out.println(instant);
        //plus jeden den
        System.out.println(instant.plus(1, ChronoUnit.DAYS));
        //nasledujuca hodina
        System.out.println(instant.plus(1, ChronoUnit.HOURS));

        /*--------------------------------Casovy posun (daylight saving)----------------------------------------------*/
        //Nie kazda krajina participuje. U nas sa cas presuva o hodinu dopredu v marci a o hodinu dozadu v novembri
        //Potom jeden den v roku ma 23 hodin a druhy 25 hodin
        //posun casu vpred
        LocalDate date = LocalDate.of(2016, Month.MARCH, 13);
        LocalTime time = LocalTime.of(1, 30);
        ZoneId zone = ZoneId.of("US/Eastern");
        ZonedDateTime dateTime = ZonedDateTime.of(date, time, zone);
        System.out.println(dateTime);
        dateTime = dateTime.plusHours(1);
        System.out.println(dateTime);
        //to iste plati pre posun casu vzad 6.nov.2016
        dateTime = dateTime.minusHours(2);
        System.out.println(dateTime);

        LocalDateTime jeruzalemDT = LocalDateTime.of(2014, Month.OCTOBER, 26, 1, 30);
        ZonedDateTime jeruzalemZDT = jeruzalemDT.atZone(ZoneId.of("Asia/Jerusalem"));
        //pri prechode na zimny cas v Jeruzaleme
        System.out.println(jeruzalemZDT);
        System.out.println(jeruzalemZDT.plusHours(1));

        //pri prechode na letny cas v Jeruzaleme
        jeruzalemZDT = ZonedDateTime.of(LocalDateTime.of(2014, Month.MARCH, 28, 1, 30), ZoneId.of("Asia/Jerusalem"));
        System.out.println(jeruzalemZDT);
        System.out.println(jeruzalemZDT.plusHours(1));
    }
}
