package com.PClab;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

class Func1 extends CreateOperands {

    private CyclicBarrier cyclicBarrier;

    Func1(int size,CyclicBarrier cyclicBarrier) {
        super(size);
        this.cyclicBarrier=cyclicBarrier;
        start();
    }

    Thread createRunnable() {
        return new Thread(() -> {

            //E=D*MT+max(B)*C
            System.out.println("Поток " + Thread.currentThread().getName() + " начат");

            c.multiNumber(b.max());
            e=d.mullVM(mt);
            e=e.sum(c);
            e.print();
            e.writeToFile();

            try {
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException ex) {
                ex.printStackTrace();
            }
            System.out.println("Поток " + Thread.currentThread().getName() + " закончен");
        });
    }
}
