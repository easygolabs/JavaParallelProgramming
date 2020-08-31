package com.PClab;

import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        int n = 5;
        long startTime, endTime, resultTimeNano;
        double resultTime;
        BlockingQueue<String> queue = new PriorityBlockingQueue<>();
        startTime = System.nanoTime();

        Func1 func1=new Func1(n,queue);
        Func2 func2=new Func2(n,queue);

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        System.out.println(executorService.submit(func1).get());
        System.out.println(executorService.submit(func2).get());
        executorService.shutdown();

        endTime = System.nanoTime();
        System.out.println("Все потоки завершили свою работу.");
        resultTimeNano=endTime-startTime;
        resultTime = (double) resultTimeNano/1_000_000_000;
        System.out.println("Время выполнения программы: " + resultTime+" cек.");
    }
}
