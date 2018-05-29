package com.insdata.concurrency;

import com.insdata.random.Randomizer;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestSynchroOverUnsafe {
    public static void main(String[] args) {
        TestSynchroOverUnsafe testSynchronization = new TestSynchroOverUnsafe();

        ExecutorService executor = Executors.newCachedThreadPool();
        for(int i = 0; i< 10; i++){
            executor.execute(()->{
                int coef = 0;
                int till = (new Random()).nextInt();
                for(int k = 0;k<till;k++){
                    String s = "sasdfasfd";
                }
                testSynchronization.increment();
                for(int j = 0;j<Integer.MAX_VALUE;j++){
                    if(j==Integer.MAX_VALUE-1){
                        coef++;
                        break;
                    }
                    if(coef == 5){
                        j=0;
                    }
                }
                testSynchronization.increment();
            });
        }
        executor.shutdown();

    }

    Unsafe unsafe;
    long offset;
    private volatile long counter = 0;
    public TestSynchroOverUnsafe() {
        unsafe = getUnsafe();
        try {
            offset = unsafe.objectFieldOffset(TestSynchroOverUnsafe.class.getDeclaredField("counter"));
        } catch (NoSuchFieldException nsfex){
            nsfex.printStackTrace();
        }
    }

    public void increment(){
        long before = counter;
        boolean res;
        while (!unsafe.compareAndSwapLong(this, offset, before, before+1)){
            before = counter;
            System.out.println("[Thread name:]"+Thread.currentThread().getName()+" unsuccessful CAS:[before:"+before+"][counter:"+counter+"]");
        }
        System.out.println("[Thread name:]"+Thread.currentThread().getName()+" successful CAS:[before:"+before+"][counter:"+counter+"]");
    }

    private static Unsafe getUnsafe(){
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            return (Unsafe) f.get(null);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
