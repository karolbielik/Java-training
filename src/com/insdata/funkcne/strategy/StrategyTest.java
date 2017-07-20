package com.insdata.funkcne.strategy;

import java.util.ArrayList;

/**
 * Created by karol.bielik on 21.6.2017.
 */
/*
* Definicia Strategy pattern
*
* Define a family of algorithms, encapsulate each one, and make them interchangeable.
* Strategy lets the algorithm vary independently from clients that use it.
* */
public class StrategyTest {
    public static void main(String[] args) {
        ArrayList<StrategyTextGenerator> arrStrategy = new ArrayList<>();
        arrStrategy.add(new PeknaVeta());
        arrStrategy.add(new SpatnaVeta());
        arrStrategy.add(new StrategyTextGenerator() {
            @Override
            public String generateText() {
                return "Nepoviem nic, uz som si odsedel!";
            }
        });

        for(StrategyTextGenerator sg : arrStrategy){
            System.out.println(sg.generateText());
        }

        /*-----------------------------------------------------------------------*/
        /*-----------------------java 8 sposob-----------------------*/
        arrStrategy.clear();
        arrStrategy.add(()->"No co ty moj maly pekny");
        arrStrategy.add(()->"No co ty moj velky spatny");

        arrStrategy.forEach((s)->System.out.println(s.generateText()));
    }
}
