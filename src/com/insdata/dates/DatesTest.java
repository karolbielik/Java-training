package com.insdata.dates;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
        LocalDate ld = LocalDate.now();
        System.out.println(ld);
        LocalTime lt = LocalTime.now();
        System.out.println(lt);
        LocalDateTime ldt = LocalDateTime.now();
        System.out.println(ldt);

        LocalDate customDatePonovom = LocalDate.of(2017, Month.JULY, 22);
        System.out.println("customDate ponovom:"+customDatePonovom);

        ZonedDateTime sysZdt = ZonedDateTime.now();
        System.out.println("System time:"+sysZdt);
        ZoneId zoneIdOfJerusalem = ZoneId.of("Asia/Jerusalem");
        ZonedDateTime zdt = ZonedDateTime.of(LocalDateTime.now(), zoneIdOfJerusalem);
        System.out.println("Jerusalem time:"+zdt);
        //zoznam moznych region-based ZoneIds:
//      ZoneId.getAvailableZoneIds().forEach(System.out::println);
//      ZoneId.getAvailableZoneIds().stream().filter(s->s.toLowerCase().startsWith("eur")).sorted().forEach(System.out::println);

        //ZoneId(ofset of casovej zony Greenwich napr. +02:00(ZoneOffset)) identifikuje pravidla(Rules) konverzie Instant(je bod v case ratany od 1.1.1970)
        // na LocalDateTime(je date-time format year-month-day-hour-minute-second bez info o casovych zonach)
        //ZoneId ma dva tvary: a)UTC/Greenwich podobu(offset-based), b)geograficky region(region-based) na kt. sa vztahuju pravidla pre
        //vypocet offset-u od Greenwich
        System.out.println("Jerusalem time offset from Greenwich:"+ZoneId.of("UTC+03:00").getId());
        System.out.println("Jerusalem time offset from Greenwich normalized:"+ZoneId.of("UTC+03:00").normalized().getId());

        System.out.println("Jerusalem geografical region:"+ZoneId.of("Asia/Jerusalem"));
        //na zaklade pravidiel(kt. sa mozu menit podla lubovole(vlady) daneho regionu) pre dany geo region, prepocita podla nejakeho absolutneho casu offset od Greenwich
        System.out.println("Jerusalem geografical region offset:"+ZoneId.of("Asia/Jerusalem").getRules().getOffset(Instant.now()));

        //manipulacia s datumom
        LocalDateTime manipulovanyDatum = LocalDateTime.now();
        System.out.println("pred manipulovanim pridavanie ponovom:"+manipulovanyDatum);
        //--------------------pridavanie hodnoty k datumu ponovom
        //musime priradit vysledok do premennej, lebo LocalDateTime je immutable
        manipulovanyDatum = manipulovanyDatum.plusDays(2).plusHours(3).plusMinutes(4).plusSeconds(5);
        System.out.println("po manipulacii pridavanie ponovom:"+manipulovanyDatum);
        //--------------------pridavanie hodnoty k datumu postarom
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        System.out.println("pred manipulovanim pridavanie postarom:"+cal.getTime());
        cal.add(Calendar.DATE,1);
        System.out.println("po manipulacii pridavanie postarom:"+cal.getTime());
        //--------------------uberanie hodnoty k datumu ponovom
        manipulovanyDatum = LocalDateTime.now();
        System.out.println("pred manipulovanim uberanie ponovom:"+manipulovanyDatum);
        manipulovanyDatum = manipulovanyDatum.minusDays(2).minusHours(3).minusMinutes(4).minusSeconds(5);
        System.out.println("po manipulacii uberanie ponovom:"+manipulovanyDatum);
        //--------------------uberanie hodnoty k datumu postarom
        cal = Calendar.getInstance();
        cal.setTime(new Date());
        System.out.println("pred manipulovanim uberanie postarom:"+cal.getTime());
        cal.add(Calendar.DATE,-1);
        System.out.println("po manipulacii uberanie postarom:"+cal.getTime());

        /*-----------------------------------------------perioda--------------------------------------------------*/
        Period period = Period.of(2, 0, 4);
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println("before period added:"+localDateTime);
        System.out.println("after period added:"+localDateTime.plus(period));

        LocalDate ld1 = LocalDate.now();
        System.out.println(ld1.withDayOfMonth(2));//nastavi den v mesiaci na 2-heho

        /*--------------------------------------------------duration-------------------------------------------------*/
        LocalTime lt1 = LocalTime.of(5, 30);
        LocalTime lt2 = LocalTime.of(6, 45);
        System.out.println("celych hodin medzi lt1 a lt2:"+ChronoUnit.HOURS.between(lt1, lt2));
        System.out.println("minut medzi lt1 a lt2:"+ChronoUnit.MINUTES.between(lt1, lt2));

        //-------------------pridaj duration ----------------------
        System.out.println(lt1.plus(Duration.ofHours(23)));

        //-------------------uber duration ----------------------
        System.out.println(lt1.minus(Duration.ofHours(23)));

        /*-----------------------------------------------Instant-------------------------------------------------------*/
        //Instant je okamih v case na "jednom mieste" na zemi. Obsahuje cas v unixovom formate.
        Instant instantNow = Instant.now();
        Instant instantFromZDT = ZonedDateTime.now().toInstant();
        //da sa spravit aj z epochy(cas od zaciatku pocitania(1970))
        Instant instantFromEpoch = Instant.ofEpochSecond(instantNow.getEpochSecond());

        /*---------------------------Formatovanie datumov--------------------------------------------------------------*/
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME;
        System.out.println("sformatovany datum:"+dateTimeFormatter.format(zdt));


        /*--------------------------------Casovy posun ----------------------------------------------------------------*/
        //posun casu vpred
        LocalDate date = LocalDate.of(2016, Month.MARCH, 13);
        LocalTime time = LocalTime.of(1, 30);
        ZoneId zone = ZoneId.of("US/Eastern");
        ZonedDateTime dateTime = ZonedDateTime.of(date, time, zone);
        System.out.println(dateTime);
        dateTime = dateTime.plusHours(1);
        System.out.println(dateTime);
        //to iste plati pre posun casu vzad 6.nov.2016


    }
}
