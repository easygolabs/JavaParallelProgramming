package com.PClab;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

class Func2 extends CreateOperands implements Callable<String> {

    private BlockingQueue<String> queue;
    Func2(int size, BlockingQueue<String> queue) {
        super(size);
        this.queue=queue;
    }

    @Override
    public String call() throws IOException, InterruptedException {
        //MА=MD*(MT+MZ)-ME*MM
        System.out.println("Поток " + Thread.currentThread().getName() + " начат");
        Matrix ma,md,mt,mz,me,mm;
        md= new Matrix(0.0,size);//Объявляем
        mt= new Matrix(0.0,size);
        mz= new Matrix(0.0,size);
        me= new Matrix(0.0,size);
        mm= new Matrix(0.0,size);

        Matrix gen=new Matrix(0.0,size);
        gen.genMatrix();//Генерируем матрицу и записываем в файл

        mz=mz.readFromFile();//Считываем данные с файла
        mt=mt.readFromFile();
        md=md.readFromFile();
        me=me.readFromFile();
        mm=mm.readFromFile();

        mz=mt.sum(mz);
        md=md.mulKahan(mz);
        me=me.mulKahan(mm);
        ma=md.sub(me);
        ma.print();//Выводим на основной экран
        ma.writeToFile("matrixRes.txt");//Сохраняем в файл

        System.out.println(Thread.currentThread().getName()+" ПРИНЯЛ СТРОКУ "+queue.take());

        System.out.println("Поток " + Thread.currentThread().getName() + " закончен");
        return "Поток Func2 выполнился без ошибок и вернул эту строку!";
    }
}
