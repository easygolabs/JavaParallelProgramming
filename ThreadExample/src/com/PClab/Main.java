package com.PClab;

import java.util.concurrent.CyclicBarrier;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        int n = 100;
        long startTime, endTime, resultTimeNano;
        double resultTime;
        startTime = System.nanoTime();
        CyclicBarrier cyclicBarrier=new CyclicBarrier(2,new Print());
        Func1 func1=new Func1(n,cyclicBarrier);
        Func2 func2=new Func2(n,cyclicBarrier);
        Thread thread1=func1.createRunnable();
        Thread thread2=func2.createRunnable();
        //thread1.setPriority(5);
        //thread2.setPriority(1);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        endTime = System.nanoTime();
        System.out.println("Все потоки завершили свою работу.");
        resultTimeNano=endTime-startTime;
        resultTime = (double) resultTimeNano/1_000_000_000;
        System.out.println("Время выполнения программы: " + resultTime+" cек.");
    }
}
