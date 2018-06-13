package com.insdata.io.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ByteArrayTest {
    public static void main(String[] args) throws InterruptedException, IOException {
        Thread.sleep(6000);
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        CustomByteArrayOutputStream baos = new CustomByteArrayOutputStream();

        //100MB
        int stoMega = 100 * 1024 * 1024;
        for(int i = 0;i<stoMega;i++){
            baos.write('c');
        }
        baos.close();
//        byte[] bytes = baos.toByteArray();
        byte[] bytes = baos.getBuffer();


    }



}

class CustomByteArrayOutputStream extends ByteArrayOutputStream{
    public byte[] getBuffer(){
        return buf;
    }
}
