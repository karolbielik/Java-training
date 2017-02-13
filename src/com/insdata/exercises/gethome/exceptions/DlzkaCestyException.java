package com.insdata.exercises.gethome.exceptions;

/**
 * Created by key on 10.2.2017.
 */
public class DlzkaCestyException extends Exception {
    public DlzkaCestyException(int min, int max) {
        super("Dlzka cesty moze byt min "+min+" a max "+max+".");
    }
}

