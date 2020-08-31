package com.PClab;

abstract class CreateOperands extends Thread{

    //E=D*MT+max(B)*C
    //MА=MD*(MT+MZ)-ME*MM
    int size;
    final Matrix mt;
    Matrix ma,md,mz,me,mm;
    Vector b,e,c,d;

    CreateOperands(int size) {
        this.size = size;
        mt = new Matrix(1,this.size);
        ma = new Matrix(1,this.size);
        md = new Matrix(1,this.size);
        mz = new Matrix(1,this.size);
        me = new Matrix(1,this.size);
        mm = new Matrix(1,this.size);
        b = new Vector(1,this.size);
        e = new Vector(1,this.size);
        c = new Vector(1,this.size);
        d = new Vector(1,this.size);
    }
}