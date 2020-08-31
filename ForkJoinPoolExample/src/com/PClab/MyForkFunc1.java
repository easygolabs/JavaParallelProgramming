package com.PClab;

import java.util.concurrent.RecursiveAction;

class MyForkFunc1 extends RecursiveAction {

    private int startSize, endSize;
    private Vector b, e, c, d;
    private Matrix mt;

    MyForkFunc1(int startSize, int endSize, Vector b, Vector e, Vector c, Vector d, Matrix mt) {
        this.startSize = startSize;
        this.endSize = endSize;
        this.b = b;
        this.e = e;
        this.c = c;
        this.d = d;
        this.mt = mt;
    }

    @Override
    protected void compute() {
            //E=D*MT+max(B)*C
            System.out.println("Поток " + Thread.currentThread().getName() + " начат");

            c.multiNumber(b.max(), startSize, endSize);
            e = d.mulKahan(mt, startSize, endSize);
            e = e.sum(c, startSize, endSize);
            e.print();//Выводим на экран
            e.writeToFile("vectorRes.txt");//Сохраняем в файл

            System.out.println("Поток " + Thread.currentThread().getName() + " закончен");
    }
}
