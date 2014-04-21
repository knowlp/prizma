package com.hrzafer.prizma.termselection;

/**
 * Created with IntelliJ IDEA.
 * User: hrzafer
 * Date: 28.03.2014
 * Time: 15:08
 * To change this template use File | Settings | File Templates.
 */
public class TermDistribution{

    private int a; //Number of Documents in this category that contains this term. Bu değer nGram'dan geliyor
    private int b; //Number of Documents in this category not contains this term. Bunu da a üzerinden hesaplamak kolay
    private int c; //Number of Documents out of this category contains this term
    private int d; //Number of Documents out of this category not contains this term

    public TermDistribution(int a, int b, int c, int d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public double getMeasurementValue(){
        return TermSelector.MEASUREMENT.measure(a,b,c,d);
    }

    @Override
    public String toString() {
        return a + " " + b + " "+ c + " " +d + " " + getMeasurementValue();
    }



}

