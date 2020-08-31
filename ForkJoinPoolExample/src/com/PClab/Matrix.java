package com.PClab;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Matrix {

    private int size;
    private Vector<Vector<Double>> v;

    Matrix(double value, int size) {
        this.size = size;
        this.v = new Vector<>(size);

        for (int i = 0; i < size; i++) {
            Vector<Double> vector = new Vector<>(size);

            for (int j = 0; j < size; j++)
                vector.add(value);
            v.add(vector);
        }
    }

    private int size() {
        return this.size;
    }

    Vector<Double> get(int index) {
        return this.v.get(index);
    }

    void genMatrix(){
        Matrix matrix = new Matrix(0.0, this.size);
        for (int i = 0; i < this.size; i++)
            for (int j = 0; j < this.size(); j++) {
                matrix.get(i).add(j, ThreadLocalRandom.current().nextDouble(0.0, 1000.0));
            }
        matrix.writeToFile("matrix.txt");
    }
    void print(int from,int to) {
        Lock lock = new ReentrantLock();
        lock.lock();
        for (int i = from; i < to; i++) {
            for (int j = 0; j < this.size; j++) {
                System.out.print(this.v.get(i).get(j) + " ");
            }
            System.out.println();
        }
        lock.unlock();
    }

    Matrix sum(Matrix matrix,int from, int to) {

        Matrix answer = new Matrix(0.0, this.size);

        for (int i = from; i < to; i++)
            for (int j = 0; j < answer.size(); j++) {
                answer.get(i).add(j, this.get(i).get(j) + matrix.get(i).get(j));
            }
        return answer;
    }

    Matrix sub(Matrix matrix,int from,int to) {

        Matrix answer = new Matrix(0.0, this.size);

        for (int i = from; i < to; i++)
            for (int j = 0; j < answer.size(); j++) {
                answer.get(i).add(j, this.get(i).get(j) - matrix.get(i).get(j));
            }
        return answer;
    }

    void writeToFile(String string){
        try(FileWriter writer = new FileWriter(string))
        {
            for (int i = 0; i < this.size; i++) {
                for (int j = 0; j < this.size; j++) {
                    writer.write(this.v.get(i).get(j) + " ");
                }
                writer.write("\n");
            }
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    private double sumAlgorithmKahan(Vector vector)
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

    Matrix mulKahan(Matrix matrix,int from,int to) {

        com.PClab.Vector vector=new com.PClab.Vector(0.0,this.size);
        Matrix answer = new Matrix(0.0, this.size);

        for (int i = from; i < to; i++) {
            for (int j = 0; j < answer.size; j++) {
                for (int k = 0; k < answer.size; k++) {
                    vector.v.add(k,this.get(i).get(k) * matrix.get(k).get(j));
                }
                answer.get(i).add(j,sumAlgorithmKahan(vector));
            }
        }
        return answer;
    }

    Matrix readFromFile() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("matrix.txt"));
        List<String> lines = new ArrayList<>();
        while (br.ready()) {
            lines.add(br.readLine());
        }
        int matrixWidth = lines.get(0).split(" ").length;
        double matrixHeight = lines.size();
        Matrix matrix = new Matrix(matrixHeight,matrixWidth);

        for (int i = 0; i < matrixHeight; i++) {
            for (int j = 0; j < matrixWidth; j++) {
                String[] line = lines.get(i).split(" ");
                matrix.get(i).add(j,Double.parseDouble(line[j]));
            }
        }
        return matrix;
    }
}