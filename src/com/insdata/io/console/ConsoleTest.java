package com.insdata.io.console;

import java.io.BufferedReader;
//import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by key on 5.2.2017.
 */
public class ConsoleTest {

    //od java 6 je k dispozicii java.io.Console pre pracu s konzolou
    public static void main(String[] args) throws IOException {
//        Console c = System.console();
//        while (true) {
//            c.readLine();
//        }
        BufferedReader br =  new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.print("Enter data:");
            String s = br.readLine();
            System.out.println(s);
        }
    }
}
