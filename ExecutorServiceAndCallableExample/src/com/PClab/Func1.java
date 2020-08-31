package com.PClab;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

class Func1 extends CreateOperands implements Callable<String> {

    private BlockingQueue<String> queue;
    Func1(int size, BlockingQueue<String> queue) {
        super(size);
        this.queue=queue;
    }

    @Override
    public String call() throws IOException {
        //E=D*MT+max(B)*C
        System.out.println("Поток " + Thread.currentThread().getName() + " начат");
        Vector b,e,c,d;
        Matrix mt;

        //genVector
        Vector gen=new Vector(0.0,size);
        gen.genVector();

        b= new Vector(0.0,size);//Объявляем
        c= new Vector(0.0,size);
        d= new Vector(0.0,size);
        mt= new Matrix(0.0,size);

        mt=mt.readFromFile();//Считываем с файла
        b=b.readFromFile();
        c=c.readFromFile();
        d=d.readFromFile();

        c.multiNumber(b.max());
        e=d.mulKahan(mt);
        e=e.sum(c);
        e.print();//Выводим на экран
        e.writeToFile("vectorRes.txt");//Сохраняем в файл

        queue.add("Название потока который передал: "+Thread.currentThread().getName());

        System.out.println("Поток " + Thread.currentThread().getName() + " закончен");
        return "Поток Func1 выполнился без ошибок и вернул эту строку!";
    }
}
