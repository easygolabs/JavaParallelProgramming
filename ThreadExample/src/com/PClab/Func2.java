package com.PClab;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

class Func2 extends CreateOperands {

    private CyclicBarrier cyclicBarrier;

    Func2(int size,CyclicBarrier cyclicBarrier) {
        super(size);
        this.cyclicBarrier=cyclicBarrier;
        start();
    }

    Thread createRunnable() {
        return new Thread(() -> {
            //MА=MD*(MT+MZ)-ME*MM
            System.out.println("Поток " + Thread.currentThread().getName() + " начат");

            mz=mt.sum(mz);
            md=md.mul(mz);
            me=me.mul(mm);
            ma=md.sub(me);

            try {
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException ex) {
                ex.printStackTrace();
            }

            ma.print();
            ma.writeToFile();

            System.out.println("Поток " + Thread.currentThread().getName() + " закончен");
        });
    }
}
