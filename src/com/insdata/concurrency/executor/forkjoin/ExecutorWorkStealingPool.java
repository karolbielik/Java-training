package com.insdata.concurrency.executor.forkjoin;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorWorkStealingPool {
    public static void main(String[] args) {
        ExecutorService service = Executors.newWorkStealingPool();
    }
}
