package com.PClab;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) throws IOException {

        int size=5;
        long startTime, endTime, resultTimeNano;
        double resultTime;
        startTime = System.nanoTime();
        Vector b,e,c,d;
        Matrix mt,ma,md,mz,me,mm;

        //Vector gen=new Vector(0.0,size);
        //gen.genVector();
        //Matrix gen=new Matrix(0.0,size);
        //gen.genMatrix();//Генерируем матрицу и записываем в файл

        b= new Vector(0.0,size);//Объявляем
        c= new Vector(0.0,size);
        d= new Vector(0.0,size);
        e= new Vector(0.0,size);
        ma= new Matrix(0.0,size);
        md= new Matrix(0.0,size);
        mt= new Matrix(0.0,size);
        mz= new Matrix(0.0,size);
        me= new Matrix(0.0,size);
        mm= new Matrix(0.0,size);

        mt=mt.readFromFile();//Считываем с файла
        b=b.readFromFile();
        c=c.readFromFile();
        d=d.readFromFile();
        mz=mz.readFromFile();
        mt=mt.readFromFile();
        md=md.readFromFile();
        me=me.readFromFile();
        mm=mm.readFromFile();

        List<Integer> sizes = resize(size);

        ForkJoinPool forkJoinPool=new ForkJoinPool();
        forkJoinPool.invoke(new MyForkFunc1(0,sizes.get(0),b,e,c,d,mt));
        forkJoinPool.invoke(new MyForkFunc1(sizes.get(0), sizes.get(0) + sizes.get(1),b,e,c,d,mt));
        forkJoinPool.invoke(new MyForkFunc1(sizes.get(0) + sizes.get(1), sizes.get(0) + sizes.get(1)+sizes.get(2),b,e,c,d,mt));
        forkJoinPool.invoke(new MyForkFunc1(sizes.get(0) + sizes.get(1)+sizes.get(2), size,b,e,c,d,mt));
        forkJoinPool.invoke(new MyForkFunc2(0,sizes.get(0),ma,md,mz,me,mt,mm));
        forkJoinPool.invoke(new MyForkFunc2(sizes.get(0), sizes.get(0) + sizes.get(1),ma,md,mz,me,mt,mm));
        forkJoinPool.invoke(new MyForkFunc2(sizes.get(0) + sizes.get(1), sizes.get(0) + sizes.get(1)+sizes.get(2),ma,md,mz,me,mt,mm));
        forkJoinPool.invoke(new MyForkFunc2(sizes.get(0) + sizes.get(1)+sizes.get(2), size,ma,md,mz,me,mt,mm));
        forkJoinPool.shutdown();



        endTime = System.nanoTime();
        System.out.println("Все потоки завершили свою работу.");
        resultTimeNano=endTime-startTime;
        resultTime = (double) resultTimeNano/1_000_000_000;
        System.out.println("Время выполнения программы: " + resultTime+" cек.");
    }
    private static List<Integer> resize(int size) {
        int[] sizes = new int[4];
        for (int i = 0; i < 4; i++)
            sizes[i] = size / 4;
        size %= 4;
        int index = 0;
        while (size > 0) {
            sizes[index]++;
            size--;

            if (index == 3)
                index = 0;
            else
                index++;
        }

        List<Integer> l = new ArrayList<>();
        for (int size1 : sizes) {
            l.add(size1);
        }
        return l;
    }
}
