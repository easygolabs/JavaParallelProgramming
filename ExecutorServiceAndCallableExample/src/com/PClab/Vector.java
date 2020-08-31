package com.PClab;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Vector extends java.util.Vector {

    java.util.Vector<Double> v;
    private int size;

    Vector(double value, int size) {
        this.size = size;
        this.v = new java.util.Vector<>(size);
        for (int i = 0; i < size; i++)
            v.add(i,value);
    }

    public Object get(int index) {
        return this.v.get(index);
    }

    void print() {
        Lock lock = new ReentrantLock();
        lock.lock();
        for (int i = 0; i < this.size; i++) {
            if (this.v.get(i) != 0) {
                System.out.print(this.v.get(i) + " ");
            }
        }
        System.out.println();
        lock.unlock();
    }

    void genVector(){
        Vector answer = new Vector(0.0, this.size);
        for (int i = 0; i < size; i++) {
            answer.v.add(i, ThreadLocalRandom.current().nextDouble(0.0, 1000.0));
        }
        answer.writeToFile("vector.txt");
    }

    Vector sum(Vector vector) {
        Vector answer = new Vector(0.0, this.size);
        for (int i = 0; i < this.size; i++)
            answer.v.add(i,(double)this.get(i) + (double)vector.get(i));
        return answer;
    }

    Vector mulKahan(Matrix matrix) {

        com.PClab.Vector vector=new com.PClab.Vector(0.0,this.size);
        Vector answer = new Vector(0.0, this.size);

        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < answer.size; j++) {
                    vector.v.add(j,(double)this.get(i) * matrix.get(i).get(j));
            }
            answer.v.add(i,sumAlgorithmKahan(vector));
        }
        return answer;
    }

    private double sumAlgorithmKahan(java.util.Vector vector)
    {
        double sum, correction, correctedNext, newSum;
        sum = (double)vector.get(0);
        correction = 0.0;
        for (int i = 1; i < this.size; i++)
        {
            correctedNext = (double)vector.get(i) - correction;
            newSum = sum + correctedNext;
            correction = (newSum - sum) - correctedNext;
            sum = newSum;
        }
        return sum;
    }

    void multiNumber(double number) {
        for (int i = 0; i < this.size; i++) {
            this.v.add(i,this.v.get(i)*number);
        }
    }

    double max() {
        double max = Integer.MIN_VALUE;
        for (int i = 0; i < this.size; i++) {
            if ((double)this.get(i) > max)
                max = (double)this.get(i);
        }
        return max;
    }

    void writeToFile(String string){
        try(FileWriter writer = new FileWriter(string))
        {
            for(int i=0;i<this.size;i++){
                writer.write(this.get(i)+" ");
            }
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    Vector readFromFile() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("vector.txt"));
        List<String> lines = new ArrayList<>();
        lines.add(br.readLine());
        int matrixWidth = lines.get(0).split(" ").length;
        Vector vector = new Vector(0.0,this.size);

        for (int i = 0; i < matrixWidth; i++) {
            String[] line = lines.get(0).split(" ");
            vector.v.add(i,Double.parseDouble(line[i]));
        }
        return vector;
    }
}
