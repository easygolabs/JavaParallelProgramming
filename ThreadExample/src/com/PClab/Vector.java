package com.PClab;

import java.io.FileWriter;
import java.io.IOException;

class Vector {

    private java.util.Vector<Integer> v;
    private int size;

    Vector(int value, int size) {
        this.size = size;
        this.v = new java.util.Vector<>(size);
        for (int i = 0; i < size; i++)
            v.add(value);
    }

    private int get(int index) {
        return this.v.get(index);
    }

    synchronized void print() {
        for (int i = 0; i < this.size; i++) {
            if (this.v.get(i) != 0) {
                System.out.print(this.v.get(i) + " ");
            }
        }
        System.out.println();
    }

    Vector sum(Vector vector) {
        Vector answer = new Vector(0, this.size);
        for (int i = 0; i < this.size; i++)
            answer.v.add(i,this.get(i) + vector.get(i));
        return answer;
    }

    Vector mullVM(Matrix matrix)
    {
        int tmp;
        Vector answer = new Vector(0,this.size);
        for (int i = 0; i < this.size; i++) {
            tmp = 0;
            for (int j = 0; j < this.size; j++) {
                tmp += this.get(i) * matrix.get(i).get(j);
            }
            answer.v.add(i, tmp);
        }
        return answer;
    }

    void multiNumber(int number) {
        for (int i = 0; i < this.size; i++) {
            this.v.add(i,this.v.get(i)*number);
        }
    }

    int max() {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < this.size; i++) {
            if (this.get(i) > max)
                max = this.get(i);
        }
        return max;
    }

    void writeToFile(){
        try(FileWriter writer = new FileWriter("vector.txt"))
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
}
