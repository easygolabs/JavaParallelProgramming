package com.PClab;

import java.util.concurrent.RecursiveAction;

class MyForkFunc2 extends RecursiveAction {

    private int startSize, endSize;
    private Matrix mt,ma,md,mz,me,mm;

    MyForkFunc2(int startSize, int endSize, Matrix ma, Matrix md, Matrix mz, Matrix me, Matrix mt, Matrix mm) {
        this.startSize = startSize;
        this.endSize = endSize;
        this.ma = ma;
        this.md = md;
        this.mz = mz;
        this.me = me;
        this.mt = mt;
        this.mm = mm;
    }

    @Override
    protected void compute() {
        //MА=MD*(MT+MZ)-ME*MM
        System.out.println("Поток " + Thread.currentThread().getName() + " начат");
        mt=md.mulKahan(mt,startSize,endSize);
        mz=md.mulKahan(mz,startSize,endSize);
        mt=mt.sum(mz,startSize,endSize);
        me=me.mulKahan(mm,startSize,endSize);
        ma=mt.sub(me,startSize,endSize);
        ma.print(startSize,endSize);//Выводим на основной экран
        ma.writeToFile("matrixRes.txt");//Сохраняем в файл
        System.out.println("Поток " + Thread.currentThread().getName() + " закончен");
    }
}
