package com.PClab;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

class Matrix {

    private int size;
    private Vector<Vector<Integer>> v;

    Matrix(int value, int size) {
        this.size = size;
        this.v = new Vector<>(size);

        for (int i = 0; i < size; i++) {
            Vector<Integer> vector = new Vector<>(size);

            for (int j = 0; j < size; j++)
                vector.add(value);
            v.add(vector);
        }
    }

    private int size() {
        return this.size;
    }

    Vector<Integer> get(int index) {
        return this.v.get(index);
    }

    synchronized void print() {
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                System.out.print(this.v.get(i).get(j) + " ");
            }
            System.out.println();
        }
    }

    Matrix mul(Matrix matrix) {

        Matrix answer = new Matrix(0, this.size);

        for (int i = 0; i < this.size; i++)
            for (int j = 0; j < answer.size(); j++) {
                int element = 0;
                for (int k = 0; k < answer.size(); k++)
                    element += this.get(i).get(k) * matrix.get(k).get(j);
                answer.get(i).add(j, element);
            }
        return answer;
    }

    Matrix sum(Matrix matrix) {

        Matrix answer = new Matrix(0, this.size);

        for (int i = 0; i < this.size; i++)
            for (int j = 0; j < answer.size(); j++) {
                answer.get(i).add(j, this.get(i).get(j) + matrix.get(i).get(j));
            }
        return answer;
    }

    Matrix sub(Matrix matrix) {

        Matrix answer = new Matrix(0, this.size);

        for (int i = 0; i < this.size; i++)
            for (int j = 0; j < answer.size(); j++) {
                answer.get(i).add(j, this.get(i).get(j) - matrix.get(i).get(j));
            }
        return answer;
    }

    void writeToFile(){
        try(FileWriter writer = new FileWriter("matrix.txt"))
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
}