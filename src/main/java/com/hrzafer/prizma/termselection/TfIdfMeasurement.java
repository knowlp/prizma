package com.hrzafer.prizma.termselection;

public class TfIdfMeasurement implements Measurement {

    @Override
    public double measure(int a, int b, int c, int d) {
        return a/(double)(a+b) * Math.log(a + b + c + d)/(a + c);
    }
}
